package com.capstone.booking.common.converter;

import com.capstone.booking.entity.City;
import com.capstone.booking.entity.dto.CityDTO;
import org.springframework.stereotype.Component;

//convert city
@Component
public class CityConverter {

    //convert from dto to entity (for add)
    public City toCity(CityDTO dto) {
        City city = new City();
        city.setName(dto.getName());
        city.setShortDescription(dto.getShortDescription());
        city.setDetailDescription(dto.getDetailDescription());
        return city;
    }

    //convert from dto to entity (for update)
    public City toCity(CityDTO dto, City city) {
        city.setName(dto.getName());
        city.setShortDescription(dto.getShortDescription());
        city.setDetailDescription(dto.getDetailDescription());
        return city;
    }

    //convert from entity to dto
    public CityDTO toDTO(City city) {
        CityDTO dto = new CityDTO();
        dto.setId(city.getId());
        dto.setName(city.getName());
        dto.setShortDescription(city.getShortDescription());
        dto.setDetailDescription(city.getDetailDescription());
        dto.setImageLink(city.getImageLink());
        return dto;
    }
}
