package com.capstone.booking.common.converter;

import com.capstone.booking.entity.ImagePlace;
import com.capstone.booking.entity.OpeningHours;
import com.capstone.booking.entity.dto.ImageDTO;
import com.capstone.booking.entity.dto.OpeningHoursDTO;
import org.springframework.stereotype.Component;

@Component
public class ImageConverter {
    public ImageDTO toDTO(ImagePlace imagePlace) {
        ImageDTO dto = new ImageDTO();
        if (imagePlace.getId() != null) {
            dto.setId(imagePlace.getId());
        }
        dto.setImageLink(imagePlace.getImageLink());
        return dto;
    }


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
