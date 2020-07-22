package com.capstone.booking.api;

import com.capstone.booking.common.key.OrderStatus;
import com.capstone.booking.entity.dto.OrderDTO;
import com.capstone.booking.service.OrderService;
import com.capstone.booking.service.impl.StripeService;
import com.stripe.model.Charge;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PaymentControllerTest {

    @Mock
    private StripeService mockStripeService;
    @Mock
    private OrderService mockOrderService;

    @InjectMocks
    private PaymentController paymentControllerUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testPayForOrder() throws Exception {
        // Setup
        when(mockStripeService.chargeNewCard("token", 0)).thenReturn(new Charge());
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockOrderService).create(new OrderDTO(), OrderStatus.PAID);

        // Run the test
        final ResponseEntity<?> result = paymentControllerUnderTest.payForOrder("{\"ticketTypeId\":1,\"ticketTypeName\":\"Vé vào cổng\",\"userId\":1,\"totalPayment\":700000,\"purchaseDay\":\"2020-07-22T03:04:37.543Z\",\"redemptionDate\":\"2020-07-22T03:04:14.102Z\",\"orderItems\":[{\"visitorTypeId\":1,\"quantity\":1}]}", "stripetoken");

        // Verify the results
    }

    @Test
    public void testPayForOrder_StripeServiceThrowsException() throws Exception {
        // Setup
        when(mockStripeService.chargeNewCard("token", 0)).thenThrow(Exception.class);
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockOrderService).create(new OrderDTO(), OrderStatus.PAID);

        // Run the test
        assertThatThrownBy(() -> {
            paymentControllerUnderTest.payForOrder("message", "stripetoken");
        }).isInstanceOf(Exception.class).hasMessageContaining("message");
    }
}
