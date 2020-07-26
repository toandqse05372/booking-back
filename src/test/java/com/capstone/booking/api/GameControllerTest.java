package com.capstone.booking.api;

import com.capstone.booking.entity.dto.GameDTO;
import com.capstone.booking.service.GameService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashSet;

import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

public class GameControllerTest {

    @Mock
    private GameService mockGameService;

    @InjectMocks
    private GameController gameControllerUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testSearchAll() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockGameService).findAll();

        // Run the test
        final ResponseEntity<?> result = gameControllerUnderTest.searchAll();

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testSearchMUL() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockGameService).findByMulParam("gameName", "placeName", 0L, 0L);

        // Run the test
        final ResponseEntity<?> result = gameControllerUnderTest.searchMUL("gameName", 0L, 0L, "placeName");

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testDeleteGame() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockGameService).delete(0L);

        // Run the test
        final ResponseEntity<?> result = gameControllerUnderTest.deleteGame(0L);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testChangeStatusGame() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockGameService).changeStatus(0L);

        // Run the test
        final ResponseEntity<?> result = gameControllerUnderTest.changeStatusGame(0L);

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void testCreateGame() {
        // Setup
        final GameDTO model = new GameDTO();
        model.setGameName("gameName");
        model.setGameDescription("gameDescription");
        model.setTicketTypeName(new HashSet<>(Arrays.asList("value")));
        model.setPlaceId(0L);
        model.setPlaceName("placeName");
        model.setStatus("status");

        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockGameService).create(model);

        // Run the test
        final ResponseEntity<?> result = gameControllerUnderTest.createGame(model);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testUpdateGame() {
        // Setup
        final GameDTO model = new GameDTO();
        model.setGameName("gameName");
        model.setGameDescription("gameDescription");
        model.setTicketTypeName(new HashSet<>(Arrays.asList("value")));
        model.setPlaceId(0L);
        model.setPlaceName("placeName");
        model.setStatus("status");

        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockGameService).update(model);

        // Run the test
        final ResponseEntity<?> result = gameControllerUnderTest.updateGame(model, 0L);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testGetGame() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockGameService).getGame(0L);

        // Run the test
        final ResponseEntity<?> result = gameControllerUnderTest.getGame(0L);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testFindByPlaceId() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockGameService).findByPlaceId(0L, 0L, 0L);

        // Run the test
        final ResponseEntity<?> result = gameControllerUnderTest.findByPlaceId(0L, 0L, 0L);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testListOptionByPlace() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockGameService).listOptionByPlace(0L);

        // Run the test
        final ResponseEntity<?> result = gameControllerUnderTest.listOptionByPlace(0L);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }
}
