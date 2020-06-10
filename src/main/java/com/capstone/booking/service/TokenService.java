package com.capstone.booking.service;


import com.capstone.booking.entity.Token;
import org.springframework.http.ResponseEntity;

public interface TokenService {

    ResponseEntity<?> createToken(Token token);

    Token findByToken(String token);
}
