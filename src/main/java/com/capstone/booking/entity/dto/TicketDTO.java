package com.capstone.booking.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode
public class TicketDTO extends BaseDTO{
    private Date redemptionDate;
    private String code;
    private Long ticketTypeId;
    //private Long orderId;
}