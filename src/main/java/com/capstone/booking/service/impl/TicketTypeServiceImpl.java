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
import java.util.stream.Collectors;


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
    @Transactional
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
                List<VisitorType> visitorTypes = visitorTypeRepository.findByTicketType(ticketType);
                if(visitorTypes.size() > 0){
                    output.setImportExcel(true);
                    List<VisitorTypeDTO> visitorTypeDTOS = new ArrayList<>();
                    for(VisitorType type: visitorTypes){
                        visitorTypeDTOS.add(visitorTypeConverter.toDTO(type));
                    }
                    ticketTypeDTO.setVisitorTypes(visitorTypeDTOS.stream().sorted(new Comparator<VisitorTypeDTO>() {
                        @Override
                        public int compare(VisitorTypeDTO o1, VisitorTypeDTO o2) {
                            return o1.getId().compareTo(o2.getId());
                        }
                    }).collect(Collectors.toList()));
                }
                list.add(ticketTypeDTO);
            }
        }
        output.setListResult(list);
        return ResponseEntity.ok(output);
    }

    //tim kiem theo tên loại vé
    @Override
    public ResponseEntity<?> findByTypeName(String typeName, Long limit, Long page) {
        Output results = ticketTypeRepository.findByTypeName(typeName, limit, page);
        return ResponseEntity.ok(results);
    }



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
