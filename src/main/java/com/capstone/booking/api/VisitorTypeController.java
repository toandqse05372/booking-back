package com.capstone.booking.api;

import com.capstone.booking.entity.dto.VisitorTypeDTO;
import com.capstone.booking.service.VisitorTypeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//visitor type api
@RestController
public class VisitorTypeController {

    @Autowired
    VisitorTypeService visitorTypeService;

    //add api
    @PostMapping("/visitorType")
    @PreAuthorize("hasAnyAuthority('VISITOR_TYPE_EDIT')")
    public ResponseEntity<?> create(@RequestPart(value = "placeId") String placeIdStr,
                                    @RequestPart(value = "visitorType") String model) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        VisitorTypeDTO visitorTypeDTO = mapper.readValue(model, VisitorTypeDTO.class);
        Long placeId = Long.parseLong(placeIdStr);
        return visitorTypeService.create(visitorTypeDTO, placeId);
    }

    //edit api
    @PutMapping("/visitorType/{id}")
    @PreAuthorize("hasAnyAuthority('VISITOR_TYPE_EDIT')")
    public ResponseEntity<?> update(@RequestBody VisitorTypeDTO model, @PathVariable("id") long id) {
        model.setId(id);
        return visitorTypeService.update(model);
    }

    //mark basic price api
    @PutMapping("/markPrice/{id}")
    @PreAuthorize("hasAnyAuthority('VISITOR_TYPE_EDIT')")
    public ResponseEntity<?> markBasicPrice(@PathVariable("id") long id,
                                            @RequestPart(value = "placeId") String placeId) {
        return visitorTypeService.markBasicPrice(id, Long.parseLong(placeId));
    }

    //delete
    @DeleteMapping("/visitorType/{id}")
    @PreAuthorize("hasAnyAuthority('VISITOR_TYPE_EDIT')")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return visitorTypeService.delete(id);
    }

    //change status visitor type api
    @PutMapping("/changeVisitorType/{id}")
    @PreAuthorize("hasAnyAuthority('VISITOR_TYPE_EDIT')")
    public ResponseEntity<?> changeVisitorTypeStatus(@PathVariable("id") long id)  {
        return visitorTypeService.changeStatus(id);
    }

    //search by Id
    @GetMapping("/visitorType/{id}")
    public ResponseEntity<?> getVisitorType(@PathVariable Long id) {
        return visitorTypeService.getById(id);
    }

    //search by ticketTypeId
    @GetMapping("/visitorType")
    public ResponseEntity<?> findByTicketTypeId(@RequestParam(value = "ticketTypeId", required = false) Long ticketTypeId) {
        return visitorTypeService.findByTicketTypeId(ticketTypeId);
    }

    //not used
    @PostMapping("/uploadVisitorFile")
    @PreAuthorize("hasAnyAuthority('TICKET_TYPE_EDIT')")
    public ResponseEntity<?> uploadFile(@RequestPart(value = "file") MultipartFile file,
                                        @RequestPart(value = "codeType") String codeType) {
        return visitorTypeService.addCodeForTicketType(file, codeType);
    }

}
