package com.capstone.booking.common.converter;

import com.capstone.booking.entity.City;
import com.capstone.booking.entity.Place;
import com.capstone.booking.entity.dto.CityDTO;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class CityConverterTest {

    private CityConverter cityConverterUnderTest;

    @Before
    public void setUp() {
        cityConverterUnderTest = new CityConverter();
    }

    @Test
    public void testToCity() {
        // Setup
        final CityDTO dto = new CityDTO();
        dto.setName("name");
        dto.setShortDescription("shortDescription");
        dto.setDetailDescription("detailDescription");
        dto.setImageLink("imageLink");

        final City expectedResult = new City();
        expectedResult.setName("name");
        expectedResult.setShortDescription("shortDescription");
        expectedResult.setDetailDescription("detailDescription");
        expectedResult.setImageLink("imageLink");
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
        expectedResult.setPlaces(new HashSet<>(Arrays.asList(place)));

        // Run the test
        final City result = cityConverterUnderTest.toCity(dto);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testToCity1() {
        // Setup
        final CityDTO dto = new CityDTO();
        dto.setName("name");
        dto.setShortDescription("shortDescription");
        dto.setDetailDescription("detailDescription");
        dto.setImageLink("imageLink");

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

        final City expectedResult = new City();
        expectedResult.setName("name");
        expectedResult.setShortDescription("shortDescription");
        expectedResult.setDetailDescription("detailDescription");
        expectedResult.setImageLink("imageLink");
        final Place place1 = new Place();
        place1.setName("name");
        place1.setPlaceKey("placeKey");
        place1.setAddress("address");
        place1.setDetailDescription("detailDescription");
        place1.setShortDescription("shortDescription");
        place1.setMail("mail");
        place1.setPhoneNumber("phoneNumber");
        place1.setStatus("status");
        place1.setLocation("location");
        place1.setCancelPolicy("cancelPolicy");
        expectedResult.setPlaces(new HashSet<>(Arrays.asList(place1)));

        // Run the test
        final City result = cityConverterUnderTest.toCity(dto, city);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testToDTO() {
        // Setup
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

        final CityDTO expectedResult = new CityDTO();
        expectedResult.setName("name");
        expectedResult.setShortDescription("shortDescription");
        expectedResult.setDetailDescription("detailDescription");
        expectedResult.setImageLink("imageLink");

        // Run the test
        final CityDTO result = cityConverterUnderTest.toDTO(city);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
