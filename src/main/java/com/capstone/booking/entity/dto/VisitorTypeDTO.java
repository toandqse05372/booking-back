package com.capstone.booking.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class VisitorTypeDTO extends BaseDTO{
    private String typeName;
    private String typeKey;
    private Long ticketTypeId;
    private int price;
    private boolean isBasicType;
    private int remaining;
    private String status;
}
