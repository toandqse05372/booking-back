package com.capstone.booking.api;

import com.capstone.booking.entity.dto.VisitorTypeDTO;
import com.capstone.booking.service.VisitorTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class VisitorTypeControllerTest {

    private VisitorTypeController visitorTypeControllerUnderTest;

    @Before
    public void setUp() {
        visitorTypeControllerUnderTest = new VisitorTypeController();
        visitorTypeControllerUnderTest.visitorTypeService = mock(VisitorTypeService.class);
    }

    @Test
    public void testCreate() throws Exception {
        // Setup
        String model = "{\"id\":\"\",\"typeName\":\"Khu vui chơi\",\"typeKey\":\"123EE\",\"price\":\"123456\",\"ticketTypeId\":5}";
        ObjectMapper mapper = new ObjectMapper();
        VisitorTypeDTO visitorTypeDTO = mapper.readValue(model, VisitorTypeDTO.class);
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(visitorTypeControllerUnderTest.visitorTypeService).create(visitorTypeDTO, 1L);

        // Run the test
        final ResponseEntity<?> result = visitorTypeControllerUnderTest.create("1", model);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testMarkBasicPrice() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(visitorTypeControllerUnderTest.visitorTypeService).markBasicPrice(0L, 0L);

        // Run the test
        final ResponseEntity<?> result = visitorTypeControllerUnderTest.markBasicPrice(0L, "1");

        // Verify the results
    }

    @Test
    public void testDelete() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(visitorTypeControllerUnderTest.visitorTypeService).delete(0L);

        // Run the test
        final ResponseEntity<?> result = visitorTypeControllerUnderTest.delete(0L);

        // Verify the results
    }

    @Test
    public void testChangeVisitorTypeStatus() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(visitorTypeControllerUnderTest.visitorTypeService).changeStatus(0L);

        // Run the test
        final ResponseEntity<?> result = visitorTypeControllerUnderTest.changeVisitorTypeStatus(0L);

        // Verify the results
    }

    @Test
    public void testGetVisitorType() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(visitorTypeControllerUnderTest.visitorTypeService).getById(0L);

        // Run the test
        final ResponseEntity<?> result = visitorTypeControllerUnderTest.getVisitorType(0L);

        // Verify the results
    }

    @Test
    public void  testFindByTicketTypeId() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(visitorTypeControllerUnderTest.visitorTypeService).findByTicketTypeId(0L);

        // Run the test
        final ResponseEntity<?> result = visitorTypeControllerUnderTest.findByTicketTypeId(0L);

        // Verify the results
    }
}
