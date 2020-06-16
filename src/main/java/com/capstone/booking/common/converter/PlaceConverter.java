package com.capstone.booking.common.converter;

import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
public class PlaceConverter {
    @Autowired
    private GameConverter gameConverter;
    @Autowired
    private PlaceTypeConverter placeTypeConverter;

    @Autowired
    private ImageConverter imageConverter;

    @Autowired
    private CityConverter cityConverter;

    @Autowired
    private OpeningHoursConverter hoursConverter;

    public Place toPlace(PlaceDTO dto) {
        Place place = new Place();
        place.setName(dto.getName());
        place.setAddress(dto.getAddress());
        place.setDescription(dto.getDescription());
        place.setMail(dto.getMail());
        place.setPhoneNumber(dto.getPhoneNumber());
        return place;
    }

    public PlaceDTO toDTO(Place place) {
        PlaceDTO dto = new PlaceDTO();
        if (place.getId() != null) {
            dto.setId(place.getId());
        }
        dto.setName(place.getName());
        dto.setAddress(place.getAddress());
        dto.setDescription(place.getDescription());
        dto.setMail(place.getMail());
        dto.setPhoneNumber(place.getPhoneNumber());

        Set<ImageDTO> imageSet = new HashSet<>();
        for (ImagePlace image : place.getImagePlace()) {
            imageSet.add(imageConverter.toDTO(image));
        }
        dto.setPlaceImage(imageSet);

        CityDTO cityDTO = new CityDTO();
        City city = place.getCity();
        cityDTO = cityConverter.toDTO(city);
        //cityDTO.setId(city.getId());
        //cityDTO.setName(city.getName());
        dto.setCity(cityDTO);

        Set<PlaceTypeDTO> placeTypeSet = new HashSet<>();
        for (PlaceType placeType : place.getPlaceTypes()) {
            placeTypeSet.add(placeTypeConverter.toDTO(placeType));
        }
        dto.setPlaceType(placeTypeSet);

        Set<OpeningHoursDTO> openingHoursSet = new HashSet<>();
        for (OpeningHours hours : place.getOpeningHours()) {
            openingHoursSet.add(hoursConverter.toDTO(hours));
        }
        dto.setOpeningHours(openingHoursSet);

        return dto;
    }

    public Place toPlace(PlaceDTO dto, Place place) {
        place.setName(dto.getName());
        place.setAddress(dto.getAddress());
        place.setDescription(dto.getDescription());
        place.setMail(dto.getMail());
        place.setPhoneNumber(dto.getPhoneNumber());
        return place;
    }
}
