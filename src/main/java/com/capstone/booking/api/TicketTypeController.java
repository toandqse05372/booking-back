package com.capstone.booking.api;

import com.capstone.booking.entity.dto.TicketTypeDTO;
import com.capstone.booking.service.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TicketTypeController {

    @Autowired
    private TicketTypeService ticketTypeService;

    //find all
    @GetMapping("/ticketTypes")
    public ResponseEntity<?> searchAll() {
        return ticketTypeService.findAll();
    }

    //search ticketType by typeName & paging
    @GetMapping("/ticketType/searchTypeName")
    @PreAuthorize("hasAnyAuthority('TICKET_TYPE_EDIT')")
    public ResponseEntity<?> searchTypeName(@RequestParam(value = "typeName", required = false) String typeName,
                                            @RequestParam(value = "limit", required = false) Long limit,
                                            @RequestParam(value = "page", required = false) Long page) {
        return ticketTypeService.findByTypeName(typeName, limit, page);
    }

    //search ticketType by PlaceId
    @GetMapping("/ticketType")
    public ResponseEntity<?> searchByPlaceId(@RequestParam(value = "placeId", required = false) Long placeId){
        return ticketTypeService.findByPlaceId(placeId);
    }


    //delete ticketType
    @DeleteMapping("/ticketType/{id}")
    @PreAuthorize("hasAnyAuthority('TICKET_TYPE_EDIT')")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return ticketTypeService.delete(id);
    }

    //add ticketType
    @PostMapping("/ticketType")
    @PreAuthorize("hasAnyAuthority('TICKET_TYPE_EDIT')")
    public ResponseEntity<?> create(@RequestBody TicketTypeDTO model) {
        return ticketTypeService.create(model);
    }

    //edit ticketType
    @PutMapping("/ticketType/{id}")
    @PreAuthorize("hasAnyAuthority('TICKET_TYPE_EDIT')")
    public ResponseEntity<?> update(@RequestBody TicketTypeDTO model, @PathVariable("id") long id) {
        model.setId(id);
        return ticketTypeService.update(model);
    }

    //search by Id
    @GetMapping("/ticketType/{id}")
    public ResponseEntity<?> getTicketType(@PathVariable Long id) {
        return ticketTypeService.getTicketType(id);
    }

    @PostMapping("/upload")
    @PreAuthorize("hasAnyAuthority('TICKET_TYPE_EDIT')")
    public ResponseEntity<?> uploadFile(@RequestPart(value = "file") MultipartFile file){
        return ticketTypeService.addCodeForTicketType(file);
    }
}
