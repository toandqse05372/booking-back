package com.capstone.booking.repository.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.UserConverter;
import com.capstone.booking.entity.Permission;
import com.capstone.booking.entity.Role;
import com.capstone.booking.entity.User;
import com.capstone.booking.entity.dto.UserDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserRepositoryImplTest {

    @Mock
    private EntityManager mockEntityManager;

    private UserRepositoryImpl userRepositoryImplUnderTest;

    @Mock
    Query query;

    @Before
    public void setUp() {
        initMocks(this);
        userRepositoryImplUnderTest = new UserRepositoryImpl(mockEntityManager);
        userRepositoryImplUnderTest.userConverter = mock(UserConverter.class);
    }

    @Test
    public void testFindByMultiParam() {
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
        final List<User> userList = Arrays.asList(user);
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
        final List<UserDTO> userDTOS = Arrays.asList(userDTO);
        final Output expectedResult = new Output();
        expectedResult.setPage(1);
        expectedResult.setTotalPage(1);
        expectedResult.setListResult(userDTOS);
        expectedResult.setTotalItems(1);
        BigInteger counter = BigInteger.valueOf(1);
        when(mockEntityManager.createNativeQuery("select count(user0_.id) from t_user user0_ INNER join t_user_role user_role0_ on user0_.id = user_role0_.user_id where  user_role0_.role_id = :id  and user0_.first_name like :fname  and user0_.mail like :mail  and user0_.last_name like :lname  and user0_.phone_number like :pnum ")).thenReturn(query);
        when(query.setParameter("lname","")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(counter);
        when(mockEntityManager.createNativeQuery("select user0_.* from t_user user0_ INNER join t_user_role user_role0_ on user0_.id = user_role0_.user_id where  user_role0_.role_id = :id  and user0_.first_name like :fname  and user0_.mail like :mail  and user0_.last_name like :lname  and user0_.phone_number like :pnum limit :from, :limit", User.class)).thenReturn(query);
        when(query.setParameter("from",1)).thenReturn(query);
        when(query.setParameter("limit",1)).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(user));
        when(userRepositoryImplUnderTest.userConverter.toDTO(user)).thenReturn(userDTO);
        // Run the test
        final Output result = userRepositoryImplUnderTest.findByMultiParam("fname", "mail", "lastName", "phoneNumber", 1L, 1L, 1L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testConvertList() {
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
        final List<User> userList = Arrays.asList(user);
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
        final List<UserDTO> expectedResult = Arrays.asList(userDTO);
        when(userRepositoryImplUnderTest.userConverter.toDTO(user)).thenReturn(userDTO);
        // Run the test
        final List<UserDTO> result = userRepositoryImplUnderTest.convertList(userList);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testQueryUser() {
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
        final Map<String, Object> params = new HashMap<>();
        when(mockEntityManager.createNativeQuery("select * from t_user user0_ ", User.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(user));
        // Run the test
        final List<User> result = userRepositoryImplUnderTest.queryUser(params, "select * from t_user user0_ ");

        // Verify the results
        assertThat(result).isEqualTo(Arrays.asList(user));
    }

    @Test
    public void testCountUser() {
        // Setup
        BigInteger expectedResult = BigInteger.valueOf(0);
        final Map<String, Object> params = new HashMap<>();
        when(mockEntityManager.createNativeQuery("select count(user0_.id) from t_user user0_ ")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(expectedResult);
        // Run the test
        final int result = userRepositoryImplUnderTest.countUser(params, "select count(user0_.id) from t_user user0_ ");

        // Verify the results
        assertThat(result).isEqualTo(0);
    }
}
