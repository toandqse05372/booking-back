package com.capstone.booking.service;

import com.capstone.booking.entity.dto.FBLoginDTO;
import com.capstone.booking.entity.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

public interface AuthService {
    //find by email
    ResponseEntity<?> findByEmail(UserDTO userDTO, String page);

    // login or register by fb account
    ResponseEntity<?> loginFb(@RequestBody FBLoginDTO fbForm) throws IOException;

    //logout site
    ResponseEntity<?> logout(String token) ;

    //check login token
    ResponseEntity<?> checkToken(String tokenStr);
}
