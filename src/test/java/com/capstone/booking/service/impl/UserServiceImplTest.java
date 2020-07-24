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
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.multipart.MultipartFile;

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
    public void testRegister() {
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
        when(mockUserRepository.findByMail("mail")).thenReturn(user);

        // Configure UserConverter.toUser(...).
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
        when(mockUserConverter.toUser(new UserDTO())).thenReturn(user1);

        // Configure RoleRepository.findByRoleKey(...).
        final Role role2 = new Role();
        role2.setRoleKey("roleKey");
        role2.setRoleName("roleName");
        final Permission permission2 = new Permission();
        permission2.setPermissionKey("permissionKey");
        permission2.setPermissionName("permissionName");
        role2.setPermissions(new HashSet<>(Arrays.asList(permission2)));
        when(mockRoleRepository.findByRoleKey("roleName")).thenReturn(role2);

        // Configure UserRepository.save(...).
        final User user2 = new User();
        user2.setPassword("password");
        user2.setFirstName("firstName");
        user2.setLastName("lastName");
        user2.setMail("mail");
        user2.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user2.setPhoneNumber("phoneNumber");
        user2.setStatus("status");
        user2.setUserType("userType");
        user2.setAvatarLink("avatarLink");
        final Role role3 = new Role();
        role3.setRoleKey("roleKey");
        role3.setRoleName("roleName");
        final Permission permission3 = new Permission();
        permission3.setPermissionKey("permissionKey");
        permission3.setPermissionName("permissionName");
        role3.setPermissions(new HashSet<>(Arrays.asList(permission3)));
        user2.setRoles(new HashSet<>(Arrays.asList(role3)));
        when(mockUserRepository.save(any(User.class))).thenReturn(user2);

        // Configure VerificationTokenRepository.save(...).
        final VerificationToken verificationToken = new VerificationToken();
        verificationToken.setConfirmationToken("confirmationToken");
        final User user3 = new User();
        user3.setPassword("password");
        user3.setFirstName("firstName");
        user3.setLastName("lastName");
        user3.setMail("mail");
        user3.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user3.setPhoneNumber("phoneNumber");
        user3.setStatus("status");
        user3.setUserType("userType");
        user3.setAvatarLink("avatarLink");
        final Role role4 = new Role();
        role4.setRoleKey("roleKey");
        role4.setRoleName("roleName");
        final Permission permission4 = new Permission();
        permission4.setPermissionKey("permissionKey");
        permission4.setPermissionName("permissionName");
        role4.setPermissions(new HashSet<>(Arrays.asList(permission4)));
        user3.setRoles(new HashSet<>(Arrays.asList(role4)));
        verificationToken.setUser(user3);
        verificationToken.setUsed(false);
        when(mockTokenRepository.save(new VerificationToken())).thenReturn(verificationToken);

        // Run the test
        final ResponseEntity<?> result = userServiceImplUnderTest.register(userDTO);

        // Verify the results
        verify(mockEmailSenderService).sendEmail(new SimpleMailMessage());
    }

    @Test
    public void testResendEmailVerify() {
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

        // Configure VerificationTokenRepository.save(...).
        final VerificationToken verificationToken = new VerificationToken();
        verificationToken.setConfirmationToken("confirmationToken");
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
        verificationToken.setUser(user1);
        verificationToken.setUsed(false);
        when(mockTokenRepository.save(new VerificationToken())).thenReturn(verificationToken);

        // Run the test
        final ResponseEntity<?> result = userServiceImplUnderTest.resendEmailVerify("mail");

        // Verify the results
        verify(mockEmailSenderService).sendEmail(new SimpleMailMessage());
    }

    @Test
    public void testVerifyEmailFb() {
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

        // Configure UserRepository.save(...).
        final User user2 = new User();
        user2.setPassword("password");
        user2.setFirstName("firstName");
        user2.setLastName("lastName");
        user2.setMail("mail");
        user2.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user2.setPhoneNumber("phoneNumber");
        user2.setStatus("status");
        user2.setUserType("userType");
        user2.setAvatarLink("avatarLink");
        final Role role1 = new Role();
        role1.setRoleKey("roleKey");
        role1.setRoleName("roleName");
        final Permission permission1 = new Permission();
        permission1.setPermissionKey("permissionKey");
        permission1.setPermissionName("permissionName");
        role1.setPermissions(new HashSet<>(Arrays.asList(permission1)));
        user2.setRoles(new HashSet<>(Arrays.asList(role1)));
        when(mockUserRepository.save(any(User.class))).thenReturn(user2);

        // Configure VerificationTokenRepository.save(...).
        final VerificationToken verificationToken = new VerificationToken();
        verificationToken.setConfirmationToken("confirmationToken");
        final User user3 = new User();
        user3.setPassword("password");
        user3.setFirstName("firstName");
        user3.setLastName("lastName");
        user3.setMail("mail");
        user3.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user3.setPhoneNumber("phoneNumber");
        user3.setStatus("status");
        user3.setUserType("userType");
        user3.setAvatarLink("avatarLink");
        final Role role2 = new Role();
        role2.setRoleKey("roleKey");
        role2.setRoleName("roleName");
        final Permission permission2 = new Permission();
        permission2.setPermissionKey("permissionKey");
        permission2.setPermissionName("permissionName");
        role2.setPermissions(new HashSet<>(Arrays.asList(permission2)));
        user3.setRoles(new HashSet<>(Arrays.asList(role2)));
        verificationToken.setUser(user3);
        verificationToken.setUsed(false);
        when(mockTokenRepository.save(new VerificationToken())).thenReturn(verificationToken);

        // Run the test
        final ResponseEntity<?> result = userServiceImplUnderTest.verifyEmailFb("mail", 0L);

        // Verify the results
        verify(mockEmailSenderService).sendEmail(new SimpleMailMessage());
    }

    @Test
    public void testSendEmailVerify() {
        // Setup
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

        // Configure VerificationTokenRepository.save(...).
        final VerificationToken verificationToken = new VerificationToken();
        verificationToken.setConfirmationToken("confirmationToken");
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
        verificationToken.setUser(user1);
        verificationToken.setUsed(false);
        when(mockTokenRepository.save(new VerificationToken())).thenReturn(verificationToken);

        // Run the test
        userServiceImplUnderTest.sendEmailVerify(user);

        // Verify the results
        verify(mockEmailSenderService).sendEmail(new SimpleMailMessage());
    }

    @Test
    public void testVerifyEmail() {
        // Setup

        // Configure VerificationTokenRepository.findByConfirmationToken(...).
        final VerificationToken verificationToken = new VerificationToken();
        verificationToken.setConfirmationToken("confirmationToken");
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
        verificationToken.setUser(user);
        verificationToken.setUsed(false);
        when(mockTokenRepository.findByConfirmationToken("verificationToken")).thenReturn(verificationToken);

        // Configure UserRepository.findByMail(...).
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
        when(mockUserRepository.findByMail("mail")).thenReturn(user1);

        // Configure UserRepository.save(...).
        final User user2 = new User();
        user2.setPassword("password");
        user2.setFirstName("firstName");
        user2.setLastName("lastName");
        user2.setMail("mail");
        user2.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user2.setPhoneNumber("phoneNumber");
        user2.setStatus("status");
        user2.setUserType("userType");
        user2.setAvatarLink("avatarLink");
        final Role role2 = new Role();
        role2.setRoleKey("roleKey");
        role2.setRoleName("roleName");
        final Permission permission2 = new Permission();
        permission2.setPermissionKey("permissionKey");
        permission2.setPermissionName("permissionName");
        role2.setPermissions(new HashSet<>(Arrays.asList(permission2)));
        user2.setRoles(new HashSet<>(Arrays.asList(role2)));
        when(mockUserRepository.save(any(User.class))).thenReturn(user2);

        // Configure AuthServiceImpl.returnToken(...).
        final Token token = new Token();
        token.setToken("token");
        token.setTokenExpDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        when(mockAuthService.returnToken(any(UserPrincipal.class))).thenReturn(token);

        when(mockAuthService.setPermission(any(User.class))).thenReturn(new UserPrincipal());

        // Run the test
        final ResponseEntity<?> result = userServiceImplUnderTest.verifyEmail("verificationToken");

        // Verify the results
    }

    @Test
    public void testUpdate() {
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

        // Configure UserConverter.toUser(...).
        final User user2 = new User();
        user2.setPassword("password");
        user2.setFirstName("firstName");
        user2.setLastName("lastName");
        user2.setMail("mail");
        user2.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user2.setPhoneNumber("phoneNumber");
        user2.setStatus("status");
        user2.setUserType("userType");
        user2.setAvatarLink("avatarLink");
        final Role role1 = new Role();
        role1.setRoleKey("roleKey");
        role1.setRoleName("roleName");
        final Permission permission1 = new Permission();
        permission1.setPermissionKey("permissionKey");
        permission1.setPermissionName("permissionName");
        role1.setPermissions(new HashSet<>(Arrays.asList(permission1)));
        user2.setRoles(new HashSet<>(Arrays.asList(role1)));
        when(mockUserConverter.toUser(eq(new UserDTO()), any(User.class))).thenReturn(user2);

        // Configure UserRepository.findByMail(...).
        final User user3 = new User();
        user3.setPassword("password");
        user3.setFirstName("firstName");
        user3.setLastName("lastName");
        user3.setMail("mail");
        user3.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user3.setPhoneNumber("phoneNumber");
        user3.setStatus("status");
        user3.setUserType("userType");
        user3.setAvatarLink("avatarLink");
        final Role role2 = new Role();
        role2.setRoleKey("roleKey");
        role2.setRoleName("roleName");
        final Permission permission2 = new Permission();
        permission2.setPermissionKey("permissionKey");
        permission2.setPermissionName("permissionName");
        role2.setPermissions(new HashSet<>(Arrays.asList(permission2)));
        user3.setRoles(new HashSet<>(Arrays.asList(role2)));
        when(mockUserRepository.findByMail("mail")).thenReturn(user3);

        // Configure RoleRepository.findByRoleKey(...).
        final Role role3 = new Role();
        role3.setRoleKey("roleKey");
        role3.setRoleName("roleName");
        final Permission permission3 = new Permission();
        permission3.setPermissionKey("permissionKey");
        permission3.setPermissionName("permissionName");
        role3.setPermissions(new HashSet<>(Arrays.asList(permission3)));
        when(mockRoleRepository.findByRoleKey("roleName")).thenReturn(role3);

        // Configure UserRepository.save(...).
        final User user4 = new User();
        user4.setPassword("password");
        user4.setFirstName("firstName");
        user4.setLastName("lastName");
        user4.setMail("mail");
        user4.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user4.setPhoneNumber("phoneNumber");
        user4.setStatus("status");
        user4.setUserType("userType");
        user4.setAvatarLink("avatarLink");
        final Role role4 = new Role();
        role4.setRoleKey("roleKey");
        role4.setRoleName("roleName");
        final Permission permission4 = new Permission();
        permission4.setPermissionKey("permissionKey");
        permission4.setPermissionName("permissionName");
        role4.setPermissions(new HashSet<>(Arrays.asList(permission4)));
        user4.setRoles(new HashSet<>(Arrays.asList(role4)));
        when(mockUserRepository.save(any(User.class))).thenReturn(user4);

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
        when(mockUserRepository.findByMail("mail")).thenReturn(user);

        // Configure UserConverter.toUser(...).
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
        when(mockUserConverter.toUser(new UserDTO())).thenReturn(user1);

        // Configure RoleRepository.findByRoleKey(...).
        final Role role2 = new Role();
        role2.setRoleKey("roleKey");
        role2.setRoleName("roleName");
        final Permission permission2 = new Permission();
        permission2.setPermissionKey("permissionKey");
        permission2.setPermissionName("permissionName");
        role2.setPermissions(new HashSet<>(Arrays.asList(permission2)));
        when(mockRoleRepository.findByRoleKey("roleName")).thenReturn(role2);

        // Configure UserRepository.save(...).
        final User user2 = new User();
        user2.setPassword("password");
        user2.setFirstName("firstName");
        user2.setLastName("lastName");
        user2.setMail("mail");
        user2.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user2.setPhoneNumber("phoneNumber");
        user2.setStatus("status");
        user2.setUserType("userType");
        user2.setAvatarLink("avatarLink");
        final Role role3 = new Role();
        role3.setRoleKey("roleKey");
        role3.setRoleName("roleName");
        final Permission permission3 = new Permission();
        permission3.setPermissionKey("permissionKey");
        permission3.setPermissionName("permissionName");
        role3.setPermissions(new HashSet<>(Arrays.asList(permission3)));
        user2.setRoles(new HashSet<>(Arrays.asList(role3)));
        when(mockUserRepository.save(any(User.class))).thenReturn(user2);

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
        when(mockUserRepository.findByMultiParam("firstName", "mail", "lastName", "phoneNumber", 0L, 0L, 0L)).thenReturn(output);

        // Run the test
        final ResponseEntity<?> result = userServiceImplUnderTest.findByMultiParam("firstName", "mail", "lastName", "phoneNumber", 0L, 0L, 0L);

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
    public void testValidatePasswordResetToken() {
        // Setup

        // Configure PasswordResetTokenRepository.findByToken(...).
        final PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setId(0);
        passwordResetToken.setToken("token");
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
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        when(mockPasswordTokenRepository.findByToken("token")).thenReturn(passwordResetToken);

        // Run the test
        final ResponseEntity<?> result = userServiceImplUnderTest.validatePasswordResetToken("token");

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
        final ResponseEntity<?> result = userServiceImplUnderTest.changePassword(0L, "oldPassword", "newPassword");

        // Verify the results
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
    }

    @Test
    public void testUpdateAvatar() {
        // Setup
        final MultipartFile file = null;

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
        final User user2 = new User();
        user2.setPassword("password");
        user2.setFirstName("firstName");
        user2.setLastName("lastName");
        user2.setMail("mail");
        user2.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user2.setPhoneNumber("phoneNumber");
        user2.setStatus("status");
        user2.setUserType("userType");
        user2.setAvatarLink("avatarLink");
        final Role role1 = new Role();
        role1.setRoleKey("roleKey");
        role1.setRoleName("roleName");
        final Permission permission1 = new Permission();
        permission1.setPermissionKey("permissionKey");
        permission1.setPermissionName("permissionName");
        role1.setPermissions(new HashSet<>(Arrays.asList(permission1)));
        user2.setRoles(new HashSet<>(Arrays.asList(role1)));
        when(mockUserRepository.save(any(User.class))).thenReturn(user2);

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
        final ResponseEntity<?> result = userServiceImplUnderTest.updateAvatar(0L, file);

        // Verify the results
        verify(mockAmazonS3ClientService).uploadFileToS3Bucket(eq(0L), any(MultipartFile.class), eq("name"), eq("ext"), eq(false));
    }

    @Test
    public void testUploadFile() {
        // Setup
        final MultipartFile file = null;

        // Run the test
        final String result = userServiceImplUnderTest.uploadFile(file, 0L);

        // Verify the results
        assertThat(result).isEqualTo("result");
        verify(mockAmazonS3ClientService).uploadFileToS3Bucket(eq(0L), any(MultipartFile.class), eq("name"), eq("ext"), eq(false));
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
        passwordResetToken.setUser(user1);
        passwordResetToken.setExpiryDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        when(mockPasswordTokenRepository.save(new PasswordResetToken())).thenReturn(passwordResetToken);

        // Run the test
        final ResponseEntity<?> result = userServiceImplUnderTest.createPasswordResetToken("mail");

        // Verify the results
    }
}
