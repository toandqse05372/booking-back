package com.capstone.booking.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class OrderItemDTO extends BaseDTO{
    private Long visitorTypeId;
    private String visitorTypeName;
    private String visitorTypeKey;
    private int quantity;
}
