package com.capstone.booking.common.converter;

import com.capstone.booking.entity.OpeningHours;
import com.capstone.booking.entity.dto.OpeningHoursDTO;
import org.springframework.stereotype.Component;

//not usse
@Component
public class OpeningHoursConverter {

    public OpeningHoursDTO toDTO(OpeningHours hours){
        OpeningHoursDTO dto = new OpeningHoursDTO();
        if (hours.getId() != null) {
            dto.setId(hours.getId());
        }
        dto.setOpenHours(hours.getOpenHours());
        dto.setCloseHours(hours.getCloseHours());
        dto.setWeekDay(hours.getWeekDay());
        return dto;
    }

}
