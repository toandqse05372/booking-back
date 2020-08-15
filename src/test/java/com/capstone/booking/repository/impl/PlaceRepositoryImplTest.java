package com.capstone.booking.repository.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.PlaceConverter;
import com.capstone.booking.entity.Place;
import com.capstone.booking.entity.dto.PlaceDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PlaceRepositoryImplTest {

    @Mock
    private EntityManager mockEntityManager;

    private PlaceRepositoryImpl placeRepositoryImplUnderTest;

    @Mock
    Query query;

    @Before
    public void setUp() {
        initMocks(this);
        placeRepositoryImplUnderTest = new PlaceRepositoryImpl(mockEntityManager);
        placeRepositoryImplUnderTest.placeConverter = mock(PlaceConverter.class);
    }

    @Test
    public void testFindByMultiParam() {
        // Setup
        final Output expectedResult = new Output();
        Place place = new Place();
        place.setId(1l);
        place.setName("name");
        List<Place> placeList = Arrays.asList(place);
        PlaceDTO placeDTO = new PlaceDTO();
        placeDTO.setId(1l);
        placeDTO.setName("name");
        expectedResult.setPage(1);
        expectedResult.setTotalPage(1);
        expectedResult.setListResult(Arrays.asList(placeDTO));
        expectedResult.setTotalItems(1);
        BigInteger counter = BigInteger.valueOf(1);
        String sqlStr = "select count(place0_.id) from t_place place0_ INNER join t_place_category ppt on place0_.id = ppt.place_id where  ppt.category_id = :ptid  and place0_.name like :name  and place0_.address like :address  and place0_.city_id = :cid ";
        when(mockEntityManager.createNativeQuery( sqlStr )).thenReturn(query);
        when(query.setParameter("name", "")).thenReturn(query);
        when(query.setParameter("address", "")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(counter);
        when(mockEntityManager.createNativeQuery("select place0_.* from t_place place0_ INNER join t_place_category ppt on place0_.id = ppt.place_id where  ppt.category_id = :ptid  and place0_.name like :name  and place0_.address like :address  and place0_.city_id = :cid limit :from, :limit", Place.class)).thenReturn(query);
        when(query.setParameter("from", 1)).thenReturn(query);
        when(query.setParameter("limit", 1)).thenReturn(query);
        when(query.getResultList()).thenReturn(placeList);
        when(placeRepositoryImplUnderTest.placeConverter.toDTO(place)).thenReturn(placeDTO);

        // Run the test
        final Output result = placeRepositoryImplUnderTest.findByMultiParam("name", "address", 1L, 1L, 1L, 1L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testFindByMultiParamForClient() {
        // Setup
        final List<Long> cityId = Arrays.asList(1L);
        final List<Long> categoryId = Arrays.asList(1L);
        final Output expectedResult = new Output();
        Place place = new Place();
        place.setId(1l);
        place.setName("name");
        List<Place> placeList = Arrays.asList(place);
        PlaceDTO placeDTO = new PlaceDTO();
        placeDTO.setId(1l);
        placeDTO.setName("name");
        expectedResult.setPage(1);
        expectedResult.setTotalPage(1);
        expectedResult.setListResult(Arrays.asList(placeDTO));
        expectedResult.setTotalItems(1);

        BigInteger counter = BigInteger.valueOf(1);

        when(mockEntityManager.createNativeQuery("select count(*) from (select count(place0_.id) from t_place place0_ INNER join t_place_category ppt on place0_.id = ppt.place_id where  place0_.status like 'ACTIVE'  and ppt.category_id in ( 1)  and place0_.name like :name  and place0_.city_id in ( 1) and place0_.id \n" +
                "in ( select tt.place_id from t_ticket_type tt \n" +
                "inner join t_visitor_type vt on tt.id = vt.ticket_type_id )\n" +
                "group by place0_.id ) placecount")).thenReturn(query);
        when(query.setParameter("name", "")).thenReturn(query);
        when(query.setParameter("address", "")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(counter);
        when(mockEntityManager.createNativeQuery("select place0_.* from t_place place0_ INNER join t_place_category ppt on place0_.id = ppt.place_id where  place0_.status like 'ACTIVE'  and ppt.category_id in ( 1)  and place0_.name like :name  and place0_.city_id in ( 1) and place0_.id \n" +
                "in ( select tt.place_id from t_ticket_type tt \n" +
                "inner join t_visitor_type vt on tt.id = vt.ticket_type_id )\n" +
                "group by place0_.id limit :from, :limit", Place.class)).thenReturn(query);
        when(query.setParameter("from", 1)).thenReturn(query);
        when(query.setParameter("limit", 1)).thenReturn(query);
        when(query.getResultList()).thenReturn(placeList);
        when(placeRepositoryImplUnderTest.placeConverter.toDTO(place)).thenReturn(placeDTO);
        // Run the test
        final Output result = placeRepositoryImplUnderTest.findByMultiParamForClient("name", 0L, 0L, cityId, categoryId, 1L, 1L);

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

        when(placeRepositoryImplUnderTest.placeConverter.toDTO(place)).thenReturn(placeDTO);
        // Run the test
        final List<PlaceDTO> result = placeRepositoryImplUnderTest.convertList(placeList);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testQueryPlace() {
        // Setup
        Place place = new Place();
        place.setId(1l);
        place.setName("name");
        final Map<String, Object> params = new HashMap<>();
        when(mockEntityManager.createNativeQuery("select place0_.* from t_place place0_", Place.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(place));
        // Run the test
        final List<Place> result = placeRepositoryImplUnderTest.queryPlace(params, "select place0_.* from t_place place0_");

        // Verify the results
        assertThat(result).isEqualTo(Arrays.asList(place));
    }

    @Test
    public void testCountPlace() {
        // Setup
        BigInteger expectedResult = BigInteger.valueOf(0);
        final Map<String, Object> params = new HashMap<>();
        when(mockEntityManager.createNativeQuery("select count(place0_.id) from t_place place0_ ")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(expectedResult);
        // Run the test
        final int result = placeRepositoryImplUnderTest.countPlace(params, "select count(place0_.id) from t_place place0_ ", "CMS");

        // Verify the results
        assertThat(result).isEqualTo(0);
    }
}
