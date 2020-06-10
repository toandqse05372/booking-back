package com.capstone.booking.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ParkTypeDTO extends BaseDTO{
    private String parkTypeName;
}
