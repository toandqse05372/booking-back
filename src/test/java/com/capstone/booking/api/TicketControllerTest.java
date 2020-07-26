package com.capstone.booking.api;

import com.capstone.booking.api.output.OutputReport;
import com.capstone.booking.api.output.ReportItem;
import com.capstone.booking.entity.dto.TicketDTO;
import com.capstone.booking.entity.dto.VisitorTypeDTO;
import com.capstone.booking.service.TicketService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TicketControllerTest {

    @Mock
    private TicketService mockTicketService;

    @InjectMocks
    private TicketController ticketControllerUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testCreate() {
        // Setup
        final TicketDTO model = new TicketDTO();
        model.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        model.setCode("code");
        final VisitorTypeDTO visitorType = new VisitorTypeDTO();
        visitorType.setTypeName("typeName");
        visitorType.setTypeKey("typeKey");
        visitorType.setTicketTypeId(0L);
        visitorType.setPrice(0);
        visitorType.setBasicType(false);
        visitorType.setRemaining(0);
        visitorType.setStatus("status");
        model.setVisitorType(visitorType);
        model.setVisitorTypeId(0L);

        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockTicketService).create(model);

        // Run the test
        final ResponseEntity<?> result = ticketControllerUnderTest.create(model);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testDelete() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockTicketService).delete(0L);

        // Run the test
        final ResponseEntity<?> result = ticketControllerUnderTest.delete(0L);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testSearchForReport() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockTicketService).getReport(0L, 0L, 0L, 0L);

        // Run the test
        final ResponseEntity<?> result = ticketControllerUnderTest.searchForReport(0L, 0L, 0L, 0L);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testSendReport() throws Exception {
        // Setup
        final OutputReport report = new OutputReport();
        final ReportItem item = new ReportItem();
        item.setTicketTypeName("ticketTypeName");
        item.setQuantity(0);
        item.setTotal(0);
        report.setReportItemList(Arrays.asList(item));
        report.setStartDate(0L);
        report.setEndDate(0L);
        report.setReportType(0L);
        report.setPlaceId(0L);
        report.setTotalRevenue(0);

        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockTicketService).createReport(report);

        // Run the test
        final ResponseEntity<?> result = ticketControllerUnderTest.sendReport(report);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }
}
