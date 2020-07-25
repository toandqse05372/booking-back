package com.capstone.booking.service.impl;

import com.capstone.booking.common.converter.VisitorTypeConverter;
import com.capstone.booking.common.helper.ExcelHelper;
import com.capstone.booking.common.key.MonoStatus;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.VisitorTypeDTO;
import com.capstone.booking.repository.CodeRepository;
import com.capstone.booking.repository.PlaceRepository;
import com.capstone.booking.repository.TicketTypeRepository;
import com.capstone.booking.repository.VisitorTypeRepository;
import com.capstone.booking.service.VisitorTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class VisitorTypeServiceImpl implements VisitorTypeService {

    @Autowired
    VisitorTypeRepository visitorTypeRepository;

    @Autowired
    VisitorTypeConverter visitorTypeConverter;

    @Autowired
    TicketTypeRepository ticketTypeRepository;

    @Autowired
    CodeRepository codeRepository;

    @Autowired
    PlaceRepository placeRepository;

    //add
    @Override
    public ResponseEntity<?> create(VisitorTypeDTO model, Long placeId) {
        VisitorType visitorType = visitorTypeConverter.toVisitorType(model);
        visitorType.setStatus(MonoStatus.ACTIVE.toString());
        List<VisitorType> typeList = visitorTypeRepository.findByTypeName(visitorType.getTypeName());
        if (typeList.size() > 0) {
            for (VisitorType t : typeList) {
                if (t.getTicketType().getId().equals(model.getTicketTypeId())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("VISITOR_TYPE_EXISTED");
                }
            }
        }

        boolean hasBasic = false;
        List<TicketType> ticketTypes = ticketTypeRepository.findByPlaceId(placeId);
        for(TicketType ticketType: ticketTypes){
            for(VisitorType type: ticketType.getVisitorType()){
                if(type.isBasicType()){ hasBasic = true;
                    break;
                }
            }
            if(hasBasic){ break; }
        }
        TicketType ticketType = ticketTypeRepository.findById(model.getTicketTypeId()).get();
        visitorType.setTicketType(ticketType);
        if(!hasBasic){
            visitorType.setBasicType(true);
        }
        visitorTypeRepository.save(visitorType);
        Optional<Place> optionalPlace = placeRepository.findById(placeId);
        Place place = optionalPlace.get();
        place.setBasicPrice(visitorType.getPrice());
        placeRepository.save(place);
        return ResponseEntity.ok(visitorTypeConverter.toDTO(visitorType));
    }

    //edit
    @Override
    public ResponseEntity<?> update(VisitorTypeDTO model) {
        VisitorType visitorType = new VisitorType();
        Optional<VisitorType> optionalVisitorType = visitorTypeRepository.findById(model.getId());
        VisitorType oldVisitor = optionalVisitorType.get();
        visitorType = visitorTypeConverter.toVisitorType(model, oldVisitor);

        Optional<TicketType> optionalTicketType = ticketTypeRepository.findById(model.getTicketTypeId());
        TicketType ticketType = optionalTicketType.get();
        visitorType.setTicketType(ticketType);

        List<VisitorType> typeList = visitorTypeRepository.findByTypeName(visitorType.getTypeName());
        if (typeList.size() > 0) {
            for (VisitorType t : typeList) {
                if (t.getTicketType().getId().equals(model.getTicketTypeId()) && !t.getId().equals(visitorType.getId())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("VISITOR_TYPE_EXISTED");
                }
            }
        }

        visitorTypeRepository.save(visitorType);
        return ResponseEntity.ok(visitorTypeConverter.toDTO(visitorType));
    }

    //delete
    @Override
    @Transactional
    public ResponseEntity<?> delete(long id) {
        VisitorType visitorType =visitorTypeRepository.findById(id).get();
        if (visitorType.isBasicType()){
            return new ResponseEntity("VISITOR_TYPE_IS_BASIC", HttpStatus.BAD_REQUEST);
        }
        if (visitorType == null) {
            return new ResponseEntity("VISITOR_TYPE_NOT_FOUND", HttpStatus.BAD_REQUEST);
        }
        if(visitorType.isBasicType()){
            return new ResponseEntity("VISITOR_TYPE_IS_BASIC", HttpStatus.BAD_REQUEST);
        }
        codeRepository.deleteByVisitorType(visitorType);
        visitorTypeRepository.deleteById(id);
        return new ResponseEntity("DELETE_SUCCESSFUL", HttpStatus.OK);
    }

    //change status of game
    @Override
    public ResponseEntity<?> changeStatus(long id) {
        VisitorType visitorType = visitorTypeRepository.findById(id).get();
        if(visitorType.isBasicType()){
            return new ResponseEntity("VISITOR_TYPE_IS_BASIC", HttpStatus.BAD_REQUEST);
        }
        if (visitorType.getStatus().equals(MonoStatus.ACTIVE.toString())) {
            visitorType.setStatus(MonoStatus.DEACTIVATE.toString());
        } else {
            visitorType.setStatus(MonoStatus.ACTIVE.toString());
        }
        visitorType = visitorTypeRepository.save(visitorType);
        return ResponseEntity.ok(visitorTypeConverter.toDTO(visitorType));
    }

    //search by ticketTypeId
    @Override
    public ResponseEntity<?> findByTicketTypeId(long id) {
        List<VisitorTypeDTO> list = new ArrayList<>();
        for (VisitorType type : visitorTypeRepository.findByTicketType(ticketTypeRepository.findById(id).get())) {
            list.add(visitorTypeConverter.toDTO(type));
        }
        return ResponseEntity.ok(list);
    }

    //search by Id
    @Override
    public ResponseEntity<?> getById(long id) {
        VisitorType type = visitorTypeRepository.findById(id).get();
        return ResponseEntity.ok(visitorTypeConverter.toDTO(type));
    }

//    //not use
//    @Override
//    public ResponseEntity<?> addCodeForVisitorType(MultipartFile file, String codeType){
//        if (ExcelHelper.hasExcelFormat(file)) {
//            try {
//                List<Code> tutorials = ExcelHelper.excelToCode(file.getInputStream());
//                codeRepository.saveAll(tutorials);
//                int reaming = codeRepository.findByVisitorType(visitorTypeRepository.findByTypeKey(codeType)).size();
//                return ResponseEntity.ok(reaming);
//            } catch (IOException e) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("COULD_NOT_UPLOAD_FILE");
//            }
//        }else
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NOT_EXCEL_FILE");
//
//    }

    //set chosen visitor type's price as basic type
    @Override
    public ResponseEntity<?> markBasicPrice(long id, long placeId) {
        Optional<VisitorType> optionalVisitorType = visitorTypeRepository.findById(id);
        VisitorType markType = optionalVisitorType.get();
        if(markType == null){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("VISITOR_TYPE_NOT_FOUND");
        }
        VisitorType oldMarType = visitorTypeRepository.findByPlaceIdAndBasic(placeId, true);
        oldMarType.setBasicType(false);
        visitorTypeRepository.save(oldMarType);

        markType.setBasicType(true);
        visitorTypeRepository.save(markType);

        Optional<Place> optionalPlace = placeRepository.findById(placeId);
        Place place = optionalPlace.get();
        place.setBasicPrice(markType.getPrice());
        placeRepository.save(place);

        return ResponseEntity.status((HttpStatus.OK)).body("UPDATED");
    }
}
