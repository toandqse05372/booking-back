package com.capstone.booking.repository;

import com.capstone.booking.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
    //find password reset token
    PasswordResetToken findByToken(String token);
}