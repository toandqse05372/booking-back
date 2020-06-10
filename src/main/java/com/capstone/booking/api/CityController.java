package com.capstone.booking.api;
import com.capstone.booking.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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


    //filter: park,
}