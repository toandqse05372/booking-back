package com.capstone.booking.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class EmailSenderServiceTest {

    @Mock
    private JavaMailSender mockJavaMailSender;

    private EmailSenderService emailSenderServiceUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
        emailSenderServiceUnderTest = new EmailSenderService(mockJavaMailSender);
    }

    @Test
    public void testSendEmail() {
        // Setup
        final SimpleMailMessage email = new SimpleMailMessage();

        // Run the test
        emailSenderServiceUnderTest.sendEmail(email);

        // Verify the results
        verify(mockJavaMailSender).send(new SimpleMailMessage());
    }
}
