package com.capstone.booking.service.impl;

import com.capstone.booking.common.converter.TicketTypeConverter;
import com.capstone.booking.common.converter.VisitorTypeConverter;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.TicketTypeDTO;
import com.capstone.booking.entity.dto.VisitorTypeDTO;
import com.capstone.booking.repository.CodeRepository;
import com.capstone.booking.repository.GameRepository;
import com.capstone.booking.repository.TicketTypeRepository;
import com.capstone.booking.repository.VisitorTypeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class TicketTypeServiceImplTest {

    private TicketTypeServiceImpl ticketTypeServiceImplUnderTest;

    @Before
    public void setUp() {
        ticketTypeServiceImplUnderTest = new TicketTypeServiceImpl();
        ticketTypeServiceImplUnderTest.ticketTypeRepository = mock(TicketTypeRepository.class);
        ticketTypeServiceImplUnderTest.ticketTypeConverter = mock(TicketTypeConverter.class);
        ticketTypeServiceImplUnderTest.gameRepository = mock(GameRepository.class);
        ticketTypeServiceImplUnderTest.codeRepository = mock(CodeRepository.class);
        ticketTypeServiceImplUnderTest.visitorTypeConverter = mock(VisitorTypeConverter.class);
        ticketTypeServiceImplUnderTest.visitorTypeRepository = mock(VisitorTypeRepository.class);
    }

    @Test
    public void testFindAll() {
        // Setup

        // Configure TicketTypeRepository.findAll(...).
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
//        final Code code = new Code();
//        code.setCode("code");
//        code.setVisitorType(new VisitorType());
//        visitorType.setCode(new HashSet<>(Arrays.asList(code)));
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
        when(ticketTypeServiceImplUnderTest.ticketTypeRepository.findAll()).thenReturn(ticketTypes);

        // Configure TicketTypeConverter.toDTO(...).
        final TicketTypeDTO ticketTypeDTO = new TicketTypeDTO();
        ticketTypeDTO.setTypeName("typeName");
        ticketTypeDTO.setGameId(new HashSet<>(Arrays.asList(0L)));
        ticketTypeDTO.setPlaceId(0L);
        final VisitorTypeDTO visitorTypeDTO = new VisitorTypeDTO();
        visitorTypeDTO.setTypeName("typeName");
        visitorTypeDTO.setTypeKey("typeKey");
        visitorTypeDTO.setTicketTypeId(0L);
        visitorTypeDTO.setPrice(0);
        visitorTypeDTO.setBasicType(false);
        visitorTypeDTO.setRemaining(0);
        visitorTypeDTO.setStatus("status");
        ticketTypeDTO.setVisitorTypes(Arrays.asList(visitorTypeDTO));
        ticketTypeDTO.setStatus("status");
        when(ticketTypeServiceImplUnderTest.ticketTypeConverter.toDTO(any(TicketType.class))).thenReturn(ticketTypeDTO);

        // Run the test
        final ResponseEntity<?> result = ticketTypeServiceImplUnderTest.findAll();

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void testDelete() {
        // Setup

        // Configure TicketTypeRepository.findById(...).
        final TicketType ticketType1 = new TicketType();
        ticketType1.setTypeName("typeName");
        ticketType1.setPlaceId(0L);
        ticketType1.setStatus("status");
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
//        final Code code = new Code();
//        code.setCode("code");
//        code.setVisitorType(new VisitorType());
//        visitorType.setCode(new HashSet<>(Arrays.asList(code)));
        ticketType1.setVisitorType(new HashSet<>(Arrays.asList(visitorType)));
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
        ticketType1.setGame(new HashSet<>(Arrays.asList(game)));
        final Optional<TicketType> ticketType = Optional.of(ticketType1);
        when(ticketTypeServiceImplUnderTest.ticketTypeRepository.findById(0L)).thenReturn(ticketType);

        // Run the test
        final ResponseEntity<?> result = ticketTypeServiceImplUnderTest.delete(0L);

        // Verify the results
        verify(ticketTypeServiceImplUnderTest.ticketTypeRepository).deleteById(0L);
    }

    @Test
    public void testCreate() {
        // Setup
        final TicketTypeDTO ticketTypeDTO = new TicketTypeDTO();
        ticketTypeDTO.setTypeName("typeName");
        ticketTypeDTO.setGameId(new HashSet<>(Arrays.asList(0L)));
        ticketTypeDTO.setPlaceId(0L);
        ticketTypeDTO.setStatus("status");

        // Configure TicketTypeRepository.findByTypeNameAndPlaceId(...).
        final TicketType ticketType = new TicketType();
        ticketType.setTypeName("typeName");
        ticketType.setPlaceId(0L);
        ticketType.setStatus("status");
        when(ticketTypeServiceImplUnderTest.ticketTypeRepository.findByTypeNameAndPlaceId("name", 0L)).thenReturn(ticketType);

        // Configure TicketTypeConverter.toTicketType(...).
        when(ticketTypeServiceImplUnderTest.ticketTypeConverter.toTicketType(ticketTypeDTO)).thenReturn(ticketType);

        // Configure GameRepository.findById(...).
        final Game game3 = new Game();
        game3.setGameName("gameName");
        game3.setGameDescription("gameDescription");
        game3.setStatus("status");
        Optional<Game> optionalGame = Optional.of(game3);
        when(ticketTypeServiceImplUnderTest.gameRepository.findById(0L)).thenReturn(optionalGame);

        // Configure TicketTypeRepository.save(...).
        when(ticketTypeServiceImplUnderTest.ticketTypeRepository.save(ticketType)).thenReturn(ticketType);

        // Configure TicketTypeConverter.toDTO(...).
        when(ticketTypeServiceImplUnderTest.ticketTypeConverter.toDTO(ticketType)).thenReturn(ticketTypeDTO);

        // Run the test
        final ResponseEntity<?> result = ticketTypeServiceImplUnderTest.create(ticketTypeDTO);

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void testUpdate() {
        // Setup
        final TicketTypeDTO ticketTypeDTO = new TicketTypeDTO();
        ticketTypeDTO.setTypeName("typeName");
        ticketTypeDTO.setGameId(new HashSet<>(Arrays.asList(0L)));
        ticketTypeDTO.setPlaceId(0L);
        ticketTypeDTO.setStatus("status");
        ticketTypeDTO.setId(0l);
        Set<Long> gameIds = new HashSet<>();
        gameIds.add(0l);
        ticketTypeDTO.setGameId(gameIds);

        // Configure TicketTypeRepository.findByTypeNameAndPlaceId(...).
        final TicketType ticketType = new TicketType();
        ticketType.setTypeName("typeName");
        ticketType.setPlaceId(0L);
        ticketType.setStatus("status");
        ticketType.setId(0l);
        final Game game = new Game();
        game.setGameName("gameName");
        game.setGameDescription("gameDescription");
        game.setStatus("status");
        Set<Game> gameSet = new HashSet<>();
        gameSet.add(game);
        ticketType.setGame(gameSet);
        when(ticketTypeServiceImplUnderTest.ticketTypeRepository.findByTypeNameAndPlaceId("typeName", 0L)).thenReturn(ticketType);

        // Configure TicketTypeRepository.findById(...).
        Optional<TicketType> optionalTicketType = Optional.of(ticketType);
        when(ticketTypeServiceImplUnderTest.ticketTypeRepository.findById(0L)).thenReturn(optionalTicketType);

        // Configure TicketTypeConverter.toTicketType(...).
        when(ticketTypeServiceImplUnderTest.ticketTypeConverter.toTicketType(ticketTypeDTO, ticketType)).thenReturn(ticketType);

        // Configure GameRepository.findById(...).
        Optional<Game> optionalGame = Optional.of(game);
        when(ticketTypeServiceImplUnderTest.gameRepository.findById(0L)).thenReturn(optionalGame);

        // Configure TicketTypeRepository.save(...).
        when(ticketTypeServiceImplUnderTest.ticketTypeRepository.save(ticketType)).thenReturn(ticketType);

        // Configure TicketTypeConverter.toDTO(...).
        when(ticketTypeServiceImplUnderTest.ticketTypeConverter.toDTO(ticketType)).thenReturn(ticketTypeDTO);

        // Run the test
        final ResponseEntity<?> result = ticketTypeServiceImplUnderTest.update(ticketTypeDTO);

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void testChangeStatus() {
        // Setup

        // Configure TicketTypeRepository.findById(...).
        final TicketType ticketType1 = new TicketType();
        ticketType1.setTypeName("typeName");
        ticketType1.setPlaceId(0L);
        ticketType1.setStatus("status");
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
//        final Code code = new Code();
//        code.setCode("code");
//        code.setVisitorType(new VisitorType());
//        visitorType.setCode(new HashSet<>(Arrays.asList(code)));
        ticketType1.setVisitorType(new HashSet<>(Arrays.asList(visitorType)));
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
        ticketType1.setGame(new HashSet<>(Arrays.asList(game)));
        final Optional<TicketType> ticketType = Optional.of(ticketType1);
        when(ticketTypeServiceImplUnderTest.ticketTypeRepository.findById(0L)).thenReturn(ticketType);

        // Configure VisitorTypeRepository.findByTicketType(...).
        final VisitorType visitorType1 = new VisitorType();
        visitorType1.setTypeName("typeName");
        visitorType1.setTypeKey("typeKey");
        visitorType1.setPrice(0);
        visitorType1.setBasicType(false);
        visitorType1.setStatus("status");
        final TicketType ticketType2 = new TicketType();
        ticketType2.setTypeName("typeName");
        ticketType2.setPlaceId(0L);
        ticketType2.setStatus("status");
        ticketType2.setVisitorType(new HashSet<>(Arrays.asList(new VisitorType())));
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
        ticketType2.setGame(new HashSet<>(Arrays.asList(game1)));
        visitorType1.setTicketType(ticketType2);
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
//        final Code code1 = new Code();
//        code1.setCode("code");
//        code1.setVisitorType(new VisitorType());
//        visitorType1.setCode(new HashSet<>(Arrays.asList(code1)));
        final List<VisitorType> visitorTypes = Arrays.asList(visitorType1);
        when(ticketTypeServiceImplUnderTest.visitorTypeRepository.findByTicketType(any(TicketType.class))).thenReturn(visitorTypes);

        // Configure TicketTypeRepository.save(...).
        final TicketType ticketType3 = new TicketType();
        ticketType3.setTypeName("typeName");
        ticketType3.setPlaceId(0L);
        ticketType3.setStatus("status");
        final VisitorType visitorType2 = new VisitorType();
        visitorType2.setTypeName("typeName");
        visitorType2.setTypeKey("typeKey");
        visitorType2.setPrice(0);
        visitorType2.setBasicType(false);
        visitorType2.setStatus("status");
        visitorType2.setTicketType(new TicketType());
        final OrderItem orderItem2 = new OrderItem();
        orderItem2.setQuantity(0);
        orderItem2.setVisitorType(new VisitorType());
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
        final Ticket ticket2 = new Ticket();
        ticket2.setCode("code");
        ticket2.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket2.setVisitorTypeId(0L);
        ticket2.setOrderItem(new OrderItem());
        orderItem2.setTicket(new HashSet<>(Arrays.asList(ticket2)));
        visitorType2.setOrderItem(new HashSet<>(Arrays.asList(orderItem2)));
//        final Code code2 = new Code();
//        code2.setCode("code");
//        code2.setVisitorType(new VisitorType());
//        visitorType2.setCode(new HashSet<>(Arrays.asList(code2)));
        ticketType3.setVisitorType(new HashSet<>(Arrays.asList(visitorType2)));
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
        ticketType3.setGame(new HashSet<>(Arrays.asList(game2)));
        when(ticketTypeServiceImplUnderTest.ticketTypeRepository.save(any(TicketType.class))).thenReturn(ticketType3);

        // Configure TicketTypeConverter.toDTO(...).
        final TicketTypeDTO ticketTypeDTO = new TicketTypeDTO();
        ticketTypeDTO.setTypeName("typeName");
        ticketTypeDTO.setGameId(new HashSet<>(Arrays.asList(0L)));
        ticketTypeDTO.setPlaceId(0L);
        final VisitorTypeDTO visitorTypeDTO = new VisitorTypeDTO();
        visitorTypeDTO.setTypeName("typeName");
        visitorTypeDTO.setTypeKey("typeKey");
        visitorTypeDTO.setTicketTypeId(0L);
        visitorTypeDTO.setPrice(0);
        visitorTypeDTO.setBasicType(false);
        visitorTypeDTO.setRemaining(0);
        visitorTypeDTO.setStatus("status");
        ticketTypeDTO.setVisitorTypes(Arrays.asList(visitorTypeDTO));
        ticketTypeDTO.setStatus("status");
        when(ticketTypeServiceImplUnderTest.ticketTypeConverter.toDTO(any(TicketType.class))).thenReturn(ticketTypeDTO);

        // Run the test
        final ResponseEntity<?> result = ticketTypeServiceImplUnderTest.changeStatus(0L);

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void testFindByPlaceId() {
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
//        final Code code = new Code();
//        code.setCode("code");
//        code.setVisitorType(new VisitorType());
//        visitorType.setCode(new HashSet<>(Arrays.asList(code)));
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
        when(ticketTypeServiceImplUnderTest.ticketTypeRepository.findByPlaceId(0L)).thenReturn(ticketTypes);

        // Configure TicketTypeConverter.toDTO(...).
        final TicketTypeDTO ticketTypeDTO = new TicketTypeDTO();
        ticketTypeDTO.setTypeName("typeName");
        ticketTypeDTO.setGameId(new HashSet<>(Arrays.asList(0L)));
        ticketTypeDTO.setPlaceId(0L);
        final VisitorTypeDTO visitorTypeDTO = new VisitorTypeDTO();
        visitorTypeDTO.setTypeName("typeName");
        visitorTypeDTO.setTypeKey("typeKey");
        visitorTypeDTO.setTicketTypeId(0L);
        visitorTypeDTO.setPrice(0);
        visitorTypeDTO.setBasicType(false);
        visitorTypeDTO.setRemaining(0);
        visitorTypeDTO.setStatus("status");
        ticketTypeDTO.setVisitorTypes(Arrays.asList(visitorTypeDTO));
        ticketTypeDTO.setStatus("status");
        when(ticketTypeServiceImplUnderTest.ticketTypeConverter.toDTO(any(TicketType.class))).thenReturn(ticketTypeDTO);

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
//        final Code code1 = new Code();
//        code1.setCode("code");
//        code1.setVisitorType(new VisitorType());
//        visitorType1.setCode(new HashSet<>(Arrays.asList(code1)));
        final List<VisitorType> visitorTypes = Arrays.asList(visitorType1);
        when(ticketTypeServiceImplUnderTest.visitorTypeRepository.findByTicketType(any(TicketType.class))).thenReturn(visitorTypes);

        // Configure VisitorTypeConverter.toDTO(...).
        final VisitorTypeDTO visitorTypeDTO1 = new VisitorTypeDTO();
        visitorTypeDTO1.setTypeName("typeName");
        visitorTypeDTO1.setTypeKey("typeKey");
        visitorTypeDTO1.setTicketTypeId(0L);
        visitorTypeDTO1.setPrice(0);
        visitorTypeDTO1.setBasicType(false);
        visitorTypeDTO1.setRemaining(0);
        visitorTypeDTO1.setStatus("status");
        when(ticketTypeServiceImplUnderTest.visitorTypeConverter.toDTO(new VisitorType())).thenReturn(visitorTypeDTO1);

        // Run the test
        final ResponseEntity<?> result = ticketTypeServiceImplUnderTest.findByPlaceId(0L, new Date());

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void testGetTicketType() {
        // Setup

        // Configure TicketTypeConverter.toDTO(...).
        final TicketTypeDTO ticketTypeDTO = new TicketTypeDTO();
        ticketTypeDTO.setTypeName("typeName");
        ticketTypeDTO.setGameId(new HashSet<>(Arrays.asList(0L)));
        ticketTypeDTO.setPlaceId(0L);
        final VisitorTypeDTO visitorTypeDTO = new VisitorTypeDTO();
        visitorTypeDTO.setTypeName("typeName");
        visitorTypeDTO.setTypeKey("typeKey");
        visitorTypeDTO.setTicketTypeId(0L);
        visitorTypeDTO.setPrice(0);
        visitorTypeDTO.setBasicType(false);
        visitorTypeDTO.setRemaining(0);
        visitorTypeDTO.setStatus("status");
        ticketTypeDTO.setVisitorTypes(Arrays.asList(visitorTypeDTO));
        ticketTypeDTO.setStatus("status");
        when(ticketTypeServiceImplUnderTest.ticketTypeConverter.toDTO(any(TicketType.class))).thenReturn(ticketTypeDTO);

        // Configure TicketTypeRepository.findById(...).
        final TicketType ticketType1 = new TicketType();
        ticketType1.setTypeName("typeName");
        ticketType1.setPlaceId(0L);
        ticketType1.setStatus("status");
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
//        final Code code = new Code();
//        code.setCode("code");
//        code.setVisitorType(new VisitorType());
//        visitorType.setCode(new HashSet<>(Arrays.asList(code)));
        ticketType1.setVisitorType(new HashSet<>(Arrays.asList(visitorType)));
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
        ticketType1.setGame(new HashSet<>(Arrays.asList(game)));
        final Optional<TicketType> ticketType = Optional.of(ticketType1);
        when(ticketTypeServiceImplUnderTest.ticketTypeRepository.findById(0L)).thenReturn(ticketType);

        // Run the test
        final ResponseEntity<?> result = ticketTypeServiceImplUnderTest.getTicketType(0L);

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }
}
