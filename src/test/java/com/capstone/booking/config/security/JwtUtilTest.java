package com.capstone.booking.config.security;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtUtilTest {

    private JwtUtil jwtUtilUnderTest;

    @Before
    public void setUp() {
        jwtUtilUnderTest = new JwtUtil();
    }

    @Test
    public void testGenerateToken() {
        // Setup
        final UserPrincipal user = new UserPrincipal();

        // Run the test
        final String result = jwtUtilUnderTest.generateToken(user);

        // Verify the results
        assertThat(result).isEqualTo("result");
    }

    @Test
    public void testGenerateExpirationDate() {
        // Setup

        // Run the test
        final Date result = jwtUtilUnderTest.generateExpirationDate();

        // Verify the results
        assertThat(result).isEqualTo(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
    }

    @Test
    public void testGetUserFromToken() {
        // Setup

        // Run the test
        final UserPrincipal result = jwtUtilUnderTest.getUserFromToken("token");

        // Verify the results
    }
}
