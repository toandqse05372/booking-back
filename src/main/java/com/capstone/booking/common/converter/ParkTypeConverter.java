package com.capstone.booking.common.converter;

import com.capstone.booking.entity.ParkType;
import com.capstone.booking.entity.dto.ParkTypeDTO;
import org.springframework.stereotype.Component;

@Component
public class ParkTypeConverter {
    public ParkTypeDTO toDTO(ParkType parkType) {
        ParkTypeDTO dto = new ParkTypeDTO();
        if (parkType.getId() != null) {
            dto.setId(parkType.getId());
        }
        dto.setParkTypeName(parkType.getTypeName());
        return dto;
    }
}
