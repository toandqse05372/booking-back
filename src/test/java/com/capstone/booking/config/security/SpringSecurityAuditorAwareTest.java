package com.capstone.booking.config.security;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class SpringSecurityAuditorAwareTest {

    private SpringSecurityAuditorAware springSecurityAuditorAwareUnderTest;

    @Before
    public void setUp() {
        springSecurityAuditorAwareUnderTest = new SpringSecurityAuditorAware();
    }

    @Test
    public void testGetCurrentAuditor() {
        // Setup
        final Optional<Long> expectedResult = Optional.of(0L);

        // Run the test
        final Optional<Long> result = springSecurityAuditorAwareUnderTest.getCurrentAuditor();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
