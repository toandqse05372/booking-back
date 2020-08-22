package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.OutputExcel;
import com.capstone.booking.common.converter.TicketTypeConverter;
import com.capstone.booking.common.converter.VisitorTypeConverter;
import com.capstone.booking.common.helper.ExcelHelper;
import com.capstone.booking.common.key.MonoStatus;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.TicketTypeDTO;
import com.capstone.booking.entity.dto.VisitorTypeDTO;
import com.capstone.booking.repository.*;
import com.capstone.booking.service.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    PlaceRepository placeRepository;

    @Autowired
    TicketTypeConverter ticketTypeConverter;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    CodeRepository codeRepository;

    @Autowired
    RemainingRepository remainingRepository;

    @Autowired
    VisitorTypeConverter visitorTypeConverter;

    @Autowired
    VisitorTypeRepository visitorTypeRepository;

    //get All (not used)
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

    //delete ticket type
    @Override
    @Transactional
    public ResponseEntity<?> delete(long id) {
        if (!ticketTypeRepository.findById(id).isPresent()) {
            return new ResponseEntity("TICKET_TYPE_NOT_FOUND", HttpStatus.BAD_REQUEST);
        }
        ticketTypeRepository.deleteById(id);
        return new ResponseEntity("DELETE_SUCCESSFUL", HttpStatus.OK);
    }

    //add ticket Type for place
    @Override
    public ResponseEntity<?> create(TicketTypeDTO ticketTypeDTO) {
        if (null != ticketTypeRepository.
                findByTypeNameAndPlaceId(ticketTypeDTO.getTypeName(), ticketTypeDTO.getPlaceId())) {
            return new ResponseEntity("TICKET_TYPE_EXISTED", HttpStatus.BAD_REQUEST);
        }
        TicketType ticketType = ticketTypeConverter.toTicketType(ticketTypeDTO);
        ticketType.setStatus(MonoStatus.ACTIVE.toString());
        Set<Game> gameSet = new HashSet<>();
        for (Long id : ticketTypeDTO.getGameId()) {
            Optional<Game> optionalGame = gameRepository.findById(id);
            gameSet.add(optionalGame.get());
        }
        ticketType.setGame(gameSet);
        ticketTypeRepository.save(ticketType);
        return ResponseEntity.ok(ticketTypeConverter.toDTO(ticketType));
    }

    //edit tickType
    @Override
    public ResponseEntity<?> update(TicketTypeDTO ticketTypeDTO) {
        TicketType existedTicketType = ticketTypeRepository.
                findByTypeNameAndPlaceId(ticketTypeDTO.getTypeName(), ticketTypeDTO.getPlaceId());
        if (null != existedTicketType && existedTicketType.getId() != ticketTypeDTO.getId()) {
            return new ResponseEntity("TICKET_TYPE_EXISTED", HttpStatus.BAD_REQUEST);
        }
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

    //change status of ticket type
    @Override
    public ResponseEntity<?> changeStatus(Long id) {
        TicketType ticketType = ticketTypeRepository.findById(id).get();
        for (VisitorType visitorType : visitorTypeRepository.findByTicketType(ticketType)) {
            if (visitorType.isBasicType()) {
                return new ResponseEntity("VISITOR_TYPE_IS_BASIC", HttpStatus.BAD_REQUEST);
            }
        }
        if (ticketType.getStatus().equals(MonoStatus.ACTIVE.toString())) {
            ticketType.setStatus(MonoStatus.DEACTIVATE.toString());
        } else {
            ticketType.setStatus(MonoStatus.ACTIVE.toString());
        }
        ticketType = ticketTypeRepository.save(ticketType);
        return ResponseEntity.ok(ticketTypeConverter.toDTO(ticketType));
    }

    //search by placeId
    @Override
    public ResponseEntity<?> findByPlaceId(long placeId, Date date) {
        List<TicketTypeDTO> list = new ArrayList<>();
        List<TicketType> ticketTypes = ticketTypeRepository.findByPlaceId(placeId);
        OutputExcel output = new OutputExcel();
        if (ticketTypes.size() > 0) {
            for (TicketType ticketType : ticketTypes) {
                TicketTypeDTO ticketTypeDTO = ticketTypeConverter.toDTO(ticketType);
                List<VisitorType> visitorTypes = visitorTypeRepository.findByTicketType(ticketType);
                if (visitorTypes.size() > 0) {
                    output.setImportExcel(true);
                    List<VisitorTypeDTO> visitorTypeDTOS = new ArrayList<>();
                    for (VisitorType type : visitorTypes) {
                        VisitorTypeDTO visitorTypeDTO = visitorTypeConverter.toDTO(type);
                        visitorTypeDTO.setRemaining(codeRepository.countByVisitorTypeReaming(type, date));
                        visitorTypeDTOS.add(visitorTypeDTO);

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
        String[] days = placeRepository.getWeekdaysById(placeId).split(",");
        List<Integer> weekDays = new ArrayList<>();
        for(String day: days){
            weekDays.add(Integer.parseInt(day));
        }
        output.setWeekdays(weekDays);
        output.setListResult(list);
        return ResponseEntity.ok(output);
    }

    //search by Id
    @Override
    public ResponseEntity<?> getTicketType(Long id) {
        TicketTypeDTO dto = ticketTypeConverter.toDTO(ticketTypeRepository.findById(id).get());
        return ResponseEntity.ok(dto);
    }

    // import code from excel
    @Override
    public ResponseEntity<?> addCodeFromExcel(MultipartFile file, long placeId, Date date) {
        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                //get code from excel 
                List<Code> codes = ExcelHelper.excelToCode(file.getInputStream());
                List<VisitorType> visitorTypes = visitorTypeRepository.findByPlaceId(placeId);
                int uniqle = 0;
                int wrongKey = 0;
                for (Iterator<Code> it = codes.iterator(); it.hasNext(); ) {
                    Code code = it.next();
                    if (visitorTypes.contains(code.getVisitorType())) {
                        try {
                            code.setRedemptionDate(date);
                            codeRepository.save(code);
                        } catch (DataIntegrityViolationException e) {
                            uniqle += 1;
                            it.remove();
                        }
                    } else {
                        wrongKey += 1;
                        it.remove();
                    }
                }
                for(VisitorType visitorType: visitorTypes){
                    int count = Math.toIntExact(codes.stream().filter(c -> c.getVisitorType().equals(visitorType)).count());
                    Remaining remaining = remainingRepository.findByRedemptionDateAndVisitorTypeId(date, visitorType.getId());
                    if(remaining == null){
                        remaining = new Remaining();
                        remaining.setRedemptionDate(date);
                        remaining.setTotal(count);
                        remaining.setVisitorTypeId(visitorType.getId());
                    }else{
                        remaining.setTotal(remaining.getTotal() + count);
                    }
                    remainingRepository.save(remaining);
                }
                if (wrongKey > 0 || uniqle > 0) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Wrong key: " + wrongKey + "\nDuplicate: " + uniqle);
                } else
                    return ResponseEntity.ok("OK");
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("COULD_NOT_UPLOAD_FILE");
            }
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NOT_EXCEL_FILE");

    }
}
