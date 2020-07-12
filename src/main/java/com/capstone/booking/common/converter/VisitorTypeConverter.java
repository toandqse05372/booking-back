package com.capstone.booking.common.converter;

import com.capstone.booking.entity.TicketType;
import com.capstone.booking.entity.VisitorType;
import com.capstone.booking.entity.dto.TicketTypeDTO;
import com.capstone.booking.entity.dto.VisitorTypeDTO;
import com.capstone.booking.repository.CodeRepository;
import com.capstone.booking.repository.VisitorTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VisitorTypeConverter {

    @Autowired
    CodeRepository codeRepository;

    public VisitorType toVisitorType(VisitorTypeDTO dto) {
        VisitorType visitorType = new VisitorType();
        visitorType.setTypeName(dto.getTypeName());
        visitorType.setTypeKey(dto.getTypeKey());
        visitorType.setPrice(dto.getPrice());
        visitorType.setBasicType(dto.isBasicType());
        return visitorType;
    }

    public VisitorType toVisitorType(VisitorTypeDTO dto, VisitorType visitorType) {
        visitorType.setTypeName(dto.getTypeName());
        visitorType.setTypeKey(dto.getTypeKey());
        visitorType.setPrice(dto.getPrice());
        visitorType.setBasicType(dto.isBasicType());
        return visitorType;
    }

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

        return dto;
    }
}
