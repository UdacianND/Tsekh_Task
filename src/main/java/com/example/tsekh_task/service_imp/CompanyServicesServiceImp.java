package com.example.tsekh_task.service_imp;

import com.example.tsekh_task.base_service.CompanyServicesService;
import com.example.tsekh_task.entity.Service;
import com.example.tsekh_task.entity.WashCompany;
import com.example.tsekh_task.entity.user.User;
import com.example.tsekh_task.payload.request.AddServiceDto;
import com.example.tsekh_task.payload.request.UpdateServiceDto;
import com.example.tsekh_task.payload.response.ServiceDto;
import com.example.tsekh_task.repository.ServiceRepository;
import com.example.tsekh_task.repository.WashCompanyRepository;
import com.example.tsekh_task.utils.AuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class CompanyServicesServiceImp implements CompanyServicesService {

    private final AuthorizationFilter authorizationFilter;
    private final WashCompanyRepository washCompanyRepository;
    private final ServiceRepository serviceRepository;

    @Override
    public List<ServiceDto> getServices(Long companyId, String searchName, int pageNumber) {
        Pageable page = PageRequest.of(pageNumber, 10);
        return serviceRepository.getServiceByName(searchName, companyId, page)
                .stream().map(ServiceDto::new).toList();
    }

    @Override
    public ServiceDto getService(Long id) {
        Service service = serviceRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(id, "Service"));
        return new ServiceDto(service);
    }

    @Override
    public void addService(Long companyId, AddServiceDto serviceDto) {
        WashCompany washCompany = washCompanyRepository.findById(companyId).orElseThrow(
                () -> new ObjectNotFoundException(companyId, "washCompany"));
        authorizationFilter.checkUserAccessToCompany(companyId,getPrincipal().getId());
        Service service = new Service(
                serviceDto.getName(),
                serviceDto.getDuration(),
                serviceDto.getPrice(),
                washCompany);
        serviceRepository.save(service);
    }

    @Override
    public void updateService(Long id, UpdateServiceDto serviceDto) {
        Service service = serviceRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(id, "service"));
        service.setName(serviceDto.getName());
        service.setDuration(serviceDto.getDuration());
        service.setPrice(serviceDto.getPrice());
        serviceRepository.save(service);
    }

    private User getPrincipal(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
