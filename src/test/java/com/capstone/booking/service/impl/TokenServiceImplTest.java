package com.capstone.booking.service.impl;

import com.capstone.booking.entity.Token;
import com.capstone.booking.repository.TokenRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TokenServiceImplTest {

    @Mock
    private TokenRepository mockTokenRepository;

    @InjectMocks
    private TokenServiceImpl tokenServiceImplUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testCreateToken() {
        // Setup
        final Token token = new Token();
        token.setToken("token");
        token.setTokenExpDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());

        // Configure TokenRepository.saveAndFlush(...).
        final Token token1 = new Token();
        token1.setToken("token");
        token1.setTokenExpDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        when(mockTokenRepository.saveAndFlush(any(Token.class))).thenReturn(token1);

        // Run the test
        final ResponseEntity<?> result = tokenServiceImplUnderTest.createToken(token);

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void testFindByToken() {
        // Setup

        // Configure TokenRepository.findByToken(...).
        final Token token = new Token();
        token.setToken("token");
        token.setTokenExpDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        when(mockTokenRepository.findByToken("token")).thenReturn(token);

        // Run the test
        final Token result = tokenServiceImplUnderTest.findByToken("token");

        // Verify the results
        Assertions.assertNotNull(result);
    }
}
