package com.capstone.booking.api;

import com.capstone.booking.entity.dto.VisitorTypeDTO;
import com.capstone.booking.service.VisitorTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class VisitorTypeController {
    @Autowired
    VisitorTypeService visitorTypeService;

    //them
    @PostMapping("/visitorType")
    public ResponseEntity<?> create(@RequestBody VisitorTypeDTO model) {
        return visitorTypeService.create(model);
    }

    //sửa
    @PutMapping("/visitorType/{id}")
    public ResponseEntity<?> update(@RequestBody VisitorTypeDTO model, @PathVariable("id") long id) {
        model.setId(id);
        return visitorTypeService.update(model);
    }

    //xóa
    @DeleteMapping("/visitorType/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return visitorTypeService.delete(id);
    }

    //search by Id
    @GetMapping("/visitorType/{id}")
    public ResponseEntity<?> getVisitorType(@PathVariable Long id) {
        return visitorTypeService.getById(id);
    }

    //search by Id
    @GetMapping("/visitorType")
    public ResponseEntity<?> findByTicketTypeId(@RequestParam(value = "ticketTypeId", required = false) Long ticketTypeId) {
        return visitorTypeService.findByTicketTypeId(ticketTypeId);
    }

}
