package com.readingisgood.readingisgood.order.repository;

import com.readingisgood.readingisgood.order.entity.Order;
import com.readingisgood.readingisgood.order.model.OrderResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT " +
            " new com.readingisgood.readingisgood.order.model.OrderResponse( od.id, " +
            " od.orderStatus, od.applicationUser.address.cityName,  " +
            "od.applicationUser.address.townName , od.applicationUser.address.districtName, " +
            "od.applicationUser.address.line ) " +
            "FROM " +
            " Order od " +
            " WHERE od.status = 'ACTIVE' and od.id = :orderId")
    OrderResponse findByOrderId(Long orderId);

    @Query("SELECT " +
            " new com.readingisgood.readingisgood.order.model.OrderResponse( od.id, " +
            " od.orderStatus, od.applicationUser.address.cityName,  " +
            "od.applicationUser.address.townName , od.applicationUser.address.districtName, " +
            "od.applicationUser.address.line ) " +
            "FROM " +
            " Order od " +
            " WHERE od.status = 'ACTIVE' and od.applicationUser.id = :userId")
    List<OrderResponse> findAllByApplicationUserId(Long userId);
}
