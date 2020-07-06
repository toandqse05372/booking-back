package com.capstone.booking.api;

import com.capstone.booking.entity.dto.CityDTO;
import com.capstone.booking.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<?> createCity(@RequestBody CityDTO model) {
        return cityService.create(model);
    }

    //edit
    @PutMapping("/city/{id}")
    @PreAuthorize("hasAnyAuthority('CITY_EDIT')")
    public ResponseEntity<?> updateCity(@RequestBody CityDTO model, @PathVariable("id") long id) {
        model.setId(id);
        return cityService.update(model);
    }
}
