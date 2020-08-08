package com.capstone.booking.service.impl;

import com.capstone.booking.common.converter.UserConverter;
import com.capstone.booking.config.facebook.RestFB;
import com.capstone.booking.config.security.JwtUtil;
import com.capstone.booking.config.security.UserPrincipal;
import com.capstone.booking.entity.Permission;
import com.capstone.booking.entity.Role;
import com.capstone.booking.entity.Token;
import com.capstone.booking.entity.User;
import com.capstone.booking.entity.dto.FBLoginDTO;
import com.capstone.booking.entity.dto.UserDTO;
import com.capstone.booking.repository.RoleRepository;
import com.capstone.booking.repository.TokenRepository;
import com.capstone.booking.repository.UserRepository;
import com.capstone.booking.service.TokenService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
        userDTO.setAvatarLink("avatarLink");

        // Configure UserRepository.findByMail(...).
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode("password");
        final User user = new User();
        user.setPassword(password);
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setMail("mail");
        user.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        user.setPhoneNumber("phoneNumber");
        user.setStatus("status");
        user.setUserType("userType");
        user.setAvatarLink("avatarLink");
        user.setId(0l);
        final Role role = new Role();
        role.setRoleKey("ADMIN");
        role.setRoleName("ADMIN");
        final Permission permission = new Permission();
        permission.setPermissionKey("permissionKey");
        permission.setPermissionName("permissionName");
        role.setPermissions(new HashSet<>(Arrays.asList(permission)));
        user.setRoles(new HashSet<>(Arrays.asList(role)));
        when(mockUserRepository.findByMail("mail")).thenReturn(user);
        when(mockTokenRepository.findByUserId(user.getId())).thenReturn(null);

        when(mockJwtUtil.generateToken(any(UserPrincipal.class))).thenReturn("result");

        // Configure JwtUtil.generateExpirationDate(...).
        final Date date = new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime();
        when(mockJwtUtil.generateExpirationDate()).thenReturn(date);

        Token token = new Token();
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockTokenService).createToken(token);

        // Run the test
        final ResponseEntity<?> result = authServiceImplUnderTest.findByEmail(userDTO, "CMS");

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
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
        final ResponseEntity<?> result = authServiceImplUnderTest.logout("Token token");

        // Verify the results
        verify(mockTokenRepository).delete(token);
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
        final ResponseEntity<?> result = authServiceImplUnderTest.checkToken("Token token");

        // Verify the results
        verify(mockTokenRepository).delete(token);
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
        user.setAvatarLink("avatarLink");
        final Role role = new Role();
        role.setRoleKey("roleKey");
        role.setRoleName("roleName");
        final Permission permission = new Permission();
        permission.setPermissionKey("permissionKey");
        permission.setPermissionName("permissionName");
        role.setPermissions(new HashSet<>(Arrays.asList(permission)));
        user.setRoles(new HashSet<>(Arrays.asList(role)));

        // Run the test
        final UserPrincipal result = authServiceImplUnderTest.setPermission(user);

        // Verify the results
        Assertions.assertNotNull(result);
    }

    @Test
    public void testReturnToken() {
        // Setup
        final UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setUserId(0l);
        when(mockJwtUtil.generateToken(userPrincipal)).thenReturn("result");

        // Configure JwtUtil.generateExpirationDate(...).
        final Date date = new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime();
        when(mockJwtUtil.generateExpirationDate()).thenReturn(date);

        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockTokenService).createToken(any(Token.class));

        // Run the test
        final Token result = authServiceImplUnderTest.returnToken(userPrincipal);

        // Verify the results
        Assertions.assertNotNull(result);
    }
}
