package com.capstone.booking.common.converter;

import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.GameDTO;
import com.capstone.booking.entity.dto.GameDTOLite;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class GameConverterTest {

    private GameConverter gameConverterUnderTest;

    @Before
    public void setUp() {
        gameConverterUnderTest = new GameConverter();
    }

    @Test
    public void testToGame() {
        // Setup
        final GameDTO dto = new GameDTO();
        dto.setGameName("gameName");
        dto.setGameDescription("gameDescription");


        final Game expectedResult = new Game();
        expectedResult.setGameName("gameName");
        expectedResult.setGameDescription("gameDescription");

        // Run the test
        final Game result = gameConverterUnderTest.toGame(dto);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testToGame1() {
        // Setup
        final GameDTO dto = new GameDTO();
        dto.setGameName("gameName");
        dto.setGameDescription("gameDescription");
        dto.setTicketTypeName(new HashSet<>(Arrays.asList("value")));
        dto.setPlaceId(0L);
        dto.setPlaceName("placeName");
        dto.setStatus("status");

        final Game game = new Game();
        game.setGameName("gameName");
        game.setGameDescription("gameDescription");
        game.setStatus("status");

        final Game expectedResult = new Game();
        expectedResult.setGameName("gameName");
        expectedResult.setGameDescription("gameDescription");
        expectedResult.setStatus("status");

        // Run the test
        final Game result = gameConverterUnderTest.toGame(dto, game);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testToDTO() {
        // Setup
        final Game game = new Game();
        game.setGameName("gameName");
        game.setGameDescription("gameDescription");
        game.setStatus("status");

        final GameDTO expectedResult = new GameDTO();
        expectedResult.setGameName("gameName");
        expectedResult.setGameDescription("gameDescription");
        expectedResult.setStatus("status");

        // Run the test
        final GameDTO result = gameConverterUnderTest.toDTO(game);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testToGameLite() {
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

        final GameDTOLite expectedResult = new GameDTOLite();
        expectedResult.setGameName("gameName");

        // Run the test
        final GameDTOLite result = gameConverterUnderTest.toGameLite(game);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
