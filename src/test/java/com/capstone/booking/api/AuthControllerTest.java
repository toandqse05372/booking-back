package com.capstone.booking.api;

import com.capstone.booking.entity.dto.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    public void testLogin_Existed_User() throws Exception {
        assertEquals(200, userCase("test@test.com", "test",
                MockMvcResultMatchers.status().isOk()).getResponse().getStatus());
    }

    @Test
    public void testLogin_Wrong_Email() throws Exception {
        assertEquals(401, userCase("tes222t@test.com", "test",
            MockMvcResultMatchers.status().isUnauthorized()).getResponse().getStatus());
    }

    @Test
    public void testLogin_Wrong_Password() throws Exception {
        assertEquals(401, userCase("test@test.com", "test999999999",
                MockMvcResultMatchers.status().isUnauthorized()).getResponse().getStatus());
    }



    private MvcResult userCase(String mail, String password, ResultMatcher expert) throws Exception {
        UserDTO dto = new UserDTO();
        dto.setPassword(password);
        dto.setMail(mail);
        String jsonRequest = mapper.writeValueAsString(dto);
        return mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(expert).andReturn();

    }

    private MvcResult fbCase(String mail, String password, ResultMatcher expert) throws Exception {
        UserDTO dto = new UserDTO();
        dto.setPassword(password);
        dto.setMail(mail);
        String jsonRequest = mapper.writeValueAsString(dto);
        return mockMvc.perform(MockMvcRequestBuilders.post("/login/fb")
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(expert).andReturn();

    }
}