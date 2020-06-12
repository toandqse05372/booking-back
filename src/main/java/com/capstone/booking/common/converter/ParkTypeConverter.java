package com.capstone.booking.common.converter;

import com.capstone.booking.entity.Park;
import com.capstone.booking.entity.ParkType;
import com.capstone.booking.entity.dto.ParkTypeDTO;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ParkTypeConverter {
    public ParkTypeDTO toDTO(ParkType parkType) {
        ParkTypeDTO dto = new ParkTypeDTO();
        if (parkType.getId() != null) {
            dto.setId(parkType.getId());
        }
        dto.setParkTypeName(parkType.getTypeName());

//        Set<Park> parkSet = parkType.getParks();
//        Set<String> parkString = new HashSet<>();
//        for (Park park : parkSet) {
//            parkString.add(park.getName());
//        }
//        dto.setParkName(parkString);
        return dto;
    }

    public ParkType toParkType(ParkTypeDTO dto) {
        ParkType parkType = new ParkType();
        parkType.setTypeName(dto.getParkTypeName());
        return parkType;
    }

    public ParkType toParkType(ParkTypeDTO dto, ParkType parkType) {
        parkType.setTypeName(dto.getParkTypeName());
        return parkType;
    }

}
