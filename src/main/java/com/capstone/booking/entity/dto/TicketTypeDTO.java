package com.capstone.booking.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;


@Data
@EqualsAndHashCode
public class TicketTypeDTO extends BaseDTO{
    private String typeKey;
    private String typeName;
    private String effectiveTime;
    private String ticketDescription;
    private String reservationInfo;
    private String cancelPolicy;
    private String conversionMethod;
    private int price;
    private String userObject;
    private Set<String> gameName;
}
