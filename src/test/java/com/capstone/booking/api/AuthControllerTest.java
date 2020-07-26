package com.capstone.booking.api;

import com.capstone.booking.entity.dto.FBLoginDTO;
import com.capstone.booking.entity.dto.UserDTO;
import com.capstone.booking.service.AuthService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AuthControllerTest {

    @Mock
    private AuthService mockAuthService;

    @InjectMocks
    private AuthController authControllerUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testLogin() {
        // Setup
        final UserDTO user = new UserDTO();
        user.setPassword("password");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setMail("mail");
        user.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user.setPhoneNumber("phoneNumber");
        user.setStatus("status");
        user.setRoleKey(new HashSet<>(Arrays.asList("value")));
        user.setUserType("userType");

        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockAuthService).findByEmail(user, "page");

        // Run the test
        final ResponseEntity<?> result = authControllerUnderTest.login(user, "page");

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testLoginFb() throws Exception {
        // Setup
        final FBLoginDTO fbForm = new FBLoginDTO();
        fbForm.setAccessToken("accessToken");
        fbForm.setEmail("email");
        fbForm.setName("name");

        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockAuthService).loginFb(fbForm);

        // Run the test
        final ResponseEntity<?> result = authControllerUnderTest.loginFb(fbForm);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testLogout() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockAuthService).logout("token");

        // Run the test
        final ResponseEntity<?> result = authControllerUnderTest.logout("token");

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testCheckToken() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockAuthService).checkToken("Token token");

        // Run the test
        final ResponseEntity<?> result = authControllerUnderTest.checkToken("Token token");

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }
}
