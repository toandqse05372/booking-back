package com.capstone.booking.entity.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PaymentDTO {
    String stripeToken;
    String payDate;
    String mail;
    String name;
    Long totalPayment;
    Long methodKey;
}
