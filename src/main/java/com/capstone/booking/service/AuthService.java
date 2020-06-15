package com.capstone.booking.service;

import com.capstone.booking.entity.dto.FBLoginDTO;
import com.capstone.booking.entity.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

public interface AuthService {
    ResponseEntity<?> findByEmail(UserDTO userDTO, String page);

    ResponseEntity<?> loginFb(@RequestBody FBLoginDTO fbForm) throws IOException;
}
