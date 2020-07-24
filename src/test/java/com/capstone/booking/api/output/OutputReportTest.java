package com.capstone.booking.api.output;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class OutputReportTest {

    private OutputReport outputReportUnderTest;

    @Before
    public void setUp() {
        outputReportUnderTest = new OutputReport();
        outputReportUnderTest.reportItemList = Arrays.asList(new ReportItem());
        outputReportUnderTest.startDate = 0L;
        outputReportUnderTest.endDate = 0L;
        outputReportUnderTest.reportType = 0L;
        outputReportUnderTest.placeId = 0L;
        outputReportUnderTest.totalRevenue = 0;
    }

    @Test
    public void testEquals() {
        // Setup

        // Run the test
        final boolean result = outputReportUnderTest.equals("o");

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    public void testHashCode() {
        // Setup

        // Run the test
        final int result = outputReportUnderTest.hashCode();

        // Verify the results
        assertThat(result).isEqualTo(1213000816);
    }

    @Test
    public void testToString() {
        // Setup

        // Run the test
        final String result = outputReportUnderTest.toString();

        // Verify the results
        assertThat(result).isEqualTo("OutputReport(reportItemList=[ReportItem(ticketTypeName=null, quantity=0, total=0)], startDate=0, endDate=0, reportType=0, placeId=0, totalRevenue=0)");
    }
}