package com.capstone.booking.repository.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.OrderConverter;
import com.capstone.booking.entity.Order;
import com.capstone.booking.entity.dto.OrderDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class OrderRepositoryImplTest {

    @Mock
    private OrderConverter mockOrderConverter;

    @InjectMocks
    private OrderRepositoryImpl orderRepositoryImplUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testFindByStatus() {
        // Setup
        final Output expectedResult = new Output();
        expectedResult.setPage(0);
        expectedResult.setTotalPage(0);
        expectedResult.setListResult(Arrays.asList());
        expectedResult.setTotalItems(0);

        // Configure OrderConverter.toDTO(...).
        final OrderDTO orderDTO = new OrderDTO();
        orderDTO.setTicketTypeId(0L);
        orderDTO.setTicketTypeName("ticketTypeName");
        orderDTO.setUserId(0L);
        orderDTO.setFirstName("firstName");
        orderDTO.setLastName("lastName");
        orderDTO.setMail("mail");
        orderDTO.setPhoneNumber("phoneNumber");
        orderDTO.setStatus("status");
        orderDTO.setOrderCode("orderCode");
        orderDTO.setTotalPayment(0);
        when(mockOrderConverter.toDTO(any(Order.class))).thenReturn(orderDTO);

        // Run the test
        final Output result = orderRepositoryImplUnderTest.findByStatus("status", "code");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testConvertList() {
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
        final List<Order> orderList = Arrays.asList(order);
        final OrderDTO orderDTO = new OrderDTO();
        orderDTO.setTicketTypeId(0L);
        orderDTO.setTicketTypeName("ticketTypeName");
        orderDTO.setUserId(0L);
        orderDTO.setFirstName("firstName");
        orderDTO.setLastName("lastName");
        orderDTO.setMail("mail");
        orderDTO.setPhoneNumber("phoneNumber");
        orderDTO.setStatus("status");
        orderDTO.setOrderCode("orderCode");
        orderDTO.setTotalPayment(0);
        final List<OrderDTO> expectedResult = Arrays.asList(orderDTO);

        // Configure OrderConverter.toDTO(...).
        final OrderDTO orderDTO1 = new OrderDTO();
        orderDTO1.setTicketTypeId(0L);
        orderDTO1.setTicketTypeName("ticketTypeName");
        orderDTO1.setUserId(0L);
        orderDTO1.setFirstName("firstName");
        orderDTO1.setLastName("lastName");
        orderDTO1.setMail("mail");
        orderDTO1.setPhoneNumber("phoneNumber");
        orderDTO1.setStatus("status");
        orderDTO1.setOrderCode("orderCode");
        orderDTO1.setTotalPayment(0);
        when(mockOrderConverter.toDTO(any(Order.class))).thenReturn(orderDTO1);

        // Run the test
        final List<OrderDTO> result = orderRepositoryImplUnderTest.convertList(orderList);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testQueryOrder() {
        // Setup
        final Map<String, Object> params = new HashMap<>();

        // Run the test
        final List<Order> result = orderRepositoryImplUnderTest.queryOrder(params, "sqlStr");

        // Verify the results
    }
}
