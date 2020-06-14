package com.capstone.booking.repository;

import com.capstone.booking.entity.PaymentMethods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodsRepository extends JpaRepository<PaymentMethods, Long> {
}
