package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.CityConverter;
import com.capstone.booking.config.aws.AmazonS3ClientService;
import com.capstone.booking.entity.Category;
import com.capstone.booking.entity.City;
import com.capstone.booking.entity.Place;
import com.capstone.booking.entity.dto.CityDTO;
import com.capstone.booking.repository.CityRepository;
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
        Assertions.assertEquals(200, result.getStatusCodeValue());
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
        Assertions.assertEquals(200, result.getStatusCodeValue());
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
        Assertions.assertEquals(200, result.getStatusCodeValue());
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
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }

    @SneakyThrows
    @Test
    public void testCreate() {
        // Setup
        final CityDTO cityDTO = new CityDTO();
        cityDTO.setName("name");
        cityDTO.setShortDescription("shortDescription");
        cityDTO.setDetailDescription("detailDescription");
        cityDTO.setImageLink("imageLink");

        // Configure CityRepository.findByName(...).
        final City city = new City();
        city.setName("name");
        city.setShortDescription("shortDescription");
        city.setDetailDescription("detailDescription");
        city.setImageLink("imageLink");
        when(mockCityRepository.findByName("name")).thenReturn(null);

        // Configure CityConverter.toCity(...).
        when(mockCityConverter.toCity(cityDTO)).thenReturn(city);

        city.setId(0l);
        // Configure CityRepository.save(...).
        when(mockCityRepository.save(city)).thenReturn(city);

        // Configure CityConverter.toDTO(...).
        when(mockCityConverter.toDTO(city)).thenReturn(cityDTO);

        File file = new File("Test.pdf");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));
        // Run the test
        final ResponseEntity<?> result = cityServiceImplUnderTest.create(cityDTO, multipartFile);

        // Verify the results
        verify(mockAmazonS3ClientService).uploadFileToS3Bucket(eq(0L), eq(multipartFile), eq("City_0"), eq(".pdf"), eq(true));
    }

    @SneakyThrows
    @Test
    public void testUpdate() {
        // Setup
        final CityDTO cityDTO = new CityDTO();
        cityDTO.setName("name1");
        cityDTO.setShortDescription("shortDescription");
        cityDTO.setDetailDescription("detailDescription");
        cityDTO.setImageLink("imageLink");
        cityDTO.setId(0l);

        File file = new File("Test.pdf");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));

        // Configure CityRepository.findByName(...).
        final City city = new City();
        city.setName("name");
        city.setShortDescription("shortDescription");
        city.setDetailDescription("detailDescription");
        city.setImageLink("imageLink");
        city.setId(0l);
        when(mockCityRepository.findByName("name")).thenReturn(null);

        // Configure CityRepository.findById(...).
        final Optional<City> cityOptional = Optional.of(city);
        when(mockCityRepository.findById(0L)).thenReturn(cityOptional);

        // Configure CityConverter.toCity(...).
        when(mockCityConverter.toCity(cityDTO, city)).thenReturn(city);

        // Configure CityRepository.save(...).
        when(mockCityRepository.save(city)).thenReturn(city);

        // Configure CityConverter.toDTO(...).
        when(mockCityConverter.toDTO(city)).thenReturn(cityDTO);

        // Run the test
        final ResponseEntity<?> result = cityServiceImplUnderTest.update(cityDTO, multipartFile);

        // Verify the results
        verify(mockAmazonS3ClientService).uploadFileToS3Bucket(eq(0L), eq(multipartFile), eq("City_0"), eq(".pdf"), eq(true));
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

    @SneakyThrows
    @Test
    public void testUploadFile() {
        // Setup
        File file = new File("Test.pdf");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));

        // Run the test
        final String result = cityServiceImplUnderTest.uploadFile(multipartFile, 0L);

        // Verify the results
        assertThat(result).isEqualTo("nullCity_0.pdf");
        verify(mockAmazonS3ClientService).uploadFileToS3Bucket(eq(0L), eq(multipartFile), eq("City_0"), eq(".pdf"), eq(true));
    }
}
