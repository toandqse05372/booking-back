package com.capstone.booking.common.helper;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSender;

public class MailConfigTest {

    private MailConfig mailConfigUnderTest;

    @Before
    public void setUp() {
        mailConfigUnderTest = new MailConfig();
    }

    @Test
    public void testGetJavaMailSender() {
        // Setup

        // Run the test
        final JavaMailSender result = mailConfigUnderTest.getJavaMailSender();

        // Verify the results
    }
}
