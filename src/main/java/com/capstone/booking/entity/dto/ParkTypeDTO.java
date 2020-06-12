package com.capstone.booking.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode
public class ParkTypeDTO extends BaseDTO{
    private String parkTypeName;
    //private Set<String> parkName;
}
