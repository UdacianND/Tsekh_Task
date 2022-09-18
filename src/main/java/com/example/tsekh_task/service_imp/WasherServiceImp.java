package com.example.tsekh_task.service_imp;

import com.example.tsekh_task.base_service.WasherService;
import com.example.tsekh_task.entity.Order;
import com.example.tsekh_task.entity.WashCompany;
import com.example.tsekh_task.entity.Washer;
import com.example.tsekh_task.entity.user.User;
import com.example.tsekh_task.payload.request.AddWasherDto;
import com.example.tsekh_task.payload.request.UpdateWasherDto;
import com.example.tsekh_task.payload.response.OrderDto;
import com.example.tsekh_task.payload.response.WasherDto;
import com.example.tsekh_task.repository.OrderRepository;
import com.example.tsekh_task.repository.WashCompanyRepository;
import com.example.tsekh_task.repository.WasherRepository;
import com.example.tsekh_task.utils.AuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

import static com.example.tsekh_task.utils.MediaService.checkFileIsImage;

@Service
@RequiredArgsConstructor
public class WasherServiceImp implements WasherService {

    private final WasherRepository washerRepository;
    private final AuthorizationFilter authorizationFilter;
    private final OrderRepository orderRepository;
    private final WashCompanyRepository washCompanyRepository;

    @Override
    public void setWasherAvatar(Long washerId, MultipartFile image) throws Exception {
        Washer washer = washerRepository.findById(washerId)
                    .orElseThrow(()-> new ObjectNotFoundException(washerId,"washer"));
        Long companyId  = washer.getWashCompany().getId();
        authorizationFilter.checkUserAccessToCompany(companyId,getPrincipal().getId());
        checkFileIsImage(image);
        washer.setImage(image.getBytes());
        washerRepository.save(washer);
    }

    @Override
    public List<WasherDto> getWashers(Long companyId, String name, int pageNumber) {
        Pageable page = PageRequest.of(pageNumber, 10);
        authorizationFilter.checkUserAccessToCompany(companyId,getPrincipal().getId());
        return washerRepository.getWashersByName(name,companyId,page)
                .stream().map(WasherDto::new).toList();
    }

    @Override
    public WasherDto getWasher(Long washerId) {
        Washer washer = washerRepository.findById(washerId)
                .orElseThrow(() -> new ObjectNotFoundException(washerId, "washer"));
        Long companyId  = washer.getWashCompany().getId();
        authorizationFilter.checkUserAccessToCompany(companyId,getPrincipal().getId());
        return new WasherDto(washer);
    }

    @Override
    public List<OrderDto> getOrders(Long washerId, boolean isActive, Date dateFrom, Date dateTo, int pageNumber) {
        Pageable page = PageRequest.of(pageNumber, 10);
        List<Order> orders = orderRepository.getWasherOrders(washerId, isActive, dateFrom, dateTo, page);
        return orders.stream().map(OrderDto::of).toList();
    }

    @Override
    public void addWasher(Long companyId, AddWasherDto washerDto) {
        WashCompany washCompany = washCompanyRepository.findById(companyId)
                .orElseThrow(() -> new ObjectNotFoundException(companyId, "washCompany"));
        authorizationFilter.checkUserAccessToCompany(companyId,getPrincipal().getId());
        Washer washer = new Washer(washerDto,washCompany);
        washerRepository.save(washer);
    }

    @Override
    public void updateWasher(Long washerId, UpdateWasherDto washerDto) {
        Washer washer = washerRepository.findById(washerId).orElseThrow(
                () -> new ObjectNotFoundException(washerId, "washer"));
        authorizationFilter.checkUserAccessToCompany(
                washer.getWashCompany().getId(),getPrincipal().getId());
        washer.setName(washerDto.getName());
        washer.setStake(washerDto.getStake());
        washer.setTelephoneNumber(washerDto.getTelephoneNumber());
        washerRepository.save(washer);
    }

    public User getPrincipal(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
