package com.example.msorder.repository;

import com.example.msorder.dto.OrderSummary;
import com.example.msorder.entity.Order;
import com.example.msorder.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);

    Optional<Order> findByOrderNumber(String orderNumber);

    default void updateOrderStatus(String orderNumber, OrderStatus status) {
        Order order = this.findByOrderNumber(orderNumber).orElseThrow();
        order.setStatus(status);
        this.save(order);
    }

    @Query(
            """
        select new com.example.msorder.dto.OrderSummary(o.orderNumber, o.status)
        from Order o
        where o.userName = :userName
        """)
    List<OrderSummary> findByUserName(String userName);

    @Query(
            """
        select distinct o
        from Order o left join fetch o.items
        where o.userName = :userName and o.orderNumber = :orderNumber
        """)
    Optional<Order> findByUserNameAndOrderNumber(String userName, String orderNumber);
}
