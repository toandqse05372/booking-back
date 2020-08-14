package com.capstone.booking.common.converter;

import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.TicketDTO;
import com.capstone.booking.entity.dto.VisitorTypeDTO;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class TicketConverterTest {

    private TicketConverter ticketConverterUnderTest;

    @Before
    public void setUp() {
        ticketConverterUnderTest = new TicketConverter();
    }

    @Test
    public void testToTicket() {
        // Setup
        final TicketDTO dto = new TicketDTO();
        dto.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        dto.setCode("code");
        final VisitorTypeDTO visitorType = new VisitorTypeDTO();
        visitorType.setTypeName("typeName");
        visitorType.setTypeKey("typeKey");
        visitorType.setTicketTypeId(0L);
        visitorType.setPrice(0);
        visitorType.setBasicType(false);
        visitorType.setRemaining(0);
        visitorType.setStatus("status");
        dto.setVisitorType(visitorType);
        dto.setVisitorTypeId(0L);

        // Run the test
        final Ticket result = ticketConverterUnderTest.toTicket(dto);

        // Verify the results
    }

    @Test
    public void testToTicket1() {
        // Setup
        final TicketDTO dto = new TicketDTO();
        dto.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        dto.setCode("code");
        final VisitorTypeDTO visitorType = new VisitorTypeDTO();
        visitorType.setTypeName("typeName");
        visitorType.setTypeKey("typeKey");
        visitorType.setTicketTypeId(0L);
        visitorType.setPrice(0);
        visitorType.setBasicType(false);
        visitorType.setRemaining(0);
        visitorType.setStatus("status");
        dto.setVisitorType(visitorType);
        dto.setVisitorTypeId(0L);

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
//        final Code code = new Code();
//        code.setCode("code");
//        code.setVisitorType(new VisitorType());
//        visitorType1.setCode(new HashSet<>(Arrays.asList(code)));
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

        // Run the test
        final Ticket result = ticketConverterUnderTest.toTicket(dto, ticket);

        // Verify the results
    }

    @Test
    public void testToDTO() {
        // Setup
        final Ticket ticket = new Ticket();
        ticket.setCode("code");
        ticket.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket.setVisitorTypeId(0L);
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
//        final Code code = new Code();
//        code.setCode("code");
//        code.setVisitorType(new VisitorType());
//        visitorType.setCode(new HashSet<>(Arrays.asList(code)));
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
        ticket.setOrderItem(orderItem);

        final TicketDTO expectedResult = new TicketDTO();
        expectedResult.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        expectedResult.setCode("code");
        expectedResult.setVisitorTypeId(0L);

        // Run the test
        final TicketDTO result = ticketConverterUnderTest.toDTO(ticket);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
