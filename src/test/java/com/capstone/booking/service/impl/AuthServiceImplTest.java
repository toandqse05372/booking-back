package com.capstone.booking.service.impl;

import com.capstone.booking.common.converter.UserConverter;
import com.capstone.booking.config.facebook.RestFB;
import com.capstone.booking.config.security.JwtUtil;
import com.capstone.booking.config.security.UserPrincipal;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.FBLoginDTO;
import com.capstone.booking.entity.dto.UserDTO;
import com.capstone.booking.repository.RoleRepository;
import com.capstone.booking.repository.TokenRepository;
import com.capstone.booking.repository.UserRepository;
import com.capstone.booking.service.TokenService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class AuthServiceImplTest {

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private JwtUtil mockJwtUtil;
    @Mock
    private TokenService mockTokenService;
    @Mock
    private TokenRepository mockTokenRepository;
    @Mock
    private RestFB mockRestFB;
    @Mock
    private UserConverter mockUserConverter;
    @Mock
    private RoleRepository mockRoleRepository;

    @InjectMocks
    private AuthServiceImpl authServiceImplUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testFindByEmail() {
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

        when(mockJwtUtil.generateToken(any(UserPrincipal.class))).thenReturn("result");

        // Configure JwtUtil.generateExpirationDate(...).
        final Date date = new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime();
        when(mockJwtUtil.generateExpirationDate()).thenReturn(date);

        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockTokenService).createToken(any(Token.class));

        // Run the test
        final ResponseEntity<?> result = authServiceImplUnderTest.findByEmail(userDTO, "page");

        // Verify the results
    }

    @Test
    public void testLoginFb() {
        // Setup
        final FBLoginDTO fbForm = new FBLoginDTO();
        fbForm.setAccessToken("accessToken");
        fbForm.setEmail("email");
        fbForm.setName("name");

        // Configure RestFB.getUserInfo(...).
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
        when(mockRestFB.getUserInfo("accessToken")).thenReturn(userDTO);

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
        when(mockUserRepository.findByMail("mail")).thenReturn(user);

        when(mockJwtUtil.generateToken(any(UserPrincipal.class))).thenReturn("result");

        // Configure JwtUtil.generateExpirationDate(...).
        final Date date = new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime();
        when(mockJwtUtil.generateExpirationDate()).thenReturn(date);

        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockTokenService).createToken(any(Token.class));

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
        when(mockUserRepository.save(any(User.class))).thenReturn(user2);

        // Run the test
        final ResponseEntity<?> result = authServiceImplUnderTest.loginFb(fbForm);

        // Verify the results
    }

    @Test
    public void testLogout() {
        // Setup

        // Configure TokenRepository.findByToken(...).
        final Token token = new Token();
        token.setToken("token");
        token.setTokenExpDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        when(mockTokenRepository.findByToken("token")).thenReturn(token);

        // Run the test
        final ResponseEntity<?> result = authServiceImplUnderTest.logout("tokenStr");

        // Verify the results
        verify(mockTokenRepository).delete(any(Token.class));
    }

    @Test
    public void testCheckToken() {
        // Setup

        // Configure TokenRepository.findByToken(...).
        final Token token = new Token();
        token.setToken("token");
        token.setTokenExpDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        when(mockTokenRepository.findByToken("token")).thenReturn(token);

        // Run the test
        final ResponseEntity<?> result = authServiceImplUnderTest.checkToken("tokenStr");

        // Verify the results
        verify(mockTokenRepository).delete(any(Token.class));
    }

    @Test
    public void testSetPermission() {
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

        // Run the test
        final UserPrincipal result = authServiceImplUnderTest.setPermission(user);

        // Verify the results
    }

    @Test
    public void testReturnToken() {
        // Setup
        final UserPrincipal userPrincipal = new UserPrincipal();
        when(mockJwtUtil.generateToken(any(UserPrincipal.class))).thenReturn("result");

        // Configure JwtUtil.generateExpirationDate(...).
        final Date date = new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime();
        when(mockJwtUtil.generateExpirationDate()).thenReturn(date);

        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockTokenService).createToken(any(Token.class));

        // Run the test
        final Token result = authServiceImplUnderTest.returnToken(userPrincipal);

        // Verify the results
    }
}
