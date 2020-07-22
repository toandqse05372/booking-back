package com.capstone.booking.config.stripe;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

public class StripeTokenTest {

    private StripeToken stripeTokenUnderTest;

    @Before
    public void setUp() {
        stripeTokenUnderTest = new StripeToken();
        stripeTokenUnderTest.token = "token";
    }

    @Test
    public void testEquals() {
        // Setup

        // Run the test
        final boolean result = stripeTokenUnderTest.equals("o");

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    public void testHashCode() {
        // Setup

        // Run the test
        final int result = stripeTokenUnderTest.hashCode();

        // Verify the results
        assertThat(result).isEqualTo(110541364);
    }

    @Test
    public void testToString() {
        // Setup

        // Run the test
        final String result = stripeTokenUnderTest.toString();

        // Verify the results
        assertNotNull(result);
    }
}
