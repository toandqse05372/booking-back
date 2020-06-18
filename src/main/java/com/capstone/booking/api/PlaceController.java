package com.capstone.booking.api;

import com.capstone.booking.entity.dto.PlaceDTO;
import com.capstone.booking.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RestController
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    //thêm place
    @PostMapping("/place")
    public ResponseEntity<?> createPlace(@RequestBody PlaceDTO model) {
        return placeService.create(model);
    }

    //sửa place
    @PutMapping("/place/{id}")
    public ResponseEntity<?> updatePlace(@RequestBody PlaceDTO model, @PathVariable("id") long id) {
        model.setId(id);
        return placeService.update(model);
    }

    //change status Place
    @PutMapping("/changePlace/{id}")
    public ResponseEntity<?> changeStatusGame(@PathVariable("id") long id) {
        placeService.changeStatus(id);
        return new ResponseEntity("Change Successful", HttpStatus.OK);
    }

    //search By Id
    @GetMapping("/place/{id}")
    public ResponseEntity<?> getPlace(@PathVariable Long id) {
        return placeService.getPlace(id);
    }


    //tim kiem place theo ten & address, description, cityId, categoryId, & paging
    @GetMapping("/place/searchMul")
    @PreAuthorize("hasAnyAuthority('READ_PLACE')")
    public ResponseEntity<?> searchMUL(@RequestParam(value = "name", required = false) String name,
                                       @RequestParam(value = "address", required = false) String address,
                                       @RequestParam(value = "limit", required = false) Long limit,
                                       @RequestParam(value = "page", required = false) Long page,
                                       @RequestParam(value = "cityId", required = false) Long cityId,
                                       @RequestParam(value = "categoryId", required = false) Long categoryId) {
        return placeService.findByMultipleParam(name, address, cityId, categoryId, limit, page);
    }

    //xoa place
    @DeleteMapping("/place/{id}")
    public ResponseEntity<?> deletePlace(@PathVariable("id") long id) {
        placeService.delete(id);
        return new ResponseEntity("Delete Successful", HttpStatus.OK);
    }

}