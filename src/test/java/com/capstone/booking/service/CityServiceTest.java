package com.capstone.booking.service;

import com.capstone.booking.common.converter.CityConverter;
import com.capstone.booking.entity.City;
import com.capstone.booking.entity.dto.CityDTO;
import com.capstone.booking.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CityServiceTest {

    private String existedCity = "Hà Nội";
    private String existedCity2 = "Hà Nội 9";
    private String newCity_add = "Hà Nội 69";
    private String newCity_add_to_read = "Hà Nội 691";
    private String newCity_update = "new_city";

    @Autowired
    CityService cityService;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    CityConverter cityConverter;

    //test if service is ready
    @Test
    void serviceNotNullTests(){ assertNotNull(cityService); }

    //create new city with new name, return new city
    @Test
    void create_city_with_new_name() {
        CityDTO dto = new CityDTO();
        dto.setName(newCity_add);
        ResponseEntity entity = cityService.create(dto, null);
        CityDTO result = (CityDTO) entity.getBody();
        assertEquals(dto.getName(), result.getName());
    }

    //create new city with existed name, catch error
    @Test
    void create_city_with_existed_name() {
        CityDTO dto = new CityDTO();
        dto.setName(existedCity);
        ResponseEntity entity = cityService.create(dto, null);
        assertEquals("CITY_EXISTED", entity.getBody().toString());
    }

    //create old city with existed name, catch error
    @Test
    void update_city_with_existed_name(){
        City city = cityRepository.findByName(existedCity);
        CityDTO existedResponse = cityConverter.toDTO(city);
        existedResponse.setName(existedCity2);
        ResponseEntity entity = cityService.update(existedResponse, null);
        assertEquals("CITY_EXISTED", entity.getBody().toString());
    }

    //read city existed
    @Test
    void read_city_existed(){
        City city = new City();
        city.setName(newCity_add);
        City saved = cityRepository.save(city);
        ResponseEntity entity = cityService.getCity(saved.getId());
        CityDTO dto = (CityDTO) entity.getBody();
        assertEquals(newCity_add_to_read, dto.getName());
    }

    //update old city with existed name, catch error
    @Test
    void update_city_with_new_name(){
        City city = cityRepository.findByName(existedCity);
        CityDTO existedResponse = cityConverter.toDTO(city);
        existedResponse.setName(newCity_update);
        ResponseEntity entity = cityService.update(existedResponse, null);
        CityDTO updatedCity = (CityDTO) entity.getBody();
        assertEquals(newCity_update, updatedCity.getName());
    }

    //delete existed city, return null
    @Test
    void delete_city_with_existed_city(){
        City city = cityRepository.findByName(existedCity);
        cityService.delete(city.getId());
        assertNull(cityRepository.findByName(existedCity));
    }

    //delete not existed city, catch error
    @Test
    void delete_city_with_not_existed_city(){
        ResponseEntity entity = cityService.delete(-1);
        assertEquals("CITY_NOT_FOUND", entity.getBody().toString());
    }

}