package com.capstone.booking.api;

import com.capstone.booking.entity.dto.TicketTypeDTO;
import com.capstone.booking.service.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

//ticket type api
@RestController
public class TicketTypeController {

    @Autowired
    private TicketTypeService ticketTypeService;

    //find all (not used)
    @GetMapping("/ticketTypes")
    public ResponseEntity<?> searchAll() {
        return ticketTypeService.findAll();
    }

    //search ticketType by PlaceId
    @GetMapping("/ticketType")
    public ResponseEntity<?> searchByPlaceId(@RequestParam(value = "placeId") Long placeId,
                                             @RequestParam(value = "date") String date  ) throws ParseException {
        return ticketTypeService.findByPlaceId(placeId, convertDate(date));
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

    //change status ticket type api
    @PutMapping("/changeTicketType/{id}")
    @PreAuthorize("hasAnyAuthority('TICKET_TYPE_EDIT')")
    public ResponseEntity<?> changeTicketTypeStatus(@PathVariable("id") long id)  {
        return ticketTypeService.changeStatus(id);
    }

    //search by Id
    @GetMapping("/ticketType/{id}")
    public ResponseEntity<?> getTicketType(@PathVariable Long id) {
        return ticketTypeService.getTicketType(id);
    }

    //import code from excel api
    @PostMapping("/upload")
    @PreAuthorize("hasAnyAuthority('TICKET_TYPE_EDIT')")
    public ResponseEntity<?> uploadFile(@RequestPart(value = "file") MultipartFile file,
                                        @RequestPart(value = "placeId") String placeId,
                                        @RequestPart(value = "date") String dateStr) throws ParseException {
        return ticketTypeService.addCodeFromExcel(file, Long.parseLong(placeId), convertDate(dateStr));
    }

    public Date convertDate(String dateStr) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse(dateStr);
        TimeZone tz = TimeZone.getDefault();
        date = new Date(date.getTime() + tz.getRawOffset());
        return date;
    }
}
