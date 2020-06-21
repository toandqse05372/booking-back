package com.capstone.booking.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode
public class OrderDTO extends BaseDTO{
    private int totalTicket;
    private int ticketTypeId;
    private UserDTO user;
    private String status;
    private int totalPayment;
    private Date purchaseDay;
}
