package com.capstone.booking.repository;

import com.capstone.booking.entity.Order;
import com.capstone.booking.repository.customRepository.OrderRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
}
