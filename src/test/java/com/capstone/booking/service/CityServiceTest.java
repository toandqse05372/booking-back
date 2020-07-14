package com.capstone.booking.service;

import com.capstone.booking.api.output.Output;
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
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
public class CityServiceTest {

    private String existedCity = "Hà Nội";
    private String newCity_add = "Hà Nội 69";
    private String existedCity_toFind = "HCM999";
    private String unknowCity = "unknow";

    @Autowired
    CityService cityService;

    @Autowired
    CityRepository cityRepository;

    @Test
    void serviceNotNullTests(){ assertNotNull(cityService); }

    @Test
    void create_city_with_new_name() {
        CityDTO dto = new CityDTO();
        dto.setName(newCity_add);
        ResponseEntity entity = cityService.create(dto, null);
        CityDTO result = (CityDTO) entity.getBody();
        assertEquals(dto.getName(), result.getName());
    }

    @Test
    void create_city_with_existed_name() {
        CityDTO dto = new CityDTO();
        dto.setName(existedCity);
        ResponseEntity entity = cityService.create(dto, null);
        assertEquals(400, entity.getStatusCodeValue());
    }

    @Test
    void find_By_Unknow_Name() {
        //unknow input
        City city = new City();
        city.setName(existedCity_toFind);
        assertEquals(0, countResult(unknowCity));
    }

    @Test
    void find_By_Existed_Name(){
        //right input
        City city = new City();
        city.setName("new City");
        assertTrue(countResult("new City") > 0);
    }

    private int countResult(String input){
        ResponseEntity entity = cityService.findByName(input,10l,1l);
        Output output = (Output) entity.getBody();
        return output.getListResult().size();
    }


}