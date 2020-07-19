package com.capstone.booking.service;


import com.capstone.booking.entity.Token;
import org.springframework.http.ResponseEntity;

public interface TokenService {

    //save token into db
    ResponseEntity<?> createToken(Token token);

    //find token in db
    Token findByToken(String token);
}
