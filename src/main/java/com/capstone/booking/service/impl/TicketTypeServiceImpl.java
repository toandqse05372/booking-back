package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.TicketTypeConverter;
import com.capstone.booking.common.helper.ExcelHelper;
import com.capstone.booking.entity.City;
import com.capstone.booking.entity.Code;
import com.capstone.booking.entity.Game;
import com.capstone.booking.entity.TicketType;
import com.capstone.booking.entity.dto.TicketTypeDTO;
import com.capstone.booking.repository.CodeRepository;
import com.capstone.booking.repository.GameRepository;
import com.capstone.booking.repository.TicketTypeRepository;
import com.capstone.booking.service.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    //xoa
    @Override
    public ResponseEntity<?> delete(long id) {
        if (!ticketTypeRepository.findById(id).isPresent()) {
            return new ResponseEntity("TICKET_TYPE_NOT_FOUND", HttpStatus.BAD_REQUEST);
        }
        ticketTypeRepository.deleteById(id);
        return new ResponseEntity("DELETE_SUCCESSFUL", HttpStatus.OK);
    }

    //tao ticketType
    @Override
    public ResponseEntity<?> create(TicketTypeDTO ticketTypeDTO) {
        TicketType ticketType = ticketTypeConverter.toTicketType(ticketTypeDTO);

//        if (ticketTypeRepository.findByTypeName(ticketType.getTypeName()) != null
//                && ticketTypeRepository.findByPlaceId(ticketTypeDTO.getPlaceId()).size() != 0) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("TICKET_TYPE_EXISTED");
//        }
        Set<Game> gameSet = new HashSet<>();
        for (Long id : ticketTypeDTO.getGameId()) {
            gameSet.add(gameRepository.findById(id).get());
        }
        ticketType.setGame(gameSet);

        ticketTypeRepository.save(ticketType);
        return ResponseEntity.ok(ticketTypeConverter.toDTO(ticketType));
    }

    //sua tickType
    @Override
    public ResponseEntity<?> update(TicketTypeDTO ticketTypeDTO) {
        TicketType ticketType = new TicketType();
        TicketType oldTicketType = ticketTypeRepository.findById(ticketTypeDTO.getId()).get();
        ticketType = ticketTypeConverter.toTicketType(ticketTypeDTO, oldTicketType);

//        TicketType existedType = ticketTypeRepository.findByTypeName(ticketType.getTypeName());
//        if (existedType != null) {
//            if (existedType.getId() != ticketType.getId()) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("TICKET_TYPE_EXISTED");
//            }
//        }

        Set<Game> gameSet = new HashSet<>();
        for (Long gameId : ticketTypeDTO.getGameId()) {
            gameSet.add(gameRepository.findById(gameId).get());
        }
        ticketType.setGame(gameSet);

        ticketTypeRepository.save(ticketType);
        return ResponseEntity.ok(ticketTypeConverter.toDTO(ticketType));
    }

    @Override
    public ResponseEntity<?> findByTypeName(String typeName, Long limit, Long page) {
        Output results = ticketTypeRepository.findByTypeName(typeName, limit, page);
        return ResponseEntity.ok(results);
    }

    @Override
    public ResponseEntity<?> addCodeForTicketType(MultipartFile file, String codeType){
        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                List<Code> tutorials = ExcelHelper.excelToTutorials(file.getInputStream());
                codeRepository.saveAll(tutorials);
                return ResponseEntity.ok("SUCCESS");
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("COULD_NOT_UPLOAD_FILE");
            }
        }else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NOT_EXCEL_FILE");

    }

    @Override
    public ResponseEntity<?> getTicketType(Long id) {
        TicketTypeDTO dto = ticketTypeConverter.toDTO(ticketTypeRepository.findById(id).get());
        return ResponseEntity.ok(dto);
    }
}
