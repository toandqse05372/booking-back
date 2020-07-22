package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.OutputReport;
import com.capstone.booking.api.output.ReportItem;
import com.capstone.booking.common.converter.TicketConverter;
import com.capstone.booking.common.helper.ExcelHelper;
import com.capstone.booking.entity.Order;
import com.capstone.booking.entity.Ticket;
import com.capstone.booking.entity.TicketType;
import com.capstone.booking.entity.VisitorType;
import com.capstone.booking.entity.dto.TicketDTO;
import com.capstone.booking.repository.*;
import com.capstone.booking.service.TicketService;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.*;
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

    @Autowired
    public JavaMailSender emailSender;

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

    // calculate number of sale tickets of a place
    @Override
    public ResponseEntity<?> getReport(Long placeId, Long reportType, Long startDateL, Long endDateL) {
        Date endDate = new Date();
        Date startDate = new Date();
        if (reportType == 0) {
            endDate = new Date();
            startDate = setDateBefore(1);
        } else if (reportType == 1) {
            endDate = new Date();
            startDate = setDateBefore(7);
        } else if (reportType == 2) {
            endDate = new Date();
            startDate = setDateBefore(30);
        } else if (reportType == 3) {
            endDate = new Date(endDateL);
            startDate = new Date(startDateL);
        }
        List<ReportItem> reportItems = new ArrayList<>();
        // get all ticket type of place
        List<TicketType> ticketTypes = ticketTypeRepository.findByPlaceId(placeId);
        int totalRevenue = 0;
        for (TicketType ticketType : ticketTypes) {
            //get all visitor type of a ticket type
            for (VisitorType visitorType : visitorTypeRepository.findByTicketType(ticketType)) {
                ReportItem reportItem = new ReportItem();
                reportItem.setTicketTypeName(ticketType.getTypeName() + " [" + visitorType.getTypeName() + "]");
                //calculate tickets
                int quantity = ticketRepository.getAllBetweenDates(visitorType.getId(), startDate, endDate).size();
                reportItem.setQuantity(quantity);
                int total = quantity * visitorType.getPrice() * quantity;
                reportItem.setTotal(total);
                reportItems.add(reportItem);
                totalRevenue += total;
            }
        }
        OutputReport outputReport = new OutputReport();
        outputReport.setEndDate(endDateL);
        outputReport.setStartDate(startDateL);
        outputReport.setPlaceId(placeId);
        outputReport.setReportType(reportType);
        outputReport.setReportItemList(reportItems);
        outputReport.setTotalRevenue(totalRevenue);
        return ResponseEntity.ok(outputReport);
    }

    // get report and send to place
    @Override
    public ResponseEntity<?> createReport(OutputReport report) throws IOException, MessagingException {
        Date endDate = new Date();
        Date startDate = new Date();
        if (report.getReportType() == 0) {
            endDate = new Date();
            startDate = setDateBefore(1);
        } else if (report.getReportType() == 1) {
            endDate = new Date();
            startDate = setDateBefore(7);
        } else if (report.getReportType() == 2) {
            endDate = new Date();
            startDate = setDateBefore(30);
        } else if (report.getReportType() == 3) {
            endDate = new Date(report.getEndDate());
            startDate = new Date(report.getStartDate());
        }
        ExcelHelper.writeExcel(report.getReportItemList(), "NiceJavaBooks.xls");
        sendEmail(new File("NiceJavaBooks.xls"));
        return ResponseEntity.ok("OK");
    }

    // get date from past
    private Date setDateBefore(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1 * days);
        return cal.getTime();
    }

    //send email
    public void sendEmail(File file) throws MessagingException, IOException {
        MimeMessage message = emailSender.createMimeMessage();

        boolean multipart = true;

        MimeMessageHelper helper = new MimeMessageHelper(message, multipart);

        helper.setTo("quangtoandao123@gmail.com");
        helper.setSubject("Test email with attachments");

        helper.setText("Hello, Im testing email with attachments!");

        String path1 = file.getPath();

        // Attachment 1
        FileSystemResource file1 = new FileSystemResource(new File(path1));
        helper.addAttachment("Report.xls", file1);
        emailSender.send(message);
    }
}
