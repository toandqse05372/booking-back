package com.capstone.booking.common.converter;

import com.capstone.booking.entity.ImagePlace;
import com.capstone.booking.entity.OpeningHours;
import com.capstone.booking.entity.dto.ImageDTO;
import com.capstone.booking.entity.dto.OpeningHoursDTO;
import org.springframework.stereotype.Component;

//convert image link
@Component
public class ImageConverter {

    //convert from entity to dto
    public ImageDTO toDTO(ImagePlace imagePlace) {
        ImageDTO dto = new ImageDTO();
        if (imagePlace.getId() != null) {
            dto.setId(imagePlace.getId());
        }
        dto.setImageLink(imagePlace.getImageLink());
        return dto;
    }

}
