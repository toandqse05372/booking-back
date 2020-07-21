package com.capstone.booking.service;

import com.capstone.booking.common.converter.PlaceConverter;
import com.capstone.booking.entity.Place;
import com.capstone.booking.entity.dto.PlaceDTO;
import com.capstone.booking.repository.PlaceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PlaceServiceTest {
    private String existedPlace = "Công viên mặt trời";
    private String existedPlace2 = "Công viên mặt trời 9";
    private String newPlace_add = "Công viên mặt trời 69";
    private String newPlace_add_to_read = "Công viên mặt trời 691";
    private String newPlace_update = "new_place";

    @Autowired
    PlaceService placeService;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    PlaceConverter placeConverter;

    //test if service is ready
    @Test
    void serviceNotNullTests(){ assertNotNull(placeService); }

    //create new place with new name, return new place
    @Test
    void create_place_with_new_name() {
        PlaceDTO dto = new PlaceDTO();
        dto.setName(newPlace_add);
        ResponseEntity entity = placeService.create(dto, null);
        PlaceDTO result = (PlaceDTO) entity.getBody();
        assertEquals(dto.getName(), result.getName());
    }

    //create new place with existed name, catch error
    @Test
    void create_place_with_existed_name() {
        PlaceDTO dto = new PlaceDTO();
        dto.setName(existedPlace);
        ResponseEntity entity = placeService.create(dto, null);
        assertEquals("PLACE_EXISTED", entity.getBody().toString());
    }

    //create old place with existed name, catch error
    @Test
    void update_place_with_existed_name(){
        Place place = placeRepository.findByName(existedPlace);
        PlaceDTO existedResponse = placeConverter.toDTO(place);
        existedResponse.setName(existedPlace2);
        ResponseEntity entity = placeService.update(existedResponse, null);
        assertEquals("PLACE_EXISTED", entity.getBody().toString());
    }

    //read place existed
    @Test
    void read_place_existed(){
        Place place = new Place();
        place.setName(newPlace_add);
        Place saved = placeRepository.save(place);
        ResponseEntity entity = placeService.getPlace(saved.getId());
        PlaceDTO dto = (PlaceDTO) entity.getBody();
        assertEquals(newPlace_add_to_read, dto.getName());
    }

    //update old place with existed name, catch error
    @Test
    void update_place_with_new_name(){
        Place place = placeRepository.findByName(existedPlace);
        PlaceDTO existedResponse = placeConverter.toDTO(place);
        existedResponse.setName(newPlace_update);
        ResponseEntity entity = placeService.update(existedResponse, null);
        Place updatedPlace = placeRepository.findByName(newPlace_update);
        assertEquals(newPlace_update, updatedPlace.getName());
    }

    //delete existed place, return null
    @Test
    void delete_place_with_existed_place(){
        Place place = placeRepository.findByName(existedPlace);
        placeService.delete(place.getId());
        assertNull(placeRepository.findByName(existedPlace));
    }

    //delete not existed place, catch error
    @Test
    void delete_place_with_not_existed_place(){
        ResponseEntity entity = placeService.delete(-1);
        assertEquals("PLACE_NOT_FOUND", entity.getBody().toString());
    }
}