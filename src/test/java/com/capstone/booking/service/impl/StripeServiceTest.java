package com.capstone.booking.service.impl;

import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StripeServiceTest {

    private StripeService stripeServiceUnderTest;

    @Before
    public void setUp() {
        stripeServiceUnderTest = new StripeService();
    }

    @Test
    public void testChargeNewCard() {
        // Verify the results
        Assertions.assertThrows(InvalidRequestException.class,
                ()->stripeServiceUnderTest.chargeNewCard("token", 0));
    }
}
