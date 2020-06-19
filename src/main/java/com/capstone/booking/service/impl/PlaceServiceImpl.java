package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.PlaceConverter;
import com.capstone.booking.common.key.Status;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.PlaceDTO;
import com.capstone.booking.entity.dto.CategoryDTO;
import com.capstone.booking.repository.*;
import com.capstone.booking.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private PlaceConverter placeConverter;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImagePlaceRepository imageRepository;
    //tim kiem place theo ten & address, description, cityId, categoryId, & paging
    @Override
    public ResponseEntity<?> findByMultipleParam(String name, String address, Long cityId,
                                                 Long categoryId, Long limit, Long page) {
        Output results = placeRepository.findByMultiParam(name, address, cityId, categoryId, limit, page);
        return ResponseEntity.ok(results);
    }

    //search By placeId
    @Override
    public ResponseEntity<?> getPlace(Long id) {
        Optional<Place> places = placeRepository.findById(id);
        Place place = places.get();
        return ResponseEntity.ok(placeConverter.toDTO(place));
    }

    //them place
    @Override
    public ResponseEntity<?> create(PlaceDTO placeDTO) {
        Place place = placeConverter.toPlace(placeDTO);
        City city= cityRepository.findById(placeDTO.getCityId()).get();
        place.setCity(city);

        Set<Category> categorySet = new HashSet<>();
        for(Long categoryId: placeDTO.getCategoryId()){
            categorySet.add(categoryRepository.findById(categoryId).get());
        }
        place.setCategories(categorySet);

        place.setStatus(Status.ACTIVE.toString());

        placeRepository.save(place);
        return ResponseEntity.ok(placeConverter.toDTO(place));
    }

    //sưa place
    @Override
    public ResponseEntity<?> update(PlaceDTO placeDTO) {
        Place place = new Place();
        Place oldplace = placeRepository.findById(placeDTO.getId()).get();
        place = placeConverter.toPlace(placeDTO, oldplace);

        City city= cityRepository.findById(placeDTO.getCityId()).get();
        place.setCity(city);

        Set<Category> categorySet = new HashSet<>();
        for(Long categoryId: placeDTO.getCategoryId()){
            categorySet.add(categoryRepository.findById(categoryId).get());
        }
        place.setCategories(categorySet);

        placeRepository.save(place);
        return ResponseEntity.ok(placeConverter.toDTO(place));
    }


    //xóa place
    @Override
    public void delete(long id) {
        placeRepository.deleteById(id);
    }

    //change status
    @Override
    public ResponseEntity<?> changeStatus(Long id) {
        Place place = placeRepository.findById(id).get();
        if (place.getStatus().equals(Status.ACTIVE.toString())) {
            place.setStatus(Status.DEACTIVATE.toString());
        } else {
            place.setStatus(Status.ACTIVE.toString());
        }
        place = placeRepository.save(place);
        return ResponseEntity.ok(placeConverter.toDTO(place));
    }

}
