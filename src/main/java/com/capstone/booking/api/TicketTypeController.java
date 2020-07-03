package com.capstone.booking.api;

import com.capstone.booking.entity.dto.TicketTypeDTO;
import com.capstone.booking.service.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TicketTypeController {

    @Autowired
    private TicketTypeService ticketTypeService;

    //find all
    @GetMapping("/ticketType")
    public ResponseEntity<?> searchAll() {
        return ticketTypeService.findAll();
    }

    //tim kiem theo TicketTypeName & paging
    @GetMapping("/ticketType/searchTypeName")
    public ResponseEntity<?> searchTypeName(@RequestParam(value = "typeName", required = false) String typeName,
                                            @RequestParam(value = "limit", required = false) Long limit,
                                            @RequestParam(value = "page", required = false) Long page) {
        return ticketTypeService.findByTypeName(typeName, limit, page);
    }

    //delete ticketType
    @DeleteMapping("/ticketType/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return ticketTypeService.delete(id);
    }

    //add ticketType
    @PostMapping("/ticketType")
    public ResponseEntity<?> create(@RequestBody TicketTypeDTO model) {
        return ticketTypeService.create(model);
    }

    //edit ticketType
    @PutMapping("/ticketType/{id}")
    public ResponseEntity<?> update(@RequestBody TicketTypeDTO model, @PathVariable("id") long id) {
        model.setId(id);
        return ticketTypeService.update(model);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestPart(value = "file") MultipartFile file,
                                        @RequestPart(value = "codeType") String codeType) {
        return ticketTypeService.addCodeForTicketType(file, codeType);
    }

    //search by Id
    @GetMapping("/ticketType/{id}")
    public ResponseEntity<?> getTicketType(@PathVariable Long id) {
        return ticketTypeService.getTicketType(id);
    }

}
