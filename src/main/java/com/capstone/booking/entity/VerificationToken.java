package com.capstone.booking.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class VerificationToken extends BaseEntity{

    private String confirmationToken;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
}