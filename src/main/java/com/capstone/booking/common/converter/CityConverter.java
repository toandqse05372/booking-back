package com.capstone.booking.common.converter;

import com.capstone.booking.entity.City;
import com.capstone.booking.entity.dto.CityDTO;
import org.springframework.stereotype.Component;

@Component
public class CityConverter {

    public CityDTO toDTO(City city) {
        CityDTO dto = new CityDTO();
        if (city.getId() != null) {
            dto.setId(city.getId());
        }

        dto.setName(city.getName());
        return dto;
    }
}
