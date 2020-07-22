package com.capstone.booking.repository.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.GameConverter;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.GameDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GameRepositoryImplTest {

    @Mock
    private GameConverter mockGameConverter;

    @InjectMocks
    private GameRepositoryImpl gameRepositoryImplUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testFindByMulParam() {
        // Setup
        final Output expectedResult = new Output();
        expectedResult.setPage(0);
        expectedResult.setTotalPage(0);
        expectedResult.setListResult(Arrays.asList());
        expectedResult.setTotalItems(0);

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
        final Output result = gameRepositoryImplUnderTest.findByMulParam("gameName", "placeName", 0L, 0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testFindByPlaceId() {
        // Setup
        final Output expectedResult = new Output();
        expectedResult.setPage(0);
        expectedResult.setTotalPage(0);
        expectedResult.setListResult(Arrays.asList());
        expectedResult.setTotalItems(0);

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
        final Output result = gameRepositoryImplUnderTest.findByPlaceId(0L, 0L, 0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testConvertList() {
        // Setup
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
        final GameDTO gameDTO = new GameDTO();
        gameDTO.setGameName("gameName");
        gameDTO.setGameDescription("gameDescription");
        gameDTO.setTicketTypeName(new HashSet<>(Arrays.asList("value")));
        gameDTO.setPlaceId(0L);
        gameDTO.setPlaceName("placeName");
        gameDTO.setStatus("status");
        final List<GameDTO> expectedResult = Arrays.asList(gameDTO);

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
        final List<GameDTO> result = gameRepositoryImplUnderTest.convertList(gameList);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testQueryGame() {
        // Setup
        final Map<String, Object> params = new HashMap<>();
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
        final List<Game> expectedResult = Arrays.asList(game);

        // Run the test
        final List<Game> result = gameRepositoryImplUnderTest.queryGame(params, "sqlStr");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
