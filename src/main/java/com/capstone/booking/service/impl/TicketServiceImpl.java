package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.OutputReport;
import com.capstone.booking.api.output.ReportItem;
import com.capstone.booking.common.converter.TicketConverter;
import com.capstone.booking.entity.Ticket;
import com.capstone.booking.entity.TicketType;
import com.capstone.booking.entity.VisitorType;
import com.capstone.booking.entity.dto.TicketDTO;
import com.capstone.booking.repository.*;
import com.capstone.booking.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketConverter ticketConverter;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Autowired
    private VisitorTypeRepository visitorTypeRepository;

    //add
    @Override
    public ResponseEntity<?> create(TicketDTO ticketDTO) {
        Ticket ticket = ticketConverter.toTicket(ticketDTO);

        ticketRepository.save(ticket);
        return ResponseEntity.ok(ticketConverter.toDTO(ticket));
    }

    //edit
    @Override
    public ResponseEntity<?> update(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        Ticket oldTicket = ticketRepository.findById(ticketDTO.getId()).get();
        ticket = ticketConverter.toTicket(ticketDTO, oldTicket);

        ticketRepository.save(ticket);
        return ResponseEntity.ok(ticketConverter.toDTO(ticket));
    }

    //delete
    @Override
    @Transactional
    public ResponseEntity<?> delete(long id) {
        if (!ticketRepository.findById(id).isPresent()) {
            return new ResponseEntity("TICKET_NOT_FOUND", HttpStatus.BAD_REQUEST);
        }
        ticketRepository.deleteById(id);
        return new ResponseEntity("DELETE_SUCCESSFUL", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getReport(Long placeId, Long reportType, Long startDateL, Long endDateL) {
        Date endDate = new Date();
        Date startDate = new Date();
        if (reportType == 1) {
            endDate = new Date();
            startDate = setDateBefore(7);
        } else if (reportType == 2) {
            endDate = new Date();
            startDate = setDateBefore(30);
        }else if(reportType == 3){
            endDate = new Date(endDateL);
            startDate = new Date(startDateL);
        }
        List<ReportItem> reportItems = new ArrayList<>();
        List<TicketType> ticketTypes = ticketTypeRepository.findByPlaceId(placeId);
        for (TicketType ticketType : ticketTypes) {
            for (VisitorType visitorType : visitorTypeRepository.findByTicketType(ticketType)) {
                ReportItem reportItem = new ReportItem();
                reportItem.setTicketTypeName(ticketType.getTypeName() + "[" + visitorType.getTypeName() + "]");
                int quantity = ticketRepository.getAllBetweenDates(visitorType.getId(), startDate, endDate).size();
                reportItem.setQuantity(quantity);
                reportItem.setTotal(quantity * visitorType.getPrice() * quantity);
                reportItems.add(reportItem);
            }
        }
        OutputReport outputReport = new OutputReport();
        outputReport.setEndDate(endDateL);
        outputReport.setStartDate(startDateL);
        outputReport.setPlaceId(placeId);
        outputReport.setReportType(reportType);
        outputReport.setReportItemList(reportItems);
        return ResponseEntity.ok(outputReport);
    }

    private Date setDateBefore(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1*days);
        return cal.getTime();
    }
}
