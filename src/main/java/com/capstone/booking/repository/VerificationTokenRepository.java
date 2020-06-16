package com.capstone.booking.repository;

import com.capstone.booking.entity.User;
import com.capstone.booking.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByConfirmationToken(String verificationToken);
}