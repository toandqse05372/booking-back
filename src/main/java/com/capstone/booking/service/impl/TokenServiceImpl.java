package com.capstone.booking.service.impl;

import com.capstone.booking.entity.Token;
import com.capstone.booking.repository.TokenRepository;
import com.capstone.booking.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    //create token in db
    public ResponseEntity<?> createToken(Token token) {
        return ResponseEntity.ok(tokenRepository.saveAndFlush(token));

    }

    //find token in db
    @Override
    public Token findByToken(String tokenStr) {
        Token token = tokenRepository.findByToken(tokenStr);
        return token;
    }

}
