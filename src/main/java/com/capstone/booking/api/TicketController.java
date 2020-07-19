package com.capstone.booking.api;

import com.capstone.booking.api.output.OutputReport;
import com.capstone.booking.entity.dto.TicketDTO;
import com.capstone.booking.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;

//ticket's api
@RestController
public class TicketController {

    @Autowired
    private TicketService ticketService;


    //add api
    @PostMapping("/ticket")
    public ResponseEntity<?> create(@RequestBody TicketDTO model) {
        return ticketService.create(model);
    }

    //edit api
    @PutMapping("/ticket/{id}")
    public ResponseEntity<?> update(@RequestBody TicketDTO model, @PathVariable("id") long id){
        model.setId(id);
        return ticketService.update(model);
    }

    //delete api
    @DeleteMapping("/ticket/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return ticketService.delete(id);
    }

    //get report api
    @GetMapping("/report")
    public ResponseEntity<?> searchForReport(@RequestParam(value = "placeId") Long placeId,
                                             @RequestParam(value = "type") Long reportType,
                                             @RequestParam(value = "startDate", required = false) Long startDate,
                                             @RequestParam(value = "endDate", required = false) Long endDate){
        return ticketService.getReport(placeId, reportType, startDate, endDate);
    }

    //send report api
    @PostMapping("/sendReport")
    public ResponseEntity<?> sendReport(@RequestBody OutputReport report) throws IOException, MessagingException {
        return ticketService.createReport(report);
    }
}
