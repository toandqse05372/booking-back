package com.capstone.booking.common.converter;

import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
public class ParkConverter {
    @Autowired
    private GameConverter gameConverter;
    @Autowired
    private ParkTypeConverter parkTypeConverter;

    @Autowired
    private ImageConverter imageConverter;

    public Park toPark(ParkDTO dto) {
        Park park = new Park();
        park.setName(dto.getName());
        park.setAddress(dto.getAddress());
        park.setDescription(dto.getDescription());
        park.setMail(dto.getMail());
        park.setPhoneNumber(dto.getPhoneNumber());
        return park;
    }

    public ParkDTO toDTO(Park park) {
        ParkDTO dto = new ParkDTO();
        if (park.getId() != null) {
            dto.setId(park.getId());
        }
        dto.setName(park.getName());
        dto.setAddress(park.getAddress());
        dto.setDescription(park.getDescription());
        dto.setMail(park.getMail());
        dto.setPhoneNumber(park.getPhoneNumber());

        CityDTO cityDTO = new CityDTO();
        City city = park.getCity();
        cityDTO.setId(city.getId());
        cityDTO.setName(city.getName());
        dto.setCity(cityDTO);

        Set<ParkTypeDTO> parkTypeSet = new HashSet<>();
        for (ParkType parkType : park.getParkTypes()) {
            parkTypeSet.add(parkTypeConverter.toDTO(parkType));
        }
        dto.setParkType(parkTypeSet);

        return dto;
    }

    public Park toPark(ParkDTO dto, Park park) {
        park.setName(dto.getName());
        park.setAddress(dto.getAddress());
        park.setDescription(dto.getDescription());
        park.setMail(dto.getMail());
        park.setPhoneNumber(dto.getPhoneNumber());
        return park;
    }
}
