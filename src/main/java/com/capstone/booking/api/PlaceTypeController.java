package com.capstone.booking.api;

import com.capstone.booking.entity.dto.PlaceTypeDTO;
import com.capstone.booking.service.PlaceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlaceTypeController {

    @Autowired
    private PlaceTypeService placeTypeService;

    //tim kiem placeType theo name & paging
    @GetMapping("/placeType/searchByName")
    public ResponseEntity<?> searchMUL(@RequestParam(value = "typeName", required = false) String typeName,
                                       @RequestParam(value = "limit", required = false) Long limit,
                                       @RequestParam(value = "page", required = false) Long page) {
        return placeTypeService.findByMulParam(typeName, limit, page);
    }


    //get All
    @GetMapping("/placeTypes")
    public ResponseEntity<?> findAllPlaceTypes() {
        return placeTypeService.getAllPlaceType();
    }

    //xóa
    @DeleteMapping("/placeType/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        placeTypeService.delete(id);
        return new ResponseEntity("Delete Successful", HttpStatus.OK);
    }

    //them
    @PostMapping("/placeType")
    public ResponseEntity<?> create(@RequestBody PlaceTypeDTO model) {
        return placeTypeService.create(model);
    }

    //sua
    @PutMapping("/placeType/{id}")
    public ResponseEntity<?> update(@RequestBody PlaceTypeDTO model, @PathVariable("id") long id) {
        model.setId(id);
        return placeTypeService.update(model);
    }


}
