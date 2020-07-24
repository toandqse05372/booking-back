package com.capstone.booking.entity.dto;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class UserDTO extends BaseDTO{
    private String password;
    private String firstName;
    private String lastName;
    private String mail;
    private Date dob;
    private String phoneNumber;
    private String status;
    private Set<String> roleKey;
    private String userType;
    private String avatarLink;
}
