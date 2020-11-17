package com.readingisgood.readingisgood.order.repository;

import com.readingisgood.readingisgood.order.entity.OrderDetail;
import com.readingisgood.readingisgood.order.model.OrderDetailResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query("SELECT " +
            " new com.readingisgood.readingisgood.order.model.OrderDetailResponse( od.id, " +
            " od.quantity, od.price, od.totalPrice, od.book.name, od.book.author) " +
            "FROM " +
            " OrderDetail od " +
            " WHERE od.status = 'ACTIVE' and od.order.id = :orderId")
    List<OrderDetailResponse> findByOrderId(Long orderId);

    @Query("SELECT SUM(od.quantity) FROM OrderDetail od WHERE od.book.id = :bookId")
    Long getOrderCountByBookId(Long bookId);

    List<OrderDetail> findByOrder_Id(Long orderId);
}
