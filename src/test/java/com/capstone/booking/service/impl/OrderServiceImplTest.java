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
import com.itextpdf.text.DocumentException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
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

        // Configure OrderConverter.toOrder(...).
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
        when(mockOrderConverter.toOrder(new OrderDTO())).thenReturn(order);

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
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findById(0L)).thenReturn(user);

        // Configure OrderRepository.findTopByOrderByIdDesc(...).
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
        when(mockOrderRepository.findTopByOrderByIdDesc()).thenReturn(order2);

        // Configure OrderRepository.save(...).
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
        when(mockOrderRepository.save(any(Order.class))).thenReturn(order3);

        // Configure OrderItemConverter.toItem(...).
        final OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(0);
        final VisitorType visitorType = new VisitorType();
        visitorType.setTypeName("typeName");
        visitorType.setTypeKey("typeKey");
        visitorType.setPrice(0);
        visitorType.setBasicType(false);
        visitorType.setStatus("status");
        final TicketType ticketType = new TicketType();
        ticketType.setTypeName("typeName");
        ticketType.setPlaceId(0L);
        ticketType.setStatus("status");
        ticketType.setVisitorType(new HashSet<>(Arrays.asList(new VisitorType())));
        final Game game = new Game();
        game.setGameName("gameName");
        game.setGameDescription("gameDescription");
        game.setStatus("status");
        game.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
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
        game.setPlace(place);
        ticketType.setGame(new HashSet<>(Arrays.asList(game)));
        visitorType.setTicketType(ticketType);
        visitorType.setOrderItem(new HashSet<>(Arrays.asList(new OrderItem())));
        final Code code = new Code();
        code.setCode("code");
        code.setVisitorType(new VisitorType());
        visitorType.setCode(new HashSet<>(Arrays.asList(code)));
        orderItem.setVisitorType(visitorType);
        final Order order4 = new Order();
        order4.setTicketTypeId(0L);
        order4.setFirstName("firstName");
        order4.setLastName("lastName");
        order4.setMail("mail");
        order4.setPhoneNumber("phoneNumber");
        order4.setStatus("status");
        order4.setOrderCode("orderCode");
        order4.setTotalPayment(0);
        order4.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order4.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        orderItem.setOrder(order4);
        final Ticket ticket = new Ticket();
        ticket.setCode("code");
        ticket.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket.setVisitorTypeId(0L);
        ticket.setOrderItem(new OrderItem());
        orderItem.setTicket(new HashSet<>(Arrays.asList(ticket)));
        when(mockOrderItemConverter.toItem(new OrderItemDTO())).thenReturn(orderItem);

        // Configure OrderItemRepository.saveAll(...).
        final OrderItem orderItem1 = new OrderItem();
        orderItem1.setQuantity(0);
        final VisitorType visitorType1 = new VisitorType();
        visitorType1.setTypeName("typeName");
        visitorType1.setTypeKey("typeKey");
        visitorType1.setPrice(0);
        visitorType1.setBasicType(false);
        visitorType1.setStatus("status");
        final TicketType ticketType1 = new TicketType();
        ticketType1.setTypeName("typeName");
        ticketType1.setPlaceId(0L);
        ticketType1.setStatus("status");
        ticketType1.setVisitorType(new HashSet<>(Arrays.asList(new VisitorType())));
        final Game game1 = new Game();
        game1.setGameName("gameName");
        game1.setGameDescription("gameDescription");
        game1.setStatus("status");
        game1.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
        final Place place1 = new Place();
        place1.setName("name");
        place1.setPlaceKey("placeKey");
        place1.setAddress("address");
        place1.setDetailDescription("detailDescription");
        place1.setShortDescription("shortDescription");
        place1.setMail("mail");
        place1.setPhoneNumber("phoneNumber");
        place1.setStatus("status");
        place1.setLocation("location");
        place1.setCancelPolicy("cancelPolicy");
        game1.setPlace(place1);
        ticketType1.setGame(new HashSet<>(Arrays.asList(game1)));
        visitorType1.setTicketType(ticketType1);
        visitorType1.setOrderItem(new HashSet<>(Arrays.asList(new OrderItem())));
        final Code code1 = new Code();
        code1.setCode("code");
        code1.setVisitorType(new VisitorType());
        visitorType1.setCode(new HashSet<>(Arrays.asList(code1)));
        orderItem1.setVisitorType(visitorType1);
        final Order order5 = new Order();
        order5.setTicketTypeId(0L);
        order5.setFirstName("firstName");
        order5.setLastName("lastName");
        order5.setMail("mail");
        order5.setPhoneNumber("phoneNumber");
        order5.setStatus("status");
        order5.setOrderCode("orderCode");
        order5.setTotalPayment(0);
        order5.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order5.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        orderItem1.setOrder(order5);
        final Ticket ticket1 = new Ticket();
        ticket1.setCode("code");
        ticket1.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket1.setVisitorTypeId(0L);
        ticket1.setOrderItem(new OrderItem());
        orderItem1.setTicket(new HashSet<>(Arrays.asList(ticket1)));
        final List<OrderItem> orderItems = Arrays.asList(orderItem1);
        when(mockOrderItemRepository.saveAll(Arrays.asList(new OrderItem()))).thenReturn(orderItems);

        // Configure OrderConverter.toDTO(...).
        final OrderDTO orderDTO1 = new OrderDTO();
        orderDTO1.setTicketTypeId(0L);
        orderDTO1.setTicketTypeName("ticketTypeName");
        orderDTO1.setUserId(0L);
        orderDTO1.setFirstName("firstName");
        orderDTO1.setLastName("lastName");
        orderDTO1.setMail("mail");
        orderDTO1.setPhoneNumber("phoneNumber");
        orderDTO1.setStatus("status");
        orderDTO1.setOrderCode("orderCode");
        orderDTO1.setTotalPayment(0);
        when(mockOrderConverter.toDTO(any(Order.class))).thenReturn(orderDTO1);

        // Run the test
        final ResponseEntity<?> result = orderServiceImplUnderTest.create(orderDTO, OrderStatus.PAID);

        // Verify the results
    }

    @Test
    public void testUpdate() {
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

        // Configure OrderConverter.toOrder(...).
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
        when(mockOrderConverter.toOrder(eq(new OrderDTO()), any(Order.class))).thenReturn(order2);

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
        user1.setOrder(new HashSet<>(Arrays.asList(order3)));
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findById(0L)).thenReturn(user);

        // Configure OrderRepository.save(...).
        final Order order4 = new Order();
        order4.setTicketTypeId(0L);
        order4.setFirstName("firstName");
        order4.setLastName("lastName");
        order4.setMail("mail");
        order4.setPhoneNumber("phoneNumber");
        order4.setStatus("status");
        order4.setOrderCode("orderCode");
        order4.setTotalPayment(0);
        order4.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order4.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        when(mockOrderRepository.save(any(Order.class))).thenReturn(order4);

        // Configure OrderConverter.toDTO(...).
        final OrderDTO orderDTO1 = new OrderDTO();
        orderDTO1.setTicketTypeId(0L);
        orderDTO1.setTicketTypeName("ticketTypeName");
        orderDTO1.setUserId(0L);
        orderDTO1.setFirstName("firstName");
        orderDTO1.setLastName("lastName");
        orderDTO1.setMail("mail");
        orderDTO1.setPhoneNumber("phoneNumber");
        orderDTO1.setStatus("status");
        orderDTO1.setOrderCode("orderCode");
        orderDTO1.setTotalPayment(0);
        when(mockOrderConverter.toDTO(any(Order.class))).thenReturn(orderDTO1);

        // Run the test
        final ResponseEntity<?> result = orderServiceImplUnderTest.update(orderDTO);

        // Verify the results
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
        when(mockOrderRepository.findByStatus("status", "code")).thenReturn(output);

        // Run the test
        final ResponseEntity<?> result = orderServiceImplUnderTest.findByStatus("status", "code");

        // Verify the results
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
        final ResponseEntity<?> result = orderServiceImplUnderTest.findByOrderId(0L);

        // Verify the results
    }

    @Test
    public void testSendTicket() throws Exception {
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
        visitorType.setTicketType(new TicketType());
        final OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(0);
        orderItem.setVisitorType(new VisitorType());
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
        orderItem.setOrder(order2);
        final Ticket ticket = new Ticket();
        ticket.setCode("code");
        ticket.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket.setVisitorTypeId(0L);
        ticket.setOrderItem(new OrderItem());
        orderItem.setTicket(new HashSet<>(Arrays.asList(ticket)));
        visitorType.setOrderItem(new HashSet<>(Arrays.asList(orderItem)));
        final Code code = new Code();
        code.setCode("code");
        code.setVisitorType(new VisitorType());
        visitorType.setCode(new HashSet<>(Arrays.asList(code)));
        ticketType1.setVisitorType(new HashSet<>(Arrays.asList(visitorType)));
        final Game game = new Game();
        game.setGameName("gameName");
        game.setGameDescription("gameDescription");
        game.setStatus("status");
        game.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
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
        game.setPlace(place);
        ticketType1.setGame(new HashSet<>(Arrays.asList(game)));
        final Optional<TicketType> ticketType = Optional.of(ticketType1);
        when(mockTicketTypeRepository.findById(0L)).thenReturn(ticketType);

        // Configure PlaceRepository.findById(...).
        final Place place2 = new Place();
        place2.setName("name");
        place2.setPlaceKey("placeKey");
        place2.setAddress("address");
        place2.setDetailDescription("detailDescription");
        place2.setShortDescription("shortDescription");
        place2.setMail("mail");
        place2.setPhoneNumber("phoneNumber");
        place2.setStatus("status");
        place2.setLocation("location");
        place2.setCancelPolicy("cancelPolicy");
        final Optional<Place> place1 = Optional.of(place2);
        when(mockPlaceRepository.findById(0L)).thenReturn(place1);

        // Configure CodeRepository.findByVisitorTypeIdLimitTo(...).
        final Code code1 = new Code();
        code1.setCode("code");
        final VisitorType visitorType1 = new VisitorType();
        visitorType1.setTypeName("typeName");
        visitorType1.setTypeKey("typeKey");
        visitorType1.setPrice(0);
        visitorType1.setBasicType(false);
        visitorType1.setStatus("status");
        final TicketType ticketType2 = new TicketType();
        ticketType2.setTypeName("typeName");
        ticketType2.setPlaceId(0L);
        ticketType2.setStatus("status");
        ticketType2.setVisitorType(new HashSet<>(Arrays.asList(new VisitorType())));
        final Game game1 = new Game();
        game1.setGameName("gameName");
        game1.setGameDescription("gameDescription");
        game1.setStatus("status");
        game1.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
        final Place place3 = new Place();
        place3.setName("name");
        place3.setPlaceKey("placeKey");
        place3.setAddress("address");
        place3.setDetailDescription("detailDescription");
        place3.setShortDescription("shortDescription");
        place3.setMail("mail");
        place3.setPhoneNumber("phoneNumber");
        place3.setStatus("status");
        place3.setLocation("location");
        place3.setCancelPolicy("cancelPolicy");
        game1.setPlace(place3);
        ticketType2.setGame(new HashSet<>(Arrays.asList(game1)));
        visitorType1.setTicketType(ticketType2);
        final OrderItem orderItem1 = new OrderItem();
        orderItem1.setQuantity(0);
        orderItem1.setVisitorType(new VisitorType());
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
        orderItem1.setOrder(order3);
        final Ticket ticket1 = new Ticket();
        ticket1.setCode("code");
        ticket1.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket1.setVisitorTypeId(0L);
        ticket1.setOrderItem(new OrderItem());
        orderItem1.setTicket(new HashSet<>(Arrays.asList(ticket1)));
        visitorType1.setOrderItem(new HashSet<>(Arrays.asList(orderItem1)));
        visitorType1.setCode(new HashSet<>(Arrays.asList(new Code())));
        code1.setVisitorType(visitorType1);
        final List<Code> codes = Arrays.asList(code1);
        when(mockCodeRepository.findByVisitorTypeIdLimitTo(0, new VisitorType())).thenReturn(codes);

        // Configure TicketRepository.saveAll(...).
        final Ticket ticket2 = new Ticket();
        ticket2.setCode("code");
        ticket2.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket2.setVisitorTypeId(0L);
        final OrderItem orderItem2 = new OrderItem();
        orderItem2.setQuantity(0);
        final VisitorType visitorType2 = new VisitorType();
        visitorType2.setTypeName("typeName");
        visitorType2.setTypeKey("typeKey");
        visitorType2.setPrice(0);
        visitorType2.setBasicType(false);
        visitorType2.setStatus("status");
        final TicketType ticketType3 = new TicketType();
        ticketType3.setTypeName("typeName");
        ticketType3.setPlaceId(0L);
        ticketType3.setStatus("status");
        ticketType3.setVisitorType(new HashSet<>(Arrays.asList(new VisitorType())));
        final Game game2 = new Game();
        game2.setGameName("gameName");
        game2.setGameDescription("gameDescription");
        game2.setStatus("status");
        game2.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
        final Place place4 = new Place();
        place4.setName("name");
        place4.setPlaceKey("placeKey");
        place4.setAddress("address");
        place4.setDetailDescription("detailDescription");
        place4.setShortDescription("shortDescription");
        place4.setMail("mail");
        place4.setPhoneNumber("phoneNumber");
        place4.setStatus("status");
        place4.setLocation("location");
        place4.setCancelPolicy("cancelPolicy");
        game2.setPlace(place4);
        ticketType3.setGame(new HashSet<>(Arrays.asList(game2)));
        visitorType2.setTicketType(ticketType3);
        visitorType2.setOrderItem(new HashSet<>(Arrays.asList(new OrderItem())));
        final Code code2 = new Code();
        code2.setCode("code");
        code2.setVisitorType(new VisitorType());
        visitorType2.setCode(new HashSet<>(Arrays.asList(code2)));
        orderItem2.setVisitorType(visitorType2);
        final Order order4 = new Order();
        order4.setTicketTypeId(0L);
        order4.setFirstName("firstName");
        order4.setLastName("lastName");
        order4.setMail("mail");
        order4.setPhoneNumber("phoneNumber");
        order4.setStatus("status");
        order4.setOrderCode("orderCode");
        order4.setTotalPayment(0);
        order4.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order4.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        orderItem2.setOrder(order4);
        orderItem2.setTicket(new HashSet<>(Arrays.asList(new Ticket())));
        ticket2.setOrderItem(orderItem2);
        final List<Ticket> tickets = Arrays.asList(ticket2);
        when(mockTicketRepository.saveAll(Arrays.asList(new Ticket()))).thenReturn(tickets);

        when(mockPdfPrinter.printPDF(Arrays.asList(new PrintRequest()))).thenReturn(new File("filename.txt"));

        // Configure OrderRepository.save(...).
        final Order order5 = new Order();
        order5.setTicketTypeId(0L);
        order5.setFirstName("firstName");
        order5.setLastName("lastName");
        order5.setMail("mail");
        order5.setPhoneNumber("phoneNumber");
        order5.setStatus("status");
        order5.setOrderCode("orderCode");
        order5.setTotalPayment(0);
        order5.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order5.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        when(mockOrderRepository.save(any(Order.class))).thenReturn(order5);

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
        final ResponseEntity<?> result = orderServiceImplUnderTest.sendTicket(0L);

        // Verify the results
        verify(mockCodeRepository).deleteAll(Arrays.asList(new Code()));
        verify(mockEmailSender).send(any(MimeMessage.class));
    }

    @Test
    public void testSendTicket_PdfPrinterThrowsIOException() throws Exception {
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
        visitorType.setTicketType(new TicketType());
        final OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(0);
        orderItem.setVisitorType(new VisitorType());
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
        orderItem.setOrder(order2);
        final Ticket ticket = new Ticket();
        ticket.setCode("code");
        ticket.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket.setVisitorTypeId(0L);
        ticket.setOrderItem(new OrderItem());
        orderItem.setTicket(new HashSet<>(Arrays.asList(ticket)));
        visitorType.setOrderItem(new HashSet<>(Arrays.asList(orderItem)));
        final Code code = new Code();
        code.setCode("code");
        code.setVisitorType(new VisitorType());
        visitorType.setCode(new HashSet<>(Arrays.asList(code)));
        ticketType1.setVisitorType(new HashSet<>(Arrays.asList(visitorType)));
        final Game game = new Game();
        game.setGameName("gameName");
        game.setGameDescription("gameDescription");
        game.setStatus("status");
        game.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
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
        game.setPlace(place);
        ticketType1.setGame(new HashSet<>(Arrays.asList(game)));
        final Optional<TicketType> ticketType = Optional.of(ticketType1);
        when(mockTicketTypeRepository.findById(0L)).thenReturn(ticketType);

        // Configure PlaceRepository.findById(...).
        final Place place2 = new Place();
        place2.setName("name");
        place2.setPlaceKey("placeKey");
        place2.setAddress("address");
        place2.setDetailDescription("detailDescription");
        place2.setShortDescription("shortDescription");
        place2.setMail("mail");
        place2.setPhoneNumber("phoneNumber");
        place2.setStatus("status");
        place2.setLocation("location");
        place2.setCancelPolicy("cancelPolicy");
        final Optional<Place> place1 = Optional.of(place2);
        when(mockPlaceRepository.findById(0L)).thenReturn(place1);

        // Configure CodeRepository.findByVisitorTypeIdLimitTo(...).
        final Code code1 = new Code();
        code1.setCode("code");
        final VisitorType visitorType1 = new VisitorType();
        visitorType1.setTypeName("typeName");
        visitorType1.setTypeKey("typeKey");
        visitorType1.setPrice(0);
        visitorType1.setBasicType(false);
        visitorType1.setStatus("status");
        final TicketType ticketType2 = new TicketType();
        ticketType2.setTypeName("typeName");
        ticketType2.setPlaceId(0L);
        ticketType2.setStatus("status");
        ticketType2.setVisitorType(new HashSet<>(Arrays.asList(new VisitorType())));
        final Game game1 = new Game();
        game1.setGameName("gameName");
        game1.setGameDescription("gameDescription");
        game1.setStatus("status");
        game1.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
        final Place place3 = new Place();
        place3.setName("name");
        place3.setPlaceKey("placeKey");
        place3.setAddress("address");
        place3.setDetailDescription("detailDescription");
        place3.setShortDescription("shortDescription");
        place3.setMail("mail");
        place3.setPhoneNumber("phoneNumber");
        place3.setStatus("status");
        place3.setLocation("location");
        place3.setCancelPolicy("cancelPolicy");
        game1.setPlace(place3);
        ticketType2.setGame(new HashSet<>(Arrays.asList(game1)));
        visitorType1.setTicketType(ticketType2);
        final OrderItem orderItem1 = new OrderItem();
        orderItem1.setQuantity(0);
        orderItem1.setVisitorType(new VisitorType());
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
        orderItem1.setOrder(order3);
        final Ticket ticket1 = new Ticket();
        ticket1.setCode("code");
        ticket1.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket1.setVisitorTypeId(0L);
        ticket1.setOrderItem(new OrderItem());
        orderItem1.setTicket(new HashSet<>(Arrays.asList(ticket1)));
        visitorType1.setOrderItem(new HashSet<>(Arrays.asList(orderItem1)));
        visitorType1.setCode(new HashSet<>(Arrays.asList(new Code())));
        code1.setVisitorType(visitorType1);
        final List<Code> codes = Arrays.asList(code1);
        when(mockCodeRepository.findByVisitorTypeIdLimitTo(0, new VisitorType())).thenReturn(codes);

        // Configure TicketRepository.saveAll(...).
        final Ticket ticket2 = new Ticket();
        ticket2.setCode("code");
        ticket2.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket2.setVisitorTypeId(0L);
        final OrderItem orderItem2 = new OrderItem();
        orderItem2.setQuantity(0);
        final VisitorType visitorType2 = new VisitorType();
        visitorType2.setTypeName("typeName");
        visitorType2.setTypeKey("typeKey");
        visitorType2.setPrice(0);
        visitorType2.setBasicType(false);
        visitorType2.setStatus("status");
        final TicketType ticketType3 = new TicketType();
        ticketType3.setTypeName("typeName");
        ticketType3.setPlaceId(0L);
        ticketType3.setStatus("status");
        ticketType3.setVisitorType(new HashSet<>(Arrays.asList(new VisitorType())));
        final Game game2 = new Game();
        game2.setGameName("gameName");
        game2.setGameDescription("gameDescription");
        game2.setStatus("status");
        game2.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
        final Place place4 = new Place();
        place4.setName("name");
        place4.setPlaceKey("placeKey");
        place4.setAddress("address");
        place4.setDetailDescription("detailDescription");
        place4.setShortDescription("shortDescription");
        place4.setMail("mail");
        place4.setPhoneNumber("phoneNumber");
        place4.setStatus("status");
        place4.setLocation("location");
        place4.setCancelPolicy("cancelPolicy");
        game2.setPlace(place4);
        ticketType3.setGame(new HashSet<>(Arrays.asList(game2)));
        visitorType2.setTicketType(ticketType3);
        visitorType2.setOrderItem(new HashSet<>(Arrays.asList(new OrderItem())));
        final Code code2 = new Code();
        code2.setCode("code");
        code2.setVisitorType(new VisitorType());
        visitorType2.setCode(new HashSet<>(Arrays.asList(code2)));
        orderItem2.setVisitorType(visitorType2);
        final Order order4 = new Order();
        order4.setTicketTypeId(0L);
        order4.setFirstName("firstName");
        order4.setLastName("lastName");
        order4.setMail("mail");
        order4.setPhoneNumber("phoneNumber");
        order4.setStatus("status");
        order4.setOrderCode("orderCode");
        order4.setTotalPayment(0);
        order4.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order4.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        orderItem2.setOrder(order4);
        orderItem2.setTicket(new HashSet<>(Arrays.asList(new Ticket())));
        ticket2.setOrderItem(orderItem2);
        final List<Ticket> tickets = Arrays.asList(ticket2);
        when(mockTicketRepository.saveAll(Arrays.asList(new Ticket()))).thenReturn(tickets);

        when(mockPdfPrinter.printPDF(Arrays.asList(new PrintRequest()))).thenThrow(IOException.class);

        // Configure OrderRepository.save(...).
        final Order order5 = new Order();
        order5.setTicketTypeId(0L);
        order5.setFirstName("firstName");
        order5.setLastName("lastName");
        order5.setMail("mail");
        order5.setPhoneNumber("phoneNumber");
        order5.setStatus("status");
        order5.setOrderCode("orderCode");
        order5.setTotalPayment(0);
        order5.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order5.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        when(mockOrderRepository.save(any(Order.class))).thenReturn(order5);

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
        assertThatThrownBy(() -> {
            orderServiceImplUnderTest.sendTicket(0L);
        }).isInstanceOf(IOException.class).hasMessageContaining("message");
        verify(mockCodeRepository).deleteAll(Arrays.asList(new Code()));
        verify(mockEmailSender).send(any(MimeMessage.class));
    }

    @Test
    public void testSendTicket_PdfPrinterThrowsDocumentException() throws Exception {
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
        visitorType.setTicketType(new TicketType());
        final OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(0);
        orderItem.setVisitorType(new VisitorType());
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
        orderItem.setOrder(order2);
        final Ticket ticket = new Ticket();
        ticket.setCode("code");
        ticket.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket.setVisitorTypeId(0L);
        ticket.setOrderItem(new OrderItem());
        orderItem.setTicket(new HashSet<>(Arrays.asList(ticket)));
        visitorType.setOrderItem(new HashSet<>(Arrays.asList(orderItem)));
        final Code code = new Code();
        code.setCode("code");
        code.setVisitorType(new VisitorType());
        visitorType.setCode(new HashSet<>(Arrays.asList(code)));
        ticketType1.setVisitorType(new HashSet<>(Arrays.asList(visitorType)));
        final Game game = new Game();
        game.setGameName("gameName");
        game.setGameDescription("gameDescription");
        game.setStatus("status");
        game.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
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
        game.setPlace(place);
        ticketType1.setGame(new HashSet<>(Arrays.asList(game)));
        final Optional<TicketType> ticketType = Optional.of(ticketType1);
        when(mockTicketTypeRepository.findById(0L)).thenReturn(ticketType);

        // Configure PlaceRepository.findById(...).
        final Place place2 = new Place();
        place2.setName("name");
        place2.setPlaceKey("placeKey");
        place2.setAddress("address");
        place2.setDetailDescription("detailDescription");
        place2.setShortDescription("shortDescription");
        place2.setMail("mail");
        place2.setPhoneNumber("phoneNumber");
        place2.setStatus("status");
        place2.setLocation("location");
        place2.setCancelPolicy("cancelPolicy");
        final Optional<Place> place1 = Optional.of(place2);
        when(mockPlaceRepository.findById(0L)).thenReturn(place1);

        // Configure CodeRepository.findByVisitorTypeIdLimitTo(...).
        final Code code1 = new Code();
        code1.setCode("code");
        final VisitorType visitorType1 = new VisitorType();
        visitorType1.setTypeName("typeName");
        visitorType1.setTypeKey("typeKey");
        visitorType1.setPrice(0);
        visitorType1.setBasicType(false);
        visitorType1.setStatus("status");
        final TicketType ticketType2 = new TicketType();
        ticketType2.setTypeName("typeName");
        ticketType2.setPlaceId(0L);
        ticketType2.setStatus("status");
        ticketType2.setVisitorType(new HashSet<>(Arrays.asList(new VisitorType())));
        final Game game1 = new Game();
        game1.setGameName("gameName");
        game1.setGameDescription("gameDescription");
        game1.setStatus("status");
        game1.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
        final Place place3 = new Place();
        place3.setName("name");
        place3.setPlaceKey("placeKey");
        place3.setAddress("address");
        place3.setDetailDescription("detailDescription");
        place3.setShortDescription("shortDescription");
        place3.setMail("mail");
        place3.setPhoneNumber("phoneNumber");
        place3.setStatus("status");
        place3.setLocation("location");
        place3.setCancelPolicy("cancelPolicy");
        game1.setPlace(place3);
        ticketType2.setGame(new HashSet<>(Arrays.asList(game1)));
        visitorType1.setTicketType(ticketType2);
        final OrderItem orderItem1 = new OrderItem();
        orderItem1.setQuantity(0);
        orderItem1.setVisitorType(new VisitorType());
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
        orderItem1.setOrder(order3);
        final Ticket ticket1 = new Ticket();
        ticket1.setCode("code");
        ticket1.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket1.setVisitorTypeId(0L);
        ticket1.setOrderItem(new OrderItem());
        orderItem1.setTicket(new HashSet<>(Arrays.asList(ticket1)));
        visitorType1.setOrderItem(new HashSet<>(Arrays.asList(orderItem1)));
        visitorType1.setCode(new HashSet<>(Arrays.asList(new Code())));
        code1.setVisitorType(visitorType1);
        final List<Code> codes = Arrays.asList(code1);
        when(mockCodeRepository.findByVisitorTypeIdLimitTo(0, new VisitorType())).thenReturn(codes);

        // Configure TicketRepository.saveAll(...).
        final Ticket ticket2 = new Ticket();
        ticket2.setCode("code");
        ticket2.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket2.setVisitorTypeId(0L);
        final OrderItem orderItem2 = new OrderItem();
        orderItem2.setQuantity(0);
        final VisitorType visitorType2 = new VisitorType();
        visitorType2.setTypeName("typeName");
        visitorType2.setTypeKey("typeKey");
        visitorType2.setPrice(0);
        visitorType2.setBasicType(false);
        visitorType2.setStatus("status");
        final TicketType ticketType3 = new TicketType();
        ticketType3.setTypeName("typeName");
        ticketType3.setPlaceId(0L);
        ticketType3.setStatus("status");
        ticketType3.setVisitorType(new HashSet<>(Arrays.asList(new VisitorType())));
        final Game game2 = new Game();
        game2.setGameName("gameName");
        game2.setGameDescription("gameDescription");
        game2.setStatus("status");
        game2.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
        final Place place4 = new Place();
        place4.setName("name");
        place4.setPlaceKey("placeKey");
        place4.setAddress("address");
        place4.setDetailDescription("detailDescription");
        place4.setShortDescription("shortDescription");
        place4.setMail("mail");
        place4.setPhoneNumber("phoneNumber");
        place4.setStatus("status");
        place4.setLocation("location");
        place4.setCancelPolicy("cancelPolicy");
        game2.setPlace(place4);
        ticketType3.setGame(new HashSet<>(Arrays.asList(game2)));
        visitorType2.setTicketType(ticketType3);
        visitorType2.setOrderItem(new HashSet<>(Arrays.asList(new OrderItem())));
        final Code code2 = new Code();
        code2.setCode("code");
        code2.setVisitorType(new VisitorType());
        visitorType2.setCode(new HashSet<>(Arrays.asList(code2)));
        orderItem2.setVisitorType(visitorType2);
        final Order order4 = new Order();
        order4.setTicketTypeId(0L);
        order4.setFirstName("firstName");
        order4.setLastName("lastName");
        order4.setMail("mail");
        order4.setPhoneNumber("phoneNumber");
        order4.setStatus("status");
        order4.setOrderCode("orderCode");
        order4.setTotalPayment(0);
        order4.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order4.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        orderItem2.setOrder(order4);
        orderItem2.setTicket(new HashSet<>(Arrays.asList(new Ticket())));
        ticket2.setOrderItem(orderItem2);
        final List<Ticket> tickets = Arrays.asList(ticket2);
        when(mockTicketRepository.saveAll(Arrays.asList(new Ticket()))).thenReturn(tickets);

        when(mockPdfPrinter.printPDF(Arrays.asList(new PrintRequest()))).thenThrow(DocumentException.class);

        // Configure OrderRepository.save(...).
        final Order order5 = new Order();
        order5.setTicketTypeId(0L);
        order5.setFirstName("firstName");
        order5.setLastName("lastName");
        order5.setMail("mail");
        order5.setPhoneNumber("phoneNumber");
        order5.setStatus("status");
        order5.setOrderCode("orderCode");
        order5.setTotalPayment(0);
        order5.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order5.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        when(mockOrderRepository.save(any(Order.class))).thenReturn(order5);

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
        assertThatThrownBy(() -> {
            orderServiceImplUnderTest.sendTicket(0L);
        }).isInstanceOf(DocumentException.class).hasMessageContaining("message");
        verify(mockCodeRepository).deleteAll(Arrays.asList(new Code()));
        verify(mockEmailSender).send(any(MimeMessage.class));
    }

    @Test
    public void testSendTicket_PdfPrinterThrowsURISyntaxException() throws Exception {
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
        visitorType.setTicketType(new TicketType());
        final OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(0);
        orderItem.setVisitorType(new VisitorType());
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
        orderItem.setOrder(order2);
        final Ticket ticket = new Ticket();
        ticket.setCode("code");
        ticket.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket.setVisitorTypeId(0L);
        ticket.setOrderItem(new OrderItem());
        orderItem.setTicket(new HashSet<>(Arrays.asList(ticket)));
        visitorType.setOrderItem(new HashSet<>(Arrays.asList(orderItem)));
        final Code code = new Code();
        code.setCode("code");
        code.setVisitorType(new VisitorType());
        visitorType.setCode(new HashSet<>(Arrays.asList(code)));
        ticketType1.setVisitorType(new HashSet<>(Arrays.asList(visitorType)));
        final Game game = new Game();
        game.setGameName("gameName");
        game.setGameDescription("gameDescription");
        game.setStatus("status");
        game.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
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
        game.setPlace(place);
        ticketType1.setGame(new HashSet<>(Arrays.asList(game)));
        final Optional<TicketType> ticketType = Optional.of(ticketType1);
        when(mockTicketTypeRepository.findById(0L)).thenReturn(ticketType);

        // Configure PlaceRepository.findById(...).
        final Place place2 = new Place();
        place2.setName("name");
        place2.setPlaceKey("placeKey");
        place2.setAddress("address");
        place2.setDetailDescription("detailDescription");
        place2.setShortDescription("shortDescription");
        place2.setMail("mail");
        place2.setPhoneNumber("phoneNumber");
        place2.setStatus("status");
        place2.setLocation("location");
        place2.setCancelPolicy("cancelPolicy");
        final Optional<Place> place1 = Optional.of(place2);
        when(mockPlaceRepository.findById(0L)).thenReturn(place1);

        // Configure CodeRepository.findByVisitorTypeIdLimitTo(...).
        final Code code1 = new Code();
        code1.setCode("code");
        final VisitorType visitorType1 = new VisitorType();
        visitorType1.setTypeName("typeName");
        visitorType1.setTypeKey("typeKey");
        visitorType1.setPrice(0);
        visitorType1.setBasicType(false);
        visitorType1.setStatus("status");
        final TicketType ticketType2 = new TicketType();
        ticketType2.setTypeName("typeName");
        ticketType2.setPlaceId(0L);
        ticketType2.setStatus("status");
        ticketType2.setVisitorType(new HashSet<>(Arrays.asList(new VisitorType())));
        final Game game1 = new Game();
        game1.setGameName("gameName");
        game1.setGameDescription("gameDescription");
        game1.setStatus("status");
        game1.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
        final Place place3 = new Place();
        place3.setName("name");
        place3.setPlaceKey("placeKey");
        place3.setAddress("address");
        place3.setDetailDescription("detailDescription");
        place3.setShortDescription("shortDescription");
        place3.setMail("mail");
        place3.setPhoneNumber("phoneNumber");
        place3.setStatus("status");
        place3.setLocation("location");
        place3.setCancelPolicy("cancelPolicy");
        game1.setPlace(place3);
        ticketType2.setGame(new HashSet<>(Arrays.asList(game1)));
        visitorType1.setTicketType(ticketType2);
        final OrderItem orderItem1 = new OrderItem();
        orderItem1.setQuantity(0);
        orderItem1.setVisitorType(new VisitorType());
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
        orderItem1.setOrder(order3);
        final Ticket ticket1 = new Ticket();
        ticket1.setCode("code");
        ticket1.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket1.setVisitorTypeId(0L);
        ticket1.setOrderItem(new OrderItem());
        orderItem1.setTicket(new HashSet<>(Arrays.asList(ticket1)));
        visitorType1.setOrderItem(new HashSet<>(Arrays.asList(orderItem1)));
        visitorType1.setCode(new HashSet<>(Arrays.asList(new Code())));
        code1.setVisitorType(visitorType1);
        final List<Code> codes = Arrays.asList(code1);
        when(mockCodeRepository.findByVisitorTypeIdLimitTo(0, new VisitorType())).thenReturn(codes);

        // Configure TicketRepository.saveAll(...).
        final Ticket ticket2 = new Ticket();
        ticket2.setCode("code");
        ticket2.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket2.setVisitorTypeId(0L);
        final OrderItem orderItem2 = new OrderItem();
        orderItem2.setQuantity(0);
        final VisitorType visitorType2 = new VisitorType();
        visitorType2.setTypeName("typeName");
        visitorType2.setTypeKey("typeKey");
        visitorType2.setPrice(0);
        visitorType2.setBasicType(false);
        visitorType2.setStatus("status");
        final TicketType ticketType3 = new TicketType();
        ticketType3.setTypeName("typeName");
        ticketType3.setPlaceId(0L);
        ticketType3.setStatus("status");
        ticketType3.setVisitorType(new HashSet<>(Arrays.asList(new VisitorType())));
        final Game game2 = new Game();
        game2.setGameName("gameName");
        game2.setGameDescription("gameDescription");
        game2.setStatus("status");
        game2.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
        final Place place4 = new Place();
        place4.setName("name");
        place4.setPlaceKey("placeKey");
        place4.setAddress("address");
        place4.setDetailDescription("detailDescription");
        place4.setShortDescription("shortDescription");
        place4.setMail("mail");
        place4.setPhoneNumber("phoneNumber");
        place4.setStatus("status");
        place4.setLocation("location");
        place4.setCancelPolicy("cancelPolicy");
        game2.setPlace(place4);
        ticketType3.setGame(new HashSet<>(Arrays.asList(game2)));
        visitorType2.setTicketType(ticketType3);
        visitorType2.setOrderItem(new HashSet<>(Arrays.asList(new OrderItem())));
        final Code code2 = new Code();
        code2.setCode("code");
        code2.setVisitorType(new VisitorType());
        visitorType2.setCode(new HashSet<>(Arrays.asList(code2)));
        orderItem2.setVisitorType(visitorType2);
        final Order order4 = new Order();
        order4.setTicketTypeId(0L);
        order4.setFirstName("firstName");
        order4.setLastName("lastName");
        order4.setMail("mail");
        order4.setPhoneNumber("phoneNumber");
        order4.setStatus("status");
        order4.setOrderCode("orderCode");
        order4.setTotalPayment(0);
        order4.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order4.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        orderItem2.setOrder(order4);
        orderItem2.setTicket(new HashSet<>(Arrays.asList(new Ticket())));
        ticket2.setOrderItem(orderItem2);
        final List<Ticket> tickets = Arrays.asList(ticket2);
        when(mockTicketRepository.saveAll(Arrays.asList(new Ticket()))).thenReturn(tickets);

        when(mockPdfPrinter.printPDF(Arrays.asList(new PrintRequest()))).thenThrow(URISyntaxException.class);

        // Configure OrderRepository.save(...).
        final Order order5 = new Order();
        order5.setTicketTypeId(0L);
        order5.setFirstName("firstName");
        order5.setLastName("lastName");
        order5.setMail("mail");
        order5.setPhoneNumber("phoneNumber");
        order5.setStatus("status");
        order5.setOrderCode("orderCode");
        order5.setTotalPayment(0);
        order5.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order5.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        when(mockOrderRepository.save(any(Order.class))).thenReturn(order5);

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
        assertThatThrownBy(() -> {
            orderServiceImplUnderTest.sendTicket(0L);
        }).isInstanceOf(URISyntaxException.class).hasMessageContaining("message");
        verify(mockCodeRepository).deleteAll(Arrays.asList(new Code()));
        verify(mockEmailSender).send(any(MimeMessage.class));
    }

    @Test
    public void testSendTicket_JavaMailSenderThrowsMailException() throws Exception {
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
        visitorType.setTicketType(new TicketType());
        final OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(0);
        orderItem.setVisitorType(new VisitorType());
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
        orderItem.setOrder(order2);
        final Ticket ticket = new Ticket();
        ticket.setCode("code");
        ticket.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket.setVisitorTypeId(0L);
        ticket.setOrderItem(new OrderItem());
        orderItem.setTicket(new HashSet<>(Arrays.asList(ticket)));
        visitorType.setOrderItem(new HashSet<>(Arrays.asList(orderItem)));
        final Code code = new Code();
        code.setCode("code");
        code.setVisitorType(new VisitorType());
        visitorType.setCode(new HashSet<>(Arrays.asList(code)));
        ticketType1.setVisitorType(new HashSet<>(Arrays.asList(visitorType)));
        final Game game = new Game();
        game.setGameName("gameName");
        game.setGameDescription("gameDescription");
        game.setStatus("status");
        game.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
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
        game.setPlace(place);
        ticketType1.setGame(new HashSet<>(Arrays.asList(game)));
        final Optional<TicketType> ticketType = Optional.of(ticketType1);
        when(mockTicketTypeRepository.findById(0L)).thenReturn(ticketType);

        // Configure PlaceRepository.findById(...).
        final Place place2 = new Place();
        place2.setName("name");
        place2.setPlaceKey("placeKey");
        place2.setAddress("address");
        place2.setDetailDescription("detailDescription");
        place2.setShortDescription("shortDescription");
        place2.setMail("mail");
        place2.setPhoneNumber("phoneNumber");
        place2.setStatus("status");
        place2.setLocation("location");
        place2.setCancelPolicy("cancelPolicy");
        final Optional<Place> place1 = Optional.of(place2);
        when(mockPlaceRepository.findById(0L)).thenReturn(place1);

        // Configure CodeRepository.findByVisitorTypeIdLimitTo(...).
        final Code code1 = new Code();
        code1.setCode("code");
        final VisitorType visitorType1 = new VisitorType();
        visitorType1.setTypeName("typeName");
        visitorType1.setTypeKey("typeKey");
        visitorType1.setPrice(0);
        visitorType1.setBasicType(false);
        visitorType1.setStatus("status");
        final TicketType ticketType2 = new TicketType();
        ticketType2.setTypeName("typeName");
        ticketType2.setPlaceId(0L);
        ticketType2.setStatus("status");
        ticketType2.setVisitorType(new HashSet<>(Arrays.asList(new VisitorType())));
        final Game game1 = new Game();
        game1.setGameName("gameName");
        game1.setGameDescription("gameDescription");
        game1.setStatus("status");
        game1.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
        final Place place3 = new Place();
        place3.setName("name");
        place3.setPlaceKey("placeKey");
        place3.setAddress("address");
        place3.setDetailDescription("detailDescription");
        place3.setShortDescription("shortDescription");
        place3.setMail("mail");
        place3.setPhoneNumber("phoneNumber");
        place3.setStatus("status");
        place3.setLocation("location");
        place3.setCancelPolicy("cancelPolicy");
        game1.setPlace(place3);
        ticketType2.setGame(new HashSet<>(Arrays.asList(game1)));
        visitorType1.setTicketType(ticketType2);
        final OrderItem orderItem1 = new OrderItem();
        orderItem1.setQuantity(0);
        orderItem1.setVisitorType(new VisitorType());
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
        orderItem1.setOrder(order3);
        final Ticket ticket1 = new Ticket();
        ticket1.setCode("code");
        ticket1.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket1.setVisitorTypeId(0L);
        ticket1.setOrderItem(new OrderItem());
        orderItem1.setTicket(new HashSet<>(Arrays.asList(ticket1)));
        visitorType1.setOrderItem(new HashSet<>(Arrays.asList(orderItem1)));
        visitorType1.setCode(new HashSet<>(Arrays.asList(new Code())));
        code1.setVisitorType(visitorType1);
        final List<Code> codes = Arrays.asList(code1);
        when(mockCodeRepository.findByVisitorTypeIdLimitTo(0, new VisitorType())).thenReturn(codes);

        // Configure TicketRepository.saveAll(...).
        final Ticket ticket2 = new Ticket();
        ticket2.setCode("code");
        ticket2.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket2.setVisitorTypeId(0L);
        final OrderItem orderItem2 = new OrderItem();
        orderItem2.setQuantity(0);
        final VisitorType visitorType2 = new VisitorType();
        visitorType2.setTypeName("typeName");
        visitorType2.setTypeKey("typeKey");
        visitorType2.setPrice(0);
        visitorType2.setBasicType(false);
        visitorType2.setStatus("status");
        final TicketType ticketType3 = new TicketType();
        ticketType3.setTypeName("typeName");
        ticketType3.setPlaceId(0L);
        ticketType3.setStatus("status");
        ticketType3.setVisitorType(new HashSet<>(Arrays.asList(new VisitorType())));
        final Game game2 = new Game();
        game2.setGameName("gameName");
        game2.setGameDescription("gameDescription");
        game2.setStatus("status");
        game2.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
        final Place place4 = new Place();
        place4.setName("name");
        place4.setPlaceKey("placeKey");
        place4.setAddress("address");
        place4.setDetailDescription("detailDescription");
        place4.setShortDescription("shortDescription");
        place4.setMail("mail");
        place4.setPhoneNumber("phoneNumber");
        place4.setStatus("status");
        place4.setLocation("location");
        place4.setCancelPolicy("cancelPolicy");
        game2.setPlace(place4);
        ticketType3.setGame(new HashSet<>(Arrays.asList(game2)));
        visitorType2.setTicketType(ticketType3);
        visitorType2.setOrderItem(new HashSet<>(Arrays.asList(new OrderItem())));
        final Code code2 = new Code();
        code2.setCode("code");
        code2.setVisitorType(new VisitorType());
        visitorType2.setCode(new HashSet<>(Arrays.asList(code2)));
        orderItem2.setVisitorType(visitorType2);
        final Order order4 = new Order();
        order4.setTicketTypeId(0L);
        order4.setFirstName("firstName");
        order4.setLastName("lastName");
        order4.setMail("mail");
        order4.setPhoneNumber("phoneNumber");
        order4.setStatus("status");
        order4.setOrderCode("orderCode");
        order4.setTotalPayment(0);
        order4.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order4.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        orderItem2.setOrder(order4);
        orderItem2.setTicket(new HashSet<>(Arrays.asList(new Ticket())));
        ticket2.setOrderItem(orderItem2);
        final List<Ticket> tickets = Arrays.asList(ticket2);
        when(mockTicketRepository.saveAll(Arrays.asList(new Ticket()))).thenReturn(tickets);

        when(mockPdfPrinter.printPDF(Arrays.asList(new PrintRequest()))).thenReturn(new File("filename.txt"));

        // Configure OrderRepository.save(...).
        final Order order5 = new Order();
        order5.setTicketTypeId(0L);
        order5.setFirstName("firstName");
        order5.setLastName("lastName");
        order5.setMail("mail");
        order5.setPhoneNumber("phoneNumber");
        order5.setStatus("status");
        order5.setOrderCode("orderCode");
        order5.setTotalPayment(0);
        order5.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order5.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        when(mockOrderRepository.save(any(Order.class))).thenReturn(order5);

        // Configure JavaMailSender.createMimeMessage(...).
        final MimeMessage mimeMessage = new MimeMessage(Session.getInstance(new Properties()));
        when(mockEmailSender.createMimeMessage()).thenReturn(mimeMessage);

        doThrow(MailException.class).when(mockEmailSender).send(any(MimeMessage.class));

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
        final ResponseEntity<?> result = orderServiceImplUnderTest.sendTicket(0L);

        // Verify the results
        verify(mockCodeRepository).deleteAll(Arrays.asList(new Code()));
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
        order1.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
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
        visitorType.setTicketType(new TicketType());
        final OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(0);
        orderItem.setVisitorType(new VisitorType());
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
        orderItem.setOrder(order2);
        final Ticket ticket = new Ticket();
        ticket.setCode("code");
        ticket.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket.setVisitorTypeId(0L);
        ticket.setOrderItem(new OrderItem());
        orderItem.setTicket(new HashSet<>(Arrays.asList(ticket)));
        visitorType.setOrderItem(new HashSet<>(Arrays.asList(orderItem)));
        final Code code = new Code();
        code.setCode("code");
        code.setVisitorType(new VisitorType());
        visitorType.setCode(new HashSet<>(Arrays.asList(code)));
        ticketType1.setVisitorType(new HashSet<>(Arrays.asList(visitorType)));
        final Game game = new Game();
        game.setGameName("gameName");
        game.setGameDescription("gameDescription");
        game.setStatus("status");
        game.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
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
        game.setPlace(place);
        ticketType1.setGame(new HashSet<>(Arrays.asList(game)));
        final Optional<TicketType> ticketType = Optional.of(ticketType1);
        when(mockTicketTypeRepository.findById(0L)).thenReturn(ticketType);

        // Configure PlaceRepository.findById(...).
        final Place place2 = new Place();
        place2.setName("name");
        place2.setPlaceKey("placeKey");
        place2.setAddress("address");
        place2.setDetailDescription("detailDescription");
        place2.setShortDescription("shortDescription");
        place2.setMail("mail");
        place2.setPhoneNumber("phoneNumber");
        place2.setStatus("status");
        place2.setLocation("location");
        place2.setCancelPolicy("cancelPolicy");
        final Optional<Place> place1 = Optional.of(place2);
        when(mockPlaceRepository.findById(0L)).thenReturn(place1);

        // Configure TicketRepository.findAllByOrderItem(...).
        final Ticket ticket1 = new Ticket();
        ticket1.setCode("code");
        ticket1.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket1.setVisitorTypeId(0L);
        final OrderItem orderItem1 = new OrderItem();
        orderItem1.setQuantity(0);
        final VisitorType visitorType1 = new VisitorType();
        visitorType1.setTypeName("typeName");
        visitorType1.setTypeKey("typeKey");
        visitorType1.setPrice(0);
        visitorType1.setBasicType(false);
        visitorType1.setStatus("status");
        final TicketType ticketType2 = new TicketType();
        ticketType2.setTypeName("typeName");
        ticketType2.setPlaceId(0L);
        ticketType2.setStatus("status");
        ticketType2.setVisitorType(new HashSet<>(Arrays.asList(new VisitorType())));
        final Game game1 = new Game();
        game1.setGameName("gameName");
        game1.setGameDescription("gameDescription");
        game1.setStatus("status");
        game1.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
        final Place place3 = new Place();
        place3.setName("name");
        place3.setPlaceKey("placeKey");
        place3.setAddress("address");
        place3.setDetailDescription("detailDescription");
        place3.setShortDescription("shortDescription");
        place3.setMail("mail");
        place3.setPhoneNumber("phoneNumber");
        place3.setStatus("status");
        place3.setLocation("location");
        place3.setCancelPolicy("cancelPolicy");
        game1.setPlace(place3);
        ticketType2.setGame(new HashSet<>(Arrays.asList(game1)));
        visitorType1.setTicketType(ticketType2);
        visitorType1.setOrderItem(new HashSet<>(Arrays.asList(new OrderItem())));
        final Code code1 = new Code();
        code1.setCode("code");
        code1.setVisitorType(new VisitorType());
        visitorType1.setCode(new HashSet<>(Arrays.asList(code1)));
        orderItem1.setVisitorType(visitorType1);
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
        orderItem1.setOrder(order3);
        orderItem1.setTicket(new HashSet<>(Arrays.asList(new Ticket())));
        ticket1.setOrderItem(orderItem1);
        final List<Ticket> tickets = Arrays.asList(ticket1);
        when(mockTicketRepository.findAllByOrderItem(any(OrderItem.class))).thenReturn(tickets);

        when(mockPdfPrinter.printPDF(Arrays.asList(new PrintRequest()))).thenReturn(new File("filename.txt"));

        // Configure OrderRepository.save(...).
        final Order order4 = new Order();
        order4.setTicketTypeId(0L);
        order4.setFirstName("firstName");
        order4.setLastName("lastName");
        order4.setMail("mail");
        order4.setPhoneNumber("phoneNumber");
        order4.setStatus("status");
        order4.setOrderCode("orderCode");
        order4.setTotalPayment(0);
        order4.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order4.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        when(mockOrderRepository.save(any(Order.class))).thenReturn(order4);

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
    public void testResendTicket_PdfPrinterThrowsIOException() throws Exception {
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
        visitorType.setTicketType(new TicketType());
        final OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(0);
        orderItem.setVisitorType(new VisitorType());
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
        orderItem.setOrder(order2);
        final Ticket ticket = new Ticket();
        ticket.setCode("code");
        ticket.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket.setVisitorTypeId(0L);
        ticket.setOrderItem(new OrderItem());
        orderItem.setTicket(new HashSet<>(Arrays.asList(ticket)));
        visitorType.setOrderItem(new HashSet<>(Arrays.asList(orderItem)));
        final Code code = new Code();
        code.setCode("code");
        code.setVisitorType(new VisitorType());
        visitorType.setCode(new HashSet<>(Arrays.asList(code)));
        ticketType1.setVisitorType(new HashSet<>(Arrays.asList(visitorType)));
        final Game game = new Game();
        game.setGameName("gameName");
        game.setGameDescription("gameDescription");
        game.setStatus("status");
        game.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
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
        game.setPlace(place);
        ticketType1.setGame(new HashSet<>(Arrays.asList(game)));
        final Optional<TicketType> ticketType = Optional.of(ticketType1);
        when(mockTicketTypeRepository.findById(0L)).thenReturn(ticketType);

        // Configure PlaceRepository.findById(...).
        final Place place2 = new Place();
        place2.setName("name");
        place2.setPlaceKey("placeKey");
        place2.setAddress("address");
        place2.setDetailDescription("detailDescription");
        place2.setShortDescription("shortDescription");
        place2.setMail("mail");
        place2.setPhoneNumber("phoneNumber");
        place2.setStatus("status");
        place2.setLocation("location");
        place2.setCancelPolicy("cancelPolicy");
        final Optional<Place> place1 = Optional.of(place2);
        when(mockPlaceRepository.findById(0L)).thenReturn(place1);

        // Configure TicketRepository.findAllByOrderItem(...).
        final Ticket ticket1 = new Ticket();
        ticket1.setCode("code");
        ticket1.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket1.setVisitorTypeId(0L);
        final OrderItem orderItem1 = new OrderItem();
        orderItem1.setQuantity(0);
        final VisitorType visitorType1 = new VisitorType();
        visitorType1.setTypeName("typeName");
        visitorType1.setTypeKey("typeKey");
        visitorType1.setPrice(0);
        visitorType1.setBasicType(false);
        visitorType1.setStatus("status");
        final TicketType ticketType2 = new TicketType();
        ticketType2.setTypeName("typeName");
        ticketType2.setPlaceId(0L);
        ticketType2.setStatus("status");
        ticketType2.setVisitorType(new HashSet<>(Arrays.asList(new VisitorType())));
        final Game game1 = new Game();
        game1.setGameName("gameName");
        game1.setGameDescription("gameDescription");
        game1.setStatus("status");
        game1.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
        final Place place3 = new Place();
        place3.setName("name");
        place3.setPlaceKey("placeKey");
        place3.setAddress("address");
        place3.setDetailDescription("detailDescription");
        place3.setShortDescription("shortDescription");
        place3.setMail("mail");
        place3.setPhoneNumber("phoneNumber");
        place3.setStatus("status");
        place3.setLocation("location");
        place3.setCancelPolicy("cancelPolicy");
        game1.setPlace(place3);
        ticketType2.setGame(new HashSet<>(Arrays.asList(game1)));
        visitorType1.setTicketType(ticketType2);
        visitorType1.setOrderItem(new HashSet<>(Arrays.asList(new OrderItem())));
        final Code code1 = new Code();
        code1.setCode("code");
        code1.setVisitorType(new VisitorType());
        visitorType1.setCode(new HashSet<>(Arrays.asList(code1)));
        orderItem1.setVisitorType(visitorType1);
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
        orderItem1.setOrder(order3);
        orderItem1.setTicket(new HashSet<>(Arrays.asList(new Ticket())));
        ticket1.setOrderItem(orderItem1);
        final List<Ticket> tickets = Arrays.asList(ticket1);
        when(mockTicketRepository.findAllByOrderItem(any(OrderItem.class))).thenReturn(tickets);

        when(mockPdfPrinter.printPDF(Arrays.asList(new PrintRequest()))).thenThrow(IOException.class);

        // Configure OrderRepository.save(...).
        final Order order4 = new Order();
        order4.setTicketTypeId(0L);
        order4.setFirstName("firstName");
        order4.setLastName("lastName");
        order4.setMail("mail");
        order4.setPhoneNumber("phoneNumber");
        order4.setStatus("status");
        order4.setOrderCode("orderCode");
        order4.setTotalPayment(0);
        order4.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order4.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        when(mockOrderRepository.save(any(Order.class))).thenReturn(order4);

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
        assertThatThrownBy(() -> {
            orderServiceImplUnderTest.resendTicket(0L);
        }).isInstanceOf(IOException.class).hasMessageContaining("message");
        verify(mockEmailSender).send(any(MimeMessage.class));
    }

    @Test
    public void testResendTicket_PdfPrinterThrowsDocumentException() throws Exception {
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
        visitorType.setTicketType(new TicketType());
        final OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(0);
        orderItem.setVisitorType(new VisitorType());
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
        orderItem.setOrder(order2);
        final Ticket ticket = new Ticket();
        ticket.setCode("code");
        ticket.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket.setVisitorTypeId(0L);
        ticket.setOrderItem(new OrderItem());
        orderItem.setTicket(new HashSet<>(Arrays.asList(ticket)));
        visitorType.setOrderItem(new HashSet<>(Arrays.asList(orderItem)));
        final Code code = new Code();
        code.setCode("code");
        code.setVisitorType(new VisitorType());
        visitorType.setCode(new HashSet<>(Arrays.asList(code)));
        ticketType1.setVisitorType(new HashSet<>(Arrays.asList(visitorType)));
        final Game game = new Game();
        game.setGameName("gameName");
        game.setGameDescription("gameDescription");
        game.setStatus("status");
        game.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
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
        game.setPlace(place);
        ticketType1.setGame(new HashSet<>(Arrays.asList(game)));
        final Optional<TicketType> ticketType = Optional.of(ticketType1);
        when(mockTicketTypeRepository.findById(0L)).thenReturn(ticketType);

        // Configure PlaceRepository.findById(...).
        final Place place2 = new Place();
        place2.setName("name");
        place2.setPlaceKey("placeKey");
        place2.setAddress("address");
        place2.setDetailDescription("detailDescription");
        place2.setShortDescription("shortDescription");
        place2.setMail("mail");
        place2.setPhoneNumber("phoneNumber");
        place2.setStatus("status");
        place2.setLocation("location");
        place2.setCancelPolicy("cancelPolicy");
        final Optional<Place> place1 = Optional.of(place2);
        when(mockPlaceRepository.findById(0L)).thenReturn(place1);

        // Configure TicketRepository.findAllByOrderItem(...).
        final Ticket ticket1 = new Ticket();
        ticket1.setCode("code");
        ticket1.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket1.setVisitorTypeId(0L);
        final OrderItem orderItem1 = new OrderItem();
        orderItem1.setQuantity(0);
        final VisitorType visitorType1 = new VisitorType();
        visitorType1.setTypeName("typeName");
        visitorType1.setTypeKey("typeKey");
        visitorType1.setPrice(0);
        visitorType1.setBasicType(false);
        visitorType1.setStatus("status");
        final TicketType ticketType2 = new TicketType();
        ticketType2.setTypeName("typeName");
        ticketType2.setPlaceId(0L);
        ticketType2.setStatus("status");
        ticketType2.setVisitorType(new HashSet<>(Arrays.asList(new VisitorType())));
        final Game game1 = new Game();
        game1.setGameName("gameName");
        game1.setGameDescription("gameDescription");
        game1.setStatus("status");
        game1.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
        final Place place3 = new Place();
        place3.setName("name");
        place3.setPlaceKey("placeKey");
        place3.setAddress("address");
        place3.setDetailDescription("detailDescription");
        place3.setShortDescription("shortDescription");
        place3.setMail("mail");
        place3.setPhoneNumber("phoneNumber");
        place3.setStatus("status");
        place3.setLocation("location");
        place3.setCancelPolicy("cancelPolicy");
        game1.setPlace(place3);
        ticketType2.setGame(new HashSet<>(Arrays.asList(game1)));
        visitorType1.setTicketType(ticketType2);
        visitorType1.setOrderItem(new HashSet<>(Arrays.asList(new OrderItem())));
        final Code code1 = new Code();
        code1.setCode("code");
        code1.setVisitorType(new VisitorType());
        visitorType1.setCode(new HashSet<>(Arrays.asList(code1)));
        orderItem1.setVisitorType(visitorType1);
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
        orderItem1.setOrder(order3);
        orderItem1.setTicket(new HashSet<>(Arrays.asList(new Ticket())));
        ticket1.setOrderItem(orderItem1);
        final List<Ticket> tickets = Arrays.asList(ticket1);
        when(mockTicketRepository.findAllByOrderItem(any(OrderItem.class))).thenReturn(tickets);

        when(mockPdfPrinter.printPDF(Arrays.asList(new PrintRequest()))).thenThrow(DocumentException.class);

        // Configure OrderRepository.save(...).
        final Order order4 = new Order();
        order4.setTicketTypeId(0L);
        order4.setFirstName("firstName");
        order4.setLastName("lastName");
        order4.setMail("mail");
        order4.setPhoneNumber("phoneNumber");
        order4.setStatus("status");
        order4.setOrderCode("orderCode");
        order4.setTotalPayment(0);
        order4.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order4.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        when(mockOrderRepository.save(any(Order.class))).thenReturn(order4);

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
        assertThatThrownBy(() -> {
            orderServiceImplUnderTest.resendTicket(0L);
        }).isInstanceOf(DocumentException.class).hasMessageContaining("message");
        verify(mockEmailSender).send(any(MimeMessage.class));
    }

    @Test
    public void testResendTicket_PdfPrinterThrowsURISyntaxException() throws Exception {
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
        visitorType.setTicketType(new TicketType());
        final OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(0);
        orderItem.setVisitorType(new VisitorType());
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
        orderItem.setOrder(order2);
        final Ticket ticket = new Ticket();
        ticket.setCode("code");
        ticket.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket.setVisitorTypeId(0L);
        ticket.setOrderItem(new OrderItem());
        orderItem.setTicket(new HashSet<>(Arrays.asList(ticket)));
        visitorType.setOrderItem(new HashSet<>(Arrays.asList(orderItem)));
        final Code code = new Code();
        code.setCode("code");
        code.setVisitorType(new VisitorType());
        visitorType.setCode(new HashSet<>(Arrays.asList(code)));
        ticketType1.setVisitorType(new HashSet<>(Arrays.asList(visitorType)));
        final Game game = new Game();
        game.setGameName("gameName");
        game.setGameDescription("gameDescription");
        game.setStatus("status");
        game.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
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
        game.setPlace(place);
        ticketType1.setGame(new HashSet<>(Arrays.asList(game)));
        final Optional<TicketType> ticketType = Optional.of(ticketType1);
        when(mockTicketTypeRepository.findById(0L)).thenReturn(ticketType);

        // Configure PlaceRepository.findById(...).
        final Place place2 = new Place();
        place2.setName("name");
        place2.setPlaceKey("placeKey");
        place2.setAddress("address");
        place2.setDetailDescription("detailDescription");
        place2.setShortDescription("shortDescription");
        place2.setMail("mail");
        place2.setPhoneNumber("phoneNumber");
        place2.setStatus("status");
        place2.setLocation("location");
        place2.setCancelPolicy("cancelPolicy");
        final Optional<Place> place1 = Optional.of(place2);
        when(mockPlaceRepository.findById(0L)).thenReturn(place1);

        // Configure TicketRepository.findAllByOrderItem(...).
        final Ticket ticket1 = new Ticket();
        ticket1.setCode("code");
        ticket1.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket1.setVisitorTypeId(0L);
        final OrderItem orderItem1 = new OrderItem();
        orderItem1.setQuantity(0);
        final VisitorType visitorType1 = new VisitorType();
        visitorType1.setTypeName("typeName");
        visitorType1.setTypeKey("typeKey");
        visitorType1.setPrice(0);
        visitorType1.setBasicType(false);
        visitorType1.setStatus("status");
        final TicketType ticketType2 = new TicketType();
        ticketType2.setTypeName("typeName");
        ticketType2.setPlaceId(0L);
        ticketType2.setStatus("status");
        ticketType2.setVisitorType(new HashSet<>(Arrays.asList(new VisitorType())));
        final Game game1 = new Game();
        game1.setGameName("gameName");
        game1.setGameDescription("gameDescription");
        game1.setStatus("status");
        game1.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
        final Place place3 = new Place();
        place3.setName("name");
        place3.setPlaceKey("placeKey");
        place3.setAddress("address");
        place3.setDetailDescription("detailDescription");
        place3.setShortDescription("shortDescription");
        place3.setMail("mail");
        place3.setPhoneNumber("phoneNumber");
        place3.setStatus("status");
        place3.setLocation("location");
        place3.setCancelPolicy("cancelPolicy");
        game1.setPlace(place3);
        ticketType2.setGame(new HashSet<>(Arrays.asList(game1)));
        visitorType1.setTicketType(ticketType2);
        visitorType1.setOrderItem(new HashSet<>(Arrays.asList(new OrderItem())));
        final Code code1 = new Code();
        code1.setCode("code");
        code1.setVisitorType(new VisitorType());
        visitorType1.setCode(new HashSet<>(Arrays.asList(code1)));
        orderItem1.setVisitorType(visitorType1);
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
        orderItem1.setOrder(order3);
        orderItem1.setTicket(new HashSet<>(Arrays.asList(new Ticket())));
        ticket1.setOrderItem(orderItem1);
        final List<Ticket> tickets = Arrays.asList(ticket1);
        when(mockTicketRepository.findAllByOrderItem(any(OrderItem.class))).thenReturn(tickets);

        when(mockPdfPrinter.printPDF(Arrays.asList(new PrintRequest()))).thenThrow(URISyntaxException.class);

        // Configure OrderRepository.save(...).
        final Order order4 = new Order();
        order4.setTicketTypeId(0L);
        order4.setFirstName("firstName");
        order4.setLastName("lastName");
        order4.setMail("mail");
        order4.setPhoneNumber("phoneNumber");
        order4.setStatus("status");
        order4.setOrderCode("orderCode");
        order4.setTotalPayment(0);
        order4.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order4.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        when(mockOrderRepository.save(any(Order.class))).thenReturn(order4);

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
        assertThatThrownBy(() -> {
            orderServiceImplUnderTest.resendTicket(0L);
        }).isInstanceOf(URISyntaxException.class).hasMessageContaining("message");
        verify(mockEmailSender).send(any(MimeMessage.class));
    }

    @Test
    public void testResendTicket_JavaMailSenderThrowsMailException() throws Exception {
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
        visitorType.setTicketType(new TicketType());
        final OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(0);
        orderItem.setVisitorType(new VisitorType());
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
        orderItem.setOrder(order2);
        final Ticket ticket = new Ticket();
        ticket.setCode("code");
        ticket.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket.setVisitorTypeId(0L);
        ticket.setOrderItem(new OrderItem());
        orderItem.setTicket(new HashSet<>(Arrays.asList(ticket)));
        visitorType.setOrderItem(new HashSet<>(Arrays.asList(orderItem)));
        final Code code = new Code();
        code.setCode("code");
        code.setVisitorType(new VisitorType());
        visitorType.setCode(new HashSet<>(Arrays.asList(code)));
        ticketType1.setVisitorType(new HashSet<>(Arrays.asList(visitorType)));
        final Game game = new Game();
        game.setGameName("gameName");
        game.setGameDescription("gameDescription");
        game.setStatus("status");
        game.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
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
        game.setPlace(place);
        ticketType1.setGame(new HashSet<>(Arrays.asList(game)));
        final Optional<TicketType> ticketType = Optional.of(ticketType1);
        when(mockTicketTypeRepository.findById(0L)).thenReturn(ticketType);

        // Configure PlaceRepository.findById(...).
        final Place place2 = new Place();
        place2.setName("name");
        place2.setPlaceKey("placeKey");
        place2.setAddress("address");
        place2.setDetailDescription("detailDescription");
        place2.setShortDescription("shortDescription");
        place2.setMail("mail");
        place2.setPhoneNumber("phoneNumber");
        place2.setStatus("status");
        place2.setLocation("location");
        place2.setCancelPolicy("cancelPolicy");
        final Optional<Place> place1 = Optional.of(place2);
        when(mockPlaceRepository.findById(0L)).thenReturn(place1);

        // Configure TicketRepository.findAllByOrderItem(...).
        final Ticket ticket1 = new Ticket();
        ticket1.setCode("code");
        ticket1.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket1.setVisitorTypeId(0L);
        final OrderItem orderItem1 = new OrderItem();
        orderItem1.setQuantity(0);
        final VisitorType visitorType1 = new VisitorType();
        visitorType1.setTypeName("typeName");
        visitorType1.setTypeKey("typeKey");
        visitorType1.setPrice(0);
        visitorType1.setBasicType(false);
        visitorType1.setStatus("status");
        final TicketType ticketType2 = new TicketType();
        ticketType2.setTypeName("typeName");
        ticketType2.setPlaceId(0L);
        ticketType2.setStatus("status");
        ticketType2.setVisitorType(new HashSet<>(Arrays.asList(new VisitorType())));
        final Game game1 = new Game();
        game1.setGameName("gameName");
        game1.setGameDescription("gameDescription");
        game1.setStatus("status");
        game1.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
        final Place place3 = new Place();
        place3.setName("name");
        place3.setPlaceKey("placeKey");
        place3.setAddress("address");
        place3.setDetailDescription("detailDescription");
        place3.setShortDescription("shortDescription");
        place3.setMail("mail");
        place3.setPhoneNumber("phoneNumber");
        place3.setStatus("status");
        place3.setLocation("location");
        place3.setCancelPolicy("cancelPolicy");
        game1.setPlace(place3);
        ticketType2.setGame(new HashSet<>(Arrays.asList(game1)));
        visitorType1.setTicketType(ticketType2);
        visitorType1.setOrderItem(new HashSet<>(Arrays.asList(new OrderItem())));
        final Code code1 = new Code();
        code1.setCode("code");
        code1.setVisitorType(new VisitorType());
        visitorType1.setCode(new HashSet<>(Arrays.asList(code1)));
        orderItem1.setVisitorType(visitorType1);
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
        orderItem1.setOrder(order3);
        orderItem1.setTicket(new HashSet<>(Arrays.asList(new Ticket())));
        ticket1.setOrderItem(orderItem1);
        final List<Ticket> tickets = Arrays.asList(ticket1);
        when(mockTicketRepository.findAllByOrderItem(any(OrderItem.class))).thenReturn(tickets);

        when(mockPdfPrinter.printPDF(Arrays.asList(new PrintRequest()))).thenReturn(new File("filename.txt"));

        // Configure OrderRepository.save(...).
        final Order order4 = new Order();
        order4.setTicketTypeId(0L);
        order4.setFirstName("firstName");
        order4.setLastName("lastName");
        order4.setMail("mail");
        order4.setPhoneNumber("phoneNumber");
        order4.setStatus("status");
        order4.setOrderCode("orderCode");
        order4.setTotalPayment(0);
        order4.setPurchaseDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        order4.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        when(mockOrderRepository.save(any(Order.class))).thenReturn(order4);

        // Configure JavaMailSender.createMimeMessage(...).
        final MimeMessage mimeMessage = new MimeMessage(Session.getInstance(new Properties()));
        when(mockEmailSender.createMimeMessage()).thenReturn(mimeMessage);

        doThrow(MailException.class).when(mockEmailSender).send(any(MimeMessage.class));

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
    public void testSendEmail_JavaMailSenderThrowsMailException() throws Exception {
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

        doThrow(MailException.class).when(mockEmailSender).send(any(MimeMessage.class));

        // Run the test
        orderServiceImplUnderTest.sendEmail(order, file);

        // Verify the results
    }
}
