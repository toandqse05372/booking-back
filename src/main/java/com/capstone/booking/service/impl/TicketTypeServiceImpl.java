package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.api.output.OutputExcel;
import com.capstone.booking.common.converter.TicketTypeConverter;
import com.capstone.booking.common.converter.VisitorTypeConverter;
import com.capstone.booking.common.helper.ExcelHelper;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.TicketTypeDTO;
import com.capstone.booking.entity.dto.VisitorTypeDTO;
import com.capstone.booking.repository.CodeRepository;
import com.capstone.booking.repository.GameRepository;
import com.capstone.booking.repository.TicketTypeRepository;
import com.capstone.booking.repository.VisitorTypeRepository;
import com.capstone.booking.service.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;


@Service
public class TicketTypeServiceImpl implements TicketTypeService {

    @Autowired
    TicketTypeRepository ticketTypeRepository;

    @Autowired
    TicketTypeConverter ticketTypeConverter;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    CodeRepository codeRepository;

    @Autowired
    VisitorTypeConverter visitorTypeConverter;

    @Autowired
    VisitorTypeRepository visitorTypeRepository;

    //get All
    @Override
    public ResponseEntity<?> findAll() {
        List<TicketTypeDTO> results = new ArrayList<>();
        List<TicketType> types = ticketTypeRepository.findAll();
        for (TicketType item : types) {
            TicketTypeDTO ticketTypeDTO = ticketTypeConverter.toDTO(item);

            results.add(ticketTypeDTO);
        }
        return ResponseEntity.ok(results);
    }

    //delete
    @Override
    @Transactional
    public ResponseEntity<?> delete(long id) {
        if (!ticketTypeRepository.findById(id).isPresent()) {
            return new ResponseEntity("TICKET_TYPE_NOT_FOUND", HttpStatus.BAD_REQUEST);
        }
        ticketTypeRepository.deleteById(id);
        return new ResponseEntity("DELETE_SUCCESSFUL", HttpStatus.OK);
    }

    //add ticketType
    @Override
    public ResponseEntity<?> create(TicketTypeDTO ticketTypeDTO) {
        TicketType ticketType = ticketTypeConverter.toTicketType(ticketTypeDTO);

        Set<Game> gameSet = new HashSet<>();
        for (Long id : ticketTypeDTO.getGameId()) {
            gameSet.add(gameRepository.findById(id).get());
        }
        ticketType.setGame(gameSet);

        ticketTypeRepository.save(ticketType);
        return ResponseEntity.ok(ticketTypeConverter.toDTO(ticketType));
    }

    //edit tickType
    @Override
    public ResponseEntity<?> update(TicketTypeDTO ticketTypeDTO) {
        TicketType ticketType = new TicketType();
        TicketType oldTicketType = ticketTypeRepository.findById(ticketTypeDTO.getId()).get();
        ticketType = ticketTypeConverter.toTicketType(ticketTypeDTO, oldTicketType);

        Set<Game> gameSet = new HashSet<>();
        for (Long gameId : ticketTypeDTO.getGameId()) {
            gameSet.add(gameRepository.findById(gameId).get());
        }
        ticketType.setGame(gameSet);

        ticketTypeRepository.save(ticketType);
        return ResponseEntity.ok(ticketTypeConverter.toDTO(ticketType));
    }

    //search by placeId
    @Override
    public ResponseEntity<?> findByPlaceId(long placeId) {
        List<TicketTypeDTO> list = new ArrayList<>();
        List<TicketType> ticketTypes = ticketTypeRepository.findByPlaceId(placeId);
        OutputExcel output = new OutputExcel();
        if(ticketTypes.size() > 0){
            for(TicketType ticketType: ticketTypes){
                TicketTypeDTO ticketTypeDTO = ticketTypeConverter.toDTO(ticketType);
                List<VisitorType> visitorTypes = visitorTypeRepository.findAllByTicketType(ticketType);
                if(visitorTypes.size() > 0){
                    output.setImportExcel(true);
                    Set<VisitorTypeDTO> visitorTypeDTOS = new HashSet<>();
                    for(VisitorType type: visitorTypes){
                        visitorTypeDTOS.add(visitorTypeConverter.toDTO(type));
                    }
                    ticketTypeDTO.setVisitorTypes(visitorTypeDTOS);
                }
                list.add(ticketTypeDTO);
            }
        }
        output.setListResult(list);
        return ResponseEntity.ok(output);
    }

    //search ticketType by typeName & paging
    @Override
    public ResponseEntity<?> findByTypeName(String typeName, Long limit, Long page) {
        Output results = ticketTypeRepository.findByTypeName(typeName, limit, page);
        return ResponseEntity.ok(results);
    }

    //search by Id
    @Override
    public ResponseEntity<?> getTicketType(Long id) {
        TicketTypeDTO dto = ticketTypeConverter.toDTO(ticketTypeRepository.findById(id).get());
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<?> addCodeForTicketType(MultipartFile file){
        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                List<Code> tutorials = ExcelHelper.excelToCode(file.getInputStream());
                codeRepository.saveAll(tutorials);
                return ResponseEntity.ok("OK");
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("COULD_NOT_UPLOAD_FILE");
            }
        }else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NOT_EXCEL_FILE");

    }
}
