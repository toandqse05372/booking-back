package com.capstone.booking.api;

import com.capstone.booking.entity.dto.VisitorTypeDTO;
import com.capstone.booking.service.VisitorTypeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

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
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(visitorTypeControllerUnderTest.visitorTypeService).create(new VisitorTypeDTO(), 0L);

        // Run the test
        final ResponseEntity<?> result = visitorTypeControllerUnderTest.create("1", "{\"id\":\"\",\"typeName\":\"Khu vui chơi\",\"typeKey\":\"123EE\",\"price\":\"123456\",\"ticketTypeId\":5}");

        // Verify the results
    }

    @Test
    public void testCreate_ThrowsJsonProcessingException() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(visitorTypeControllerUnderTest.visitorTypeService).create(new VisitorTypeDTO(), 0L);

        // Run the test
        assertThatThrownBy(() -> {
            visitorTypeControllerUnderTest.create("1", "message");
        }).isInstanceOf(JsonProcessingException.class).hasMessageContaining("message");
    }

    @Test
    public void testUpdate() {
        // Setup
        final VisitorTypeDTO model = new VisitorTypeDTO();
        model.setTypeName("typeName");
        model.setTypeKey("typeKey");
        model.setTicketTypeId(0L);
        model.setPrice(0);
        model.setBasicType(false);
        model.setRemaining(0);
        model.setStatus("status");

        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(visitorTypeControllerUnderTest.visitorTypeService).update(new VisitorTypeDTO());

        // Run the test
        final ResponseEntity<?> result = visitorTypeControllerUnderTest.update(model, 0L);

        // Verify the results
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
    public void testFindByTicketTypeId() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(visitorTypeControllerUnderTest.visitorTypeService).findByTicketTypeId(0L);

        // Run the test
        final ResponseEntity<?> result = visitorTypeControllerUnderTest.findByTicketTypeId(0L);

        // Verify the results
    }

    @Test
    public void testUploadFile() {
        // Setup
        final MultipartFile file = null;
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(visitorTypeControllerUnderTest.visitorTypeService).addCodeForTicketType(any(MultipartFile.class), eq("codeType"));

        // Run the test
        final ResponseEntity<?> result = visitorTypeControllerUnderTest.uploadFile(file, "codeType");

        // Verify the results
    }
}
