package com.capstone.booking.api;
import com.capstone.booking.entity.dto.FBLoginDTO;
import com.capstone.booking.entity.dto.UserDTO;
import com.capstone.booking.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;

//authentication api
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    //normal login api
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO user,
                                   @RequestParam(value = "page", required = false) String page) {
        return authService.findByEmail(user, page);
    }

    //login fb api
    @PostMapping("/login/fb")
    public ResponseEntity<?> loginFb(@RequestBody FBLoginDTO fbForm) throws IOException {
        return authService.loginFb(fbForm);
    }

    //logout api
    @PostMapping(value = "/login/logout")
    public ResponseEntity<?> logout(@RequestHeader(value = "Authorization") String token){
        return authService.logout(token);
    }

    //check token token api
    @PostMapping(value = "/login/checkToken")
    public ResponseEntity<?> checkToken(@RequestHeader(value = "Authorization") String token){
        return authService.checkToken(token);
    }

}
