package com.capstone.booking.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode
public class OpeningHoursDTO extends BaseDTO {
    private String weekDay;
    private Date openHours;
    private Date closeHours;

}
