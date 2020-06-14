package com.capstone.booking.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode
public class PlaceTypeDTO extends BaseDTO{
    private String placeTypeName;
    //private Set<String> placeName;
}
