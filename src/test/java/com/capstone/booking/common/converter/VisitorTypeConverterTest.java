package com.capstone.booking.common.converter;

import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.VisitorTypeDTO;
import com.capstone.booking.repository.CodeRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VisitorTypeConverterTest {

    private VisitorTypeConverter visitorTypeConverterUnderTest;

    @Before
    public void setUp() {
        visitorTypeConverterUnderTest = new VisitorTypeConverter();
        visitorTypeConverterUnderTest.codeRepository = mock(CodeRepository.class);
    }

    @Test
    public void testToVisitorType() {
        // Setup
        final VisitorTypeDTO dto = new VisitorTypeDTO();
        dto.setTypeName("typeName");
        dto.setTypeKey("typeKey");
        dto.setTicketTypeId(0L);
        dto.setPrice(0);
        dto.setBasicType(false);
        dto.setRemaining(0);

        final VisitorType expectedResult = new VisitorType();
        expectedResult.setTypeName("typeName");
        expectedResult.setTypeKey("typeKey");
        expectedResult.setPrice(0);
        expectedResult.setBasicType(false);
        final TicketType ticketType = new TicketType();
        ticketType.setId(0l);
        ticketType.setTypeName("typeName");
        ticketType.setPlaceId(0L);

        // Run the test
        final VisitorType result = visitorTypeConverterUnderTest.toVisitorType(dto);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testToVisitorType1() {
        // Setup
        final VisitorTypeDTO dto = new VisitorTypeDTO();
        dto.setTypeName("typeName");
        dto.setTypeKey("typeKey");
        dto.setTicketTypeId(0L);
        dto.setPrice(0);
        dto.setBasicType(false);
        dto.setRemaining(0);
        dto.setStatus("status");

        final VisitorType visitorType = new VisitorType();
        visitorType.setTypeName("typeName");
        visitorType.setTypeKey("typeKey");
        visitorType.setPrice(0);
        visitorType.setBasicType(false);
        visitorType.setStatus("status");
        final TicketType ticketType = new TicketType();
        ticketType.setTypeName("typeName");
        ticketType.setPlaceId(0L);
        ticketType.setStatus("status");

        final VisitorType expectedResult = new VisitorType();
        expectedResult.setTypeName("typeName");
        expectedResult.setTypeKey("typeKey");
        expectedResult.setPrice(0);
        expectedResult.setBasicType(false);
        expectedResult.setStatus("status");
        final TicketType ticketType1 = new TicketType();
        ticketType1.setTypeName("typeName");
        ticketType1.setPlaceId(0L);
        ticketType1.setStatus("status");
        ticketType1.setVisitorType(new HashSet<>(Arrays.asList(visitorType)));

        // Run the test
        final VisitorType result = visitorTypeConverterUnderTest.toVisitorType(dto, visitorType);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testToDTO() {
        // Setup
        final VisitorType visitorType = new VisitorType();
        visitorType.setTypeName("typeName");
        visitorType.setTypeKey("typeKey");
        visitorType.setPrice(0);
        visitorType.setBasicType(false);
        visitorType.setStatus("status");
        final TicketType ticketType = new TicketType();
        ticketType.setTypeName("typeName");
        ticketType.setPlaceId(0L);
        ticketType.setStatus("status");
        ticketType.setId(0l);
        visitorType.setTicketType(ticketType);

        final VisitorTypeDTO expectedResult = new VisitorTypeDTO();
        expectedResult.setTypeName("typeName");
        expectedResult.setTypeKey("typeKey");
        expectedResult.setTicketTypeId(0L);
        expectedResult.setPrice(0);
        expectedResult.setBasicType(false);
        expectedResult.setRemaining(0);
        expectedResult.setStatus("status");

        // Configure CodeRepository.findByVisitorType(...).
        final Code code1 = new Code();
        code1.setCode("code");
        final VisitorType visitorType1 = new VisitorType();
        visitorType1.setTypeName("typeName");
        visitorType1.setTypeKey("typeKey");
        visitorType1.setPrice(0);
        visitorType1.setBasicType(false);
        visitorType1.setStatus("status");
        final TicketType ticketType1 = new TicketType();
        ticketType1.setTypeName("typeName");
        ticketType1.setPlaceId(0L);
        ticketType1.setId(0l);
        ticketType1.setStatus("status");
        code1.setVisitorType(visitorType1);
        final List<Code> codes = Arrays.asList(code1);
        when(visitorTypeConverterUnderTest.codeRepository.findByVisitorType(new VisitorType())).thenReturn(codes);

        // Run the test
        final VisitorTypeDTO result = visitorTypeConverterUnderTest.toDTO(visitorType);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
