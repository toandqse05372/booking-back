package com.capstone.booking.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class CityDTO extends BaseDTO{

    private String name;

    private String shortDescription;

    private String detailDescription;

    private String imageLink;
}
