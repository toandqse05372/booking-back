package com.capstone.booking.repository;

import com.capstone.booking.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    //find token cf saved in db
    VerificationToken findByConfirmationToken(String verificationToken);

    VerificationToken findByUid(Long uid);

    void deleteByUid(Long uid);
}