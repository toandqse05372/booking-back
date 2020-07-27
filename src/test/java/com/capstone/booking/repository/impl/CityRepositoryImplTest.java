package com.capstone.booking.repository.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.CategoryConverter;
import com.capstone.booking.common.converter.CityConverter;
import com.capstone.booking.entity.Category;
import com.capstone.booking.entity.City;
import com.capstone.booking.entity.Place;
import com.capstone.booking.entity.dto.CityDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CityRepositoryImplTest {

    @Mock
    private EntityManager mockEntityManager;

    private CityRepositoryImpl cityRepositoryImplUnderTest;

    @Mock
    Query query;

    @Before
    public void setUp() {
        initMocks(this);
        cityRepositoryImplUnderTest = new CityRepositoryImpl(mockEntityManager);
        cityRepositoryImplUnderTest.cityConverter = mock(CityConverter.class);
    }

    @Test
    public void testFindByName() {
        // Setup
        final Output expectedResult = new Output();
        expectedResult.setPage(1);
        expectedResult.setTotalPage(1);
        expectedResult.setListResult(Arrays.asList());
        expectedResult.setTotalItems(1);

        BigInteger counter = BigInteger.valueOf(1);
        when(mockEntityManager.createNativeQuery("select count(c.id) from t_city c  where c.name like :cname ")).thenReturn(query);
        when(query.setParameter("cname","")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(counter);
        when(mockEntityManager.createNativeQuery("select c.* from t_city c  where c.name like :cname  limit :from, :limit", City.class)).thenReturn(query);
        when(query.setParameter("from",1)).thenReturn(query);
        when(query.setParameter("limit",1)).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList());

        // Run the test
        final Output result = cityRepositoryImplUnderTest.findByName("name", 01L, 1L);

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
        final CityDTO cityDTO = new CityDTO();
        cityDTO.setName("name");
        cityDTO.setShortDescription("shortDescription");
        cityDTO.setDetailDescription("detailDescription");
        cityDTO.setImageLink("imageLink");
        final List<CityDTO> expectedResult = Arrays.asList(cityDTO);
        when(cityRepositoryImplUnderTest.cityConverter.toDTO(city)).thenReturn(cityDTO);
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
        when(mockEntityManager.createNativeQuery("select * from t_city", City.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedResult);

        // Run the test
        final List<City> result = cityRepositoryImplUnderTest.queryCity(params, "select * from t_city");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testCountCity() {
        // Setup
        final Map<String, Object> params = new HashMap<>();
        BigInteger expectedResult = BigInteger.valueOf(0);
        when(mockEntityManager.createNativeQuery("select count(pt.id) from t_city pt")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(expectedResult);

        // Run the test
        final int result = cityRepositoryImplUnderTest.countCity(params, "select count(pt.id) from t_city pt");

        // Verify the results
        assertThat(result).isEqualTo(0);
    }
}
