package com.capstone.booking.repository.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.OrderConverter;
import com.capstone.booking.entity.Order;
import com.capstone.booking.entity.dto.OrderDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class OrderRepositoryImplTest {

    @Mock
    private EntityManager mockEntityManager;

    private OrderRepositoryImpl orderRepositoryImplUnderTest;

    @Mock
    Query query;

    @Before
    public void setUp() {
        initMocks(this);
        orderRepositoryImplUnderTest = new OrderRepositoryImpl(mockEntityManager);
        orderRepositoryImplUnderTest.orderConverter = mock(OrderConverter.class);
    }

    @Test
    public void testFindByStatus() {
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
        final Output expectedResult = new Output();
        expectedResult.setPage(0);
        expectedResult.setTotalPage(0);
        expectedResult.setListResult(Arrays.asList(orderDTO));
        expectedResult.setTotalItems(0);

        when(mockEntityManager.createNativeQuery("select o.* from t_order o  where o.status like :status  and o.order_code like :code ", Order.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(orderList);
        when(orderRepositoryImplUnderTest.orderConverter.toDTO(order)).thenReturn(orderDTO);
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
        when(orderRepositoryImplUnderTest.orderConverter.toDTO(order)).thenReturn(orderDTO);
        // Run the test
        final List<OrderDTO> result = orderRepositoryImplUnderTest.convertList(orderList);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testQueryOrder() {
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
        final Map<String, Object> params = new HashMap<>();
        when(mockEntityManager.createNativeQuery("select * from order", Order.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(orderList);

        // Run the test
        final List<Order> result = orderRepositoryImplUnderTest.queryOrder(params, "select * from order");

        // Verify the results
        Assertions.assertEquals(result, orderList);
    }
}
