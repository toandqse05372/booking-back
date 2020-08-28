package com.capstone.booking.api;

import com.capstone.booking.common.key.OrderStatus;
import com.capstone.booking.entity.dto.OrderDTO;
import com.capstone.booking.service.OrderService;
import com.capstone.booking.service.impl.StripeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
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
        String model = "{\"ticketTypeId\":1,\"ticketTypeName\":\"Vé vào cổng\",\"userId\":1,\"totalPayment\":700000,\"purchaseDay\":\"2020-07-22T03:04:37.543Z\",\"redemptionDate\":\"2020-07-22T03:04:14.102Z\",\"orderItems\":[{\"visitorTypeId\":1,\"quantity\":1}]}";
        when(mockStripeService.chargeNewCard("token", 0)).thenReturn("");
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockOrderService).create(new OrderDTO(), OrderStatus.PAID, null);

        // Run the test
        final ResponseEntity<?> result = paymentControllerUnderTest.payForOrder( model, "stripetoken", "PAID");

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }
}
