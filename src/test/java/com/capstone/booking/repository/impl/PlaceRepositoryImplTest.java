package com.capstone.booking.repository.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.PlaceConverter;
import com.capstone.booking.entity.Place;
import com.capstone.booking.entity.dto.PlaceDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PlaceRepositoryImplTest {

    @Mock
    private PlaceConverter mockPlaceConverter;

    @InjectMocks
    private PlaceRepositoryImpl placeRepositoryImplUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testFindByMultiParam() {
        // Setup
        final Output expectedResult = new Output();
        expectedResult.setPage(0);
        expectedResult.setTotalPage(0);
        expectedResult.setListResult(Arrays.asList());
        expectedResult.setTotalItems(0);

        // Configure PlaceConverter.toDTO(...).
        final PlaceDTO placeDTO = new PlaceDTO();
        placeDTO.setName("name");
        placeDTO.setPlaceKey("placeKey");
        placeDTO.setAddress("address");
        placeDTO.setShortDescription("shortDescription");
        placeDTO.setDetailDescription("detailDescription");
        placeDTO.setMail("mail");
        placeDTO.setPhoneNumber("phoneNumber");
        placeDTO.setPlaceImageLink(new HashSet<>(Arrays.asList("value")));
        placeDTO.setCityId(0L);
        placeDTO.setCityName("cityName");
        when(mockPlaceConverter.toDTO(any(Place.class))).thenReturn(placeDTO);

        // Run the test
        final Output result = placeRepositoryImplUnderTest.findByMultiParam("name", "address", 0L, 0L, 0L, 0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testFindByMultiParamForClient() {
        // Setup
        final List<Long> cityId = Arrays.asList(0L);
        final List<Long> categoryId = Arrays.asList(0L);
        final Output expectedResult = new Output();
        expectedResult.setPage(0);
        expectedResult.setTotalPage(0);
        expectedResult.setListResult(Arrays.asList());
        expectedResult.setTotalItems(0);

        // Configure PlaceConverter.toDTO(...).
        final PlaceDTO placeDTO = new PlaceDTO();
        placeDTO.setName("name");
        placeDTO.setPlaceKey("placeKey");
        placeDTO.setAddress("address");
        placeDTO.setShortDescription("shortDescription");
        placeDTO.setDetailDescription("detailDescription");
        placeDTO.setMail("mail");
        placeDTO.setPhoneNumber("phoneNumber");
        placeDTO.setPlaceImageLink(new HashSet<>(Arrays.asList("value")));
        placeDTO.setCityId(0L);
        placeDTO.setCityName("cityName");
        when(mockPlaceConverter.toDTO(any(Place.class))).thenReturn(placeDTO);

        // Run the test
        final Output result = placeRepositoryImplUnderTest.findByMultiParamForClient("name", 0L, 0L, cityId, categoryId, 0L, 0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testConvertList() {
        // Setup
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
        final List<Place> placeList = Arrays.asList(place);
        final PlaceDTO placeDTO = new PlaceDTO();
        placeDTO.setName("name");
        placeDTO.setPlaceKey("placeKey");
        placeDTO.setAddress("address");
        placeDTO.setShortDescription("shortDescription");
        placeDTO.setDetailDescription("detailDescription");
        placeDTO.setMail("mail");
        placeDTO.setPhoneNumber("phoneNumber");
        placeDTO.setPlaceImageLink(new HashSet<>(Arrays.asList("value")));
        placeDTO.setCityId(0L);
        placeDTO.setCityName("cityName");
        final List<PlaceDTO> expectedResult = Arrays.asList(placeDTO);

        // Configure PlaceConverter.toDTO(...).
        final PlaceDTO placeDTO1 = new PlaceDTO();
        placeDTO1.setName("name");
        placeDTO1.setPlaceKey("placeKey");
        placeDTO1.setAddress("address");
        placeDTO1.setShortDescription("shortDescription");
        placeDTO1.setDetailDescription("detailDescription");
        placeDTO1.setMail("mail");
        placeDTO1.setPhoneNumber("phoneNumber");
        placeDTO1.setPlaceImageLink(new HashSet<>(Arrays.asList("value")));
        placeDTO1.setCityId(0L);
        placeDTO1.setCityName("cityName");
        when(mockPlaceConverter.toDTO(any(Place.class))).thenReturn(placeDTO1);

        // Run the test
        final List<PlaceDTO> result = placeRepositoryImplUnderTest.convertList(placeList);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testQueryPlace() {
        // Setup
        final Map<String, Object> params = new HashMap<>();

        // Run the test
        final List<Place> result = placeRepositoryImplUnderTest.queryPlace(params, "sqlStr");

        // Verify the results
    }
}
