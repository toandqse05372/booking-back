package com.capstone.booking.service.impl;

import com.capstone.booking.common.converter.VisitorTypeConverter;
import com.capstone.booking.entity.TicketType;
import com.capstone.booking.entity.VisitorType;
import com.capstone.booking.entity.dto.VisitorTypeDTO;
import com.capstone.booking.repository.TicketTypeRepository;
import com.capstone.booking.repository.VisitorTypeRepository;
import com.capstone.booking.service.VisitorTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class VisitorTypeServiceImpl implements VisitorTypeService {

    @Autowired
    VisitorTypeRepository visitorTypeRepository;

    @Autowired
    VisitorTypeConverter visitorTypeConverter;

    @Autowired
    TicketTypeRepository ticketTypeRepository;


    //thêm
    @Override
    public ResponseEntity<?> create(VisitorTypeDTO model) {
        VisitorType visitorType = visitorTypeConverter.toVisitorType(model);

        TicketType ticketType = ticketTypeRepository.findById(model.getTicketType().getId()).get();
        visitorType.setTicketType(ticketType);

        visitorTypeRepository.save(visitorType);
        return ResponseEntity.ok(visitorTypeConverter.toDTO(visitorType));
    }

    //sửa
    @Override
    public ResponseEntity<?> update(VisitorTypeDTO model) {
        VisitorType visitorType = new VisitorType();
        VisitorType oldVisitor = visitorTypeRepository.findById(model.getId()).get();
        visitorType = visitorTypeConverter.toVisitorType(model, oldVisitor);

        TicketType ticketType = ticketTypeRepository.findById(model.getTicketType().getId()).get();
        visitorType.setTicketType(ticketType);

        visitorTypeRepository.save(visitorType);
        return ResponseEntity.ok(visitorTypeConverter.toDTO(visitorType));
    }

    //xóa
    @Override
    public ResponseEntity<?> delete(long id) {
       if (!visitorTypeRepository.findById(id).isPresent()){
           return new ResponseEntity("VISITOR_TYPE_NOT_FOUND", HttpStatus.BAD_REQUEST);
       }
       visitorTypeRepository.deleteById(id);
       return new ResponseEntity("DELETE_SUCCESSFUL", HttpStatus.OK);
    }
}
