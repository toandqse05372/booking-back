package com.capstone.booking.common.converter;

import com.capstone.booking.entity.Order;
import com.capstone.booking.entity.Permission;
import com.capstone.booking.entity.Role;
import com.capstone.booking.entity.User;
import com.capstone.booking.entity.dto.UserDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserConverterTest {

    private UserConverter userConverterUnderTest;

    @Before
    public void setUp() {
        userConverterUnderTest = new UserConverter();
    }

    @Test
    public void testToDTO() {
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

        final UserDTO expectedResult = new UserDTO();
        Set<String> roleKeys = new HashSet<>(Arrays.asList("roleKey"));
        expectedResult.setRoleKey(roleKeys);
        expectedResult.setPassword("password");
        expectedResult.setFirstName("firstName");
        expectedResult.setLastName("lastName");
        expectedResult.setMail("mail");
        expectedResult.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        expectedResult.setPhoneNumber("phoneNumber");
        expectedResult.setStatus("status");
        expectedResult.setRoleKey(new HashSet<>(Arrays.asList("roleKey")));
        expectedResult.setUserType("userType");

        // Run the test
        final UserDTO result = userConverterUnderTest.toDTO(user);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testToDTOClient() {
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

        final UserDTO expectedResult = new UserDTO();
        expectedResult.setFirstName("firstName");
        expectedResult.setLastName("lastName");
        expectedResult.setMail("mail");
        expectedResult.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        expectedResult.setPhoneNumber("phoneNumber");

        // Run the test
        final UserDTO result = userConverterUnderTest.toDTOClient(user);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testToUser() {
        // Setup
        final UserDTO dto = new UserDTO();
        dto.setPassword("password");
        dto.setFirstName("firstName");
        dto.setLastName("lastName");
        dto.setMail("mail");
        dto.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        dto.setPhoneNumber("phoneNumber");
        dto.setStatus("status");
        dto.setRoleKey(new HashSet<>(Arrays.asList("value")));
        dto.setUserType("userType");

        // Run the test
        final User result = userConverterUnderTest.toUser(dto);

        // Verify the results
    }

    @Test
    public void testToUser1() {
        // Setup
        final UserDTO dto = new UserDTO();
        dto.setPassword("password");
        dto.setFirstName("firstName");
        dto.setLastName("lastName");
        dto.setMail("mail");
        dto.setDob(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        dto.setPhoneNumber("phoneNumber");
        dto.setStatus("status");
        dto.setRoleKey(new HashSet<>(Arrays.asList("value")));
        dto.setUserType("userType");

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
        final User result = userConverterUnderTest.toUser(dto, user);

        // Verify the results
    }
}
