package com.capstone.booking.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode
public class PlaceDTO extends BaseDTO{
    private String name;
    private String address;
    private String description;
    private String mail;
    private String phoneNumber;
    private Set<ImageDTO> placeImage;
    private CityDTO city;
    //private Set<GameDTO> game;
    private Set<CategoryDTO> category;
    private Set<OpeningHoursDTO> openingHours;
    private String status;
}
