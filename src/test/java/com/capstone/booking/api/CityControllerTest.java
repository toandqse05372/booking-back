package com.capstone.booking.api;

import com.capstone.booking.entity.dto.CityDTO;
import com.capstone.booking.service.CityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

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
    }

    @Test
    public void testGetTop3() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockCityService).getTop3();

        // Run the test
        final ResponseEntity<?> result = cityControllerUnderTest.getTop3();

        // Verify the results
    }

    @Test
    public void testGetCity() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockCityService).getCity(0L);

        // Run the test
        final ResponseEntity<?> result = cityControllerUnderTest.getCity(0L);

        // Verify the results
    }

    @Test
    public void testSearchByName() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockCityService).findByName("name", 0L, 0L);

        // Run the test
        final ResponseEntity<?> result = cityControllerUnderTest.searchByName("name", 0L, 0L);

        // Verify the results
    }

    @Test
    public void testDeleteCity() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockCityService).delete(0L);

        // Run the test
        final ResponseEntity<?> result = cityControllerUnderTest.deleteCity(0L);

        // Verify the results
    }

    @Test
    public void testCreateCity() throws Exception {
        // Setup
        final MultipartFile file = null;
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockCityService).create(eq(new CityDTO()), any(MultipartFile.class));

        // Run the test
        final ResponseEntity<?> result = cityControllerUnderTest.createCity(file, "{\"id\":null,\"name\":\"dbadmin1\"" +
                ",\"shortDescription\":null,\"detailDescription\":null}");

        // Verify the results
    }

    @Test
    public void testCreateCity_ThrowsJsonProcessingException() {
        // Setup
        final MultipartFile file = null;
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockCityService).create(eq(new CityDTO()), any(MultipartFile.class));

        // Run the test
        assertThatThrownBy(() -> {
            cityControllerUnderTest.createCity(file, "message");
        }).isInstanceOf(JsonProcessingException.class).hasMessageContaining("message");
    }

    @Test
    public void testUpdateCity() throws Exception {
        // Setup
        final MultipartFile file = null;
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockCityService).update(eq(new CityDTO()), any(MultipartFile.class));

        // Run the test
        final ResponseEntity<?> result = cityControllerUnderTest.updateCity(file, "{\"id\":5,\"name\":\"" +
                "dbadmin1\",\"shortDescription\":null,\"detailDescription\":null}", 0L);

        // Verify the results
    }

    @Test
    public void testUpdateCity_ThrowsJsonProcessingException() {
        // Setup
        final MultipartFile file = null;
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockCityService).update(eq(new CityDTO()), any(MultipartFile.class));

        // Run the test
        assertThatThrownBy(() -> {
            cityControllerUnderTest.updateCity(file, "message", 0L);
        }).isInstanceOf(JsonProcessingException.class).hasMessageContaining("message");
    }
}
