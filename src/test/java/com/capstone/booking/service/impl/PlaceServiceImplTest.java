package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.PlaceConverter;
import com.capstone.booking.common.converter.TicketTypeConverter;
import com.capstone.booking.common.converter.VisitorTypeConverter;
import com.capstone.booking.config.aws.AmazonS3ClientService;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.*;
import com.capstone.booking.entity.dto.lite.PlaceDTOLite;
import com.capstone.booking.repository.ImagePlaceRepository;
import com.capstone.booking.repository.PlaceRepository;
import com.capstone.booking.repository.TicketTypeRepository;
import com.capstone.booking.repository.VisitorTypeRepository;
import lombok.SneakyThrows;
import org.apache.poi.util.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
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
        Assertions.assertEquals(200, result.getStatusCodeValue());
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
        Assertions.assertEquals(200, result.getStatusCodeValue());
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
        place1.setId(0l);
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
        placeDTOClient.setId(0l);
        when(mockPlaceConverter.toPlaceClient(place1)).thenReturn(placeDTOClient);

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
        ticketType.setVisitorType(new HashSet<>(Arrays.asList(visitorType)));
        final List<TicketType> ticketTypes = Arrays.asList(ticketType);
        when(mockTicketTypeRepository.findByPlaceIdAndStatus(0L, "ACTIVE")).thenReturn(ticketTypes);

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
        visitorTypeDTO.setStatus("ACTIVE");
        ticketTypeDTO.setVisitorTypes(Arrays.asList(visitorTypeDTO));
        ticketTypeDTO.setStatus("ACTIVE");
        when(mockTicketTypeConverter.toDTO(ticketType)).thenReturn(ticketTypeDTO);

        List<VisitorType> visitorTypes = new ArrayList<>();
        visitorTypes.add(visitorType);
        // Configure VisitorTypeRepository.findByTicketTypeAndStatus(...).
        when(mockVisitorTypeRepository.findByTicketTypeAndStatus(ticketType, "ACTIVE")).thenReturn(visitorTypes);

        // Configure VisitorTypeConverter.toDTO(...).
        when(mockVisitorTypeConverter.toDTO(new VisitorType())).thenReturn(visitorTypeDTO);

        // Run the test
        final ResponseEntity<?> result = placeServiceImplUnderTest.getPlaceClient(0L);

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }

    @SneakyThrows
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
        place.setId(0l);
        when(mockPlaceRepository.findByName("name")).thenReturn(null);

        // Configure PlaceConverter.toPlace(...).
        when(mockPlaceConverter.toPlace(placeDTO)).thenReturn(place);

        // Configure PlaceRepository.save(...).
        when(mockPlaceRepository.save(place)).thenReturn(place);

        Optional<Place> optionalPlace = Optional.of(place);
        // Configure PlaceRepository.findById(...).
        when(mockPlaceRepository.findById(0L)).thenReturn(optionalPlace);

        // Configure ImagePlaceRepository.findByImageName(...).
        final ImagePlace imagePlace1 = new ImagePlace();
        imagePlace1.setImageLink("imageLink");
        imagePlace1.setImageName("imageName");
        imagePlace1.setPlace(place);
        when(mockImagePlaceRepository.findByImageName("Place_0_1")).thenReturn(null);

        // Configure ImagePlaceRepository.save(...).
        when(mockImagePlaceRepository.save(imagePlace1)).thenReturn(imagePlace1);

        // Configure PlaceConverter.toDTO(...).
        when(mockPlaceConverter.toDTO(place)).thenReturn(placeDTO);

        File file;
        file = new File("Test.pdf");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));
        MultipartFile[] multipartFiles = new MultipartFile[1];
        multipartFiles[0] = multipartFile;

        // Run the test
        final ResponseEntity<?> result = placeServiceImplUnderTest.create(placeDTO, multipartFiles);

        // Verify the results
        verify(mockAmazonS3ClientService).uploadFileToS3Bucket(eq(0L), eq(multipartFile), eq("Place_0_1"), eq(".pdf"), eq(true));
    }

    @SneakyThrows
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
        placeDTO.setCityId(0L);
        placeDTO.setCityName("cityName");
        placeDTO.setId(0l);

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
        place.setId(0l);
        when(mockPlaceRepository.findByName("name")).thenReturn(place);

        // Configure PlaceRepository.findById(...).
        Optional<Place> optionalPlace = Optional.of(place);
        when(mockPlaceRepository.findById(0L)).thenReturn(optionalPlace);

        // Configure PlaceConverter.toPlace(...).
        when(mockPlaceConverter.toPlace(placeDTO, place)).thenReturn(place);

        // Configure PlaceRepository.save(...).
        when(mockPlaceRepository.save(place)).thenReturn(place);

        // Configure ImagePlaceRepository.findByImageName(...).
        final ImagePlace imagePlace = new ImagePlace();
        imagePlace.setImageLink("imageLink");
        imagePlace.setImageName("imageName");
        when(mockImagePlaceRepository.findByImageName("Place_0_1")).thenReturn(imagePlace);

        // Configure ImagePlaceRepository.save(...).
        when(mockImagePlaceRepository.save(imagePlace)).thenReturn(imagePlace);

        // Configure PlaceConverter.toDTO(...).
        when(mockPlaceConverter.toDTO(place)).thenReturn(placeDTO);

        File file;
        file = new File("Test.pdf");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));
        MultipartFile[] multipartFiles = new MultipartFile[1];
        multipartFiles[0] = multipartFile;

        // Run the test
        final ResponseEntity<?> result = placeServiceImplUnderTest.update(placeDTO, multipartFiles);

        // Verify the results
        verify(mockAmazonS3ClientService).uploadFileToS3Bucket(eq(0L), eq(multipartFile), eq("Place_0_1"), eq(".pdf"), eq(true));
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
        final ImagePlace imagePlace1 = new ImagePlace();
        imagePlace1.setImageLink("imageLink");
        imagePlace1.setImageName("imageName");
        Set<ImagePlace> imagePlaces = new HashSet<>();
        imagePlaces.add(imagePlace1);
        place1.setImagePlace(imagePlaces);
        final Optional<Place> place = Optional.of(place1);
        when(mockPlaceRepository.findById(0L)).thenReturn(place);

        // Run the test
        final ResponseEntity<?> result = placeServiceImplUnderTest.delete(0L);

        // Verify the results
        verify(mockImagePlaceRepository).delete(imagePlace1);
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
        Assertions.assertEquals(200, result.getStatusCodeValue());
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
        when(mockPlaceRepository.findAllByStatus("ACTIVE")).thenReturn(placeList);

        // Configure PlaceConverter.toPlaceLite(...).
        final PlaceDTOLite lite = new PlaceDTOLite();
        lite.setName("name");
        when(mockPlaceConverter.toPlaceLite(place)).thenReturn(lite);

        // Run the test
        final ResponseEntity<?> result = placeServiceImplUnderTest.getAll();

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
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
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }

    @SneakyThrows
    @Test
    public void testUploadFile() {
        // Setup
        File file;
        file = new File("Test.pdf");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));
        MultipartFile[] multipartFiles = new MultipartFile[1];
        multipartFiles[0] = multipartFile;

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
        when(mockImagePlaceRepository.findByImageName("imageName")).thenReturn(null);

        // Configure PlaceRepository.findById(...).
        final Optional<Place> place1 = Optional.of(place);
        when(mockPlaceRepository.findById(0L)).thenReturn(place1);

        // Configure ImagePlaceRepository.save(...).
        when(mockImagePlaceRepository.save(imagePlace)).thenReturn(imagePlace);

        // Run the test
        placeServiceImplUnderTest.uploadFile(multipartFiles, 0L);

        // Verify the results
        verify(mockAmazonS3ClientService).uploadFileToS3Bucket(eq(0L), eq(multipartFile), eq("Place_0_1"), eq(".pdf"), eq(true));
    }
}
