package com.capstone.booking.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode
public class OrderDTO extends BaseDTO{
    private int totalTicket;
    private int ticketTypeId;
    private Long userId;
    private String firstName;
    private String lastName;
    private String mail;
    private String phoneNumber;
    private String status;
    private int totalPayment;
    private Date purchaseDay;
}
