package com.capstone.booking.repository.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.UserConverter;
import com.capstone.booking.entity.Order;
import com.capstone.booking.entity.Permission;
import com.capstone.booking.entity.Role;
import com.capstone.booking.entity.User;
import com.capstone.booking.entity.dto.UserDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserRepositoryImplTest {

    @Mock
    private UserConverter mockUserConverter;

    @InjectMocks
    private UserRepositoryImpl userRepositoryImplUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testFindByMultiParam() {
        // Setup
        final Output expectedResult = new Output();
        expectedResult.setPage(0);
        expectedResult.setTotalPage(0);
        expectedResult.setListResult(Arrays.asList());
        expectedResult.setTotalItems(0);

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
        final Output result = userRepositoryImplUnderTest.findByMultiParam("fname", "mail", "lastName", "phoneNumber", 0L, 0L, 0L);

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
        final List<UserDTO> expectedResult = Arrays.asList(userDTO);

        // Configure UserConverter.toDTO(...).
        final UserDTO userDTO1 = new UserDTO();
        userDTO1.setPassword("password");
        userDTO1.setFirstName("firstName");
        userDTO1.setLastName("lastName");
        userDTO1.setMail("mail");
        userDTO1.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        userDTO1.setPhoneNumber("phoneNumber");
        userDTO1.setStatus("status");
        userDTO1.setRoleKey(new HashSet<>(Arrays.asList("value")));
        userDTO1.setUserType("userType");
        when(mockUserConverter.toDTO(any(User.class))).thenReturn(userDTO1);

        // Run the test
        final List<UserDTO> result = userRepositoryImplUnderTest.convertList(userList);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testQueryUser() {
        // Setup
        final Map<String, Object> params = new HashMap<>();

        // Run the test
        final List<User> result = userRepositoryImplUnderTest.queryUser(params, "sqlStr");

        // Verify the results
    }
}
