package com.capstone.booking.common.converter;

import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.OrderDTO;
import com.capstone.booking.entity.dto.OrderItemDTO;
import com.capstone.booking.repository.OrderItemRepository;
import com.capstone.booking.repository.TicketTypeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class OrderConverterTest {

    @Mock
    private OrderItemConverter mockOrderItemConverter;
    @Mock
    private TicketTypeRepository mockTicketTypeRepository;
    @Mock
    private OrderItemRepository mockOrderItemRepository;

    @InjectMocks
    private OrderConverter orderConverterUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testToOrder() {
        // Setup
        final OrderDTO dto = new OrderDTO();
        dto.setTicketTypeId(0L);
        dto.setTicketTypeName("ticketTypeName");
        dto.setUserId(0L);
        dto.setFirstName("firstName");
        dto.setLastName("lastName");
        dto.setMail("mail");
        dto.setPhoneNumber("phoneNumber");
        dto.setStatus("status");
        dto.setOrderCode("orderCode");
        dto.setTotalPayment(0);

        // Run the test
        final Order result = orderConverterUnderTest.toOrder(dto);

        // Verify the results
    }

    @Test
    public void testToOrder1() {
        // Setup
        final OrderDTO dto = new OrderDTO();
        dto.setTicketTypeId(0L);
        dto.setTicketTypeName("ticketTypeName");
        dto.setUserId(0L);
        dto.setFirstName("firstName");
        dto.setLastName("lastName");
        dto.setMail("mail");
        dto.setPhoneNumber("phoneNumber");
        dto.setStatus("status");
        dto.setOrderCode("orderCode");
        dto.setTotalPayment(0);

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

        // Run the test
        final Order result = orderConverterUnderTest.toOrder(dto, order);

        // Verify the results
    }

    @Test
    public void testToDTO() {
        // Setup
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

        final OrderDTO expectedResult = new OrderDTO();
        expectedResult.setTicketTypeId(0L);
        expectedResult.setTicketTypeName("ticketTypeName");
        expectedResult.setUserId(0L);
        expectedResult.setFirstName("firstName");
        expectedResult.setLastName("lastName");
        expectedResult.setMail("mail");
        expectedResult.setPhoneNumber("phoneNumber");
        expectedResult.setStatus("status");
        expectedResult.setOrderCode("orderCode");
        expectedResult.setTotalPayment(0);

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
        orderItem.setOrder(order1);
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
        when(mockTicketTypeRepository.findById(0L)).thenReturn(ticketType);

        // Configure OrderItemRepository.findAllByOrder(...).
        final OrderItem orderItem1 = new OrderItem();
        orderItem1.setQuantity(0);
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
        visitorType1.setOrderItem(new HashSet<>(Arrays.asList(new OrderItem())));
        final Code code1 = new Code();
        code1.setCode("code");
        code1.setVisitorType(new VisitorType());
        visitorType1.setCode(new HashSet<>(Arrays.asList(code1)));
        orderItem1.setVisitorType(visitorType1);
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
        orderItem1.setOrder(order2);
        final Ticket ticket1 = new Ticket();
        ticket1.setCode("code");
        ticket1.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket1.setVisitorTypeId(0L);
        ticket1.setOrderItem(new OrderItem());
        orderItem1.setTicket(new HashSet<>(Arrays.asList(ticket1)));
        final List<OrderItem> orderItems = Arrays.asList(orderItem1);
        when(mockOrderItemRepository.findAllByOrder(any(Order.class))).thenReturn(orderItems);

        // Configure OrderItemConverter.toDTO(...).
        final OrderItemDTO dto = new OrderItemDTO();
        dto.setVisitorTypeId(0L);
        dto.setVisitorTypeName("visitorTypeName");
        dto.setVisitorTypeKey("visitorTypeKey");
        dto.setQuantity(0);
        User user = new User();
        user.setFirstName("first name");
        user.setFirstName("last name");
        user.setPhoneNumber("0123456789");
        order.setUser(user);
        when(mockOrderItemConverter.toDTO(any(OrderItem.class))).thenReturn(dto);

        // Run the test
        final OrderDTO result = orderConverterUnderTest.toDTO(order);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
