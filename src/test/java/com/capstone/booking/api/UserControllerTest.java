package com.capstone.booking.api;

import com.capstone.booking.entity.dto.UserDTO;
import com.capstone.booking.service.UserService;
import lombok.SneakyThrows;
import org.apache.poi.util.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserControllerTest {

    @Mock
    private UserService mockUserService;

    @InjectMocks
    private UserController userControllerUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testRegister() {
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
        user.setAvatarLink("avatarLink");

        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockUserService).register(user);

        // Run the test
        final ResponseEntity<?> result = userControllerUnderTest.register(user);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testResendEmail() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockUserService).resendEmailVerify("mail");

        // Run the test
        final ResponseEntity<?> result = userControllerUnderTest.resendEmail("mail");

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testActive() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockUserService).verifyEmail("verificationToken");

        // Run the test
        final ResponseEntity<?> result = userControllerUnderTest.active("verificationToken");

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testCreateUserCMS() {
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
        user.setAvatarLink("avatarLink");

        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockUserService).createUserCMS(user);

        // Run the test
        final ResponseEntity<?> result = userControllerUnderTest.createUserCMS(user);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testUpdateUser() {
        // Setup
        final UserDTO model = new UserDTO();
        model.setPassword("password");
        model.setFirstName("firstName");
        model.setLastName("lastName");
        model.setMail("mail");
        model.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        model.setPhoneNumber("phoneNumber");
        model.setStatus("status");
        model.setRoleKey(new HashSet<>(Arrays.asList("value")));
        model.setUserType("userType");
        model.setAvatarLink("avatarLink");

        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockUserService).update(model);

        // Run the test
        final ResponseEntity<?> result = userControllerUnderTest.updateUser(model, 0L);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testFindByMultiParam() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockUserService).findByMultiParam("fname", "mail", "lastName", "phoneNumber", 0L, 0L, 0L);

        // Run the test
        final ResponseEntity<?> result = userControllerUnderTest.findByMultiParam("fname", "mail", "lastName", "phoneNumber", 0L, 0L, 0L);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testDeleteUser() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockUserService).delete(0L);

        // Run the test
        final ResponseEntity<?> result = userControllerUnderTest.deleteUser(0L);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testGetUser() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockUserService).getUser(0L);

        // Run the test
        final ResponseEntity<?> result = userControllerUnderTest.getUser(0L);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testFindAllRoles() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockUserService).findAllRoles();

        // Run the test
        final ResponseEntity<?> result = userControllerUnderTest.findAllRoles();

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testResetPasswordRequest() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockUserService).changePassword(0L, "oldPassword", "newPassword");

        // Run the test
        final ResponseEntity<?> result = userControllerUnderTest.changePassword("0", "oldPassword", "newPassword");

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testVerifyEmailFb() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockUserService).verifyEmailFb("mail", 0L);

        // Run the test
        final ResponseEntity<?> result = userControllerUnderTest.verifyEmailFb("mail", 0L);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testGetUserClient() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockUserService).getUserClient(0L);

        // Run the test
        final ResponseEntity<?> result = userControllerUnderTest.getUserClient(0L);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @SneakyThrows
    @Test
    public void testUpdateAvatar() {
        // Setup
        File file = new File("Test.pdf");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockUserService).updateAvatar(0l, multipartFile);

        // Run the test
        final ResponseEntity<?> result = userControllerUnderTest.updateAvatar(0L, multipartFile);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }
}
