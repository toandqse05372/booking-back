package com.capstone.booking.entity.dto;

import com.capstone.booking.entity.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode
public class ParkDTO extends BaseDTO{
    private String name;
    private String address;
    private String description;
    private String mail;
    private String phoneNumber;
    private Set<ImageDTO> parkImage;
    private CityDTO city;
    private Set<GameDTO> game;
    private Set<ParkTypeDTO> parkType;
    private Set<OpeningHoursDTO> openingHours;
}
