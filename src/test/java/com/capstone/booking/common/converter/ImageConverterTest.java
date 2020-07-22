package com.capstone.booking.common.converter;

import com.capstone.booking.entity.ImagePlace;
import com.capstone.booking.entity.Place;
import com.capstone.booking.entity.dto.ImageDTO;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ImageConverterTest {

    private ImageConverter imageConverterUnderTest;

    @Before
    public void setUp() {
        imageConverterUnderTest = new ImageConverter();
    }

    @Test
    public void testToDTO() {
        // Setup
        final ImagePlace imagePlace = new ImagePlace();
        imagePlace.setImageLink("imageLink");
        imagePlace.setImageName("imageName");
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
        imagePlace.setPlace(place);

        final ImageDTO expectedResult = new ImageDTO();
        expectedResult.setImageLink("imageLink");

        // Run the test
        final ImageDTO result = imageConverterUnderTest.toDTO(imagePlace);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
