package com.capstone.booking.api;

import com.capstone.booking.entity.dto.CategoryDTO;
import com.capstone.booking.entity.dto.CityDTO;
import com.capstone.booking.service.CityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.util.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

public class CityControllerTest {

    @Mock
    private CityService mockCityService;

    @InjectMocks
    private CityController cityControllerUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testGetAllCity() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockCityService).findAllCity();

        // Run the test
        final ResponseEntity<?> result = cityControllerUnderTest.getAllCity();

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testGetTop3() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockCityService).getTop3();

        // Run the test
        final ResponseEntity<?> result = cityControllerUnderTest.getTop3();

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testGetCity() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockCityService).getCity(0L);

        // Run the test
        final ResponseEntity<?> result = cityControllerUnderTest.getCity(0L);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testSearchByName() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockCityService).findByName("name", 0L, 0L);

        // Run the test
        final ResponseEntity<?> result = cityControllerUnderTest.searchByName("name", 0L, 0L);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testDeleteCity() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockCityService).delete(0L);

        // Run the test
        final ResponseEntity<?> result = cityControllerUnderTest.deleteCity(0L);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testCreateCity() throws Exception {
        // Setup
        String model = "{\"id\":null,\"name\":\"dbadmin1\"" +
                ",\"shortDescription\":null,\"detailDescription\":null}";
        ObjectMapper mapper = new ObjectMapper();
        CityDTO cityDTO = mapper.readValue(model, CityDTO.class);
        File file = new File("Test.pdf");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockCityService).create(cityDTO, multipartFile);

        // Run the test
        final ResponseEntity<?> result = cityControllerUnderTest.createCity(multipartFile, model);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testUpdateCity() throws Exception {
        // Setup
        String model = "{\"id\":0,\"name\":\"dbadmin1\"" +
                ",\"shortDescription\":null,\"detailDescription\":null}";
        ObjectMapper mapper = new ObjectMapper();
        CityDTO cityDTO = mapper.readValue(model, CityDTO.class);
        File file = new File("Test.pdf");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockCityService).update(cityDTO, multipartFile);

        // Run the test
        final ResponseEntity<?> result = cityControllerUnderTest.updateCity(multipartFile, model, 0L);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }
}
