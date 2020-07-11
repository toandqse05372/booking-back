package com.capstone.booking.common.converter;

import com.capstone.booking.entity.City;
import com.capstone.booking.entity.dto.CityDTO;
import org.springframework.stereotype.Component;

@Component
public class CityConverter {

    public City toCity(CityDTO dto) {
        City city = new City();
        city.setName(dto.getName());
        city.setShortDescription(dto.getShortDescription());
        city.setDetailDescription(dto.getDetailDescription());
        return city;
    }

    public City toCity(CityDTO dto, City city) {
        city.setName(dto.getName());
        city.setShortDescription(dto.getShortDescription());
        city.setDetailDescription(dto.getDetailDescription());
        return city;
    }

    public CityDTO toDTO(City city) {
        CityDTO dto = new CityDTO();
        if (city.getId() != null) {
            dto.setId(city.getId());
        }
        dto.setName(city.getName());
        dto.setShortDescription(city.getShortDescription());
        dto.setDetailDescription(city.getDetailDescription());
        dto.setImageLink(city.getImageLink());
        return dto;
    }
}
