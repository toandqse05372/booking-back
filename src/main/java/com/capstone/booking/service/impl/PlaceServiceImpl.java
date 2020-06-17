package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.PlaceConverter;
import com.capstone.booking.common.key.Status;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.PlaceDTO;
import com.capstone.booking.entity.dto.PlaceTypeDTO;
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
    private PlaceTypeRepository placeTypeRepository;

    @Autowired
    private ImagePlaceRepository imageRepository;
    //tim kiem place theo ten & address, description, cityId, placeTypeId, & paging
    @Override
    public ResponseEntity<?> findByMultipleParam(String name, String address, Long cityId,
                                                 Long placeTypeId, Long limit, Long page) {
        Output results = placeRepository.findByMultiParam(name, address, cityId, placeTypeId, limit, page);
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
        City cityName = cityRepository.findByName(placeDTO.getCity().getName());
        place.setCity(cityName);

        Set<PlaceType> placeTypeSet = new HashSet<>();
        for(PlaceTypeDTO placeTypeDTO : placeDTO.getPlaceType()){
            placeTypeSet.add(placeTypeRepository.findOneByTypeName(placeTypeDTO.getPlaceTypeName()));
        }
        place.setPlaceTypes(placeTypeSet);

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

        City cityName = cityRepository.findByName(placeDTO.getCity().getName());
        place.setCity(cityName);

        Set<PlaceType> placeTypeSet = new HashSet<>();
        for(PlaceTypeDTO placeTypeDTO : placeDTO.getPlaceType()){
            placeTypeSet.add(placeTypeRepository.findOneByTypeName(placeTypeDTO.getPlaceTypeName()));
        }
        place.setPlaceTypes(placeTypeSet);

        placeRepository.save(place);
        return ResponseEntity.ok(placeConverter.toDTO(place));
    }


    //xóa place
    @Override
    public void delete(long id) {
        placeRepository.deleteById(id);
    }

}
