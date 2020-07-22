package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.UserConverter;
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

import java.util.*;

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
        final Role role = new Role();
        role.setRoleKey("roleKey");
        role.setRoleName("roleName");
        final Permission permission = new Permission();
        permission.setPermissionKey("permissionKey");
        permission.setPermissionName("permissionName");
        role.setPermissions(new HashSet<>(Arrays.asList(permission)));
        user.setRoles(new HashSet<>(Arrays.asList(role)));
        final Order order = new Order();
        order.setTicketTypeId(0L);
        order.setFirstName("firstName");
        order.setLastName("lastName");
        order.setMail("mail");
        order.setPhoneNumber("phoneNumber");
        order.setStatus("status");
        order.setOrderCode("orderCode");
        order.setTotalPayment(0);
        order.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user.setOrder(new HashSet<>(Arrays.asList(order)));
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
        final Role role1 = new Role();
        role1.setRoleKey("roleKey");
        role1.setRoleName("roleName");
        final Permission permission1 = new Permission();
        permission1.setPermissionKey("permissionKey");
        permission1.setPermissionName("permissionName");
        role1.setPermissions(new HashSet<>(Arrays.asList(permission1)));
        user1.setRoles(new HashSet<>(Arrays.asList(role1)));
        final Order order1 = new Order();
        order1.setTicketTypeId(0L);
        order1.setFirstName("firstName");
        order1.setLastName("lastName");
        order1.setMail("mail");
        order1.setPhoneNumber("phoneNumber");
        order1.setStatus("status");
        order1.setOrderCode("orderCode");
        order1.setTotalPayment(0);
        order1.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order1.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user1.setOrder(new HashSet<>(Arrays.asList(order1)));
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
        final Role role3 = new Role();
        role3.setRoleKey("roleKey");
        role3.setRoleName("roleName");
        final Permission permission3 = new Permission();
        permission3.setPermissionKey("permissionKey");
        permission3.setPermissionName("permissionName");
        role3.setPermissions(new HashSet<>(Arrays.asList(permission3)));
        user2.setRoles(new HashSet<>(Arrays.asList(role3)));
        final Order order2 = new Order();
        order2.setTicketTypeId(0L);
        order2.setFirstName("firstName");
        order2.setLastName("lastName");
        order2.setMail("mail");
        order2.setPhoneNumber("phoneNumber");
        order2.setStatus("status");
        order2.setOrderCode("orderCode");
        order2.setTotalPayment(0);
        order2.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order2.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user2.setOrder(new HashSet<>(Arrays.asList(order2)));
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
        final Role role4 = new Role();
        role4.setRoleKey("roleKey");
        role4.setRoleName("roleName");
        final Permission permission4 = new Permission();
        permission4.setPermissionKey("permissionKey");
        permission4.setPermissionName("permissionName");
        role4.setPermissions(new HashSet<>(Arrays.asList(permission4)));
        user3.setRoles(new HashSet<>(Arrays.asList(role4)));
        final Order order3 = new Order();
        order3.setTicketTypeId(0L);
        order3.setFirstName("firstName");
        order3.setLastName("lastName");
        order3.setMail("mail");
        order3.setPhoneNumber("phoneNumber");
        order3.setStatus("status");
        order3.setOrderCode("orderCode");
        order3.setTotalPayment(0);
        order3.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order3.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user3.setOrder(new HashSet<>(Arrays.asList(order3)));
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
        final Role role = new Role();
        role.setRoleKey("roleKey");
        role.setRoleName("roleName");
        final Permission permission = new Permission();
        permission.setPermissionKey("permissionKey");
        permission.setPermissionName("permissionName");
        role.setPermissions(new HashSet<>(Arrays.asList(permission)));
        user.setRoles(new HashSet<>(Arrays.asList(role)));
        final Order order = new Order();
        order.setTicketTypeId(0L);
        order.setFirstName("firstName");
        order.setLastName("lastName");
        order.setMail("mail");
        order.setPhoneNumber("phoneNumber");
        order.setStatus("status");
        order.setOrderCode("orderCode");
        order.setTotalPayment(0);
        order.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user.setOrder(new HashSet<>(Arrays.asList(order)));
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
        final Role role1 = new Role();
        role1.setRoleKey("roleKey");
        role1.setRoleName("roleName");
        final Permission permission1 = new Permission();
        permission1.setPermissionKey("permissionKey");
        permission1.setPermissionName("permissionName");
        role1.setPermissions(new HashSet<>(Arrays.asList(permission1)));
        user1.setRoles(new HashSet<>(Arrays.asList(role1)));
        final Order order1 = new Order();
        order1.setTicketTypeId(0L);
        order1.setFirstName("firstName");
        order1.setLastName("lastName");
        order1.setMail("mail");
        order1.setPhoneNumber("phoneNumber");
        order1.setStatus("status");
        order1.setOrderCode("orderCode");
        order1.setTotalPayment(0);
        order1.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order1.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user1.setOrder(new HashSet<>(Arrays.asList(order1)));
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
        final Role role = new Role();
        role.setRoleKey("roleKey");
        role.setRoleName("roleName");
        final Permission permission = new Permission();
        permission.setPermissionKey("permissionKey");
        permission.setPermissionName("permissionName");
        role.setPermissions(new HashSet<>(Arrays.asList(permission)));
        user1.setRoles(new HashSet<>(Arrays.asList(role)));
        final Order order = new Order();
        order.setTicketTypeId(0L);
        order.setFirstName("firstName");
        order.setLastName("lastName");
        order.setMail("mail");
        order.setPhoneNumber("phoneNumber");
        order.setStatus("status");
        order.setOrderCode("orderCode");
        order.setTotalPayment(0);
        order.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user1.setOrder(new HashSet<>(Arrays.asList(order)));
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
        final Role role1 = new Role();
        role1.setRoleKey("roleKey");
        role1.setRoleName("roleName");
        final Permission permission1 = new Permission();
        permission1.setPermissionKey("permissionKey");
        permission1.setPermissionName("permissionName");
        role1.setPermissions(new HashSet<>(Arrays.asList(permission1)));
        user2.setRoles(new HashSet<>(Arrays.asList(role1)));
        final Order order1 = new Order();
        order1.setTicketTypeId(0L);
        order1.setFirstName("firstName");
        order1.setLastName("lastName");
        order1.setMail("mail");
        order1.setPhoneNumber("phoneNumber");
        order1.setStatus("status");
        order1.setOrderCode("orderCode");
        order1.setTotalPayment(0);
        order1.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order1.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user2.setOrder(new HashSet<>(Arrays.asList(order1)));
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
        final Role role2 = new Role();
        role2.setRoleKey("roleKey");
        role2.setRoleName("roleName");
        final Permission permission2 = new Permission();
        permission2.setPermissionKey("permissionKey");
        permission2.setPermissionName("permissionName");
        role2.setPermissions(new HashSet<>(Arrays.asList(permission2)));
        user3.setRoles(new HashSet<>(Arrays.asList(role2)));
        final Order order2 = new Order();
        order2.setTicketTypeId(0L);
        order2.setFirstName("firstName");
        order2.setLastName("lastName");
        order2.setMail("mail");
        order2.setPhoneNumber("phoneNumber");
        order2.setStatus("status");
        order2.setOrderCode("orderCode");
        order2.setTotalPayment(0);
        order2.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order2.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user3.setOrder(new HashSet<>(Arrays.asList(order2)));
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
        final Role role = new Role();
        role.setRoleKey("roleKey");
        role.setRoleName("roleName");
        final Permission permission = new Permission();
        permission.setPermissionKey("permissionKey");
        permission.setPermissionName("permissionName");
        role.setPermissions(new HashSet<>(Arrays.asList(permission)));
        user.setRoles(new HashSet<>(Arrays.asList(role)));
        final Order order = new Order();
        order.setTicketTypeId(0L);
        order.setFirstName("firstName");
        order.setLastName("lastName");
        order.setMail("mail");
        order.setPhoneNumber("phoneNumber");
        order.setStatus("status");
        order.setOrderCode("orderCode");
        order.setTotalPayment(0);
        order.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user.setOrder(new HashSet<>(Arrays.asList(order)));

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
        final Role role1 = new Role();
        role1.setRoleKey("roleKey");
        role1.setRoleName("roleName");
        final Permission permission1 = new Permission();
        permission1.setPermissionKey("permissionKey");
        permission1.setPermissionName("permissionName");
        role1.setPermissions(new HashSet<>(Arrays.asList(permission1)));
        user1.setRoles(new HashSet<>(Arrays.asList(role1)));
        final Order order1 = new Order();
        order1.setTicketTypeId(0L);
        order1.setFirstName("firstName");
        order1.setLastName("lastName");
        order1.setMail("mail");
        order1.setPhoneNumber("phoneNumber");
        order1.setStatus("status");
        order1.setOrderCode("orderCode");
        order1.setTotalPayment(0);
        order1.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order1.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user1.setOrder(new HashSet<>(Arrays.asList(order1)));
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
        final Role role = new Role();
        role.setRoleKey("roleKey");
        role.setRoleName("roleName");
        final Permission permission = new Permission();
        permission.setPermissionKey("permissionKey");
        permission.setPermissionName("permissionName");
        role.setPermissions(new HashSet<>(Arrays.asList(permission)));
        user.setRoles(new HashSet<>(Arrays.asList(role)));
        final Order order = new Order();
        order.setTicketTypeId(0L);
        order.setFirstName("firstName");
        order.setLastName("lastName");
        order.setMail("mail");
        order.setPhoneNumber("phoneNumber");
        order.setStatus("status");
        order.setOrderCode("orderCode");
        order.setTotalPayment(0);
        order.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user.setOrder(new HashSet<>(Arrays.asList(order)));
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
        final Role role1 = new Role();
        role1.setRoleKey("roleKey");
        role1.setRoleName("roleName");
        final Permission permission1 = new Permission();
        permission1.setPermissionKey("permissionKey");
        permission1.setPermissionName("permissionName");
        role1.setPermissions(new HashSet<>(Arrays.asList(permission1)));
        user1.setRoles(new HashSet<>(Arrays.asList(role1)));
        final Order order1 = new Order();
        order1.setTicketTypeId(0L);
        order1.setFirstName("firstName");
        order1.setLastName("lastName");
        order1.setMail("mail");
        order1.setPhoneNumber("phoneNumber");
        order1.setStatus("status");
        order1.setOrderCode("orderCode");
        order1.setTotalPayment(0);
        order1.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order1.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user1.setOrder(new HashSet<>(Arrays.asList(order1)));
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
        final Role role2 = new Role();
        role2.setRoleKey("roleKey");
        role2.setRoleName("roleName");
        final Permission permission2 = new Permission();
        permission2.setPermissionKey("permissionKey");
        permission2.setPermissionName("permissionName");
        role2.setPermissions(new HashSet<>(Arrays.asList(permission2)));
        user2.setRoles(new HashSet<>(Arrays.asList(role2)));
        final Order order2 = new Order();
        order2.setTicketTypeId(0L);
        order2.setFirstName("firstName");
        order2.setLastName("lastName");
        order2.setMail("mail");
        order2.setPhoneNumber("phoneNumber");
        order2.setStatus("status");
        order2.setOrderCode("orderCode");
        order2.setTotalPayment(0);
        order2.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order2.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user2.setOrder(new HashSet<>(Arrays.asList(order2)));
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
        final Role role = new Role();
        role.setRoleKey("roleKey");
        role.setRoleName("roleName");
        final Permission permission = new Permission();
        permission.setPermissionKey("permissionKey");
        permission.setPermissionName("permissionName");
        role.setPermissions(new HashSet<>(Arrays.asList(permission)));
        user1.setRoles(new HashSet<>(Arrays.asList(role)));
        final Order order = new Order();
        order.setTicketTypeId(0L);
        order.setFirstName("firstName");
        order.setLastName("lastName");
        order.setMail("mail");
        order.setPhoneNumber("phoneNumber");
        order.setStatus("status");
        order.setOrderCode("orderCode");
        order.setTotalPayment(0);
        order.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user1.setOrder(new HashSet<>(Arrays.asList(order)));
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
        final Role role1 = new Role();
        role1.setRoleKey("roleKey");
        role1.setRoleName("roleName");
        final Permission permission1 = new Permission();
        permission1.setPermissionKey("permissionKey");
        permission1.setPermissionName("permissionName");
        role1.setPermissions(new HashSet<>(Arrays.asList(permission1)));
        user2.setRoles(new HashSet<>(Arrays.asList(role1)));
        final Order order1 = new Order();
        order1.setTicketTypeId(0L);
        order1.setFirstName("firstName");
        order1.setLastName("lastName");
        order1.setMail("mail");
        order1.setPhoneNumber("phoneNumber");
        order1.setStatus("status");
        order1.setOrderCode("orderCode");
        order1.setTotalPayment(0);
        order1.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order1.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user2.setOrder(new HashSet<>(Arrays.asList(order1)));
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
        final Role role2 = new Role();
        role2.setRoleKey("roleKey");
        role2.setRoleName("roleName");
        final Permission permission2 = new Permission();
        permission2.setPermissionKey("permissionKey");
        permission2.setPermissionName("permissionName");
        role2.setPermissions(new HashSet<>(Arrays.asList(permission2)));
        user3.setRoles(new HashSet<>(Arrays.asList(role2)));
        final Order order2 = new Order();
        order2.setTicketTypeId(0L);
        order2.setFirstName("firstName");
        order2.setLastName("lastName");
        order2.setMail("mail");
        order2.setPhoneNumber("phoneNumber");
        order2.setStatus("status");
        order2.setOrderCode("orderCode");
        order2.setTotalPayment(0);
        order2.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order2.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user3.setOrder(new HashSet<>(Arrays.asList(order2)));
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
        final Role role4 = new Role();
        role4.setRoleKey("roleKey");
        role4.setRoleName("roleName");
        final Permission permission4 = new Permission();
        permission4.setPermissionKey("permissionKey");
        permission4.setPermissionName("permissionName");
        role4.setPermissions(new HashSet<>(Arrays.asList(permission4)));
        user4.setRoles(new HashSet<>(Arrays.asList(role4)));
        final Order order3 = new Order();
        order3.setTicketTypeId(0L);
        order3.setFirstName("firstName");
        order3.setLastName("lastName");
        order3.setMail("mail");
        order3.setPhoneNumber("phoneNumber");
        order3.setStatus("status");
        order3.setOrderCode("orderCode");
        order3.setTotalPayment(0);
        order3.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order3.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user4.setOrder(new HashSet<>(Arrays.asList(order3)));
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
        final Role role = new Role();
        role.setRoleKey("roleKey");
        role.setRoleName("roleName");
        final Permission permission = new Permission();
        permission.setPermissionKey("permissionKey");
        permission.setPermissionName("permissionName");
        role.setPermissions(new HashSet<>(Arrays.asList(permission)));
        user.setRoles(new HashSet<>(Arrays.asList(role)));
        final Order order = new Order();
        order.setTicketTypeId(0L);
        order.setFirstName("firstName");
        order.setLastName("lastName");
        order.setMail("mail");
        order.setPhoneNumber("phoneNumber");
        order.setStatus("status");
        order.setOrderCode("orderCode");
        order.setTotalPayment(0);
        order.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user.setOrder(new HashSet<>(Arrays.asList(order)));
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
        final Role role1 = new Role();
        role1.setRoleKey("roleKey");
        role1.setRoleName("roleName");
        final Permission permission1 = new Permission();
        permission1.setPermissionKey("permissionKey");
        permission1.setPermissionName("permissionName");
        role1.setPermissions(new HashSet<>(Arrays.asList(permission1)));
        user1.setRoles(new HashSet<>(Arrays.asList(role1)));
        final Order order1 = new Order();
        order1.setTicketTypeId(0L);
        order1.setFirstName("firstName");
        order1.setLastName("lastName");
        order1.setMail("mail");
        order1.setPhoneNumber("phoneNumber");
        order1.setStatus("status");
        order1.setOrderCode("orderCode");
        order1.setTotalPayment(0);
        order1.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order1.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user1.setOrder(new HashSet<>(Arrays.asList(order1)));
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
        final Role role3 = new Role();
        role3.setRoleKey("roleKey");
        role3.setRoleName("roleName");
        final Permission permission3 = new Permission();
        permission3.setPermissionKey("permissionKey");
        permission3.setPermissionName("permissionName");
        role3.setPermissions(new HashSet<>(Arrays.asList(permission3)));
        user2.setRoles(new HashSet<>(Arrays.asList(role3)));
        final Order order2 = new Order();
        order2.setTicketTypeId(0L);
        order2.setFirstName("firstName");
        order2.setLastName("lastName");
        order2.setMail("mail");
        order2.setPhoneNumber("phoneNumber");
        order2.setStatus("status");
        order2.setOrderCode("orderCode");
        order2.setTotalPayment(0);
        order2.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order2.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user2.setOrder(new HashSet<>(Arrays.asList(order2)));
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
        final Role role = new Role();
        role.setRoleKey("roleKey");
        role.setRoleName("roleName");
        final Permission permission = new Permission();
        permission.setPermissionKey("permissionKey");
        permission.setPermissionName("permissionName");
        role.setPermissions(new HashSet<>(Arrays.asList(permission)));
        user1.setRoles(new HashSet<>(Arrays.asList(role)));
        final Order order = new Order();
        order.setTicketTypeId(0L);
        order.setFirstName("firstName");
        order.setLastName("lastName");
        order.setMail("mail");
        order.setPhoneNumber("phoneNumber");
        order.setStatus("status");
        order.setOrderCode("orderCode");
        order.setTotalPayment(0);
        order.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user1.setOrder(new HashSet<>(Arrays.asList(order)));
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
        final Role role = new Role();
        role.setRoleKey("roleKey");
        role.setRoleName("roleName");
        final Permission permission = new Permission();
        permission.setPermissionKey("permissionKey");
        permission.setPermissionName("permissionName");
        role.setPermissions(new HashSet<>(Arrays.asList(permission)));
        user1.setRoles(new HashSet<>(Arrays.asList(role)));
        final Order order = new Order();
        order.setTicketTypeId(0L);
        order.setFirstName("firstName");
        order.setLastName("lastName");
        order.setMail("mail");
        order.setPhoneNumber("phoneNumber");
        order.setStatus("status");
        order.setOrderCode("orderCode");
        order.setTotalPayment(0);
        order.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user1.setOrder(new HashSet<>(Arrays.asList(order)));
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
        final Role role = new Role();
        role.setRoleKey("roleKey");
        role.setRoleName("roleName");
        final Permission permission = new Permission();
        permission.setPermissionKey("permissionKey");
        permission.setPermissionName("permissionName");
        role.setPermissions(new HashSet<>(Arrays.asList(permission)));
        user.setRoles(new HashSet<>(Arrays.asList(role)));
        final Order order = new Order();
        order.setTicketTypeId(0L);
        order.setFirstName("firstName");
        order.setLastName("lastName");
        order.setMail("mail");
        order.setPhoneNumber("phoneNumber");
        order.setStatus("status");
        order.setOrderCode("orderCode");
        order.setTotalPayment(0);
        order.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user.setOrder(new HashSet<>(Arrays.asList(order)));
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
        final Role role = new Role();
        role.setRoleKey("roleKey");
        role.setRoleName("roleName");
        final Permission permission = new Permission();
        permission.setPermissionKey("permissionKey");
        permission.setPermissionName("permissionName");
        role.setPermissions(new HashSet<>(Arrays.asList(permission)));
        user1.setRoles(new HashSet<>(Arrays.asList(role)));
        final Order order = new Order();
        order.setTicketTypeId(0L);
        order.setFirstName("firstName");
        order.setLastName("lastName");
        order.setMail("mail");
        order.setPhoneNumber("phoneNumber");
        order.setStatus("status");
        order.setOrderCode("orderCode");
        order.setTotalPayment(0);
        order.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user1.setOrder(new HashSet<>(Arrays.asList(order)));
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
        final Role role = new Role();
        role.setRoleKey("roleKey");
        role.setRoleName("roleName");
        final Permission permission = new Permission();
        permission.setPermissionKey("permissionKey");
        permission.setPermissionName("permissionName");
        role.setPermissions(new HashSet<>(Arrays.asList(permission)));
        user1.setRoles(new HashSet<>(Arrays.asList(role)));
        final Order order = new Order();
        order.setTicketTypeId(0L);
        order.setFirstName("firstName");
        order.setLastName("lastName");
        order.setMail("mail");
        order.setPhoneNumber("phoneNumber");
        order.setStatus("status");
        order.setOrderCode("orderCode");
        order.setTotalPayment(0);
        order.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user1.setOrder(new HashSet<>(Arrays.asList(order)));
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findById(0L)).thenReturn(user);

        // Run the test
        final ResponseEntity<?> result = userServiceImplUnderTest.changePassword(0L, "oldPassword", "newPassword");

        // Verify the results
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
        final Role role = new Role();
        role.setRoleKey("roleKey");
        role.setRoleName("roleName");
        final Permission permission = new Permission();
        permission.setPermissionKey("permissionKey");
        permission.setPermissionName("permissionName");
        role.setPermissions(new HashSet<>(Arrays.asList(permission)));
        user.setRoles(new HashSet<>(Arrays.asList(role)));
        final Order order = new Order();
        order.setTicketTypeId(0L);
        order.setFirstName("firstName");
        order.setLastName("lastName");
        order.setMail("mail");
        order.setPhoneNumber("phoneNumber");
        order.setStatus("status");
        order.setOrderCode("orderCode");
        order.setTotalPayment(0);
        order.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user.setOrder(new HashSet<>(Arrays.asList(order)));
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
        final Role role1 = new Role();
        role1.setRoleKey("roleKey");
        role1.setRoleName("roleName");
        final Permission permission1 = new Permission();
        permission1.setPermissionKey("permissionKey");
        permission1.setPermissionName("permissionName");
        role1.setPermissions(new HashSet<>(Arrays.asList(permission1)));
        user1.setRoles(new HashSet<>(Arrays.asList(role1)));
        final Order order1 = new Order();
        order1.setTicketTypeId(0L);
        order1.setFirstName("firstName");
        order1.setLastName("lastName");
        order1.setMail("mail");
        order1.setPhoneNumber("phoneNumber");
        order1.setStatus("status");
        order1.setOrderCode("orderCode");
        order1.setTotalPayment(0);
        order1.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order1.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user1.setOrder(new HashSet<>(Arrays.asList(order1)));
        passwordResetToken.setUser(user1);
        passwordResetToken.setExpiryDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        when(mockPasswordTokenRepository.save(new PasswordResetToken())).thenReturn(passwordResetToken);

        // Run the test
        final ResponseEntity<?> result = userServiceImplUnderTest.createPasswordResetToken("mail");

        // Verify the results
    }
}
