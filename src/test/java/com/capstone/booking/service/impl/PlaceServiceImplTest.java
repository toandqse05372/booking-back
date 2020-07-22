package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.PlaceConverter;
import com.capstone.booking.common.converter.TicketTypeConverter;
import com.capstone.booking.common.converter.VisitorTypeConverter;
import com.capstone.booking.config.aws.AmazonS3ClientService;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.*;
import com.capstone.booking.repository.ImagePlaceRepository;
import com.capstone.booking.repository.PlaceRepository;
import com.capstone.booking.repository.TicketTypeRepository;
import com.capstone.booking.repository.VisitorTypeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PlaceServiceImplTest {

    @Mock
    private PlaceRepository mockPlaceRepository;
    @Mock
    private TicketTypeRepository mockTicketTypeRepository;
    @Mock
    private PlaceConverter mockPlaceConverter;
    @Mock
    private AmazonS3ClientService mockAmazonS3ClientService;
    @Mock
    private ImagePlaceRepository mockImagePlaceRepository;
    @Mock
    private VisitorTypeRepository mockVisitorTypeRepository;
    @Mock
    private TicketTypeConverter mockTicketTypeConverter;
    @Mock
    private VisitorTypeConverter mockVisitorTypeConverter;

    @InjectMocks
    private PlaceServiceImpl placeServiceImplUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testFindByMultipleParam() {
        // Setup

        // Configure PlaceRepository.findByMultiParam(...).
        final Output output = new Output();
        output.setPage(0);
        output.setTotalPage(0);
        output.setListResult(Arrays.asList());
        output.setTotalItems(0);
        when(mockPlaceRepository.findByMultiParam("name", "address", 0L, 0L, 0L, 0L)).thenReturn(output);

        // Run the test
        final ResponseEntity<?> result = placeServiceImplUnderTest.findByMultipleParam("name", "address", 0L, 0L, 0L, 0L);

        // Verify the results
    }

    @Test
    public void testGetPlace() {
        // Setup

        // Configure PlaceRepository.findById(...).
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
        final Optional<Place> place = Optional.of(place1);
        when(mockPlaceRepository.findById(0L)).thenReturn(place);

        // Configure PlaceConverter.toDTO(...).
        final PlaceDTO placeDTO = new PlaceDTO();
        placeDTO.setName("name");
        placeDTO.setPlaceKey("placeKey");
        placeDTO.setAddress("address");
        placeDTO.setShortDescription("shortDescription");
        placeDTO.setDetailDescription("detailDescription");
        placeDTO.setMail("mail");
        placeDTO.setPhoneNumber("phoneNumber");
        placeDTO.setPlaceImageLink(new HashSet<>(Arrays.asList("value")));
        placeDTO.setCityId(0L);
        placeDTO.setCityName("cityName");
        when(mockPlaceConverter.toDTO(any(Place.class))).thenReturn(placeDTO);

        // Run the test
        final ResponseEntity<?> result = placeServiceImplUnderTest.getPlace(0L);

        // Verify the results
    }

    @Test
    public void testGetPlaceClient() {
        // Setup

        // Configure PlaceRepository.findById(...).
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
        final Optional<Place> place = Optional.of(place1);
        when(mockPlaceRepository.findById(0L)).thenReturn(place);

        // Configure PlaceConverter.toPlaceClient(...).
        final PlaceDTOClient placeDTOClient = new PlaceDTOClient();
        placeDTOClient.setName("name");
        placeDTOClient.setPlaceKey("placeKey");
        placeDTOClient.setAddress("address");
        placeDTOClient.setShortDescription("shortDescription");
        placeDTOClient.setDetailDescription("detailDescription");
        placeDTOClient.setMail("mail");
        placeDTOClient.setPhoneNumber("phoneNumber");
        placeDTOClient.setPlaceImageLink(new HashSet<>(Arrays.asList("value")));
        placeDTOClient.setCityId(0L);
        placeDTOClient.setCityName("cityName");
        when(mockPlaceConverter.toPlaceClient(any(Place.class))).thenReturn(placeDTOClient);

        // Configure TicketTypeRepository.findByPlaceIdAndStatus(...).
        final TicketType ticketType = new TicketType();
        ticketType.setTypeName("typeName");
        ticketType.setPlaceId(0L);
        ticketType.setStatus("status");
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
        orderItem.setOrder(order);
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
        ticketType.setVisitorType(new HashSet<>(Arrays.asList(visitorType)));
        final Game game = new Game();
        game.setGameName("gameName");
        game.setGameDescription("gameDescription");
        game.setStatus("status");
        game.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
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
        game.setPlace(place2);
        ticketType.setGame(new HashSet<>(Arrays.asList(game)));
        final List<TicketType> ticketTypes = Arrays.asList(ticketType);
        when(mockTicketTypeRepository.findByPlaceIdAndStatus(0L, "status")).thenReturn(ticketTypes);

        // Configure TicketTypeConverter.toDTO(...).
        final TicketTypeDTO ticketTypeDTO = new TicketTypeDTO();
        ticketTypeDTO.setTypeName("typeName");
        ticketTypeDTO.setGameId(new HashSet<>(Arrays.asList(0L)));
        ticketTypeDTO.setPlaceId(0L);
        final VisitorTypeDTO visitorTypeDTO = new VisitorTypeDTO();
        visitorTypeDTO.setTypeName("typeName");
        visitorTypeDTO.setTypeKey("typeKey");
        visitorTypeDTO.setTicketTypeId(0L);
        visitorTypeDTO.setPrice(0);
        visitorTypeDTO.setBasicType(false);
        visitorTypeDTO.setRemaining(0);
        visitorTypeDTO.setStatus("status");
        ticketTypeDTO.setVisitorTypes(Arrays.asList(visitorTypeDTO));
        ticketTypeDTO.setStatus("status");
        when(mockTicketTypeConverter.toDTO(any(TicketType.class))).thenReturn(ticketTypeDTO);

        // Configure VisitorTypeRepository.findByTicketTypeAndStatus(...).
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
        ticketType1.setGame(new HashSet<>(Arrays.asList(game1)));
        visitorType1.setTicketType(ticketType1);
        final OrderItem orderItem1 = new OrderItem();
        orderItem1.setQuantity(0);
        orderItem1.setVisitorType(new VisitorType());
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
        orderItem1.setOrder(order1);
        final Ticket ticket1 = new Ticket();
        ticket1.setCode("code");
        ticket1.setRedemptionDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        ticket1.setVisitorTypeId(0L);
        ticket1.setOrderItem(new OrderItem());
        orderItem1.setTicket(new HashSet<>(Arrays.asList(ticket1)));
        visitorType1.setOrderItem(new HashSet<>(Arrays.asList(orderItem1)));
        final Code code1 = new Code();
        code1.setCode("code");
        code1.setVisitorType(new VisitorType());
        visitorType1.setCode(new HashSet<>(Arrays.asList(code1)));
        final List<VisitorType> visitorTypes = Arrays.asList(visitorType1);
        when(mockVisitorTypeRepository.findByTicketTypeAndStatus(any(TicketType.class), eq("status"))).thenReturn(visitorTypes);

        // Configure VisitorTypeConverter.toDTO(...).
        final VisitorTypeDTO visitorTypeDTO1 = new VisitorTypeDTO();
        visitorTypeDTO1.setTypeName("typeName");
        visitorTypeDTO1.setTypeKey("typeKey");
        visitorTypeDTO1.setTicketTypeId(0L);
        visitorTypeDTO1.setPrice(0);
        visitorTypeDTO1.setBasicType(false);
        visitorTypeDTO1.setRemaining(0);
        visitorTypeDTO1.setStatus("status");
        when(mockVisitorTypeConverter.toDTO(new VisitorType())).thenReturn(visitorTypeDTO1);

        // Run the test
        final ResponseEntity<?> result = placeServiceImplUnderTest.getPlaceClient(0L);

        // Verify the results
    }

    @Test
    public void testCreate() {
        // Setup
        final PlaceDTO placeDTO = new PlaceDTO();
        placeDTO.setName("name");
        placeDTO.setPlaceKey("placeKey");
        placeDTO.setAddress("address");
        placeDTO.setShortDescription("shortDescription");
        placeDTO.setDetailDescription("detailDescription");
        placeDTO.setMail("mail");
        placeDTO.setPhoneNumber("phoneNumber");
        placeDTO.setPlaceImageLink(new HashSet<>(Arrays.asList("value")));
        placeDTO.setCityId(0L);
        placeDTO.setCityName("cityName");

        final MultipartFile[] files = new MultipartFile[]{};

        // Configure PlaceRepository.findByName(...).
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
        when(mockPlaceRepository.findByName("name")).thenReturn(place);

        // Configure PlaceConverter.toPlace(...).
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
        when(mockPlaceConverter.toPlace(new PlaceDTO())).thenReturn(place1);

        // Configure PlaceRepository.save(...).
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
        when(mockPlaceRepository.save(any(Place.class))).thenReturn(place2);

        // Configure ImagePlaceRepository.findByImageName(...).
        final ImagePlace imagePlace = new ImagePlace();
        imagePlace.setImageLink("imageLink");
        imagePlace.setImageName("imageName");
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
        imagePlace.setPlace(place3);
        when(mockImagePlaceRepository.findByImageName("imageName")).thenReturn(imagePlace);

        // Configure PlaceRepository.findById(...).
        final Place place5 = new Place();
        place5.setName("name");
        place5.setPlaceKey("placeKey");
        place5.setAddress("address");
        place5.setDetailDescription("detailDescription");
        place5.setShortDescription("shortDescription");
        place5.setMail("mail");
        place5.setPhoneNumber("phoneNumber");
        place5.setStatus("status");
        place5.setLocation("location");
        place5.setCancelPolicy("cancelPolicy");
        final Optional<Place> place4 = Optional.of(place5);
        when(mockPlaceRepository.findById(0L)).thenReturn(place4);

        // Configure ImagePlaceRepository.save(...).
        final ImagePlace imagePlace1 = new ImagePlace();
        imagePlace1.setImageLink("imageLink");
        imagePlace1.setImageName("imageName");
        final Place place6 = new Place();
        place6.setName("name");
        place6.setPlaceKey("placeKey");
        place6.setAddress("address");
        place6.setDetailDescription("detailDescription");
        place6.setShortDescription("shortDescription");
        place6.setMail("mail");
        place6.setPhoneNumber("phoneNumber");
        place6.setStatus("status");
        place6.setLocation("location");
        place6.setCancelPolicy("cancelPolicy");
        imagePlace1.setPlace(place6);
        when(mockImagePlaceRepository.save(any(ImagePlace.class))).thenReturn(imagePlace1);

        // Configure PlaceConverter.toDTO(...).
        final PlaceDTO placeDTO1 = new PlaceDTO();
        placeDTO1.setName("name");
        placeDTO1.setPlaceKey("placeKey");
        placeDTO1.setAddress("address");
        placeDTO1.setShortDescription("shortDescription");
        placeDTO1.setDetailDescription("detailDescription");
        placeDTO1.setMail("mail");
        placeDTO1.setPhoneNumber("phoneNumber");
        placeDTO1.setPlaceImageLink(new HashSet<>(Arrays.asList("value")));
        placeDTO1.setCityId(0L);
        placeDTO1.setCityName("cityName");
        when(mockPlaceConverter.toDTO(any(Place.class))).thenReturn(placeDTO1);

        // Run the test
        final ResponseEntity<?> result = placeServiceImplUnderTest.create(placeDTO, files);

        // Verify the results
        verify(mockAmazonS3ClientService).uploadFileToS3Bucket(eq(0L), any(MultipartFile.class), eq("name"), eq("ext"), eq(false));
    }

    @Test
    public void testUpdate() {
        // Setup
        final PlaceDTO placeDTO = new PlaceDTO();
        placeDTO.setName("name");
        placeDTO.setPlaceKey("placeKey");
        placeDTO.setAddress("address");
        placeDTO.setShortDescription("shortDescription");
        placeDTO.setDetailDescription("detailDescription");
        placeDTO.setMail("mail");
        placeDTO.setPhoneNumber("phoneNumber");
        placeDTO.setPlaceImageLink(new HashSet<>(Arrays.asList("value")));
        placeDTO.setCityId(0L);
        placeDTO.setCityName("cityName");

        final MultipartFile[] files = new MultipartFile[]{};

        // Configure PlaceRepository.findByName(...).
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
        when(mockPlaceRepository.findByName("name")).thenReturn(place);

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

        // Configure PlaceConverter.toPlace(...).
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
        when(mockPlaceConverter.toPlace(eq(new PlaceDTO()), any(Place.class))).thenReturn(place3);

        // Configure PlaceRepository.save(...).
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
        when(mockPlaceRepository.save(any(Place.class))).thenReturn(place4);

        // Configure ImagePlaceRepository.findByImageName(...).
        final ImagePlace imagePlace = new ImagePlace();
        imagePlace.setImageLink("imageLink");
        imagePlace.setImageName("imageName");
        final Place place5 = new Place();
        place5.setName("name");
        place5.setPlaceKey("placeKey");
        place5.setAddress("address");
        place5.setDetailDescription("detailDescription");
        place5.setShortDescription("shortDescription");
        place5.setMail("mail");
        place5.setPhoneNumber("phoneNumber");
        place5.setStatus("status");
        place5.setLocation("location");
        place5.setCancelPolicy("cancelPolicy");
        imagePlace.setPlace(place5);
        when(mockImagePlaceRepository.findByImageName("imageName")).thenReturn(imagePlace);

        // Configure ImagePlaceRepository.save(...).
        final ImagePlace imagePlace1 = new ImagePlace();
        imagePlace1.setImageLink("imageLink");
        imagePlace1.setImageName("imageName");
        final Place place6 = new Place();
        place6.setName("name");
        place6.setPlaceKey("placeKey");
        place6.setAddress("address");
        place6.setDetailDescription("detailDescription");
        place6.setShortDescription("shortDescription");
        place6.setMail("mail");
        place6.setPhoneNumber("phoneNumber");
        place6.setStatus("status");
        place6.setLocation("location");
        place6.setCancelPolicy("cancelPolicy");
        imagePlace1.setPlace(place6);
        when(mockImagePlaceRepository.save(any(ImagePlace.class))).thenReturn(imagePlace1);

        // Configure PlaceConverter.toDTO(...).
        final PlaceDTO placeDTO1 = new PlaceDTO();
        placeDTO1.setName("name");
        placeDTO1.setPlaceKey("placeKey");
        placeDTO1.setAddress("address");
        placeDTO1.setShortDescription("shortDescription");
        placeDTO1.setDetailDescription("detailDescription");
        placeDTO1.setMail("mail");
        placeDTO1.setPhoneNumber("phoneNumber");
        placeDTO1.setPlaceImageLink(new HashSet<>(Arrays.asList("value")));
        placeDTO1.setCityId(0L);
        placeDTO1.setCityName("cityName");
        when(mockPlaceConverter.toDTO(any(Place.class))).thenReturn(placeDTO1);

        // Run the test
        final ResponseEntity<?> result = placeServiceImplUnderTest.update(placeDTO, files);

        // Verify the results
        verify(mockAmazonS3ClientService).uploadFileToS3Bucket(eq(0L), any(MultipartFile.class), eq("name"), eq("ext"), eq(false));
    }

    @Test
    public void testDelete() {
        // Setup

        // Configure PlaceRepository.findById(...).
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
        final Optional<Place> place = Optional.of(place1);
        when(mockPlaceRepository.findById(0L)).thenReturn(place);

        // Run the test
        final ResponseEntity<?> result = placeServiceImplUnderTest.delete(0L);

        // Verify the results
        verify(mockImagePlaceRepository).delete(any(ImagePlace.class));
        verify(mockPlaceRepository).deleteById(0L);
    }

    @Test
    public void testChangeStatus() {
        // Setup

        // Configure PlaceRepository.findById(...).
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
        final Optional<Place> place = Optional.of(place1);
        when(mockPlaceRepository.findById(0L)).thenReturn(place);

        // Configure PlaceRepository.save(...).
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
        when(mockPlaceRepository.save(any(Place.class))).thenReturn(place2);

        // Configure PlaceConverter.toDTO(...).
        final PlaceDTO placeDTO = new PlaceDTO();
        placeDTO.setName("name");
        placeDTO.setPlaceKey("placeKey");
        placeDTO.setAddress("address");
        placeDTO.setShortDescription("shortDescription");
        placeDTO.setDetailDescription("detailDescription");
        placeDTO.setMail("mail");
        placeDTO.setPhoneNumber("phoneNumber");
        placeDTO.setPlaceImageLink(new HashSet<>(Arrays.asList("value")));
        placeDTO.setCityId(0L);
        placeDTO.setCityName("cityName");
        when(mockPlaceConverter.toDTO(any(Place.class))).thenReturn(placeDTO);

        // Run the test
        final ResponseEntity<?> result = placeServiceImplUnderTest.changeStatus(0L);

        // Verify the results
    }

    @Test
    public void testGetAll() {
        // Setup

        // Configure PlaceRepository.findAllByStatus(...).
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
        final List<Place> placeList = Arrays.asList(place);
        when(mockPlaceRepository.findAllByStatus("status")).thenReturn(placeList);

        // Configure PlaceConverter.toPlaceLite(...).
        final PlaceDTOLite lite = new PlaceDTOLite();
        lite.setName("name");
        when(mockPlaceConverter.toPlaceLite(any(Place.class))).thenReturn(lite);

        // Run the test
        final ResponseEntity<?> result = placeServiceImplUnderTest.getAll();

        // Verify the results
    }

    @Test
    public void testSearchPlaceForClient() {
        // Setup
        final List<Long> cityId = Arrays.asList(0L);
        final List<Long> categoryId = Arrays.asList(0L);

        // Configure PlaceRepository.findByMultiParamForClient(...).
        final Output output = new Output();
        output.setPage(0);
        output.setTotalPage(0);
        output.setListResult(Arrays.asList());
        output.setTotalItems(0);
        when(mockPlaceRepository.findByMultiParamForClient("name", 0L, 0L, Arrays.asList(0L), Arrays.asList(0L), 0L, 0L)).thenReturn(output);

        // Run the test
        final ResponseEntity<?> result = placeServiceImplUnderTest.searchPlaceForClient("name", 0L, 0L, cityId, categoryId, 0L, 0L);

        // Verify the results
    }

    @Test
    public void testUploadFile() {
        // Setup
        final MultipartFile[] files = new MultipartFile[]{};

        // Configure ImagePlaceRepository.findByImageName(...).
        final ImagePlace imagePlace = new ImagePlace();
        imagePlace.setImageLink("imageLink");
        imagePlace.setImageName("imageName");
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
        imagePlace.setPlace(place);
        when(mockImagePlaceRepository.findByImageName("imageName")).thenReturn(imagePlace);

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

        // Configure ImagePlaceRepository.save(...).
        final ImagePlace imagePlace1 = new ImagePlace();
        imagePlace1.setImageLink("imageLink");
        imagePlace1.setImageName("imageName");
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
        imagePlace1.setPlace(place3);
        when(mockImagePlaceRepository.save(any(ImagePlace.class))).thenReturn(imagePlace1);

        // Run the test
        placeServiceImplUnderTest.uploadFile(files, 0L);

        // Verify the results
        verify(mockAmazonS3ClientService).uploadFileToS3Bucket(eq(0L), any(MultipartFile.class), eq("name"), eq("ext"), eq(false));
    }
}
