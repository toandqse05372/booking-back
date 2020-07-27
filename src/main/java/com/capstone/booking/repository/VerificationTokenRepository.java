package com.capstone.booking.repository;

import com.capstone.booking.entity.User;
import com.capstone.booking.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    //find token cf saved in db
    VerificationToken findByConfirmationToken(String verificationToken);

    VerificationToken findByUser(User user);

    void deleteByUser(User user);
}