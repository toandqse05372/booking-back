package com.capstone.booking.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class VisitorTypeDTO extends BaseDTO{
    private String typeName;
    private String typeKey;
    private int price;
    private TicketTypeDTO ticketType;
}
