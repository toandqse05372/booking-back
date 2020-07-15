package com.capstone.booking.service.impl;

import com.capstone.booking.common.converter.VisitorTypeConverter;
import com.capstone.booking.common.helper.ExcelHelper;
import com.capstone.booking.entity.Code;
import com.capstone.booking.entity.Game;
import com.capstone.booking.entity.TicketType;
import com.capstone.booking.entity.VisitorType;
import com.capstone.booking.entity.dto.VisitorTypeDTO;
import com.capstone.booking.repository.CodeRepository;
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

    //thêm
    @Override
    public ResponseEntity<?> create(VisitorTypeDTO model) {
        VisitorType visitorType = visitorTypeConverter.toVisitorType(model);

        List<VisitorType> typeList = visitorTypeRepository.findByTypeName(visitorType.getTypeName());
        if (typeList.size() > 0) {
            for (VisitorType t : typeList) {
                if (t.getTicketType().getId().equals(model.getTicketTypeId())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("VISITOR_TYPE_EXISTED");
                }
            }
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

        TicketType ticketType = ticketTypeRepository.findById(model.getTicketTypeId()).get();
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

    //xóa
    @Override
    @Transactional
    public ResponseEntity<?> delete(long id) {
        if (!visitorTypeRepository.findById(id).isPresent()) {
            return new ResponseEntity("VISITOR_TYPE_NOT_FOUND", HttpStatus.BAD_REQUEST);
        }
        codeRepository.deleteByVisitorType(visitorTypeRepository.findById(id).get());
        visitorTypeRepository.deleteById(id);
        return new ResponseEntity("DELETE_SUCCESSFUL", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findByTicketTypeId(long id) {
        List<VisitorTypeDTO> list = new ArrayList<>();
        for (VisitorType type : visitorTypeRepository.findByTicketType(ticketTypeRepository.findById(id).get())) {
            list.add(visitorTypeConverter.toDTO(type));
        }
        return ResponseEntity.ok(list);
    }

    @Override
    public ResponseEntity<?> getById(long id) {
        VisitorType type = visitorTypeRepository.findById(id).get();
        return ResponseEntity.ok(visitorTypeConverter.toDTO(type));
    }

    @Override
    public ResponseEntity<?> addCodeForTicketType(MultipartFile file, String codeType){
        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                List<Code> tutorials = ExcelHelper.excelToCode(file.getInputStream());
                codeRepository.saveAll(tutorials);
                int reaming = codeRepository.findByVisitorType(visitorTypeRepository.findByTypeKey(codeType)).size();
                return ResponseEntity.ok(reaming);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("COULD_NOT_UPLOAD_FILE");
            }
        }else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NOT_EXCEL_FILE");

    }

    @Override
    public ResponseEntity<?> markBasicPrice(long id) {
        VisitorType markType = visitorTypeRepository.findById(id).get();
        if(markType == null){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("VISITOR_TYPE_NOT_FOUND");
        }
        VisitorType oldMarType = visitorTypeRepository.findByIsBasicType(true);
        oldMarType.setBasicType(false);
        visitorTypeRepository.save(oldMarType);

        markType.setBasicType(true);
        visitorTypeRepository.save(markType);

        return ResponseEntity.status((HttpStatus.OK)).body("UPDATED");
    }
}
