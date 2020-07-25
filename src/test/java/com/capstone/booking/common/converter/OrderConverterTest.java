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
        expectedResult.setTicketTypeName("typeName");
        expectedResult.setUserId(0L);
        expectedResult.setFirstName("firstName");
        expectedResult.setLastName("lastName");
        expectedResult.setMail("mail");
        expectedResult.setPhoneNumber("0123456789");
        expectedResult.setStatus("status");
        expectedResult.setOrderCode("orderCode");
        expectedResult.setTotalPayment(0);
        expectedResult.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        expectedResult.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());

        // Configure TicketTypeRepository.findById(...).
        final TicketType ticketType1 = new TicketType();
        ticketType1.setTypeName("typeName");
        ticketType1.setPlaceId(0L);
        ticketType1.setStatus("status");

        Optional<TicketType> optionalTicketType = Optional.of(ticketType1);
        when(mockTicketTypeRepository.findById(0L)).thenReturn(optionalTicketType);

        // Configure OrderItemRepository.findAllByOrder(...).
        final OrderItem orderItem1 = new OrderItem();
        orderItem1.setQuantity(0);
        List<OrderItem>  orderItems = new ArrayList<>();
        orderItems.add(orderItem1);
        when(mockOrderItemRepository.findAllByOrder(order)).thenReturn(orderItems);

        // Configure OrderItemConverter.toDTO(...).
        final OrderItemDTO dto = new OrderItemDTO();
        dto.setVisitorTypeId(0L);
        dto.setVisitorTypeName("visitorTypeName");
        dto.setVisitorTypeKey("visitorTypeKey");
        dto.setQuantity(0);
        User user = new User();
        user.setLastName("lastName");
        user.setFirstName("firstName");
        user.setPhoneNumber("0123456789");
        user.setId(0l);
        order.setUser(user);
        Set<OrderItemDTO> orderItemDTOS = new HashSet<>();
        orderItemDTOS.add(dto);
        expectedResult.setOrderItems(orderItemDTOS);
        when(mockOrderItemConverter.toDTO(orderItem1)).thenReturn(dto);

        // Run the test
        final OrderDTO result = orderConverterUnderTest.toDTO(order);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
