package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.GameConverter;
import com.capstone.booking.common.key.MonoStatus;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.GameDTO;
import com.capstone.booking.entity.dto.GameDTOLite;
import com.capstone.booking.repository.GameRepository;
import com.capstone.booking.repository.PlaceRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
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
        when(mockGameRepository.findByGameNameAndPlace("gameName", place1)).thenReturn(null);

        // Configure GameConverter.toGame(...).
        when(mockGameConverter.toGame(gameDTO)).thenReturn(game);

        // Configure GameRepository.save(...).
        when(mockGameRepository.save(game)).thenReturn(game);

        // Configure GameConverter.toDTO(...).
        when(mockGameConverter.toDTO(game)).thenReturn(gameDTO);

        // Run the test
        final ResponseEntity<?> result = gameServiceImplUnderTest.create(gameDTO);

        Assert.assertEquals(200, result.getStatusCodeValue());
        // Verify the results
    }

    @Test
    public void testUpdate() {
        // Setup
        final GameDTO gameDTO = new GameDTO();
        gameDTO.setGameName("gameName");
        gameDTO.setGameDescription("gameDescription");
        gameDTO.setPlaceId(0L);
        gameDTO.setPlaceName("placeName");
        gameDTO.setStatus("status");
        gameDTO.setId(0l);

        // Configure PlaceRepository.findById(...).
        final Place place1 = new Place();
        place1.setId(0l);
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
        game.setId(0l);
        game.setPlace(place1);
        when(mockGameRepository.findByGameNameAndPlace("name", place1)).thenReturn(null);

        // Configure GameRepository.findById(...).
        final Optional<Game> gameOptional = Optional.of(game);
        when(mockGameRepository.findById(0L)).thenReturn(gameOptional);

        // Configure GameConverter.toGame(...).
        when(mockGameConverter.toGame(gameDTO, game)).thenReturn(game);

        // Configure GameRepository.save(...).
        when(mockGameRepository.save(game)).thenReturn(game);

        // Configure GameConverter.toDTO(...).
        when(mockGameConverter.toDTO(game)).thenReturn(gameDTO);

        // Run the test
        final ResponseEntity<?> result = gameServiceImplUnderTest.update(gameDTO);

        // Verify the results
        Assert.assertEquals(200, result.getStatusCodeValue());
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
//        final Code code = new Code();
//        code.setCode("code");
//        code.setVisitorType(new VisitorType());
//        visitorType.setCode(new HashSet<>(Arrays.asList(code)));
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
//        final Code code = new Code();
//        code.setCode("code");
//        code.setVisitorType(new VisitorType());
//        visitorType.setCode(new HashSet<>(Arrays.asList(code)));
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
        Assertions.assertEquals(200, result.getStatusCodeValue());
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
        Assertions.assertEquals(200, result.getStatusCodeValue());
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
//        final Code code = new Code();
//        code.setCode("code");
//        code.setVisitorType(new VisitorType());
//        visitorType.setCode(new HashSet<>(Arrays.asList(code)));
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
        Assertions.assertEquals(200, result.getStatusCodeValue());
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
//        final Code code = new Code();
//        code.setCode("code");
//        code.setVisitorType(new VisitorType());
//        visitorType.setCode(new HashSet<>(Arrays.asList(code)));
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
//        final Code code1 = new Code();
//        code1.setCode("code");
//        code1.setVisitorType(new VisitorType());
//        visitorType1.setCode(new HashSet<>(Arrays.asList(code1)));
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
        Assertions.assertEquals(200, result.getStatusCodeValue());
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
        Assertions.assertEquals(200, result.getStatusCodeValue());
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
//        final Code code = new Code();
//        code.setCode("code");
//        code.setVisitorType(new VisitorType());
//        visitorType.setCode(new HashSet<>(Arrays.asList(code)));
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
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }
}
