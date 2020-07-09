package com.capstone.booking.service;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.entity.dto.CityDTO;
import com.capstone.booking.repository.CityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
public class CityServiceTest {

    private String existedCity = "Hà Nội";
    private String newCity_add = "Hà Nội 69";
    private String existedCity_forDelete = "Hà Nội 23";
    private String unknowCity = "unknow";

    @Autowired
    CityService cityService;

    @Test
    void serviceNotNullTests(){ assertNotNull(cityService); }

    @Test
    void find_By_Unknow_Name() {
        //unknow input
        assertEquals(0, countResult(unknowCity));
    }

    @Test
    void find_By_Existed_Name(){
        //right input
        assertTrue(countResult(existedCity) > 0);
    }

    @Test
    void create_city_with_new_name() {
        CityDTO dto = new CityDTO();
        dto.setName(newCity_add);
        ResponseEntity entity = cityService.create(dto);
        CityDTO result = (CityDTO) entity.getBody();
        assertEquals(dto.getName(), result.getName());
    }

    @Test
    void create_city_with_existed_name() {
        CityDTO dto = new CityDTO();
        dto.setName(existedCity);
        ResponseEntity entity = cityService.create(dto);
        assertEquals(400, entity.getStatusCodeValue());
    }

    private int countResult(String input){
        ResponseEntity entity = cityService.findByName(input,10l,1l);
        Output output = (Output) entity.getBody();
        return output.getListResult().size();
    }


}