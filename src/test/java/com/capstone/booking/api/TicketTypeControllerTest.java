package com.capstone.booking.api;

import com.capstone.booking.entity.dto.TicketTypeDTO;
import com.capstone.booking.entity.dto.VisitorTypeDTO;
import com.capstone.booking.service.TicketTypeService;
import lombok.SneakyThrows;
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
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

public class TicketTypeControllerTest {

    @Mock
    private TicketTypeService mockTicketTypeService;

    @InjectMocks
    private TicketTypeController ticketTypeControllerUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testSearchAll() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockTicketTypeService).findAll();

        // Run the test
        final ResponseEntity<?> result = ticketTypeControllerUnderTest.searchAll();

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testSearchByPlaceId() throws ParseException {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockTicketTypeService).findByPlaceId(0L, new Date());

        // Run the test
        final ResponseEntity<?> result = ticketTypeControllerUnderTest.searchByPlaceId(0L, "31/12/2020");

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testDelete() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockTicketTypeService).delete(0L);

        // Run the test
        final ResponseEntity<?> result = ticketTypeControllerUnderTest.delete(0L);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testCreate() {
        // Setup
        final TicketTypeDTO model = new TicketTypeDTO();
        model.setTypeName("typeName");
        model.setGameId(new HashSet<>(Arrays.asList(0L)));
        model.setPlaceId(0L);
        final VisitorTypeDTO visitorTypeDTO = new VisitorTypeDTO();
        visitorTypeDTO.setTypeName("typeName");
        visitorTypeDTO.setTypeKey("typeKey");
        visitorTypeDTO.setTicketTypeId(0L);
        visitorTypeDTO.setPrice(0);
        visitorTypeDTO.setBasicType(false);
        visitorTypeDTO.setRemaining(0);
        visitorTypeDTO.setStatus("status");
        model.setVisitorTypes(Arrays.asList(visitorTypeDTO));
        model.setStatus("status");

        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockTicketTypeService).create(model);

        // Run the test
        final ResponseEntity<?> result = ticketTypeControllerUnderTest.create(model);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testUpdate() {
        // Setup
        final TicketTypeDTO model = new TicketTypeDTO();
        model.setTypeName("typeName");
        model.setGameId(new HashSet<>(Arrays.asList(0L)));
        model.setPlaceId(0L);
        final VisitorTypeDTO visitorTypeDTO = new VisitorTypeDTO();
        visitorTypeDTO.setTypeName("typeName");
        visitorTypeDTO.setTypeKey("typeKey");
        visitorTypeDTO.setTicketTypeId(0L);
        visitorTypeDTO.setPrice(0);
        visitorTypeDTO.setBasicType(false);
        visitorTypeDTO.setRemaining(0);
        visitorTypeDTO.setStatus("status");
        model.setVisitorTypes(Arrays.asList(visitorTypeDTO));
        model.setStatus("status");

        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockTicketTypeService).update(model);

        // Run the test
        final ResponseEntity<?> result = ticketTypeControllerUnderTest.update(model, 0L);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testChangeTicketTypeStatus() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockTicketTypeService).changeStatus(0L);

        // Run the test
        final ResponseEntity<?> result = ticketTypeControllerUnderTest.changeTicketTypeStatus(0L);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testGetTicketType() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockTicketTypeService).getTicketType(0L);

        // Run the test
        final ResponseEntity<?> result = ticketTypeControllerUnderTest.getTicketType(0L);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @SneakyThrows
    @Test
    public void testUploadFile() {
        // Setup
        File file = new File("Test.pdf");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockTicketTypeService).addCodeFromExcel(any(MultipartFile.class), eq(0L), new Date());

        // Run the test
        final ResponseEntity<?> result = ticketTypeControllerUnderTest.uploadFile(multipartFile, "0", "31/12/2020");

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }
}
