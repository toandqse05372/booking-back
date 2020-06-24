package com.capstone.booking.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;


@Data
@EqualsAndHashCode
public class TicketTypeDTO extends BaseDTO{
    private String typeName;
    private String effectiveTime;
    private String ticketDescription;
    private String reservationInfo;
    private String cancelPolicy;
    private String conversionMethod;
    private Set<Long> gameId;
    private Long placeId;
}
