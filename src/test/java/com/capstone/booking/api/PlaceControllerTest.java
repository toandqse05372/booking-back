package com.capstone.booking.api;

import com.capstone.booking.entity.dto.PlaceDTO;
import com.capstone.booking.service.PlaceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

public class PlaceControllerTest {

    @Mock
    private PlaceService mockPlaceService;

    @InjectMocks
    private PlaceController placeControllerUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testGetAllPlace() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockPlaceService).getAll();

        // Run the test
        final ResponseEntity<?> result = placeControllerUnderTest.getAllPlace();

        // Verify the results
    }

    @Test
    public void testCreatePlace() throws Exception {
        // Setup
        final MultipartFile[] files = new MultipartFile[]{};
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockPlaceService).create(eq(new PlaceDTO()), any(MultipartFile[].class));

        // Run the test
        final ResponseEntity<?> result = placeControllerUnderTest.createPlace(files, "{\"id\":null,\"name\":\"Tòa quan sát\",\"shortDescription\":\"\",\"detailDescription\":\"\",\"address\":\"Validity\",\"mail\":\"hieufusw@gmail.com\",\"cityId\":1,\"phoneNumber\":\"0123456780\",\"categoryId\":[3],\"weekDays\":[1],\"openingHours\":\"\"}");

        // Verify the results
    }

    @Test
    public void testCreatePlace_ThrowsJsonProcessingException() {
        // Setup
        final MultipartFile[] files = new MultipartFile[]{};
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockPlaceService).create(eq(new PlaceDTO()), any(MultipartFile[].class));

        // Run the test
        assertThatThrownBy(() -> {
            placeControllerUnderTest.createPlace(files, "message");
        }).isInstanceOf(JsonProcessingException.class).hasMessageContaining("message");
    }

    @Test
    public void testUpdatePlace() throws Exception {
        // Setup
        final MultipartFile[] files = new MultipartFile[]{};
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockPlaceService).update(eq(new PlaceDTO()), any(MultipartFile[].class));

        // Run the test
        final ResponseEntity<?> result = placeControllerUnderTest.updatePlace(files, "{\"id\":1,\"name\":\"Tòa quan sát\",\"shortDescription\":\"\",\"detailDescription\":\"\",\"address\":\"Validity\",\"mail\":\"hieufusw@gmail.com\",\"cityId\":1,\"phoneNumber\":\"0123456780\",\"categoryId\":[3],\"weekDays\":[1],\"openingHours\":\"\"}", 0L);

        // Verify the results
    }

    @Test
    public void testUpdatePlace_ThrowsJsonProcessingException() {
        // Setup
        final MultipartFile[] files = new MultipartFile[]{};
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockPlaceService).update(eq(new PlaceDTO()), any(MultipartFile[].class));

        // Run the test
        assertThatThrownBy(() -> {
            placeControllerUnderTest.updatePlace(files, "message", 0L);
        }).isInstanceOf(JsonProcessingException.class).hasMessageContaining("message");
    }

    @Test
    public void testChangeStatusPlace() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockPlaceService).changeStatus(0L);

        // Run the test
        final ResponseEntity<?> result = placeControllerUnderTest.changeStatusPlace(0L);

        // Verify the results
    }

    @Test
    public void testGetPlace() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockPlaceService).getPlace(0L);

        // Run the test
        final ResponseEntity<?> result = placeControllerUnderTest.getPlace(0L);

        // Verify the results
    }

    @Test
    public void testGetPlaceClient() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockPlaceService).getPlaceClient(0L);

        // Run the test
        final ResponseEntity<?> result = placeControllerUnderTest.getPlaceClient(0L);

        // Verify the results
    }

    @Test
    public void testSearchMUL() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockPlaceService).findByMultipleParam("name", "address", 0L, 0L, 0L, 0L);

        // Run the test
        final ResponseEntity<?> result = placeControllerUnderTest.searchMUL("name", "address", 0L, 0L, 0L, 0L);

        // Verify the results
    }

    @Test
    public void testSearchPlaceForClient() {
        // Setup
        final List<Long> cityId = Arrays.asList(0L);
        final List<Long> categoryId = Arrays.asList(0L);
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockPlaceService).searchPlaceForClient("name", 0L, 0L, Arrays.asList(0L), Arrays.asList(0L), 0L, 0L);

        // Run the test
        final ResponseEntity<?> result = placeControllerUnderTest.searchPlaceForClient("name", 0L, 0L, cityId, categoryId, 0L, 0L);

        // Verify the results
    }

    @Test
    public void testDeletePlace() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockPlaceService).delete(0L);

        // Run the test
        final ResponseEntity<?> result = placeControllerUnderTest.deletePlace(0L);

        // Verify the results
    }
}
