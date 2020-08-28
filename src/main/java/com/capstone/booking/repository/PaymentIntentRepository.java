package com.capstone.booking.repository;

import com.capstone.booking.entity.PaymentIntent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentIntentRepository extends JpaRepository<PaymentIntent, Long> {
    PaymentIntent findByOid(long oid);
}
