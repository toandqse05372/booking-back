package com.capstone.booking.common;

import org.apache.poi.ss.formula.functions.T;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

public class PayloadTest {

    private Payload<T> payloadUnderTest;

    @Before
    public void setUp() {
        payloadUnderTest = new Payload<>();
    }

    @Test
    public void testSuccess() {
        // Setup
        final T data = null;

        // Run the test
        final Payload<T> result = payloadUnderTest.success(data);

        // Verify the results
    }

    @Test
    public void testFail() {
        // Setup
        final T data = null;

        // Run the test
        final Payload<T> result = payloadUnderTest.fail(data);

        // Verify the results
    }

    @Test
    public void testError() {
        // Setup

        // Run the test
        final Payload<T> result = payloadUnderTest.error("message");

        // Verify the results
    }

    @Test
    public void testError1() {
        // Setup
        final T data = null;

        // Run the test
        final Payload<T> result = payloadUnderTest.error("message", data);

        // Verify the results
    }

    @Test
    public void testInternalError() {
        // Setup

        // Run the test
        final ResponseEntity<?> result = Payload.internalError();

        // Verify the results
    }

    @Test
    public void testSuccessResponse() {
        // Setup

        // Run the test
        final ResponseEntity<Payload<Object>> result = Payload.successResponse("data");

        // Verify the results
    }

    @Test
    public void testFailureResponse() {
        // Setup

        // Run the test
        final ResponseEntity<Payload<Object>> result = Payload.failureResponse("message");

        // Verify the results
    }

    @Test
    public void testFailedAuthorization() {
        // Setup

        // Run the test
        final ResponseEntity<Payload<Object>> result = Payload.failedAuthorization("message");

        // Verify the results
    }
}
