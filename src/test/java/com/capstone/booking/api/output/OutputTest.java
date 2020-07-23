package com.capstone.booking.api.output;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OutputTest {

    private Output outputUnderTest;

    @Before
    public void setUp() {
        outputUnderTest = new Output();
    }

    @Test
    public void testEquals() {
        // Setup

        // Run the test
        final boolean result = outputUnderTest.equals("o");

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    public void testHashCode() {
        // Setup

        // Run the test
        final int result = outputUnderTest.hashCode();

        // Verify the results
        assertThat(result).isEqualTo(12119898);
    }

    @Test
    public void testToString() {
        // Setup

        // Run the test
        final String result = outputUnderTest.toString();

        // Verify the results
        assertThat(result).isEqualTo("Output(page=0, totalPage=0, listResult=null, totalItems=0)");
    }
}
