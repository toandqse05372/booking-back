package com.capstone.booking.api;

import com.capstone.booking.entity.dto.PlaceDTO;
import com.capstone.booking.service.PlaceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    //getAll place
    @GetMapping("/places")
    public ResponseEntity<?> getAllPlace() {
        return placeService.getAll();
    }

    //add place
    @PostMapping("/place")
    @PreAuthorize("hasAnyAuthority('PLACE_EDIT')")
    public ResponseEntity<?> createPlace(@RequestPart(value = "file", required = false) MultipartFile[] files,
                                         @RequestPart(value = "place") String model)
            throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        PlaceDTO placeDTO = mapper.readValue(model, PlaceDTO.class);
        return placeService.create(placeDTO, files);
    }

    //edit place
    @PutMapping("/place/{id}")
    @PreAuthorize("hasAnyAuthority('PLACE_EDIT')")
    public ResponseEntity<?> updatePlace(@RequestPart(value = "file", required = false) MultipartFile[] files,
                                         @RequestPart(value = "place") String model,
                                         @PathVariable("id") long id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        PlaceDTO placeDTO = mapper.readValue(model, PlaceDTO.class);
        placeDTO.setId(id);
        return placeService.update(placeDTO, files);
    }

    //change status Place
    @PutMapping("/changePlace/{id}")
    @PreAuthorize("hasAnyAuthority('PLACE_EDIT')")
    public ResponseEntity<?> changeStatusPlace(@PathVariable("id") long id)  {
        return placeService.changeStatus(id);
    }

    //search By Id
    @GetMapping("/place/{id}")
    public ResponseEntity<?> getPlace(@PathVariable Long id){
        return placeService.getPlace(id);
    }


    //search place by ten & address, cityId, categoryId, & paging
    @GetMapping("/place/searchMul")
    @PreAuthorize("hasAnyAuthority('PLACE_EDIT')")
    public ResponseEntity<?> searchMUL(@RequestParam(value = "name", required = false) String name,
                                       @RequestParam(value = "address", required = false) String address,
                                       @RequestParam(value = "limit", required = false) Long limit,
                                       @RequestParam(value = "page", required = false) Long page,
                                       @RequestParam(value = "cityId", required = false) Long cityId,
                                       @RequestParam(value = "categoryId", required = false) Long categoryId) {
        return placeService.findByMultipleParam(name, address, cityId, categoryId, limit, page);
    }

    @GetMapping("/place/searchClient")
    public ResponseEntity<?> searchPlaceForClient(@RequestParam(value = "name", required = false) String name,
                                       @RequestParam(value = "limit", required = false) Long limit,
                                       @RequestParam(value = "page", required = false) Long page,
                                       @RequestParam(value = "cityId", required = false) List<Long> cityId,
                                       @RequestParam(value = "categoryId", required = false) List<Long> categoryId,
                                       @RequestParam(value = "minValue", required = false) Long minValue,
                                       @RequestParam(value = "maxValue", required = false) Long maxValue) {
        return placeService.searchPlaceForClient(name, minValue, maxValue, cityId, categoryId, limit, page);
    }

    //delete place
    @DeleteMapping("/place/{id}")
    @PreAuthorize("hasAnyAuthority('PLACE_EDIT')")
    public ResponseEntity<?> deletePlace(@PathVariable("id") long id) {
        placeService.delete(id);
        return new ResponseEntity("Delete Successful", HttpStatus.OK);
    }

}