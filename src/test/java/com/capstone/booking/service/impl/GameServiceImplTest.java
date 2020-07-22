package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.GameConverter;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.GameDTO;
import com.capstone.booking.entity.dto.GameDTOLite;
import com.capstone.booking.repository.GameRepository;
import com.capstone.booking.repository.PlaceRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GameServiceImplTest {

    @Mock
    private GameRepository mockGameRepository;
    @Mock
    private GameConverter mockGameConverter;
    @Mock
    private PlaceRepository mockPlaceRepository;

    @InjectMocks
    private GameServiceImpl gameServiceImplUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testCreate() {
        // Setup
        final GameDTO gameDTO = new GameDTO();
        gameDTO.setGameName("gameName");
        gameDTO.setGameDescription("gameDescription");
        gameDTO.setTicketTypeName(new HashSet<>(Arrays.asList("value")));
        gameDTO.setPlaceId(0L);
        gameDTO.setPlaceName("placeName");
        gameDTO.setStatus("status");

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

        // Configure GameRepository.findByGameNameAndPlace(...).
        final Game game = new Game();
        game.setGameName("gameName");
        game.setGameDescription("gameDescription");
        game.setStatus("status");
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
        ticketType.setGame(new HashSet<>(Arrays.asList(new Game())));
        game.setTicketTypes(new HashSet<>(Arrays.asList(ticketType)));
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
        game.setPlace(place2);
        when(mockGameRepository.findByGameNameAndPlace(eq("name"), any(Place.class))).thenReturn(game);

        // Configure GameConverter.toGame(...).
        final Game game1 = new Game();
        game1.setGameName("gameName");
        game1.setGameDescription("gameDescription");
        game1.setStatus("status");
        final TicketType ticketType1 = new TicketType();
        ticketType1.setTypeName("typeName");
        ticketType1.setPlaceId(0L);
        ticketType1.setStatus("status");
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
        final Code code1 = new Code();
        code1.setCode("code");
        code1.setVisitorType(new VisitorType());
        visitorType1.setCode(new HashSet<>(Arrays.asList(code1)));
        ticketType1.setVisitorType(new HashSet<>(Arrays.asList(visitorType1)));
        ticketType1.setGame(new HashSet<>(Arrays.asList(new Game())));
        game1.setTicketTypes(new HashSet<>(Arrays.asList(ticketType1)));
        final Place place3 = new Place();
        place3.setName("name");
        place3.setPlaceKey("placeKey");
        place3.setAddress("address");
        place3.setDetailDescription("detailDescription");
        place3.setShortDescription("shortDescription");
        place3.setMail("mail");
        place3.setPhoneNumber("phoneNumber");
        place3.setStatus("status");
        place3.setLocation("location");
        place3.setCancelPolicy("cancelPolicy");
        game1.setPlace(place3);
        when(mockGameConverter.toGame(new GameDTO())).thenReturn(game1);

        // Configure GameRepository.save(...).
        final Game game2 = new Game();
        game2.setGameName("gameName");
        game2.setGameDescription("gameDescription");
        game2.setStatus("status");
        final TicketType ticketType2 = new TicketType();
        ticketType2.setTypeName("typeName");
        ticketType2.setPlaceId(0L);
        ticketType2.setStatus("status");
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
        final Code code2 = new Code();
        code2.setCode("code");
        code2.setVisitorType(new VisitorType());
        visitorType2.setCode(new HashSet<>(Arrays.asList(code2)));
        ticketType2.setVisitorType(new HashSet<>(Arrays.asList(visitorType2)));
        ticketType2.setGame(new HashSet<>(Arrays.asList(new Game())));
        game2.setTicketTypes(new HashSet<>(Arrays.asList(ticketType2)));
        final Place place4 = new Place();
        place4.setName("name");
        place4.setPlaceKey("placeKey");
        place4.setAddress("address");
        place4.setDetailDescription("detailDescription");
        place4.setShortDescription("shortDescription");
        place4.setMail("mail");
        place4.setPhoneNumber("phoneNumber");
        place4.setStatus("status");
        place4.setLocation("location");
        place4.setCancelPolicy("cancelPolicy");
        game2.setPlace(place4);
        when(mockGameRepository.save(new Game())).thenReturn(game2);

        // Configure GameConverter.toDTO(...).
        final GameDTO gameDTO1 = new GameDTO();
        gameDTO1.setGameName("gameName");
        gameDTO1.setGameDescription("gameDescription");
        gameDTO1.setTicketTypeName(new HashSet<>(Arrays.asList("value")));
        gameDTO1.setPlaceId(0L);
        gameDTO1.setPlaceName("placeName");
        gameDTO1.setStatus("status");
        when(mockGameConverter.toDTO(new Game())).thenReturn(gameDTO1);

        // Run the test
        final ResponseEntity<?> result = gameServiceImplUnderTest.create(gameDTO);

        // Verify the results
    }

    @Test
    public void testUpdate() {
        // Setup
        final GameDTO gameDTO = new GameDTO();
        gameDTO.setGameName("gameName");
        gameDTO.setGameDescription("gameDescription");
        gameDTO.setTicketTypeName(new HashSet<>(Arrays.asList("value")));
        gameDTO.setPlaceId(0L);
        gameDTO.setPlaceName("placeName");
        gameDTO.setStatus("status");

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

        // Configure GameRepository.findByGameNameAndPlace(...).
        final Game game = new Game();
        game.setGameName("gameName");
        game.setGameDescription("gameDescription");
        game.setStatus("status");
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
        ticketType.setGame(new HashSet<>(Arrays.asList(new Game())));
        game.setTicketTypes(new HashSet<>(Arrays.asList(ticketType)));
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
        game.setPlace(place2);
        when(mockGameRepository.findByGameNameAndPlace(eq("name"), any(Place.class))).thenReturn(game);

        // Configure GameRepository.findById(...).
        final Game game2 = new Game();
        game2.setGameName("gameName");
        game2.setGameDescription("gameDescription");
        game2.setStatus("status");
        final TicketType ticketType1 = new TicketType();
        ticketType1.setTypeName("typeName");
        ticketType1.setPlaceId(0L);
        ticketType1.setStatus("status");
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
        final Code code1 = new Code();
        code1.setCode("code");
        code1.setVisitorType(new VisitorType());
        visitorType1.setCode(new HashSet<>(Arrays.asList(code1)));
        ticketType1.setVisitorType(new HashSet<>(Arrays.asList(visitorType1)));
        ticketType1.setGame(new HashSet<>(Arrays.asList(new Game())));
        game2.setTicketTypes(new HashSet<>(Arrays.asList(ticketType1)));
        final Place place3 = new Place();
        place3.setName("name");
        place3.setPlaceKey("placeKey");
        place3.setAddress("address");
        place3.setDetailDescription("detailDescription");
        place3.setShortDescription("shortDescription");
        place3.setMail("mail");
        place3.setPhoneNumber("phoneNumber");
        place3.setStatus("status");
        place3.setLocation("location");
        place3.setCancelPolicy("cancelPolicy");
        game2.setPlace(place3);
        final Optional<Game> game1 = Optional.of(game2);
        when(mockGameRepository.findById(0L)).thenReturn(game1);

        // Configure GameConverter.toGame(...).
        final Game game3 = new Game();
        game3.setGameName("gameName");
        game3.setGameDescription("gameDescription");
        game3.setStatus("status");
        final TicketType ticketType2 = new TicketType();
        ticketType2.setTypeName("typeName");
        ticketType2.setPlaceId(0L);
        ticketType2.setStatus("status");
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
        final Code code2 = new Code();
        code2.setCode("code");
        code2.setVisitorType(new VisitorType());
        visitorType2.setCode(new HashSet<>(Arrays.asList(code2)));
        ticketType2.setVisitorType(new HashSet<>(Arrays.asList(visitorType2)));
        ticketType2.setGame(new HashSet<>(Arrays.asList(new Game())));
        game3.setTicketTypes(new HashSet<>(Arrays.asList(ticketType2)));
        final Place place4 = new Place();
        place4.setName("name");
        place4.setPlaceKey("placeKey");
        place4.setAddress("address");
        place4.setDetailDescription("detailDescription");
        place4.setShortDescription("shortDescription");
        place4.setMail("mail");
        place4.setPhoneNumber("phoneNumber");
        place4.setStatus("status");
        place4.setLocation("location");
        place4.setCancelPolicy("cancelPolicy");
        game3.setPlace(place4);
        when(mockGameConverter.toGame(new GameDTO(), new Game())).thenReturn(game3);

        // Configure GameRepository.save(...).
        final Game game4 = new Game();
        game4.setGameName("gameName");
        game4.setGameDescription("gameDescription");
        game4.setStatus("status");
        final TicketType ticketType3 = new TicketType();
        ticketType3.setTypeName("typeName");
        ticketType3.setPlaceId(0L);
        ticketType3.setStatus("status");
        final VisitorType visitorType3 = new VisitorType();
        visitorType3.setTypeName("typeName");
        visitorType3.setTypeKey("typeKey");
        visitorType3.setPrice(0);
        visitorType3.setBasicType(false);
        visitorType3.setStatus("status");
        visitorType3.setTicketType(new TicketType());
        final OrderItem orderItem3 = new OrderItem();
        orderItem3.setQuantity(0);
        orderItem3.setVisitorType(new VisitorType());
        final Order order3 = new Order();
        order3.setTicketTypeId(0L);
        order3.setFirstName("firstName");
        order3.setLastName("lastName");
        order3.setMail("mail");
        order3.setPhoneNumber("phoneNumber");
        order3.setStatus("status");
        order3.setOrderCode("orderCode");
        order3.setTotalPayment(0);
        order3.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order3.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        orderItem3.setOrder(order3);
        final Ticket ticket3 = new Ticket();
        ticket3.setCode("code");
        ticket3.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket3.setVisitorTypeId(0L);
        ticket3.setOrderItem(new OrderItem());
        orderItem3.setTicket(new HashSet<>(Arrays.asList(ticket3)));
        visitorType3.setOrderItem(new HashSet<>(Arrays.asList(orderItem3)));
        final Code code3 = new Code();
        code3.setCode("code");
        code3.setVisitorType(new VisitorType());
        visitorType3.setCode(new HashSet<>(Arrays.asList(code3)));
        ticketType3.setVisitorType(new HashSet<>(Arrays.asList(visitorType3)));
        ticketType3.setGame(new HashSet<>(Arrays.asList(new Game())));
        game4.setTicketTypes(new HashSet<>(Arrays.asList(ticketType3)));
        final Place place5 = new Place();
        place5.setName("name");
        place5.setPlaceKey("placeKey");
        place5.setAddress("address");
        place5.setDetailDescription("detailDescription");
        place5.setShortDescription("shortDescription");
        place5.setMail("mail");
        place5.setPhoneNumber("phoneNumber");
        place5.setStatus("status");
        place5.setLocation("location");
        place5.setCancelPolicy("cancelPolicy");
        game4.setPlace(place5);
        when(mockGameRepository.save(new Game())).thenReturn(game4);

        // Configure GameConverter.toDTO(...).
        final GameDTO gameDTO1 = new GameDTO();
        gameDTO1.setGameName("gameName");
        gameDTO1.setGameDescription("gameDescription");
        gameDTO1.setTicketTypeName(new HashSet<>(Arrays.asList("value")));
        gameDTO1.setPlaceId(0L);
        gameDTO1.setPlaceName("placeName");
        gameDTO1.setStatus("status");
        when(mockGameConverter.toDTO(new Game())).thenReturn(gameDTO1);

        // Run the test
        final ResponseEntity<?> result = gameServiceImplUnderTest.update(gameDTO);

        // Verify the results
    }

    @Test
    public void testDelete() {
        // Setup

        // Configure GameRepository.findById(...).
        final Game game1 = new Game();
        game1.setGameName("gameName");
        game1.setGameDescription("gameDescription");
        game1.setStatus("status");
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
        ticketType.setGame(new HashSet<>(Arrays.asList(new Game())));
        game1.setTicketTypes(new HashSet<>(Arrays.asList(ticketType)));
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
        game1.setPlace(place);
        final Optional<Game> game = Optional.of(game1);
        when(mockGameRepository.findById(0L)).thenReturn(game);

        // Run the test
        final ResponseEntity<?> result = gameServiceImplUnderTest.delete(0L);

        // Verify the results
        verify(mockGameRepository).deleteById(0L);
    }

    @Test
    public void testGetGame() {
        // Setup

        // Configure GameRepository.findById(...).
        final Game game1 = new Game();
        game1.setGameName("gameName");
        game1.setGameDescription("gameDescription");
        game1.setStatus("status");
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
        ticketType.setGame(new HashSet<>(Arrays.asList(new Game())));
        game1.setTicketTypes(new HashSet<>(Arrays.asList(ticketType)));
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
        game1.setPlace(place);
        final Optional<Game> game = Optional.of(game1);
        when(mockGameRepository.findById(0L)).thenReturn(game);

        // Configure GameConverter.toDTO(...).
        final GameDTO gameDTO = new GameDTO();
        gameDTO.setGameName("gameName");
        gameDTO.setGameDescription("gameDescription");
        gameDTO.setTicketTypeName(new HashSet<>(Arrays.asList("value")));
        gameDTO.setPlaceId(0L);
        gameDTO.setPlaceName("placeName");
        gameDTO.setStatus("status");
        when(mockGameConverter.toDTO(new Game())).thenReturn(gameDTO);

        // Run the test
        final ResponseEntity<?> result = gameServiceImplUnderTest.getGame(0L);

        // Verify the results
    }

    @Test
    public void testFindByMulParam() {
        // Setup

        // Configure GameRepository.findByMulParam(...).
        final Output output = new Output();
        output.setPage(0);
        output.setTotalPage(0);
        output.setListResult(Arrays.asList());
        output.setTotalItems(0);
        when(mockGameRepository.findByMulParam("gameName", "placeName", 0L, 0L)).thenReturn(output);

        // Run the test
        final ResponseEntity<?> result = gameServiceImplUnderTest.findByMulParam("gameName", "placeName", 0L, 0L);

        // Verify the results
    }

    @Test
    public void testFindAll() {
        // Setup

        // Configure GameRepository.findAll(...).
        final Game game = new Game();
        game.setGameName("gameName");
        game.setGameDescription("gameDescription");
        game.setStatus("status");
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
        ticketType.setGame(new HashSet<>(Arrays.asList(new Game())));
        game.setTicketTypes(new HashSet<>(Arrays.asList(ticketType)));
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
        final List<Game> gameList = Arrays.asList(game);
        when(mockGameRepository.findAll()).thenReturn(gameList);

        // Configure GameConverter.toDTO(...).
        final GameDTO gameDTO = new GameDTO();
        gameDTO.setGameName("gameName");
        gameDTO.setGameDescription("gameDescription");
        gameDTO.setTicketTypeName(new HashSet<>(Arrays.asList("value")));
        gameDTO.setPlaceId(0L);
        gameDTO.setPlaceName("placeName");
        gameDTO.setStatus("status");
        when(mockGameConverter.toDTO(new Game())).thenReturn(gameDTO);

        // Run the test
        final ResponseEntity<?> result = gameServiceImplUnderTest.findAll();

        // Verify the results
    }

    @Test
    public void testChangeStatus() {
        // Setup

        // Configure GameRepository.findById(...).
        final Game game1 = new Game();
        game1.setGameName("gameName");
        game1.setGameDescription("gameDescription");
        game1.setStatus("status");
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
        ticketType.setGame(new HashSet<>(Arrays.asList(new Game())));
        game1.setTicketTypes(new HashSet<>(Arrays.asList(ticketType)));
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
        game1.setPlace(place);
        final Optional<Game> game = Optional.of(game1);
        when(mockGameRepository.findById(0L)).thenReturn(game);

        // Configure GameRepository.save(...).
        final Game game2 = new Game();
        game2.setGameName("gameName");
        game2.setGameDescription("gameDescription");
        game2.setStatus("status");
        final TicketType ticketType1 = new TicketType();
        ticketType1.setTypeName("typeName");
        ticketType1.setPlaceId(0L);
        ticketType1.setStatus("status");
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
        final Code code1 = new Code();
        code1.setCode("code");
        code1.setVisitorType(new VisitorType());
        visitorType1.setCode(new HashSet<>(Arrays.asList(code1)));
        ticketType1.setVisitorType(new HashSet<>(Arrays.asList(visitorType1)));
        ticketType1.setGame(new HashSet<>(Arrays.asList(new Game())));
        game2.setTicketTypes(new HashSet<>(Arrays.asList(ticketType1)));
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
        game2.setPlace(place1);
        when(mockGameRepository.save(new Game())).thenReturn(game2);

        // Configure GameConverter.toDTO(...).
        final GameDTO gameDTO = new GameDTO();
        gameDTO.setGameName("gameName");
        gameDTO.setGameDescription("gameDescription");
        gameDTO.setTicketTypeName(new HashSet<>(Arrays.asList("value")));
        gameDTO.setPlaceId(0L);
        gameDTO.setPlaceName("placeName");
        gameDTO.setStatus("status");
        when(mockGameConverter.toDTO(new Game())).thenReturn(gameDTO);

        // Run the test
        final ResponseEntity<?> result = gameServiceImplUnderTest.changeStatus(0L);

        // Verify the results
    }

    @Test
    public void testFindByPlaceId() {
        // Setup

        // Configure GameRepository.findByPlaceId(...).
        final Output output = new Output();
        output.setPage(0);
        output.setTotalPage(0);
        output.setListResult(Arrays.asList());
        output.setTotalItems(0);
        when(mockGameRepository.findByPlaceId(0L, 0L, 0L)).thenReturn(output);

        // Run the test
        final ResponseEntity<?> result = gameServiceImplUnderTest.findByPlaceId(0L, 0L, 0L);

        // Verify the results
    }

    @Test
    public void testListOptionByPlace() {
        // Setup

        // Configure GameRepository.findByPlaceIdAndStatus(...).
        final Game game = new Game();
        game.setGameName("gameName");
        game.setGameDescription("gameDescription");
        game.setStatus("status");
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
        ticketType.setGame(new HashSet<>(Arrays.asList(new Game())));
        game.setTicketTypes(new HashSet<>(Arrays.asList(ticketType)));
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
        final List<Game> gameList = Arrays.asList(game);
        when(mockGameRepository.findByPlaceIdAndStatus(0L, "status")).thenReturn(gameList);

        // Configure GameConverter.toGameLite(...).
        final GameDTOLite gameDTOLite = new GameDTOLite();
        gameDTOLite.setGameName("gameName");
        when(mockGameConverter.toGameLite(new Game())).thenReturn(gameDTOLite);

        // Run the test
        final ResponseEntity<?> result = gameServiceImplUnderTest.listOptionByPlace(0L);

        // Verify the results
    }
}
