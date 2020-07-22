package com.capstone.booking.service.impl;

import com.stripe.model.Charge;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StripeServiceTest {

    private StripeService stripeServiceUnderTest;

    @Before
    public void setUp() {
        stripeServiceUnderTest = new StripeService();
    }

    @Test
    public void testChargeNewCard() throws Exception {
        // Setup
        final Charge expectedResult = new Charge();

        // Run the test
        final Charge result = stripeServiceUnderTest.chargeNewCard("token", 0);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testChargeNewCard_ThrowsException() {
        // Setup

        // Run the test
        assertThatThrownBy(() -> {
            stripeServiceUnderTest.chargeNewCard("token", 0);
        }).isInstanceOf(Exception.class).hasMessageContaining("message");
    }
}
