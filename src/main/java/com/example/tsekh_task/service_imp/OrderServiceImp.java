package com.example.tsekh_task.service_imp;

import com.example.tsekh_task.base_service.OrderService;
import com.example.tsekh_task.entity.Order;
import com.example.tsekh_task.entity.Service;
import com.example.tsekh_task.entity.WashCompany;
import com.example.tsekh_task.entity.Washer;
import com.example.tsekh_task.entity.user.User;
import com.example.tsekh_task.payload.request.AddOrderDto;
import com.example.tsekh_task.payload.request.UpdateOrderDto;
import com.example.tsekh_task.payload.response.AnalyticsDto;
import com.example.tsekh_task.payload.response.OrderDto;
import com.example.tsekh_task.repository.*;
import com.example.tsekh_task.utils.AuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {
    private final OrderRepository orderRepository;
    private final ServiceRepository serviceRepository;
    private final WasherRepository washerRepository;
    private final WashCompanyRepository washCompanyRepository;
    private final AuthorizationFilter authorizationFilter;

    @Override
    public List<OrderDto> getOrders(
            Long companyId,
            boolean isActive,
            Date dateFrom,
            Date dateTo,
            int pageNumber)
    {
        Pageable page = PageRequest.of(pageNumber, 10);
        List<Order> orders = orderRepository
                .getOrders(companyId, isActive, dateFrom, dateTo, page);
        return orders.stream().map(OrderDto::of).toList();
    }

    @Override
    public OrderDto getOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, "order"));
        return OrderDto.of(order);
    }

    @Override
    public void addOrder(Long companyId, AddOrderDto orderDto) {
        WashCompany washCompany = washCompanyRepository.findById(companyId)
                .orElseThrow(() -> new ObjectNotFoundException(companyId, "washCompany"));
        Set<Service> services = getServices(orderDto.getServiceIds());
        Set<Washer> washers = getWashers(orderDto.getWasherIds());
        Integer price = services.stream().reduce(0, (totalPrice, service) ->
                totalPrice + service.getPrice(), Integer::sum);
        Order order = new Order(services,washers,price,orderDto,getPrincipal().getUsername());
        order.setWashCompany(washCompany);
        orderRepository.save(order);
    }

    @Override
    public void updateOrder(Long orderId, UpdateOrderDto orderDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ObjectNotFoundException(orderId, "order"));
        if(getPrincipal().getUsername().equals(order.getClientName()))
            throw new AccessDeniedException("User "+getPrincipal().getUsername() +
                    " han no access to order " + order.getId());
        order.setCarModel(orderDto.getCarModel());
        order.setCarNumber(orderDto.getCarNumber());
        Set<Service> services = getServices(orderDto.getServiceIds());
        int price = services.stream().reduce(0, (totalPrice, service) ->
                totalPrice + service.getPrice(), Integer::sum);
        order.setPrice(price);
        order.setServices(services);
        order.setWashers(getWashers(orderDto.getWasherIds()));
    }

    @Override
    public AnalyticsDto getAnalytics(Long companyId, Date dateFrom, Date dateTo) {
        authorizationFilter.checkUserAccessToCompany(companyId,getPrincipal().getId());
        List<Order> orders = orderRepository.getOrders(companyId, dateFrom, dateTo);
        Set<Washer> washers = new HashSet<>();
        orders.forEach(order ->
                washers.addAll(order.getWashers()));
        int price = orders.stream().reduce(0, (totalPrice, order) ->
                totalPrice + order.getPrice(), Integer::sum);
        double washersStake = washers.stream().reduce(0, (totalStake, washer) ->
                totalStake + washer.getStake(), Integer::sum);
        return new AnalyticsDto(orders.size(), washers.size(),
                price, washersStake * price * 0.01);
    }

    private Set<Service> getServices(Set<Long> serviceIds){
        return serviceIds.stream().map(
                        serviceId -> serviceRepository.findById(serviceId)
                                .orElseThrow(() -> new ObjectNotFoundException(serviceId, "service")))
                .collect(Collectors.toSet());
    }

    private Set<Washer> getWashers(Set<Long> washerIds){
        return washerIds.stream().map(
                        washerId -> washerRepository.findById(washerId)
                                .orElseThrow(() -> new ObjectNotFoundException(washerId, "service")))
                .collect(Collectors.toSet());
    }

    private User getPrincipal(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
