package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.OutputReport;
import com.capstone.booking.api.output.ReportItem;
import com.capstone.booking.common.converter.TicketConverter;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.TicketDTO;
import com.capstone.booking.entity.dto.VisitorTypeDTO;
import com.capstone.booking.repository.TicketRepository;
import com.capstone.booking.repository.TicketTypeRepository;
import com.capstone.booking.repository.VisitorTypeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class TicketServiceImplTest {

    @Mock
    private TicketRepository mockTicketRepository;
    @Mock
    private TicketConverter mockTicketConverter;
    @Mock
    private TicketTypeRepository mockTicketTypeRepository;
    @Mock
    private VisitorTypeRepository mockVisitorTypeRepository;
    @Mock
    private JavaMailSender mockEmailSender;

    @InjectMocks
    private TicketServiceImpl ticketServiceImplUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testCreate() {
        // Setup
        final TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticketDTO.setCode("code");
        final VisitorTypeDTO visitorType = new VisitorTypeDTO();
        visitorType.setTypeName("typeName");
        visitorType.setTypeKey("typeKey");
        visitorType.setTicketTypeId(0L);
        visitorType.setPrice(0);
        visitorType.setBasicType(false);
        visitorType.setRemaining(0);
        visitorType.setStatus("status");
        ticketDTO.setVisitorType(visitorType);
        ticketDTO.setVisitorTypeId(0L);

        // Configure TicketConverter.toTicket(...).
        final Ticket ticket = new Ticket();
        ticket.setCode("code");
        ticket.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket.setVisitorTypeId(0L);
        final OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(0);
        final VisitorType visitorType1 = new VisitorType();
        visitorType1.setTypeName("typeName");
        visitorType1.setTypeKey("typeKey");
        visitorType1.setPrice(0);
        visitorType1.setBasicType(false);
        visitorType1.setStatus("status");
        final TicketType ticketType = new TicketType();
        ticketType.setTypeName("typeName");
        ticketType.setPlaceId(0L);
        ticketType.setStatus("status");
        ticketType.setVisitorType(new HashSet<>(Arrays.asList(new VisitorType())));
        final Game game = new Game();
        game.setGameName("gameName");
        game.setGameDescription("gameDescription");
        game.setStatus("status");
        game.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
        final Place place = new Place();
        place.setName("name");
        place.setPlaceKey("placeKey");
        place.setAddress("address");
        place.setDetailDescription("detailDescription");
        place.setShortDescription("shortDescription");
        place.setMail("mail");
        place.setPhoneNumber("phoneNumber");
        place.setStatus("status");
        place.setLocation("location");
        place.setCancelPolicy("cancelPolicy");
        game.setPlace(place);
        ticketType.setGame(new HashSet<>(Arrays.asList(game)));
        visitorType1.setTicketType(ticketType);
        visitorType1.setOrderItem(new HashSet<>(Arrays.asList(new OrderItem())));
        final Code code = new Code();
        code.setCode("code");
        code.setVisitorType(new VisitorType());
        visitorType1.setCode(new HashSet<>(Arrays.asList(code)));
        orderItem.setVisitorType(visitorType1);
        final Order order = new Order();
        order.setTicketTypeId(0L);
        order.setFirstName("firstName");
        order.setLastName("lastName");
        order.setMail("mail");
        order.setPhoneNumber("phoneNumber");
        order.setStatus("status");
        order.setOrderCode("orderCode");
        order.setTotalPayment(0);
        order.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        orderItem.setOrder(order);
        orderItem.setTicket(new HashSet<>(Arrays.asList(new Ticket())));
        ticket.setOrderItem(orderItem);
        when(mockTicketConverter.toTicket(new TicketDTO())).thenReturn(ticket);

        // Configure TicketRepository.save(...).
        final Ticket ticket1 = new Ticket();
        ticket1.setCode("code");
        ticket1.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket1.setVisitorTypeId(0L);
        final OrderItem orderItem1 = new OrderItem();
        orderItem1.setQuantity(0);
        final VisitorType visitorType2 = new VisitorType();
        visitorType2.setTypeName("typeName");
        visitorType2.setTypeKey("typeKey");
        visitorType2.setPrice(0);
        visitorType2.setBasicType(false);
        visitorType2.setStatus("status");
        final TicketType ticketType1 = new TicketType();
        ticketType1.setTypeName("typeName");
        ticketType1.setPlaceId(0L);
        ticketType1.setStatus("status");
        ticketType1.setVisitorType(new HashSet<>(Arrays.asList(new VisitorType())));
        final Game game1 = new Game();
        game1.setGameName("gameName");
        game1.setGameDescription("gameDescription");
        game1.setStatus("status");
        game1.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
        final Place place1 = new Place();
        place1.setName("name");
        place1.setPlaceKey("placeKey");
        place1.setAddress("address");
        place1.setDetailDescription("detailDescription");
        place1.setShortDescription("shortDescription");
        place1.setMail("mail");
        place1.setPhoneNumber("phoneNumber");
        place1.setStatus("status");
        place1.setLocation("location");
        place1.setCancelPolicy("cancelPolicy");
        game1.setPlace(place1);
        ticketType1.setGame(new HashSet<>(Arrays.asList(game1)));
        visitorType2.setTicketType(ticketType1);
        visitorType2.setOrderItem(new HashSet<>(Arrays.asList(new OrderItem())));
        final Code code1 = new Code();
        code1.setCode("code");
        code1.setVisitorType(new VisitorType());
        visitorType2.setCode(new HashSet<>(Arrays.asList(code1)));
        orderItem1.setVisitorType(visitorType2);
        final Order order1 = new Order();
        order1.setTicketTypeId(0L);
        order1.setFirstName("firstName");
        order1.setLastName("lastName");
        order1.setMail("mail");
        order1.setPhoneNumber("phoneNumber");
        order1.setStatus("status");
        order1.setOrderCode("orderCode");
        order1.setTotalPayment(0);
        order1.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order1.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        orderItem1.setOrder(order1);
        orderItem1.setTicket(new HashSet<>(Arrays.asList(new Ticket())));
        ticket1.setOrderItem(orderItem1);
        when(mockTicketRepository.save(any(Ticket.class))).thenReturn(ticket1);

        // Configure TicketConverter.toDTO(...).
        final TicketDTO ticketDTO1 = new TicketDTO();
        ticketDTO1.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticketDTO1.setCode("code");
        final VisitorTypeDTO visitorType3 = new VisitorTypeDTO();
        visitorType3.setTypeName("typeName");
        visitorType3.setTypeKey("typeKey");
        visitorType3.setTicketTypeId(0L);
        visitorType3.setPrice(0);
        visitorType3.setBasicType(false);
        visitorType3.setRemaining(0);
        visitorType3.setStatus("status");
        ticketDTO1.setVisitorType(visitorType3);
        ticketDTO1.setVisitorTypeId(0L);
        when(mockTicketConverter.toDTO(any(Ticket.class))).thenReturn(ticketDTO1);

        // Run the test
        final ResponseEntity<?> result = ticketServiceImplUnderTest.create(ticketDTO);

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void testDelete() {
        // Setup

        // Configure TicketRepository.findById(...).
        final Ticket ticket1 = new Ticket();
        ticket1.setCode("code");
        ticket1.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket1.setVisitorTypeId(0L);
        final OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(0);
        final VisitorType visitorType = new VisitorType();
        visitorType.setTypeName("typeName");
        visitorType.setTypeKey("typeKey");
        visitorType.setPrice(0);
        visitorType.setBasicType(false);
        visitorType.setStatus("status");
        final TicketType ticketType = new TicketType();
        ticketType.setTypeName("typeName");
        ticketType.setPlaceId(0L);
        ticketType.setStatus("status");
        ticketType.setVisitorType(new HashSet<>(Arrays.asList(new VisitorType())));
        final Game game = new Game();
        game.setGameName("gameName");
        game.setGameDescription("gameDescription");
        game.setStatus("status");
        game.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
        final Place place = new Place();
        place.setName("name");
        place.setPlaceKey("placeKey");
        place.setAddress("address");
        place.setDetailDescription("detailDescription");
        place.setShortDescription("shortDescription");
        place.setMail("mail");
        place.setPhoneNumber("phoneNumber");
        place.setStatus("status");
        place.setLocation("location");
        place.setCancelPolicy("cancelPolicy");
        game.setPlace(place);
        ticketType.setGame(new HashSet<>(Arrays.asList(game)));
        visitorType.setTicketType(ticketType);
        visitorType.setOrderItem(new HashSet<>(Arrays.asList(new OrderItem())));
        final Code code = new Code();
        code.setCode("code");
        code.setVisitorType(new VisitorType());
        visitorType.setCode(new HashSet<>(Arrays.asList(code)));
        orderItem.setVisitorType(visitorType);
        final Order order = new Order();
        order.setTicketTypeId(0L);
        order.setFirstName("firstName");
        order.setLastName("lastName");
        order.setMail("mail");
        order.setPhoneNumber("phoneNumber");
        order.setStatus("status");
        order.setOrderCode("orderCode");
        order.setTotalPayment(0);
        order.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        orderItem.setOrder(order);
        orderItem.setTicket(new HashSet<>(Arrays.asList(new Ticket())));
        ticket1.setOrderItem(orderItem);
        final Optional<Ticket> ticket = Optional.of(ticket1);
        when(mockTicketRepository.findById(0L)).thenReturn(ticket);

        // Run the test
        final ResponseEntity<?> result = ticketServiceImplUnderTest.delete(0L);

        // Verify the results
        verify(mockTicketRepository).deleteById(0L);
    }

    @Test
    public void testGetReport() {
        // Setup

        // Configure TicketTypeRepository.findByPlaceId(...).
        final TicketType ticketType = new TicketType();
        ticketType.setTypeName("typeName");
        ticketType.setPlaceId(0L);
        ticketType.setStatus("status");
        final VisitorType visitorType = new VisitorType();
        visitorType.setTypeName("typeName");
        visitorType.setTypeKey("typeKey");
        visitorType.setPrice(0);
        visitorType.setBasicType(false);
        visitorType.setStatus("status");
        visitorType.setTicketType(new TicketType());
        final OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(0);
        orderItem.setVisitorType(new VisitorType());
        final Order order = new Order();
        order.setTicketTypeId(0L);
        order.setFirstName("firstName");
        order.setLastName("lastName");
        order.setMail("mail");
        order.setPhoneNumber("phoneNumber");
        order.setStatus("status");
        order.setOrderCode("orderCode");
        order.setTotalPayment(0);
        order.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        orderItem.setOrder(order);
        final Ticket ticket = new Ticket();
        ticket.setCode("code");
        ticket.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket.setVisitorTypeId(0L);
        ticket.setOrderItem(new OrderItem());
        orderItem.setTicket(new HashSet<>(Arrays.asList(ticket)));
        visitorType.setOrderItem(new HashSet<>(Arrays.asList(orderItem)));
        final Code code = new Code();
        code.setCode("code");
        code.setVisitorType(new VisitorType());
        visitorType.setCode(new HashSet<>(Arrays.asList(code)));
        ticketType.setVisitorType(new HashSet<>(Arrays.asList(visitorType)));
        final Game game = new Game();
        game.setGameName("gameName");
        game.setGameDescription("gameDescription");
        game.setStatus("status");
        game.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
        final Place place = new Place();
        place.setName("name");
        place.setPlaceKey("placeKey");
        place.setAddress("address");
        place.setDetailDescription("detailDescription");
        place.setShortDescription("shortDescription");
        place.setMail("mail");
        place.setPhoneNumber("phoneNumber");
        place.setStatus("status");
        place.setLocation("location");
        place.setCancelPolicy("cancelPolicy");
        game.setPlace(place);
        ticketType.setGame(new HashSet<>(Arrays.asList(game)));
        final List<TicketType> ticketTypes = Arrays.asList(ticketType);
        when(mockTicketTypeRepository.findByPlaceId(0L)).thenReturn(ticketTypes);

        // Configure VisitorTypeRepository.findByTicketType(...).
        final VisitorType visitorType1 = new VisitorType();
        visitorType1.setTypeName("typeName");
        visitorType1.setTypeKey("typeKey");
        visitorType1.setPrice(0);
        visitorType1.setBasicType(false);
        visitorType1.setStatus("status");
        final TicketType ticketType1 = new TicketType();
        ticketType1.setTypeName("typeName");
        ticketType1.setPlaceId(0L);
        ticketType1.setStatus("status");
        ticketType1.setVisitorType(new HashSet<>(Arrays.asList(new VisitorType())));
        final Game game1 = new Game();
        game1.setGameName("gameName");
        game1.setGameDescription("gameDescription");
        game1.setStatus("status");
        game1.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
        final Place place1 = new Place();
        place1.setName("name");
        place1.setPlaceKey("placeKey");
        place1.setAddress("address");
        place1.setDetailDescription("detailDescription");
        place1.setShortDescription("shortDescription");
        place1.setMail("mail");
        place1.setPhoneNumber("phoneNumber");
        place1.setStatus("status");
        place1.setLocation("location");
        place1.setCancelPolicy("cancelPolicy");
        game1.setPlace(place1);
        ticketType1.setGame(new HashSet<>(Arrays.asList(game1)));
        visitorType1.setTicketType(ticketType1);
        final OrderItem orderItem1 = new OrderItem();
        orderItem1.setQuantity(0);
        orderItem1.setVisitorType(new VisitorType());
        final Order order1 = new Order();
        order1.setTicketTypeId(0L);
        order1.setFirstName("firstName");
        order1.setLastName("lastName");
        order1.setMail("mail");
        order1.setPhoneNumber("phoneNumber");
        order1.setStatus("status");
        order1.setOrderCode("orderCode");
        order1.setTotalPayment(0);
        order1.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order1.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        orderItem1.setOrder(order1);
        final Ticket ticket1 = new Ticket();
        ticket1.setCode("code");
        ticket1.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket1.setVisitorTypeId(0L);
        ticket1.setOrderItem(new OrderItem());
        orderItem1.setTicket(new HashSet<>(Arrays.asList(ticket1)));
        visitorType1.setOrderItem(new HashSet<>(Arrays.asList(orderItem1)));
        final Code code1 = new Code();
        code1.setCode("code");
        code1.setVisitorType(new VisitorType());
        visitorType1.setCode(new HashSet<>(Arrays.asList(code1)));
        final List<VisitorType> visitorTypes = Arrays.asList(visitorType1);
        when(mockVisitorTypeRepository.findByTicketType(any(TicketType.class))).thenReturn(visitorTypes);

        // Configure TicketRepository.getAllBetweenDates(...).
        final Ticket ticket2 = new Ticket();
        ticket2.setCode("code");
        ticket2.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket2.setVisitorTypeId(0L);
        final OrderItem orderItem2 = new OrderItem();
        orderItem2.setQuantity(0);
        final VisitorType visitorType2 = new VisitorType();
        visitorType2.setTypeName("typeName");
        visitorType2.setTypeKey("typeKey");
        visitorType2.setPrice(0);
        visitorType2.setBasicType(false);
        visitorType2.setStatus("status");
        final TicketType ticketType2 = new TicketType();
        ticketType2.setTypeName("typeName");
        ticketType2.setPlaceId(0L);
        ticketType2.setStatus("status");
        ticketType2.setVisitorType(new HashSet<>(Arrays.asList(new VisitorType())));
        final Game game2 = new Game();
        game2.setGameName("gameName");
        game2.setGameDescription("gameDescription");
        game2.setStatus("status");
        game2.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
        final Place place2 = new Place();
        place2.setName("name");
        place2.setPlaceKey("placeKey");
        place2.setAddress("address");
        place2.setDetailDescription("detailDescription");
        place2.setShortDescription("shortDescription");
        place2.setMail("mail");
        place2.setPhoneNumber("phoneNumber");
        place2.setStatus("status");
        place2.setLocation("location");
        place2.setCancelPolicy("cancelPolicy");
        game2.setPlace(place2);
        ticketType2.setGame(new HashSet<>(Arrays.asList(game2)));
        visitorType2.setTicketType(ticketType2);
        visitorType2.setOrderItem(new HashSet<>(Arrays.asList(new OrderItem())));
        final Code code2 = new Code();
        code2.setCode("code");
        code2.setVisitorType(new VisitorType());
        visitorType2.setCode(new HashSet<>(Arrays.asList(code2)));
        orderItem2.setVisitorType(visitorType2);
        final Order order2 = new Order();
        order2.setTicketTypeId(0L);
        order2.setFirstName("firstName");
        order2.setLastName("lastName");
        order2.setMail("mail");
        order2.setPhoneNumber("phoneNumber");
        order2.setStatus("status");
        order2.setOrderCode("orderCode");
        order2.setTotalPayment(0);
        order2.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order2.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        orderItem2.setOrder(order2);
        orderItem2.setTicket(new HashSet<>(Arrays.asList(new Ticket())));
        ticket2.setOrderItem(orderItem2);
        final List<Ticket> tickets = Arrays.asList(ticket2);
        when(mockTicketRepository.getAllBetweenDates(0L, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime())).thenReturn(tickets);

        // Run the test
        final ResponseEntity<?> result = ticketServiceImplUnderTest.getReport(0L, 0L, 0L, 0L);

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void testCreateReport() throws Exception {
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

        // Configure JavaMailSender.createMimeMessage(...).
        final MimeMessage mimeMessage = new MimeMessage(Session.getInstance(new Properties()));
        when(mockEmailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Run the test
        final ResponseEntity<?> result = ticketServiceImplUnderTest.createReport(report);

        // Verify the results
        verify(mockEmailSender).send(any(MimeMessage.class));
    }

    @Test
    public void testSendEmail() throws Exception {
        // Setup
        final File file = new File("filename.txt");

        // Configure JavaMailSender.createMimeMessage(...).
        final MimeMessage mimeMessage = new MimeMessage(Session.getInstance(new Properties()));
        when(mockEmailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Run the test
        ticketServiceImplUnderTest.sendEmail(file, "mail", "content");

        // Verify the results
        verify(mockEmailSender).send(any(MimeMessage.class));
    }
}
