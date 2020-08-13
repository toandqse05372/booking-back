package com.capstone.booking.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class VerificationToken extends BaseEntity{

    private String confirmationToken;

    private Long uid;

    private boolean used = false;
}