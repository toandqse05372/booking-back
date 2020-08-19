package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.UserConverter;
import com.capstone.booking.config.aws.AmazonS3ClientService;
import com.capstone.booking.config.security.UserPrincipal;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.UserDTO;
import com.capstone.booking.repository.PasswordResetTokenRepository;
import com.capstone.booking.repository.RoleRepository;
import com.capstone.booking.repository.UserRepository;
import com.capstone.booking.repository.VerificationTokenRepository;
import lombok.SneakyThrows;
import org.apache.poi.util.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceImplTest {

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private RoleRepository mockRoleRepository;
    @Mock
    private UserConverter mockUserConverter;
    @Mock
    private VerificationTokenRepository mockTokenRepository;
    @Mock
    private EmailSenderService mockEmailSenderService;
    @Mock
    private AuthServiceImpl mockAuthService;
    @Mock
    private PasswordResetTokenRepository mockPasswordTokenRepository;
    @Mock
    private AmazonS3ClientService mockAmazonS3ClientService;

    @InjectMocks
    private UserServiceImpl userServiceImplUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }



    @Test
    public void testUpdate() {
        // Setup
        final UserDTO userDTO = new UserDTO();
        userDTO.setId(0l);
        userDTO.setPassword("password");
        userDTO.setFirstName("firstName");
        userDTO.setLastName("lastName");
        userDTO.setMail("mail");
        userDTO.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        userDTO.setPhoneNumber("phoneNumber");
        userDTO.setStatus("status");
        userDTO.setRoleKey(new HashSet<>(Arrays.asList("value")));
        userDTO.setUserType("userType");
        userDTO.setAvatarLink("avatarLink");

        // Configure UserRepository.findById(...).
        final User user1 = new User();
        user1.setId(0l);
        user1.setPassword("password");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setMail("mail");
        user1.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user1.setPhoneNumber("phoneNumber");
        user1.setStatus("status");
        user1.setUserType("userType");
        user1.setAvatarLink("avatarLink");
        final Role role = new Role();
        role.setRoleKey("roleKey");
        role.setRoleName("roleName");
        final Permission permission = new Permission();
        permission.setPermissionKey("permissionKey");
        permission.setPermissionName("permissionName");
        role.setPermissions(new HashSet<>(Arrays.asList(permission)));
        user1.setRoles(new HashSet<>(Arrays.asList(role)));
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findById(0L)).thenReturn(user);

        // Configure UserConverter.toUser(...).
        when(mockUserConverter.toUser(userDTO, user1)).thenReturn(user1);

        // Configure UserRepository.findByMail(...).
        when(mockUserRepository.findByMail("mail")).thenReturn(user1);

        // Configure RoleRepository.findByRoleKey(...).
        when(mockRoleRepository.findByRoleKey("roleName")).thenReturn(role);

        // Configure UserRepository.save(...).
        when(mockUserRepository.save(user1)).thenReturn(user1);

        // Run the test
        final ResponseEntity<?> result = userServiceImplUnderTest.update(userDTO);

        // Verify the results
    }

    @Test
    public void testCreateUserCMS() {
        // Setup
        final UserDTO userDTO = new UserDTO();
        userDTO.setPassword("password");
        userDTO.setFirstName("firstName");
        userDTO.setLastName("lastName");
        userDTO.setMail("mail");
        userDTO.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        userDTO.setPhoneNumber("phoneNumber");
        userDTO.setStatus("status");
        userDTO.setRoleKey(new HashSet<>(Arrays.asList("value")));
        userDTO.setUserType("userType");
        userDTO.setAvatarLink("avatarLink");

        // Configure UserRepository.findByMail(...).
        final User user = new User();
        user.setPassword("password");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setMail("mail");
        user.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user.setPhoneNumber("phoneNumber");
        user.setStatus("status");
        user.setUserType("userType");
        user.setAvatarLink("avatarLink");
        final Role role = new Role();
        role.setRoleKey("roleKey");
        role.setRoleName("roleName");
        final Permission permission = new Permission();
        permission.setPermissionKey("permissionKey");
        permission.setPermissionName("permissionName");
        role.setPermissions(new HashSet<>(Arrays.asList(permission)));
        user.setRoles(new HashSet<>(Arrays.asList(role)));
        when(mockUserRepository.findByMail("mail")).thenReturn(null);

        // Configure UserConverter.toUser(...).
        when(mockUserConverter.toUser(userDTO)).thenReturn(user);

        // Configure RoleRepository.findByRoleKey(...).
        when(mockRoleRepository.findByRoleKey("roleName")).thenReturn(role);

        // Configure UserRepository.save(...).
        when(mockUserRepository.save(user)).thenReturn(user);

        // Run the test
        final ResponseEntity<?> result = userServiceImplUnderTest.createUserCMS(userDTO);

        // Verify the results
    }

    @Test
    public void testFindByMultiParam() {
        // Setup

        // Configure UserRepository.findByMultiParam(...).
        final Output output = new Output();
        output.setPage(0);
        output.setTotalPage(0);
        output.setListResult(Arrays.asList());
        output.setTotalItems(0);
        when(mockUserRepository.findByMultiParam("firstName", "mail", "lastName", "0123456789", 0L, 0L, 0L)).thenReturn(output);

        // Run the test
        final ResponseEntity<?> result = userServiceImplUnderTest.findByMultiParam("firstName", "mail", "lastName", "0123456789", 0L, 0L, 0L);

        // Verify the results
    }

    @Test
    public void testDelete() {
        // Setup

        // Configure UserRepository.findById(...).
        final User user1 = new User();
        user1.setPassword("password");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setMail("mail");
        user1.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user1.setPhoneNumber("phoneNumber");
        user1.setStatus("status");
        user1.setUserType("userType");
        user1.setAvatarLink("avatarLink");
        final Role role = new Role();
        role.setRoleKey("roleKey");
        role.setRoleName("roleName");
        final Permission permission = new Permission();
        permission.setPermissionKey("permissionKey");
        permission.setPermissionName("permissionName");
        role.setPermissions(new HashSet<>(Arrays.asList(permission)));
        user1.setRoles(new HashSet<>(Arrays.asList(role)));
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findById(0L)).thenReturn(user);

        // Run the test
        final ResponseEntity<?> result = userServiceImplUnderTest.delete(0L);

        // Verify the results
        verify(mockUserRepository).deleteById(0L);
    }

    @Test
    public void testGetUser() {
        // Setup

        // Configure UserRepository.findById(...).
        final User user1 = new User();
        user1.setPassword("password");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setMail("mail");
        user1.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user1.setPhoneNumber("phoneNumber");
        user1.setStatus("status");
        user1.setUserType("userType");
        user1.setAvatarLink("avatarLink");
        final Role role = new Role();
        role.setRoleKey("roleKey");
        role.setRoleName("roleName");
        final Permission permission = new Permission();
        permission.setPermissionKey("permissionKey");
        permission.setPermissionName("permissionName");
        role.setPermissions(new HashSet<>(Arrays.asList(permission)));
        user1.setRoles(new HashSet<>(Arrays.asList(role)));
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findById(0L)).thenReturn(user);

        // Configure UserConverter.toDTO(...).
        final UserDTO userDTO = new UserDTO();
        userDTO.setPassword("password");
        userDTO.setFirstName("firstName");
        userDTO.setLastName("lastName");
        userDTO.setMail("mail");
        userDTO.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        userDTO.setPhoneNumber("phoneNumber");
        userDTO.setStatus("status");
        userDTO.setRoleKey(new HashSet<>(Arrays.asList("value")));
        userDTO.setUserType("userType");
        userDTO.setAvatarLink("avatarLink");
        when(mockUserConverter.toDTO(any(User.class))).thenReturn(userDTO);

        // Run the test
        final ResponseEntity<?> result = userServiceImplUnderTest.getUser(0L);

        // Verify the results
    }

    @Test
    public void testFindAllRoles() {
        // Setup

        // Configure RoleRepository.findAll(...).
        final Role role = new Role();
        role.setRoleKey("roleKey");
        role.setRoleName("roleName");
        final Permission permission = new Permission();
        permission.setPermissionKey("permissionKey");
        permission.setPermissionName("permissionName");
        role.setPermissions(new HashSet<>(Arrays.asList(permission)));
        final List<Role> roles = Arrays.asList(role);
        when(mockRoleRepository.findAll()).thenReturn(roles);

        // Run the test
        final ResponseEntity<?> result = userServiceImplUnderTest.findAllRoles();

        // Verify the results
    }

    @Test
    public void testChangePasswordAfterReset() {
        // Setup

        // Configure UserRepository.findById(...).
        final User user1 = new User();
        user1.setPassword("password");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setMail("mail");
        user1.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user1.setPhoneNumber("phoneNumber");
        user1.setStatus("status");
        user1.setUserType("userType");
        user1.setAvatarLink("avatarLink");
        final Role role = new Role();
        role.setRoleKey("roleKey");
        role.setRoleName("roleName");
        final Permission permission = new Permission();
        permission.setPermissionKey("permissionKey");
        permission.setPermissionName("permissionName");
        role.setPermissions(new HashSet<>(Arrays.asList(permission)));
        user1.setRoles(new HashSet<>(Arrays.asList(role)));
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findById(0L)).thenReturn(user);

        // Run the test
        final ResponseEntity<?> result = userServiceImplUnderTest.changePasswordAfterReset(0L, "newPassword");

        // Verify the results
    }

    @Test
    public void testChangePassword() {
        // Setup
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Configure UserRepository.findById(...).
        final User user1 = new User();
        user1.setPassword(encoder.encode("password"));
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setMail("mail");
        user1.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user1.setPhoneNumber("phoneNumber");
        user1.setStatus("status");
        user1.setUserType("userType");
        user1.setAvatarLink("avatarLink");
        final Role role = new Role();
        role.setRoleKey("roleKey");
        role.setRoleName("roleName");
        final Permission permission = new Permission();
        permission.setPermissionKey("permissionKey");
        permission.setPermissionName("permissionName");
        role.setPermissions(new HashSet<>(Arrays.asList(permission)));
        user1.setRoles(new HashSet<>(Arrays.asList(role)));
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findById(0L)).thenReturn(user);

        // Run the test
        final ResponseEntity<?> result = userServiceImplUnderTest.changePassword(0L, "password", "newPassword");

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void testGetUserClient() {
        // Setup

        // Configure UserRepository.findById(...).
        final User user1 = new User();
        user1.setPassword("password");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setMail("mail");
        user1.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user1.setPhoneNumber("phoneNumber");
        user1.setStatus("status");
        user1.setUserType("userType");
        user1.setAvatarLink("avatarLink");
        final Role role = new Role();
        role.setRoleKey("roleKey");
        role.setRoleName("roleName");
        final Permission permission = new Permission();
        permission.setPermissionKey("permissionKey");
        permission.setPermissionName("permissionName");
        role.setPermissions(new HashSet<>(Arrays.asList(permission)));
        user1.setRoles(new HashSet<>(Arrays.asList(role)));
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findById(0L)).thenReturn(user);

        // Configure UserConverter.toDTOClient(...).
        final UserDTO userDTO = new UserDTO();
        userDTO.setPassword("password");
        userDTO.setFirstName("firstName");
        userDTO.setLastName("lastName");
        userDTO.setMail("mail");
        userDTO.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        userDTO.setPhoneNumber("phoneNumber");
        userDTO.setStatus("status");
        userDTO.setRoleKey(new HashSet<>(Arrays.asList("value")));
        userDTO.setUserType("userType");
        userDTO.setAvatarLink("avatarLink");
        when(mockUserConverter.toDTOClient(any(User.class))).thenReturn(userDTO);

        // Run the test
        final ResponseEntity<?> result = userServiceImplUnderTest.getUserClient(0L);

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }

    @SneakyThrows
    @Test
    public void testUpdateAvatar() {
        // Setup
        File file = new File("Test.pdf");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));

        // Configure UserRepository.findById(...).
        final User user1 = new User();
        user1.setPassword("password");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setMail("mail");
        user1.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user1.setPhoneNumber("phoneNumber");
        user1.setStatus("status");
        user1.setUserType("userType");
        user1.setAvatarLink("avatarLink");
        final Role role = new Role();
        role.setRoleKey("roleKey");
        role.setRoleName("roleName");
        final Permission permission = new Permission();
        permission.setPermissionKey("permissionKey");
        permission.setPermissionName("permissionName");
        role.setPermissions(new HashSet<>(Arrays.asList(permission)));
        user1.setRoles(new HashSet<>(Arrays.asList(role)));
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findById(0L)).thenReturn(user);
        // Configure UserRepository.save(...).
        when(mockUserRepository.save(user1)).thenReturn(user1);

        // Configure UserConverter.toDTO(...).
        final UserDTO userDTO = new UserDTO();
        userDTO.setId(0l);
        userDTO.setPassword("password");
        userDTO.setFirstName("firstName");
        userDTO.setLastName("lastName");
        userDTO.setMail("mail");
        userDTO.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        userDTO.setPhoneNumber("phoneNumber");
        userDTO.setStatus("status");
        userDTO.setRoleKey(new HashSet<>(Arrays.asList("value")));
        userDTO.setUserType("userType");
        userDTO.setAvatarLink("avatarLink");
        when(mockUserConverter.toDTO(user1)).thenReturn(userDTO);

        // Run the test
        final ResponseEntity<?> result = userServiceImplUnderTest.updateAvatar(0L, multipartFile);

        // Verify the results
        verify(mockAmazonS3ClientService).uploadFileToS3Bucket(eq(0L), eq(multipartFile), eq("User_0"), eq(".pdf"), eq(true));
    }

    @SneakyThrows
    @Test
    public void testUploadFile() {
        // Setup
        File file = new File("Test.pdf");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));

        // Run the test
        final String result = userServiceImplUnderTest.uploadFile(multipartFile, new User());

        // Verify the results
        assertThat(result).isEqualTo("nullUser_0.pdf");
        verify(mockAmazonS3ClientService).uploadFileToS3Bucket(eq(0L), eq(multipartFile), eq("User_0"), eq(".pdf"), eq(true));
    }

    @Test
    public void testCreatePasswordResetToken() {
        // Setup

        // Configure UserRepository.findByMail(...).
        final User user = new User();
        user.setPassword("password");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setMail("mail");
        user.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user.setPhoneNumber("phoneNumber");
        user.setStatus("status");
        user.setUserType("userType");
        user.setAvatarLink("avatarLink");
        final Role role = new Role();
        role.setRoleKey("roleKey");
        role.setRoleName("roleName");
        final Permission permission = new Permission();
        permission.setPermissionKey("permissionKey");
        permission.setPermissionName("permissionName");
        role.setPermissions(new HashSet<>(Arrays.asList(permission)));
        user.setRoles(new HashSet<>(Arrays.asList(role)));
        when(mockUserRepository.findByMail("mail")).thenReturn(user);

        // Configure PasswordResetTokenRepository.save(...).
        final PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setId(0);
        passwordResetToken.setToken("token");
        final User user1 = new User();
        user1.setPassword("password");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setMail("mail");
        user1.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user1.setPhoneNumber("phoneNumber");
        user1.setStatus("status");
        user1.setUserType("userType");
        user1.setAvatarLink("avatarLink");
        final Role role1 = new Role();
        role1.setRoleKey("roleKey");
        role1.setRoleName("roleName");
        final Permission permission1 = new Permission();
        permission1.setPermissionKey("permissionKey");
        permission1.setPermissionName("permissionName");
        role1.setPermissions(new HashSet<>(Arrays.asList(permission1)));
        user1.setRoles(new HashSet<>(Arrays.asList(role1)));
        passwordResetToken.setUid(0l);
        passwordResetToken.setExpiryDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        when(mockPasswordTokenRepository.save(new PasswordResetToken())).thenReturn(passwordResetToken);

        // Run the test
        final ResponseEntity<?> result = userServiceImplUnderTest.createPasswordResetToken("mail");

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void testUpdateClient() {
        // Setup
        final UserDTO userDTO = new UserDTO();
        userDTO.setPassword("password");
        userDTO.setFirstName("firstName");
        userDTO.setLastName("lastName");
        userDTO.setMail("mail");
        userDTO.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        userDTO.setPhoneNumber("phoneNumber");
        userDTO.setStatus("status");
        userDTO.setRoleKey(new HashSet<>(Arrays.asList("value")));
        userDTO.setUserType("userType");
        userDTO.setAvatarLink("avatarLink");
        userDTO.setId(0l);

        // Configure UserRepository.findById(...).
        final User user = new User();
        user.setId(0l);
        user.setPassword("password");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setMail("mail");
        user.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user.setPhoneNumber("phoneNumber");
        user.setStatus("status");
        user.setUserType("userType");
        user.setAvatarLink("avatarLink");
        final Optional<User> optionalUser = Optional.of(user);
        when(mockUserRepository.findById(0L)).thenReturn(optionalUser);

        // Configure UserConverter.toUserFromClient(...).
        when(mockUserConverter.toUserFromClient(userDTO, user)).thenReturn(user);

        // Configure UserRepository.save(...).
        when(mockUserRepository.save(user)).thenReturn(user);

        // Configure UserConverter.toDTOClient(...).
        when(mockUserConverter.toDTOClient(user)).thenReturn(userDTO);

        // Run the test
        final ResponseEntity<?> result = userServiceImplUnderTest.updateClient(userDTO);

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }
}
