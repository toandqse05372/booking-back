package com.capstone.booking.api;

import com.capstone.booking.entity.dto.ParkTypeDTO;
import com.capstone.booking.service.ParkTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ParkTypeController {

    @Autowired
    private ParkTypeService parkTypeService;

    //get All
    @GetMapping("/parkTypes")
    public ResponseEntity<?> findAllParkTypes() {
        return parkTypeService.getAllParkType();
    }

    //xóa
    @DeleteMapping("/parkType/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        parkTypeService.delete(id);
        return new ResponseEntity("Delete Successful", HttpStatus.OK);
    }


    //them
    @PostMapping("/parkType")
    public ResponseEntity<?> create(@RequestBody ParkTypeDTO model) {
        return parkTypeService.create(model);
    }

    //sua
    @PutMapping("/parkType/{id}")
    public ResponseEntity<?> update(@RequestBody ParkTypeDTO model, @PathVariable("id") long id) {
        model.setId(id);
        return parkTypeService.update(model);
    }


}
