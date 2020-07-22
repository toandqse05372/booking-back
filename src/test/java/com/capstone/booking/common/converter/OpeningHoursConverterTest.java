package com.capstone.booking.common.converter;

import com.capstone.booking.entity.OpeningHours;
import com.capstone.booking.entity.Place;
import com.capstone.booking.entity.dto.OpeningHoursDTO;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.assertj.core.api.Assertions.assertThat;

public class OpeningHoursConverterTest {

    private OpeningHoursConverter openingHoursConverterUnderTest;

    @Before
    public void setUp() {
        openingHoursConverterUnderTest = new OpeningHoursConverter();
    }

    @Test
    public void testToDTO() {
        // Setup
        final OpeningHours hours = new OpeningHours();
        hours.setWeekDay("weekDay");
        hours.setOpenHours(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        hours.setCloseHours(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
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
        hours.setPlace(place);

        final OpeningHoursDTO expectedResult = new OpeningHoursDTO();
        expectedResult.setWeekDay("weekDay");
        expectedResult.setOpenHours(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        expectedResult.setCloseHours(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());

        // Run the test
        final OpeningHoursDTO result = openingHoursConverterUnderTest.toDTO(hours);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
