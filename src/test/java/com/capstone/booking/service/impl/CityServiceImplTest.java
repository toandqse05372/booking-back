package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.CityConverter;
import com.capstone.booking.config.aws.AmazonS3ClientService;
import com.capstone.booking.entity.City;
import com.capstone.booking.entity.Place;
import com.capstone.booking.entity.dto.CityDTO;
import com.capstone.booking.repository.CityRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CityServiceImplTest {

    @Mock
    private CityRepository mockCityRepository;
    @Mock
    private CityConverter mockCityConverter;
    @Mock
    private AmazonS3ClientService mockAmazonS3ClientService;

    @InjectMocks
    private CityServiceImpl cityServiceImplUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testFindAllCity() {
        // Setup

        // Configure CityRepository.findAll(...).
        final City city = new City();
        city.setName("name");
        city.setShortDescription("shortDescription");
        city.setDetailDescription("detailDescription");
        city.setImageLink("imageLink");
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
        city.setPlaces(new HashSet<>(Arrays.asList(place)));
        final List<City> cities = Arrays.asList(city);
        when(mockCityRepository.findAll()).thenReturn(cities);

        // Configure CityConverter.toDTO(...).
        final CityDTO dto = new CityDTO();
        dto.setName("name");
        dto.setShortDescription("shortDescription");
        dto.setDetailDescription("detailDescription");
        dto.setImageLink("imageLink");
        when(mockCityConverter.toDTO(new City())).thenReturn(dto);

        // Run the test
        final ResponseEntity<?> result = cityServiceImplUnderTest.findAllCity();

        // Verify the results
    }

    @Test
    public void testGetTop3() {
        // Setup

        // Configure CityRepository.getTop3(...).
        final City city = new City();
        city.setName("name");
        city.setShortDescription("shortDescription");
        city.setDetailDescription("detailDescription");
        city.setImageLink("imageLink");
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
        city.setPlaces(new HashSet<>(Arrays.asList(place)));
        final List<City> cities = Arrays.asList(city);
        when(mockCityRepository.getTop3()).thenReturn(cities);

        // Configure CityConverter.toDTO(...).
        final CityDTO dto = new CityDTO();
        dto.setName("name");
        dto.setShortDescription("shortDescription");
        dto.setDetailDescription("detailDescription");
        dto.setImageLink("imageLink");
        when(mockCityConverter.toDTO(new City())).thenReturn(dto);

        // Run the test
        final ResponseEntity<?> result = cityServiceImplUnderTest.getTop3();

        // Verify the results
    }

    @Test
    public void testGetCity() {
        // Setup

        // Configure CityRepository.findById(...).
        final City city1 = new City();
        city1.setName("name");
        city1.setShortDescription("shortDescription");
        city1.setDetailDescription("detailDescription");
        city1.setImageLink("imageLink");
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
        city1.setPlaces(new HashSet<>(Arrays.asList(place)));
        final Optional<City> city = Optional.of(city1);
        when(mockCityRepository.findById(0L)).thenReturn(city);

        // Configure CityConverter.toDTO(...).
        final CityDTO dto = new CityDTO();
        dto.setName("name");
        dto.setShortDescription("shortDescription");
        dto.setDetailDescription("detailDescription");
        dto.setImageLink("imageLink");
        when(mockCityConverter.toDTO(new City())).thenReturn(dto);

        // Run the test
        final ResponseEntity<?> result = cityServiceImplUnderTest.getCity(0L);

        // Verify the results
    }

    @Test
    public void testFindByName() {
        // Setup

        // Configure CityRepository.findByName(...).
        final Output output = new Output();
        output.setPage(0);
        output.setTotalPage(0);
        output.setListResult(Arrays.asList());
        output.setTotalItems(0);
        when(mockCityRepository.findByName("name", 0L, 0L)).thenReturn(output);

        // Run the test
        final ResponseEntity<?> result = cityServiceImplUnderTest.findByName("name", 0L, 0L);

        // Verify the results
    }

    @Test
    public void testCreate() {
        // Setup
        final CityDTO cityDTO = new CityDTO();
        cityDTO.setName("name");
        cityDTO.setShortDescription("shortDescription");
        cityDTO.setDetailDescription("detailDescription");
        cityDTO.setImageLink("imageLink");

        final MultipartFile file = null;

        // Configure CityRepository.findByName(...).
        final City city = new City();
        city.setName("name");
        city.setShortDescription("shortDescription");
        city.setDetailDescription("detailDescription");
        city.setImageLink("imageLink");
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
        city.setPlaces(new HashSet<>(Arrays.asList(place)));
        when(mockCityRepository.findByName("name")).thenReturn(city);

        // Configure CityConverter.toCity(...).
        final City city1 = new City();
        city1.setName("name");
        city1.setShortDescription("shortDescription");
        city1.setDetailDescription("detailDescription");
        city1.setImageLink("imageLink");
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
        city1.setPlaces(new HashSet<>(Arrays.asList(place1)));
        when(mockCityConverter.toCity(new CityDTO())).thenReturn(city1);

        // Configure CityRepository.save(...).
        final City city2 = new City();
        city2.setName("name");
        city2.setShortDescription("shortDescription");
        city2.setDetailDescription("detailDescription");
        city2.setImageLink("imageLink");
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
        city2.setPlaces(new HashSet<>(Arrays.asList(place2)));
        when(mockCityRepository.save(new City())).thenReturn(city2);

        // Configure CityConverter.toDTO(...).
        final CityDTO dto = new CityDTO();
        dto.setName("name");
        dto.setShortDescription("shortDescription");
        dto.setDetailDescription("detailDescription");
        dto.setImageLink("imageLink");
        when(mockCityConverter.toDTO(new City())).thenReturn(dto);

        // Run the test
        final ResponseEntity<?> result = cityServiceImplUnderTest.create(cityDTO, file);

        // Verify the results
        verify(mockAmazonS3ClientService).uploadFileToS3Bucket(eq(0L), any(MultipartFile.class), eq("name"), eq("ext"), eq(false));
    }

    @Test
    public void testUpdate() {
        // Setup
        final CityDTO cityDTO = new CityDTO();
        cityDTO.setName("name");
        cityDTO.setShortDescription("shortDescription");
        cityDTO.setDetailDescription("detailDescription");
        cityDTO.setImageLink("imageLink");

        final MultipartFile file = null;

        // Configure CityRepository.findByName(...).
        final City city = new City();
        city.setName("name");
        city.setShortDescription("shortDescription");
        city.setDetailDescription("detailDescription");
        city.setImageLink("imageLink");
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
        city.setPlaces(new HashSet<>(Arrays.asList(place)));
        when(mockCityRepository.findByName("name")).thenReturn(city);

        // Configure CityRepository.findById(...).
        final City city2 = new City();
        city2.setName("name");
        city2.setShortDescription("shortDescription");
        city2.setDetailDescription("detailDescription");
        city2.setImageLink("imageLink");
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
        city2.setPlaces(new HashSet<>(Arrays.asList(place1)));
        final Optional<City> city1 = Optional.of(city2);
        when(mockCityRepository.findById(0L)).thenReturn(city1);

        // Configure CityConverter.toCity(...).
        final City city3 = new City();
        city3.setName("name");
        city3.setShortDescription("shortDescription");
        city3.setDetailDescription("detailDescription");
        city3.setImageLink("imageLink");
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
        city3.setPlaces(new HashSet<>(Arrays.asList(place2)));
        when(mockCityConverter.toCity(new CityDTO(), new City())).thenReturn(city3);

        // Configure CityRepository.save(...).
        final City city4 = new City();
        city4.setName("name");
        city4.setShortDescription("shortDescription");
        city4.setDetailDescription("detailDescription");
        city4.setImageLink("imageLink");
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
        city4.setPlaces(new HashSet<>(Arrays.asList(place3)));
        when(mockCityRepository.save(new City())).thenReturn(city4);

        // Configure CityConverter.toDTO(...).
        final CityDTO dto = new CityDTO();
        dto.setName("name");
        dto.setShortDescription("shortDescription");
        dto.setDetailDescription("detailDescription");
        dto.setImageLink("imageLink");
        when(mockCityConverter.toDTO(new City())).thenReturn(dto);

        // Run the test
        final ResponseEntity<?> result = cityServiceImplUnderTest.update(cityDTO, file);

        // Verify the results
        verify(mockAmazonS3ClientService).uploadFileToS3Bucket(eq(0L), any(MultipartFile.class), eq("name"), eq("ext"), eq(false));
    }

    @Test
    public void testDelete() {
        // Setup

        // Configure CityRepository.findById(...).
        final City city1 = new City();
        city1.setName("name");
        city1.setShortDescription("shortDescription");
        city1.setDetailDescription("detailDescription");
        city1.setImageLink("imageLink");
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
        city1.setPlaces(new HashSet<>(Arrays.asList(place)));
        final Optional<City> city = Optional.of(city1);
        when(mockCityRepository.findById(0L)).thenReturn(city);

        // Run the test
        final ResponseEntity<?> result = cityServiceImplUnderTest.delete(0L);

        // Verify the results
        verify(mockCityRepository).deleteById(0L);
    }

    @Test
    public void testUploadFile() {
        // Setup
        final MultipartFile file = null;

        // Run the test
        final String result = cityServiceImplUnderTest.uploadFile(file, 0L);

        // Verify the results
        assertThat(result).isEqualTo("result");
        verify(mockAmazonS3ClientService).uploadFileToS3Bucket(eq(0L), any(MultipartFile.class), eq("name"), eq("ext"), eq(false));
    }
}
