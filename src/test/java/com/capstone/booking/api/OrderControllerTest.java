package com.capstone.booking.api;

import com.capstone.booking.common.helper.pdf.PrintTicketRequest;
import com.capstone.booking.common.key.OrderStatus;
import com.capstone.booking.entity.dto.OrderDTO;
import com.capstone.booking.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

public class OrderControllerTest {

    @Mock
    private OrderService mockOrderService;

    @InjectMocks
    private OrderController orderControllerUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testDeleteMethod() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockOrderService).delete(0L);

        // Run the test
        final ResponseEntity<?> result = orderControllerUnderTest.delete(0L);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testOrderFilter() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockOrderService).findByStatus("status", "code", 1l, 1l, 10l);

        // Run the test
        final ResponseEntity<?> result = orderControllerUnderTest.orderFilter("status", "code", 1l, 1l, 10l);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testCreateMethod() {
        // Setup
        final OrderDTO model = new OrderDTO();
        model.setTicketTypeId(0L);
        model.setTicketTypeName("ticketTypeName");
        model.setUserId(0L);
        model.setFirstName("firstName");
        model.setLastName("lastName");
        model.setMail("mail");
        model.setPhoneNumber("phoneNumber");
        model.setStatus("status");
        model.setOrderCode("orderCode");
        model.setTotalPayment(0);

        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockOrderService).create(model, OrderStatus.UNPAID, null);

        // Run the test
        final ResponseEntity<?> result = orderControllerUnderTest.create(model);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testSendTicket() throws Exception {
        // Setup
        final PrintTicketRequest request = new PrintTicketRequest();
        request.setOrderId(0L);
        request.setType(0);

        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockOrderService).sendTicket(0L);

        // Run the test
        final ResponseEntity<?> result = orderControllerUnderTest.sendTicket(request);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testreSendTicket() throws Exception {
        // Setup
        final PrintTicketRequest request = new PrintTicketRequest();
        request.setOrderId(0L);
        request.setType(2);

        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockOrderService).resendTicket(0L);

        // Run the test
        final ResponseEntity<?> result = orderControllerUnderTest.sendTicket(request);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testGetOrderById() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockOrderService).findByOrderId(0L, 0l);

        // Run the test
        final ResponseEntity<?> result = orderControllerUnderTest.getOrderById(0L, "0l");

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testGetOrdersByUid() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockOrderService).getOrderByUid(0L, 0l);

        // Run the test
        final ResponseEntity<?> result = orderControllerUnderTest.getOrdersByUid(0L, "3", "1", "10");

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testGetOrdersByUidTop3() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockOrderService).getOrderByUidTop3(0L, 0l);

        // Run the test
        final ResponseEntity<?> result = orderControllerUnderTest.getOrdersByUidTop3(0L, "3");

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }
}
