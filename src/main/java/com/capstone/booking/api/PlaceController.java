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

//place api
@RestController
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    //getAll place api (not used)
    @GetMapping("/places")
    public ResponseEntity<?> getAllPlace() {
        return placeService.getAll();
    }

    //add place api
    @PostMapping("/place")
    @PreAuthorize("hasAnyAuthority('PLACE_EDIT')")
    public ResponseEntity<?> createPlace(@RequestPart(value = "file", required = false) MultipartFile[] files,
                                         @RequestPart(value = "place") String model)
            throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        PlaceDTO placeDTO = mapper.readValue(model, PlaceDTO.class);
        return placeService.create(placeDTO, files);
    }

    //edit place api
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

    //change status Place api
    @PutMapping("/changePlace/{id}")
    @PreAuthorize("hasAnyAuthority('PLACE_EDIT')")
    public ResponseEntity<?> changeStatusPlace(@PathVariable("id") long id) {
        return placeService.changeStatus(id);
    }

    //search By Id api
    @GetMapping("/place/{id}")
    @PreAuthorize("hasAnyAuthority('PLACE_EDIT')")
    public ResponseEntity<?> getPlace(@PathVariable Long id) {
        return placeService.getPlace(id);
    }

    @GetMapping("/placeClient/{id}")
    public ResponseEntity<?> getPlaceClient(@PathVariable Long id) {
        return placeService.getPlaceClient(id);
    }

    //search place by name & address, cityId, categoryId, & paging api
    @GetMapping("/place/searchMul")
    @PreAuthorize("hasAnyAuthority('PLACE_EDIT')")
    public ResponseEntity<?> searchMUL(@RequestParam(value = "name", required = false) String name,
                                       @RequestParam(value = "address", required = false) String address,
                                       @RequestParam(value = "limit", required = true) Long limit,
                                       @RequestParam(value = "page", required = true) Long page,
                                       @RequestParam(value = "cityId", required = false) Long cityId,
                                       @RequestParam(value = "categoryId", required = false) Long categoryId) {
        return placeService.findByMultipleParam(name, address, cityId, categoryId, limit, page);
    }

    //search place for client api
    @GetMapping("/place/searchClient")
    public ResponseEntity<?> searchPlaceForClient(@RequestParam(value = "name", required = false) String name,
                                                  @RequestParam(value = "limit", required = true) Long limit,
                                                  @RequestParam(value = "page", required = true) Long page,
                                                  @RequestParam(value = "cityId", required = false) List<Long> cityId,
                                                  @RequestParam(value = "categoryId", required = false) List<Long> categoryId,
                                                  @RequestParam(value = "minValue", required = true) Long minValue,
                                                  @RequestParam(value = "maxValue", required = true) Long maxValue) {
        return placeService.searchPlaceForClient(name, minValue, maxValue, cityId, categoryId, limit, page);
    }

    //delete place api
    @DeleteMapping("/place/{id}")
    @PreAuthorize("hasAnyAuthority('PLACE_EDIT')")
    public ResponseEntity<?> deletePlace(@PathVariable("id") long id) {
        placeService.delete(id);
        return new ResponseEntity("Delete Successful", HttpStatus.OK);
    }

    //getTop8ByCityId
    @GetMapping("/topPlace")
    public ResponseEntity<?> getTop8PlaceByCityId(@RequestParam(value = "cityId", required = false) Long cityId) {
        return placeService.getTop8PlaceByCityId(cityId);
    }

}