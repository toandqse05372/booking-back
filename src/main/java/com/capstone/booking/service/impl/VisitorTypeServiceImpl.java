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

import java.util.ArrayList;
import java.util.List;


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
        if (visitorTypeRepository.findByTypeName(visitorType.getTypeName()) != null
                && visitorTypeRepository.findByTicketTypeId(model.getTicketTypeId()).size() != 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("VISITOR_TYPE_EXISTED");
        }
        TicketType ticketType = ticketTypeRepository.findById(model.getTicketTypeId()).get();
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

        VisitorType existedVisitor = visitorTypeRepository.findByTypeName(visitorType.getTypeName());
        if (existedVisitor != null) {
            if (existedVisitor.getId() != visitorType.getId()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("VISITOR_TYPE_EXISTED");
            }
        }

        TicketType ticketType = ticketTypeRepository.findById(model.getTicketTypeId()).get();
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

    @Override
    public ResponseEntity<?> findByTicketTypeId(long id) {
        List<VisitorTypeDTO> list = new ArrayList<>();
        for(VisitorType type: visitorTypeRepository.findAllByTicketType(ticketTypeRepository.findById(id).get())){
            list.add(visitorTypeConverter.toDTO(type));
        }
        return ResponseEntity.ok(list);
    }

    @Override
    public ResponseEntity<?> getById(long id) {
        VisitorType type = visitorTypeRepository.findById(id).get();
        return ResponseEntity.ok(visitorTypeConverter.toDTO(type));
    }
}
