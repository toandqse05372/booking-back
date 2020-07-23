package com.capstone.booking.common.converter;

import com.capstone.booking.entity.ImagePlace;
import com.capstone.booking.entity.dto.ImageDTO;
import org.springframework.stereotype.Component;

//convert image link
@Component
public class ImageConverter {

    //convert from entity to dto
    public ImageDTO toDTO(ImagePlace imagePlace) {
        ImageDTO dto = new ImageDTO();
        dto.setId(imagePlace.getId());
        dto.setImageLink(imagePlace.getImageLink());
        return dto;
    }

}
