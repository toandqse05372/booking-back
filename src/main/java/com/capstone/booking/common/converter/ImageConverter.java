package com.capstone.booking.common.converter;

import com.capstone.booking.entity.Image;
import com.capstone.booking.entity.OpeningHours;
import com.capstone.booking.entity.dto.ImageDTO;
import com.capstone.booking.entity.dto.OpeningHoursDTO;
import org.springframework.stereotype.Component;

@Component
public class ImageConverter {
    public ImageDTO toDTO(Image image) {
        ImageDTO dto = new ImageDTO();
        if (image.getId() != null) {
            dto.setId(image.getId());
        }
        dto.setImageLink(image.getImageLink());
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
