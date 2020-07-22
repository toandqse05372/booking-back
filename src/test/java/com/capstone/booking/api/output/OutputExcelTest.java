package com.capstone.booking.api.output;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OutputExcelTest {

    private OutputExcel outputExcelUnderTest;

    @Before
    public void setUp() {
        outputExcelUnderTest = new OutputExcel();
    }

    @Test
    public void testEquals() {
        // Setup

        // Run the test
        final boolean result = outputExcelUnderTest.equals("o");

        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    public void testHashCode() {
        // Setup

        // Run the test
        final int result = outputExcelUnderTest.hashCode();

        // Verify the results
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void testToString() {
        // Setup

        // Run the test
        final String result = outputExcelUnderTest.toString();

        // Verify the results
        assertThat(result).isEqualTo("result");
    }
}
