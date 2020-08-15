package com.capstone.booking.service.impl;

import com.capstone.booking.common.converter.VisitorTypeConverter;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.VisitorTypeDTO;
import com.capstone.booking.repository.CodeRepository;
import com.capstone.booking.repository.PlaceRepository;
import com.capstone.booking.repository.TicketTypeRepository;
import com.capstone.booking.repository.VisitorTypeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VisitorTypeServiceImplTest {

    private VisitorTypeServiceImpl visitorTypeServiceImplUnderTest;

    @Before
    public void setUp() {
        visitorTypeServiceImplUnderTest = new VisitorTypeServiceImpl();
        visitorTypeServiceImplUnderTest.visitorTypeRepository = mock(VisitorTypeRepository.class);
        visitorTypeServiceImplUnderTest.visitorTypeConverter = mock(VisitorTypeConverter.class);
        visitorTypeServiceImplUnderTest.ticketTypeRepository = mock(TicketTypeRepository.class);
        visitorTypeServiceImplUnderTest.codeRepository = mock(CodeRepository.class);
        visitorTypeServiceImplUnderTest.placeRepository = mock(PlaceRepository.class);
    }

    @Test
    public void testCreate() {
        // Setup
        final VisitorTypeDTO model = new VisitorTypeDTO();
        model.setTypeName("typeName");
        model.setTypeKey("typeKey");
        model.setTicketTypeId(0L);
        model.setPrice(0);
        model.setBasicType(false);
        model.setRemaining(0);
        model.setStatus("status");
        model.setTicketTypeId(0l);

        // Configure VisitorTypeConverter.toVisitorType(...).
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
        ticketType.setId(1l);
        Set<VisitorType> visitorTypeSet = new HashSet<>();
        visitorTypeSet.add(visitorType);
        ticketType.setVisitorType(visitorTypeSet);

        final List<TicketType> ticketTypes = new ArrayList<>();
        ticketTypes.add(ticketType);

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

        visitorType.setTicketType(ticketType);

        when(visitorTypeServiceImplUnderTest.visitorTypeConverter.toVisitorType(model)).thenReturn(visitorType);

        // Configure VisitorTypeRepository.findByTypeName(...).
        final List<VisitorType> visitorTypes = new ArrayList<>();
        visitorTypes.add(visitorType);
        when(visitorTypeServiceImplUnderTest.visitorTypeRepository.findByTypeName("typeName")).thenReturn(visitorTypes);

        // Configure TicketTypeRepository.findByPlaceId(...).
        when(visitorTypeServiceImplUnderTest.ticketTypeRepository.findByPlaceId(0L)).thenReturn(ticketTypes);

        // Configure TicketTypeRepository.findById(...).
        Optional<TicketType> optionalTicketType = Optional.of(ticketType);
        when(visitorTypeServiceImplUnderTest.ticketTypeRepository.findById(0L)).thenReturn(optionalTicketType);

        // Configure VisitorTypeRepository.save(...).
        when(visitorTypeServiceImplUnderTest.visitorTypeRepository.save(visitorType)).thenReturn(visitorType);

        // Configure PlaceRepository.findById(...).
        Optional<Place> placeOptional = Optional.of(place);
        when(visitorTypeServiceImplUnderTest.placeRepository.findById(0l)).thenReturn(placeOptional);
        when(visitorTypeServiceImplUnderTest.placeRepository.save(place)).thenReturn(place);

        // Configure VisitorTypeConverter.toDTO(...).
        VisitorTypeDTO visitorTypeDTO = new VisitorTypeDTO();
        when(visitorTypeServiceImplUnderTest.visitorTypeConverter.toDTO(visitorType)).thenReturn(visitorTypeDTO);

        // Run the test
        final ResponseEntity<?> result = visitorTypeServiceImplUnderTest.create(model, 0L);

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void testUpdate() {
        // Setup
        final VisitorTypeDTO model = new VisitorTypeDTO();
        model.setTypeName("typeName");
        model.setTypeKey("typeKey");
        model.setTicketTypeId(0L);
        model.setPrice(0);
        model.setBasicType(false);
        model.setRemaining(0);
        model.setStatus("status");
        model.setId(0l);

        // Configure VisitorTypeRepository.findById(...).
        final VisitorType visitorType1 = new VisitorType();
        visitorType1.setTypeName("typeName");
        visitorType1.setTypeKey("typeKey");
        visitorType1.setPrice(0);
        visitorType1.setBasicType(false);
        visitorType1.setStatus("status");
        visitorType1.setId(2l);
        final TicketType ticketType = new TicketType();
        ticketType.setTypeName("typeName");
        ticketType.setPlaceId(0L);
        ticketType.setStatus("status");
        ticketType.setId(1l);
        Set<VisitorType> visitorTypes = new HashSet<>();
        visitorTypes.add(visitorType1);
        ticketType.setVisitorType(visitorTypes);

        final VisitorTypeDTO visitorTypeDTO = new VisitorTypeDTO();
        visitorTypeDTO.setTypeName("typeName");
        visitorTypeDTO.setTypeKey("typeKey");
        visitorTypeDTO.setTicketTypeId(0L);
        visitorTypeDTO.setPrice(0);
        visitorTypeDTO.setBasicType(false);
        visitorTypeDTO.setRemaining(0);
        visitorTypeDTO.setStatus("status");

        Optional<VisitorType> optionalVisitorType = Optional.of(visitorType1);
        when(visitorTypeServiceImplUnderTest.visitorTypeRepository.findById(0L)).thenReturn(optionalVisitorType);

        // Configure VisitorTypeConverter.toVisitorType(...).
        when(visitorTypeServiceImplUnderTest.visitorTypeConverter.toVisitorType(visitorTypeDTO, visitorType1)).thenReturn(visitorType1);

        // Configure TicketTypeRepository.findById(...).
        Optional<TicketType> optionalTicketType = Optional.of(ticketType);
        when(visitorTypeServiceImplUnderTest.ticketTypeRepository.findById(0L)).thenReturn(optionalTicketType);

        // Configure VisitorTypeRepository.findByTypeName(...).
        final List<VisitorType> visitorTypesList = new ArrayList<>();
        visitorTypesList.add(visitorType1);
        when(visitorTypeServiceImplUnderTest.visitorTypeRepository.findByTypeName("typeName")).thenReturn(visitorTypesList);

        // Configure VisitorTypeRepository.save(...).
        when(visitorTypeServiceImplUnderTest.visitorTypeRepository.save(visitorType1)).thenReturn(visitorType1);

        // Configure VisitorTypeConverter.toDTO(...).
        when(visitorTypeServiceImplUnderTest.visitorTypeConverter.toDTO(visitorType1)).thenReturn(visitorTypeDTO);

        // Run the test
        final ResponseEntity<?> result = visitorTypeServiceImplUnderTest.update(model, 0l);

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void testDelete() {
        // Setup

        // Configure VisitorTypeRepository.findById(...).
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
        final Code code = new Code();
        code.setCode("code");
        code.setVisitorType(visitorType1);
        final Optional<VisitorType> visitorType = Optional.of(visitorType1);
        when(visitorTypeServiceImplUnderTest.visitorTypeRepository.findById(0L)).thenReturn(visitorType);

        // Run the test
        final ResponseEntity<?> result = visitorTypeServiceImplUnderTest.delete(0L);

        // Verify the results
        verify(visitorTypeServiceImplUnderTest.codeRepository).deleteByVisitorType(visitorType1);
        verify(visitorTypeServiceImplUnderTest.visitorTypeRepository).deleteById(0L);
    }

    @Test
    public void testChangeStatus() {
        // Configure VisitorTypeRepository.findById(...).
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
        visitorType1.setOrderItem(new HashSet<>(Arrays.asList(orderItem)));
//        final Code code = new Code();
//        code.setCode("code");
//        code.setVisitorType(new VisitorType());
//        visitorType1.setCode(new HashSet<>(Arrays.asList(code)));
        final Optional<VisitorType> visitorType = Optional.of(visitorType1);
        when(visitorTypeServiceImplUnderTest.visitorTypeRepository.findById(0L)).thenReturn(visitorType);

        // Configure VisitorTypeRepository.save(...).
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
        visitorType2.setOrderItem(new HashSet<>(Arrays.asList(orderItem1)));
//        final Code code1 = new Code();
//        code1.setCode("code");
//        code1.setVisitorType(new VisitorType());
//        visitorType2.setCode(new HashSet<>(Arrays.asList(code1)));
        when(visitorTypeServiceImplUnderTest.visitorTypeRepository.save(new VisitorType())).thenReturn(visitorType2);

        // Configure VisitorTypeConverter.toDTO(...).
        final VisitorTypeDTO visitorTypeDTO = new VisitorTypeDTO();
        visitorTypeDTO.setTypeName("typeName");
        visitorTypeDTO.setTypeKey("typeKey");
        visitorTypeDTO.setTicketTypeId(0L);
        visitorTypeDTO.setPrice(0);
        visitorTypeDTO.setBasicType(false);
        visitorTypeDTO.setRemaining(0);
        visitorTypeDTO.setStatus("status");
        when(visitorTypeServiceImplUnderTest.visitorTypeConverter.toDTO(new VisitorType())).thenReturn(visitorTypeDTO);

        // Run the test
        final ResponseEntity<?> result = visitorTypeServiceImplUnderTest.changeStatus(0L);

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void testFindByTicketTypeId() {
        // Setup

        // Configure VisitorTypeRepository.findByTicketType(...).
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
        final List<VisitorType> visitorTypes = Arrays.asList(visitorType);
        when(visitorTypeServiceImplUnderTest.visitorTypeRepository.findByTicketType(any(TicketType.class))).thenReturn(visitorTypes);

        // Configure TicketTypeRepository.findById(...).
        final TicketType ticketType2 = new TicketType();
        ticketType2.setTypeName("typeName");
        ticketType2.setPlaceId(0L);
        ticketType2.setStatus("status");
        final VisitorType visitorType1 = new VisitorType();
        visitorType1.setTypeName("typeName");
        visitorType1.setTypeKey("typeKey");
        visitorType1.setPrice(0);
        visitorType1.setBasicType(false);
        visitorType1.setStatus("status");
        visitorType1.setTicketType(new TicketType());
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
        ticketType2.setVisitorType(new HashSet<>(Arrays.asList(visitorType1)));
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
        final Optional<TicketType> ticketType1 = Optional.of(ticketType2);
        when(visitorTypeServiceImplUnderTest.ticketTypeRepository.findById(0L)).thenReturn(ticketType1);

        // Configure VisitorTypeConverter.toDTO(...).
        final VisitorTypeDTO visitorTypeDTO = new VisitorTypeDTO();
        visitorTypeDTO.setTypeName("typeName");
        visitorTypeDTO.setTypeKey("typeKey");
        visitorTypeDTO.setTicketTypeId(0L);
        visitorTypeDTO.setPrice(0);
        visitorTypeDTO.setBasicType(false);
        visitorTypeDTO.setRemaining(0);
        visitorTypeDTO.setStatus("status");
        when(visitorTypeServiceImplUnderTest.visitorTypeConverter.toDTO(new VisitorType())).thenReturn(visitorTypeDTO);

        // Run the test
        final ResponseEntity<?> result = visitorTypeServiceImplUnderTest.findByTicketTypeId(0L);

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void testGetById() {
        // Setup

        // Configure VisitorTypeRepository.findById(...).
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
        visitorType1.setOrderItem(new HashSet<>(Arrays.asList(orderItem)));
//        final Code code = new Code();
//        code.setCode("code");
//        code.setVisitorType(new VisitorType());
//        visitorType1.setCode(new HashSet<>(Arrays.asList(code)));
        final Optional<VisitorType> visitorType = Optional.of(visitorType1);
        when(visitorTypeServiceImplUnderTest.visitorTypeRepository.findById(0L)).thenReturn(visitorType);

        // Configure VisitorTypeConverter.toDTO(...).
        final VisitorTypeDTO visitorTypeDTO = new VisitorTypeDTO();
        visitorTypeDTO.setTypeName("typeName");
        visitorTypeDTO.setTypeKey("typeKey");
        visitorTypeDTO.setTicketTypeId(0L);
        visitorTypeDTO.setPrice(0);
        visitorTypeDTO.setBasicType(false);
        visitorTypeDTO.setRemaining(0);
        visitorTypeDTO.setStatus("status");
        when(visitorTypeServiceImplUnderTest.visitorTypeConverter.toDTO(new VisitorType())).thenReturn(visitorTypeDTO);

        // Run the test
        final ResponseEntity<?> result = visitorTypeServiceImplUnderTest.getById(0L);

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }
//
//    @SneakyThrows
//    @Test
//    public void testAddCodeForTicketType() {
//        // Setup
//        File file = new File("wrong key.xlsx");
//        FileInputStream input = new FileInputStream(file);
//        MultipartFile multipartFile = new MockMultipartFile("file",
//                file.getName(), "text/plain", IOUtils.toByteArray(input));
//
//        // Configure CodeRepository.saveAll(...).
//        final Code code = new Code();
//        code.setCode("code");
//        final VisitorType visitorType = new VisitorType();
//        visitorType.setTypeName("typeName");
//        visitorType.setTypeKey("typeKey");
//        visitorType.setPrice(0);
//        visitorType.setBasicType(false);
//        visitorType.setStatus("status");
//
//        when(visitorTypeServiceImplUnderTest.codeRepository.saveAll(Arrays.asList(new Code()))).thenReturn(codes);
//
//        // Configure CodeRepository.findByVisitorType(...).
//
//        when(visitorTypeServiceImplUnderTest.codeRepository.findByVisitorType(new VisitorType())).thenReturn(codes1);
//
//        // Configure VisitorTypeRepository.findByTypeKey(...).
//
//        when(visitorTypeServiceImplUnderTest.visitorTypeRepository.findByTypeKey("key")).thenReturn(visitorType2);
//
//        // Run the test
//        final ResponseEntity<?> result = visitorTypeServiceImplUnderTest.addCodeForTicketType(file, "codeType");
//
//        // Verify the results
//    }

    @Test
    public void testMarkBasicPrice() {
        // Setup

        // Configure VisitorTypeRepository.findById(...).
        final VisitorType visitorType1 = new VisitorType();
        visitorType1.setTypeName("typeName");
        visitorType1.setTypeKey("typeKey");
        visitorType1.setPrice(0);
        visitorType1.setBasicType(false);
        visitorType1.setStatus("status");
        visitorType1.setId(0l);

        final VisitorType visitorType2 = new VisitorType();
        visitorType2.setTypeName("typeName");
        visitorType2.setTypeKey("typeKey");
        visitorType2.setPrice(2);
        visitorType2.setBasicType(false);
        visitorType2.setStatus("status");

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
        place.setId(0l);
        place.setBasicPrice(2);

        Optional<VisitorType> optionalVisitorType = Optional.of(visitorType1);
        when(visitorTypeServiceImplUnderTest.visitorTypeRepository.findById(0L)).thenReturn(optionalVisitorType);

        // Configure VisitorTypeRepository.findByPlaceIdAndBasic(...).
        when(visitorTypeServiceImplUnderTest.visitorTypeRepository.findByPlaceIdAndBasic(0L, true)).thenReturn(visitorType1);

        // Configure VisitorTypeRepository.save(...).

        when(visitorTypeServiceImplUnderTest.visitorTypeRepository.save(visitorType1)).thenReturn(visitorType1);

        // Configure PlaceRepository.findById(...).
        place.setBasicPrice(visitorType1.getPrice());
        Optional<Place> optionalPlace = Optional.of(place);
        when(visitorTypeServiceImplUnderTest.placeRepository.findById(0L)).thenReturn(optionalPlace);

        // Configure PlaceRepository.save(...).
        when(visitorTypeServiceImplUnderTest.placeRepository.save(place)).thenReturn(place);

        // Run the test
        final ResponseEntity<?> result = visitorTypeServiceImplUnderTest.markBasicPrice(0L, 0L);

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }
}
