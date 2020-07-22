package com.capstone.booking.repository.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.CityConverter;
import com.capstone.booking.entity.City;
import com.capstone.booking.entity.Place;
import com.capstone.booking.entity.dto.CityDTO;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CityRepositoryImplTest {

    private CityRepositoryImpl cityRepositoryImplUnderTest;

    @Before
    public void setUp() {
        cityRepositoryImplUnderTest = new CityRepositoryImpl();
        cityRepositoryImplUnderTest.cityConverter = mock(CityConverter.class);
    }

    @Test
    public void testFindByName() {
        // Setup
        final Output expectedResult = new Output();
        expectedResult.setPage(0);
        expectedResult.setTotalPage(0);
        expectedResult.setListResult(Arrays.asList());
        expectedResult.setTotalItems(0);

        // Configure CityConverter.toDTO(...).
        final CityDTO dto = new CityDTO();
        dto.setName("name");
        dto.setShortDescription("shortDescription");
        dto.setDetailDescription("detailDescription");
        dto.setImageLink("imageLink");
        when(cityRepositoryImplUnderTest.cityConverter.toDTO(new City())).thenReturn(dto);

        // Run the test
        final Output result = cityRepositoryImplUnderTest.findByName("name", 0L, 0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testConvertList() {
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
        final List<City> cityList = Arrays.asList(city);
        final CityDTO dto = new CityDTO();
        dto.setName("name");
        dto.setShortDescription("shortDescription");
        dto.setDetailDescription("detailDescription");
        dto.setImageLink("imageLink");
        final List<CityDTO> expectedResult = Arrays.asList(dto);

        // Configure CityConverter.toDTO(...).
        final CityDTO dto1 = new CityDTO();
        dto1.setName("name");
        dto1.setShortDescription("shortDescription");
        dto1.setDetailDescription("detailDescription");
        dto1.setImageLink("imageLink");
        when(cityRepositoryImplUnderTest.cityConverter.toDTO(new City())).thenReturn(dto1);

        // Run the test
        final List<CityDTO> result = cityRepositoryImplUnderTest.convertList(cityList);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testQueryCity() {
        // Setup
        final Map<String, Object> params = new HashMap<>();
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
        final List<City> expectedResult = Arrays.asList(city);

        // Run the test
        final List<City> result = cityRepositoryImplUnderTest.queryCity(params, "sqlStr");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
