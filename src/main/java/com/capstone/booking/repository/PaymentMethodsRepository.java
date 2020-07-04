package com.capstone.booking.repository;

import com.capstone.booking.entity.PaymentMethods;
import com.capstone.booking.repository.customRepository.PaymentMethodsCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodsRepository extends JpaRepository<PaymentMethods, Long>, PaymentMethodsCustom {
    PaymentMethods findByMethodName(String name);
}
