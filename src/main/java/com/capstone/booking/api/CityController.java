package com.capstone.booking.api;

import com.capstone.booking.entity.dto.CategoryDTO;
import com.capstone.booking.entity.dto.CityDTO;
import com.capstone.booking.service.CityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class CityController {
    @Autowired
    private CityService cityService;

    //getAllCity
    @GetMapping("/city")
    public ResponseEntity<?> getAllCity() {
        return cityService.findAllCity();
    }

    //search by Id
    @GetMapping("/city/{id}")
    public ResponseEntity<?> getCity(@PathVariable Long id) {
        return cityService.getCity(id);
    }

    //search cityName & paging
    @GetMapping("/city/searchByName")
    @PreAuthorize("hasAnyAuthority('CITY_EDIT')")
    public ResponseEntity<?> searchByName(@RequestParam(value = "name", required = false) String name,
                                          @RequestParam(value = "limit", required = false) Long limit,
                                          @RequestParam(value = "page", required = false) Long page) {
        return cityService.findByName(name, limit, page);
    }

    //delete City
    @DeleteMapping("/city/{id}")
    @PreAuthorize("hasAnyAuthority('CITY_EDIT')")
    public ResponseEntity<?> deleteCity(@PathVariable("id") long id) {
        return cityService.delete(id);
    }

    //add
    @PostMapping("/city")
    @PreAuthorize("hasAnyAuthority('CITY_EDIT')")
    public ResponseEntity<?> createCity(@RequestPart(value = "file") MultipartFile file,
                                        @RequestPart(value = "city") String model) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CityDTO cityDTO = mapper.readValue(model, CityDTO.class);
        return cityService.create(cityDTO, file);
    }

    //edit
    @PutMapping("/city/{id}")
    @PreAuthorize("hasAnyAuthority('CITY_EDIT')")
    public ResponseEntity<?> updateCity(@RequestPart(value = "file") MultipartFile file,
                                        @RequestPart(value = "city") String model,
                                        @PathVariable("id") long id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CityDTO cityDTO = mapper.readValue(model, CityDTO.class);
        cityDTO.setId(id);
        return cityService.update(cityDTO, file);
    }
}
