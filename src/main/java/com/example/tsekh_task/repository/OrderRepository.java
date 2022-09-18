package com.example.tsekh_task.repository;

import com.example.tsekh_task.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query("select o from orders o where o.washCompany.id =:companyId and o.isActive = :isActive " +
            "and o.date between :dateFrom and :dateTo")
    List<Order> getOrders(@Param("companyId") Long companyId,
                          @Param("isActive") boolean isActive,
                          @Param("dateFrom") Date dateFrom,
                          @Param("dateTo") Date dateTo, Pageable page);

    @Query("select o from orders o where o.washCompany.id =:companyId " +
            "and o.date between :dateFrom and :dateTo")
    List<Order> getOrders(@Param("companyId") Long companyId,
                          @Param("dateFrom") Date dateFrom,
                          @Param("dateTo") Date dateTo);

    @Query("select o from orders o  inner join o.washers washer where" +
            " washer.id =:washerId and o.isActive = :isActive " +
            "and o.date between :dateFrom and :dateTo")
    List<Order> getWasherOrders(@Param("washerId") Long washerId,
                                @Param("isActive") boolean isActive,
                                @Param("dateFrom") Date dateFrom,
                                @Param("dateTo") Date dateTo,
                                Pageable page);
}
