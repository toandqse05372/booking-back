package com.capstone.booking.common.converter;

import com.capstone.booking.entity.PlaceType;
import com.capstone.booking.entity.dto.PlaceTypeDTO;
import org.springframework.stereotype.Component;

@Component
public class PlaceTypeConverter {
    public PlaceTypeDTO toDTO(PlaceType placeType) {
        PlaceTypeDTO dto = new PlaceTypeDTO();
        if (placeType.getId() != null) {
            dto.setId(placeType.getId());
        }
        dto.setPlaceTypeName(placeType.getTypeName());
        dto.setTypeKey(placeType.getTypeKey());
//        Set<Place> placeSet = placeType.getplaces();
//        Set<String> placeString = new HashSet<>();
//        for (Place place : placeSet) {
//            placeString.add(place.getName());
//        }
//        dto.setplaceName(placeString);
        return dto;
    }

    public PlaceType toPlaceType(PlaceTypeDTO dto) {
        PlaceType placeType = new PlaceType();
        placeType.setTypeName(dto.getPlaceTypeName());
        placeType.setTypeKey(dto.getTypeKey());
        return placeType;
    }

    public PlaceType toPlaceType(PlaceTypeDTO dto, PlaceType placeType) {
        placeType.setTypeName(dto.getPlaceTypeName());
        placeType.setTypeKey(dto.getTypeKey());
        return placeType;
    }

}
