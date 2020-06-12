package com.capstone.booking.api;

import com.capstone.booking.entity.dto.ParkDTO;
import com.capstone.booking.service.ParkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RestController
public class ParkController {

    @Autowired
    private ParkService parkService;

    //thêm Park
    @PostMapping("/park")
    public ResponseEntity<?> createPark(@RequestBody ParkDTO model) {
        return parkService.create(model);
    }

    //sửa Park
    @PutMapping("/park/{id}")
    public ResponseEntity<?> updatePark(@RequestBody ParkDTO model, @PathVariable("id") long id) {
        model.setId(id);
        return parkService.update(model);
    }

    //search By Id
    @GetMapping("/park/{id}")
    public ResponseEntity<?> getPark(@PathVariable Long id) {
        return parkService.getPark(id);
    }


    //tim kiem Park theo ten & address, description, cityId, parkTypeId, & paging
    @GetMapping("/park/searchMUL")
    @PreAuthorize("hasAnyAuthority('READ_PARK')")
    public ResponseEntity<?> searchMUL(@RequestParam(value = "name", required = false) String name,
                                       @RequestParam(value = "address", required = false) String address,
                                       @RequestParam(value = "limit", required = false) Long limit,
                                       @RequestParam(value = "page", required = false) Long page,
                                       @RequestParam(value = "cityId", required = false) Long cityId,
                                       @RequestParam(value = "parkTypeId", required = false) Long parkTypeId) {
        return parkService.findByMultipleParam(name, address, cityId, parkTypeId, limit, page);
    }

    //xoa Park
    @DeleteMapping("/park/{id}")
    public ResponseEntity<?> deletePark(@PathVariable("id") long id) {
        parkService.delete(id);
        return new ResponseEntity("Delete Successful", HttpStatus.OK);
    }

}