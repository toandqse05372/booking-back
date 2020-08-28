package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.OrderConverter;
import com.capstone.booking.common.converter.OrderItemConverter;
import com.capstone.booking.common.helper.pdf.PdfPrinter;
import com.capstone.booking.common.helper.pdf.PrintRequest;
import com.capstone.booking.common.key.OrderStatus;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.OrderDTO;
import com.capstone.booking.entity.dto.OrderItemDTO;
import com.capstone.booking.repository.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class OrderServiceImplTest {

    @Mock
    private OrderRepository mockOrderRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private OrderConverter mockOrderConverter;
    @Mock
    private CodeRepository mockCodeRepository;
    @Mock
    private TicketRepository mockTicketRepository;
    @Mock
    private TicketTypeRepository mockTicketTypeRepository;
    @Mock
    private OrderItemRepository mockOrderItemRepository;
    @Mock
    private OrderItemConverter mockOrderItemConverter;
    @Mock
    private PlaceRepository mockPlaceRepository;
    @Mock
    private PdfPrinter mockPdfPrinter;
    @Mock
    private JavaMailSender mockEmailSender;

    @InjectMocks
    private OrderServiceImpl orderServiceImplUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testCreate() {
        // Setup
        final OrderDTO orderDTO = new OrderDTO();
        orderDTO.setTicketTypeId(0L);
        orderDTO.setTicketTypeName("ticketTypeName");
        orderDTO.setUserId(0L);
        orderDTO.setFirstName("firstName");
        orderDTO.setLastName("lastName");
        orderDTO.setMail("mail");
        orderDTO.setPhoneNumber("phoneNumber");
        orderDTO.setStatus("status");
        orderDTO.setOrderCode("orderCode");
        orderDTO.setTotalPayment(0);

        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setVisitorTypeId(0l);
        orderItemDTO.setQuantity(1);

        Set<OrderItemDTO> orderItemDTOS = new HashSet<>();
        orderItemDTOS.add(orderItemDTO);
        orderDTO.setOrderItems(orderItemDTOS);

        // Configure OrderConverter.toOrder(...).
        final Order order = new Order();
        order.setId(0l);
        order.setTicketTypeId(0L);
        order.setFirstName("firstName");
        order.setLastName("lastName");
        order.setMail("mail");
        order.setPhoneNumber("phoneNumber");
        order.setStatus("status");
        order.setOrderCode("orderCode");
        order.setTotalPayment(0);
        order.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order.setRedemptionDate(new GregorianCalendar(2029, Calendar.JANUARY, 1).getTime());
        when(mockOrderConverter.toOrder(orderDTO)).thenReturn(order);

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
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findById(0L)).thenReturn(user);

        // Configure OrderRepository.findTopByOrderByIdDesc(...).
        when(mockOrderRepository.findTopByOrderByIdDesc()).thenReturn(order);

        // Configure OrderRepository.save(...).
        when(mockOrderRepository.save(order)).thenReturn(order);

        // Configure OrderItemConverter.toItem(...).
        final OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(1);
        final VisitorType visitorType = new VisitorType();
        visitorType.setTypeName("typeName");
        visitorType.setTypeKey("typeKey");
        visitorType.setPrice(0);
        visitorType.setBasicType(false);
        visitorType.setStatus("status");
        visitorType.setId(0l);
        orderItem.setVisitorType(visitorType);

        when(mockOrderItemConverter.toItem(orderItemDTO)).thenReturn(orderItem);

        // Configure OrderItemRepository.saveAll(...).
        final List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        when(mockOrderItemRepository.saveAll(orderItems)).thenReturn(orderItems);

        // Configure OrderConverter.toDTO(...).
        when(mockOrderConverter.toDTO(order)).thenReturn(orderDTO);

        // Run the test
        final ResponseEntity<?> result = orderServiceImplUnderTest.create(orderDTO, OrderStatus.PAID, "paymentIntentId");

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void testDelete() {
        // Setup

        // Configure OrderRepository.findById(...).
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
        final Optional<Order> order = Optional.of(order1);
        when(mockOrderRepository.findById(0L)).thenReturn(order);

        // Run the test
        final ResponseEntity<?> result = orderServiceImplUnderTest.delete(0L);

        // Verify the results
        verify(mockOrderItemRepository).deleteAllByOrder(any(Order.class));
        verify(mockOrderRepository).deleteById(0L);
    }

    @Test
    public void testFindByStatus() {
        // Setup

        // Configure OrderRepository.findByStatus(...).
        final Output output = new Output();
        output.setPage(0);
        output.setTotalPage(0);
        output.setListResult(Arrays.asList());
        output.setTotalItems(0);
        when(mockOrderRepository.findByStatus("status", "code", 1l, 1l, 10l)).thenReturn(output);

        // Run the test
        final ResponseEntity<?> result = orderServiceImplUnderTest.findByStatus("status", "code", 1l, 1l, 10l);

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void testFindByOrderId() {
        // Setup

        // Configure OrderRepository.findById(...).
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
        final Optional<Order> order = Optional.of(order1);
        when(mockOrderRepository.findById(0L)).thenReturn(order);

        // Configure OrderConverter.toDTO(...).
        final OrderDTO orderDTO = new OrderDTO();
        orderDTO.setTicketTypeId(0L);
        orderDTO.setTicketTypeName("ticketTypeName");
        orderDTO.setUserId(0L);
        orderDTO.setFirstName("firstName");
        orderDTO.setLastName("lastName");
        orderDTO.setMail("mail");
        orderDTO.setPhoneNumber("phoneNumber");
        orderDTO.setStatus("status");
        orderDTO.setOrderCode("orderCode");
        orderDTO.setTotalPayment(0);
        when(mockOrderConverter.toDTO(any(Order.class))).thenReturn(orderDTO);

        // Run the test
        final ResponseEntity<?> result = orderServiceImplUnderTest.findByOrderId(0L, 0l);

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void testResendTicket() throws Exception {
        // Setup

        // Configure OrderRepository.findById(...).
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
        order1.setRedemptionDate(new GregorianCalendar(2029, Calendar.JANUARY, 1).getTime());
        final OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(0);
        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(orderItem);
        order1.setOrderItem(orderItems);

        final Optional<Order> order = Optional.of(order1);
        when(mockOrderRepository.findById(0L)).thenReturn(order);

        // Configure TicketTypeRepository.findById(...).
        final TicketType ticketType1 = new TicketType();
        ticketType1.setTypeName("typeName");
        ticketType1.setPlaceId(0L);
        ticketType1.setStatus("status");
        final VisitorType visitorType = new VisitorType();
        visitorType.setTypeName("typeName");
        visitorType.setTypeKey("typeKey");
        visitorType.setPrice(0);
        visitorType.setBasicType(false);
        visitorType.setStatus("status");
        visitorType.setTicketType(ticketType1);
        orderItem.setVisitorType(visitorType);

        final Ticket ticket = new Ticket();
        ticket.setCode("code");
        ticket.setRedemptionDate(new GregorianCalendar(2029, Calendar.JANUARY, 1).getTime());
        ticket.setVisitorTypeId(0L);
        ticket.setOrderItem(new OrderItem());
        orderItem.setTicket(new HashSet<>(Arrays.asList(ticket)));
        visitorType.setOrderItem(new HashSet<>(Arrays.asList(orderItem)));

        final Place place = new Place();
        place.setName("name");
        place.setPlaceKey("placeKey");
        place.setAddress("address");
        place.setDetailDescription("detailDescription");
        place.setShortDescription("shortDescription");
        place.setMail("mail");
        place.setPhoneNumber("phoneNumber");
        place.setStatus("status");
        place.setLocation("location");
        place.setCancelPolicy("cancelPolicy");
        final Optional<TicketType> ticketType = Optional.of(ticketType1);
        when(mockTicketTypeRepository.findById(0L)).thenReturn(ticketType);

        // Configure PlaceRepository.findById(...).
        final Optional<Place> place1 = Optional.of(place);
        when(mockPlaceRepository.findById(0L)).thenReturn(place1);

        // Configure TicketRepository.findAllByOrderItem(...).
        final Ticket ticket1 = new Ticket();
        ticket1.setCode("code");
        ticket1.setRedemptionDate(new GregorianCalendar(2029, Calendar.JANUARY, 1).getTime());
        ticket1.setVisitorTypeId(0L);
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(ticket1);
        when(mockTicketRepository.findAllByOrderItem(orderItem)).thenReturn(tickets);

        File file = new File("Test.pdf");
        List<PrintRequest> printRequests = new ArrayList<>();
        PrintRequest printRequest = new PrintRequest();
        printRequest.setRedemptionDate(new GregorianCalendar(2029, Calendar.JANUARY, 1).getTime());
        printRequest.setPlace(place);
        printRequest.setVisitorType(visitorType);
        printRequest.setTicketType(ticketType1);
        printRequest.setTickets(tickets);
        printRequests.add(printRequest);
        when(mockPdfPrinter.printPDF(printRequests, place.getPlaceKey())).thenReturn(file);

        // Configure OrderRepository.save(...).
        when(mockOrderRepository.save(order1)).thenReturn(order1);

        // Configure JavaMailSender.createMimeMessage(...).
        final MimeMessage mimeMessage = new MimeMessage(Session.getInstance(new Properties()));
        when(mockEmailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Configure OrderConverter.toDTO(...).
        final OrderDTO orderDTO = new OrderDTO();
        orderDTO.setTicketTypeId(0L);
        orderDTO.setTicketTypeName("ticketTypeName");
        orderDTO.setUserId(0L);
        orderDTO.setFirstName("firstName");
        orderDTO.setLastName("lastName");
        orderDTO.setMail("mail");
        orderDTO.setPhoneNumber("phoneNumber");
        orderDTO.setStatus("status");
        orderDTO.setOrderCode("orderCode");
        orderDTO.setTotalPayment(0);
        when(mockOrderConverter.toDTO(any(Order.class))).thenReturn(orderDTO);

        // Run the test
        final ResponseEntity<?> result = orderServiceImplUnderTest.resendTicket(0L);

        // Verify the results
        verify(mockEmailSender).send(any(MimeMessage.class));
    }

    @Test
    public void testGetOrderByUid() {
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

        // Configure OrderRepository.findAllByUserOrderByCreatedAtDesc(...).
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
        final List<Order> orders = Arrays.asList(order);
        when(mockOrderRepository.findAllByUserOrderByCreatedAtDesc(any(User.class))).thenReturn(orders);

        // Configure OrderConverter.toDTO(...).
        final OrderDTO orderDTO = new OrderDTO();
        orderDTO.setTicketTypeId(0L);
        orderDTO.setTicketTypeName("ticketTypeName");
        orderDTO.setUserId(0L);
        orderDTO.setFirstName("firstName");
        orderDTO.setLastName("lastName");
        orderDTO.setMail("mail");
        orderDTO.setPhoneNumber("phoneNumber");
        orderDTO.setStatus("status");
        orderDTO.setOrderCode("orderCode");
        orderDTO.setTotalPayment(0);
        when(mockOrderConverter.toDTO(any(Order.class))).thenReturn(orderDTO);

        // Run the test
        final ResponseEntity<?> result = orderServiceImplUnderTest.getOrderByUid(0L, 0l);

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void testSendEmail() throws Exception {
        // Setup
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

        final File file = new File("filename.txt");

        // Configure JavaMailSender.createMimeMessage(...).
        final MimeMessage mimeMessage = new MimeMessage(Session.getInstance(new Properties()));
        when(mockEmailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Run the test
        orderServiceImplUnderTest.sendEmail(order, file);

        // Verify the results
        verify(mockEmailSender).send(any(MimeMessage.class));
    }

    @Test
    public void testGetOrderByUidTop3() {
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

        // Configure OrderRepository.getTop3(...).
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
        final List<Order> orders = Arrays.asList(order);
        when(mockOrderRepository.getTop3(0L)).thenReturn(orders);

        // Configure OrderConverter.toDTO(...).
        final OrderDTO orderDTO = new OrderDTO();
        orderDTO.setTicketTypeId(0L);
        orderDTO.setTicketTypeName("ticketTypeName");
        orderDTO.setUserId(0L);
        orderDTO.setFirstName("firstName");
        orderDTO.setLastName("lastName");
        orderDTO.setMail("mail");
        orderDTO.setPhoneNumber("phoneNumber");
        orderDTO.setStatus("status");
        orderDTO.setOrderCode("orderCode");
        orderDTO.setTotalPayment(0);
        when(mockOrderConverter.toDTO(any(Order.class))).thenReturn(orderDTO);

        // Run the test
        final ResponseEntity<?> result = orderServiceImplUnderTest.getOrderByUidTop3(0L, 0l);

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }
}
