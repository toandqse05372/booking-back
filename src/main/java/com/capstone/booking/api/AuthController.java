package com.capstone.booking.api;
import com.capstone.booking.entity.dto.FBLoginDTO;
import com.capstone.booking.entity.dto.UserDTO;
import com.capstone.booking.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    //login tk bt
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO user) {
        return authService.findByEmail(user);
    }

    //login tk fb
    @PostMapping("/login/fb")
    public ResponseEntity<?> loginFb(@RequestBody FBLoginDTO fbForm) throws IOException {
        return authService.loginFb(fbForm);
    }

}
