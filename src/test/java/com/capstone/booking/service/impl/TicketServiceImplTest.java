package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.OutputReport;
import com.capstone.booking.api.output.ReportItem;
import com.capstone.booking.common.converter.TicketConverter;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.TicketDTO;
import com.capstone.booking.entity.dto.VisitorTypeDTO;
import com.capstone.booking.repository.*;
import org.junit.Before;
import org.junit.Test;
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
    private CodeRepository mockCodeRepository;
    @Mock
    private VisitorTypeRepository mockVisitorTypeRepository;
    @Mock
    private PlaceRepository mockPlaceRepository;
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
        final List<VisitorType> visitorTypes = Arrays.asList(visitorType1);
        when(mockVisitorTypeRepository.findByTicketType(any(TicketType.class))).thenReturn(visitorTypes);

        when(mockTicketRepository.getAllBetweenDates(0L, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime())).thenReturn(0);
        when(mockCodeRepository.getAllBetweenDates(new VisitorType(), new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime())).thenReturn(0);

        // Run the test
        final ResponseEntity<?> result = ticketServiceImplUnderTest.getReport(0L, 0L, 0L, 0L);

        // Verify the results
    }

    @Test
    public void testCreateReport() throws Exception {
        // Setup
        final OutputReport report = new OutputReport();
        final ReportItem reportItem = new ReportItem();
        reportItem.setTicketTypeName("ticketTypeName");
        reportItem.setQuantity(0);
        reportItem.setTotal(0);
        reportItem.setRemaining(0);
        report.setReportItemList(Arrays.asList(reportItem));
        report.setStartDate(0L);
        report.setEndDate(0L);
        report.setReportType(0L);
        report.setPlaceId(0L);
        report.setTotalRevenue(0);

        // Configure PlaceRepository.findById(...).
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
        final Optional<Place> place = Optional.of(place1);
        when(mockPlaceRepository.findById(0L)).thenReturn(place);

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
        ticketServiceImplUnderTest.sendEmail(file, "placeEmail", "content");

        // Verify the results
        verify(mockEmailSender).send(any(MimeMessage.class));
    }
}
