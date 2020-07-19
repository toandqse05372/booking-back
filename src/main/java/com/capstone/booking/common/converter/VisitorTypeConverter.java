package com.capstone.booking.common.converter;

import com.capstone.booking.entity.VisitorType;
import com.capstone.booking.entity.dto.VisitorTypeDTO;
import com.capstone.booking.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//convert game
@Component
public class VisitorTypeConverter {

    @Autowired
    CodeRepository codeRepository;

    //convert from dto to entity (for add)
    public VisitorType toVisitorType(VisitorTypeDTO dto) {
        VisitorType visitorType = new VisitorType();
        visitorType.setTypeName(dto.getTypeName());
        visitorType.setTypeKey(dto.getTypeKey());
        visitorType.setPrice(dto.getPrice());
        return visitorType;
    }

    //convert from dto to entity (for update)
    public VisitorType toVisitorType(VisitorTypeDTO dto, VisitorType visitorType) {
        visitorType.setTypeName(dto.getTypeName());
        visitorType.setTypeKey(dto.getTypeKey());
        visitorType.setPrice(dto.getPrice());
        return visitorType;
    }

    //convert from entity to dto
    public VisitorTypeDTO toDTO(VisitorType visitorType) {
        VisitorTypeDTO dto = new VisitorTypeDTO();
        if (visitorType.getId() != null) {
            dto.setId(visitorType.getId());
        }
        dto.setTypeName(visitorType.getTypeName());
        dto.setTypeKey(visitorType.getTypeKey());
        dto.setPrice(visitorType.getPrice());
        dto.setBasicType(visitorType.isBasicType());
        dto.setRemaining(codeRepository.findByVisitorType(visitorType).size());
        dto.setTicketTypeId(visitorType.getTicketType().getId());
        dto.setBasicType(visitorType.isBasicType());

        return dto;
    }
}
