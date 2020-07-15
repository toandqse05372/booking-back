package com.capstone.booking.repository;

import com.capstone.booking.entity.Order;
import com.capstone.booking.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllByOrder(Order order);
}