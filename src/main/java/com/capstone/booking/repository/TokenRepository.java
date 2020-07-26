package com.capstone.booking.repository;

import com.capstone.booking.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TokenRepository extends JpaRepository<Token, Long> {

    //find jwt token saved in db
    Token findByToken(String token);

    Token findByUserId(long uid);
}
