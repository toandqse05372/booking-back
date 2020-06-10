package com.capstone.booking.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class OpeningHoursDTO extends BaseDTO{
    private String closeHours;
    private String openHours;
    private String weekDay;
}
