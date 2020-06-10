package com.capstone.booking.entity.dto;

import com.capstone.booking.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

//form nhận request tk fb
@Getter
@Setter
public class FBLoginDTO {

    private String accessToken;

    private String email;

    private String name;

//    public User convertToUser(){
//        return new User(displayName);
//    }
}