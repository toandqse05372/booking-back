package com.capstone.booking.service.impl;

import com.capstone.booking.entity.Token;
import com.capstone.booking.repository.TokenRepository;
import com.capstone.booking.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

//nghiệp vụ token
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public ResponseEntity<?> createToken(Token token) {
        return ResponseEntity.ok(tokenRepository.saveAndFlush(token));

    }

    @Override
    public Token findByToken(String token) {
        Token tokent = tokenRepository.findByToken(token);
        return tokent;
    }

}
