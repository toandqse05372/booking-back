package com.capstone.booking.service;

import com.capstone.booking.api.output.OutputReport;
import com.capstone.booking.entity.dto.TicketDTO;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface TicketService {

    //edd
    ResponseEntity<?> create(TicketDTO ticketDTO);

    //edit
    ResponseEntity<?> update(TicketDTO ticketDTO);

    //delete
    ResponseEntity<?> delete(long id);

    ResponseEntity<?> getReport(Long placeId, Long reportType, Long startDate, Long endDate);

    ResponseEntity<?> sendReport(OutputReport report) throws IOException, MessagingException;
}
