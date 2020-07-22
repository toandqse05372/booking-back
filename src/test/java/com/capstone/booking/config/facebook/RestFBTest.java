package com.capstone.booking.config.facebook;

import com.capstone.booking.entity.dto.UserDTO;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class RestFBTest {

    private RestFB restFBUnderTest;

    @Before
    public void setUp() {
        restFBUnderTest = new RestFB();
    }

    @Test
    public void testGetUserInfo() {
        // Setup
        final UserDTO expectedResult = new UserDTO();
        expectedResult.setPassword("password");
        expectedResult.setFirstName("firstName");
        expectedResult.setLastName("lastName");
        expectedResult.setMail("mail");
        expectedResult.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        expectedResult.setPhoneNumber("phoneNumber");
        expectedResult.setStatus("status");
        expectedResult.setRoleKey(new HashSet<>(Arrays.asList("value")));
        expectedResult.setUserType("userType");

        // Run the test
        final UserDTO result = restFBUnderTest.getUserInfo("accessToken");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
