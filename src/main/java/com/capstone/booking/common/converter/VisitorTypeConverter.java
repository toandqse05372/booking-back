package com.capstone.booking.common.converter;

import com.capstone.booking.entity.VisitorType;
import com.capstone.booking.entity.dto.VisitorTypeDTO;
import com.capstone.booking.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

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
        dto.setId(visitorType.getId());
        dto.setStatus(visitorType.getStatus());
        dto.setTypeName(visitorType.getTypeName());
        dto.setTypeKey(visitorType.getTypeKey());
        dto.setPrice(visitorType.getPrice());
        dto.setBasicType(visitorType.isBasicType());

        //set timezone to hanoi
        TimeZone hanoiVietnam = TimeZone.getTimeZone("GMT+7:00");
        Calendar c = Calendar.getInstance(hanoiVietnam);
        c.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        Date d1 = c.getTime();
        dto.setTicketTypeId(visitorType.getTicketType().getId());
        dto.setBasicType(visitorType.isBasicType());

        return dto;
    }
}
