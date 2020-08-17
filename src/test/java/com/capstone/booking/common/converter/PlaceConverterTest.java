package com.capstone.booking.common.converter;

import com.capstone.booking.entity.Category;
import com.capstone.booking.entity.City;
import com.capstone.booking.entity.Place;
import com.capstone.booking.entity.dto.PlaceDTO;
import com.capstone.booking.entity.dto.PlaceDTOClient;
import com.capstone.booking.entity.dto.lite.PlaceDTOLite;
import com.capstone.booking.repository.CategoryRepository;
import com.capstone.booking.repository.CityRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PlaceConverterTest {

    @Mock
    private CategoryRepository mockCategoryRepository;
    @Mock
    private ImageConverter mockImageConverter;
    @Mock
    private CityRepository mockCityRepository;

    @InjectMocks
    private PlaceConverter placeConverterUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testToPlace() {
        // Setup
        final PlaceDTO dto = new PlaceDTO();
        dto.setName("name");
        dto.setPlaceKey("placeKey");
        dto.setAddress("address");
        dto.setShortDescription("shortDescription");
        dto.setDetailDescription("detailDescription");
        dto.setMail("mail");
        dto.setPhoneNumber("phoneNumber");
        dto.setPlaceImageLink(new HashSet<>(Arrays.asList("value")));
        dto.setCityId(0L);
        dto.setCityName("cityName");

        // Configure CategoryRepository.findById(...).
        final Optional<Category> category = Optional.of(new Category("typeName", "typeKey", "description"));
        when(mockCategoryRepository.findById(0L)).thenReturn(category);

        // Configure CityRepository.findById(...).
        final City city1 = new City();
        city1.setName("name");
        city1.setShortDescription("shortDescription");
        city1.setDetailDescription("detailDescription");
        city1.setImageLink("imageLink");
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
        city1.setPlaces(new HashSet<>(Arrays.asList(place)));
        final Optional<City> city = Optional.of(city1);
        when(mockCityRepository.findById(0L)).thenReturn(city);

        // Run the test
        final Place result = placeConverterUnderTest.toPlace(dto);

        // Verify the results
    }

    @Test
    public void testToPlace1() {
        // Setup
        final PlaceDTO dto = new PlaceDTO();
        dto.setName("name");
        dto.setPlaceKey("placeKey");
        dto.setAddress("address");
        dto.setShortDescription("shortDescription");
        dto.setDetailDescription("detailDescription");
        dto.setMail("mail");
        dto.setPhoneNumber("phoneNumber");
        dto.setPlaceImageLink(new HashSet<>(Arrays.asList("value")));
        dto.setCityId(0L);
        dto.setCityName("cityName");
        List<Integer> weekDays = new ArrayList<>();
        weekDays.add(1);
        weekDays.add(2);
        weekDays.add(3);
        dto.setWeekDays(weekDays);

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
        place.setWeekDays("1,2,3");

        // Configure CategoryRepository.findById(...).
        final Optional<Category> category = Optional.of(new Category("typeName", "typeKey", "description"));
        when(mockCategoryRepository.findById(0L)).thenReturn(category);

        // Configure CityRepository.findById(...).
        final City city1 = new City();
        city1.setName("name");
        city1.setShortDescription("shortDescription");
        city1.setDetailDescription("detailDescription");
        city1.setImageLink("imageLink");
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
        place1.setWeekDays("1,2,3");
        city1.setPlaces(new HashSet<>(Arrays.asList(place1)));
        final Optional<City> city = Optional.of(city1);
        when(mockCityRepository.findById(0L)).thenReturn(city);

        // Run the test
        final Place result = placeConverterUnderTest.toPlace(dto, place);

        // Verify the results
    }

    @Test
    public void testToPlaceLite() {
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

        final PlaceDTOLite expectedResult = new PlaceDTOLite();
        expectedResult.setName("name");

        // Run the test
        final PlaceDTOLite result = placeConverterUnderTest.toPlaceLite(place);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testToDTO() {
        // Setup
        final Place place = new Place();
        City city = new City();
        city.setId(0L);
        city.setName("cityName");
        Category category = new Category();
        category.setId(1l);
        Set<Category> categories = new HashSet<>();
        categories.add(category);
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
        place.setWeekDays("1,2,3");
        place.setCity(city);
        place.setCategories(categories);

        final PlaceDTO expectedResult = new PlaceDTO();
        expectedResult.setName("name");
        expectedResult.setPlaceKey("placeKey");
        expectedResult.setAddress("address");
        expectedResult.setShortDescription("shortDescription");
        expectedResult.setDetailDescription("detailDescription");
        expectedResult.setMail("mail");
        expectedResult.setPhoneNumber("phoneNumber");
        expectedResult.setCityId(0L);
        expectedResult.setCityName("cityName");
        List<Integer> weekDays = new ArrayList<>();
        weekDays.add(1);
        weekDays.add(2);
        weekDays.add(3);
        expectedResult.setWeekDays(weekDays);
        expectedResult.setStatus("status");
        Set<Long> longs = new HashSet<>();
        longs.add(1l);
        expectedResult.setCategoryId(longs);
        expectedResult.setCancelPolicy("cancelPolicy");
        // Run the test
        final PlaceDTO result = placeConverterUnderTest.toDTO(place);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testToPlaceClient() {
        // Setup
        final Place place = new Place();
        Category category = new Category();
        category.setId(1l);
        Set<Category> categories = new HashSet<>();
        categories.add(category);
        place.setName("name");
        place.setCategories(categories);
        place.setPlaceKey("placeKey");
        place.setAddress("address");
        place.setDetailDescription("detailDescription");
        place.setShortDescription("shortDescription");
        place.setMail("mail");
        place.setPhoneNumber("phoneNumber");
        place.setStatus("status");
        place.setLocation("location");
        place.setCancelPolicy("cancelPolicy");
        place.setWeekDays("1,2,3");

        final PlaceDTOClient expectedResult = new PlaceDTOClient();
        expectedResult.setName("name");
        expectedResult.setPlaceKey("placeKey");
        expectedResult.setAddress("address");
        expectedResult.setShortDescription("shortDescription");
        expectedResult.setDetailDescription("detailDescription");
        expectedResult.setMail("mail");
        expectedResult.setPhoneNumber("phoneNumber");
        List<Integer> weekDays = new ArrayList<>();
        weekDays.add(1);
        weekDays.add(2);
        weekDays.add(3);
        expectedResult.setWeekDays(weekDays);
        Set<Long> longs = new HashSet<>();
        longs.add(1l);
        expectedResult.setCategoryId(longs);
        expectedResult.setCancelPolicy("cancelPolicy");
        expectedResult.setStatus("status");
        // Run the test
        final PlaceDTOClient result = placeConverterUnderTest.toPlaceClient(place);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
