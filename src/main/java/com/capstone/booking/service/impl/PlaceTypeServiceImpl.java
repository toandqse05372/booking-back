package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.PlaceTypeConverter;
import com.capstone.booking.entity.PlaceType;
import com.capstone.booking.entity.dto.PlaceTypeDTO;
import com.capstone.booking.repository.PlaceTypeRepository;
import com.capstone.booking.service.PlaceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaceTypeServiceImpl implements PlaceTypeService {

    @Autowired
    private PlaceTypeRepository placeTypeRepository;

    @Autowired
    private PlaceTypeConverter placeTypeConverter;

    //get All
    @Override
    public ResponseEntity<?> getAllPlaceType() {
        List<PlaceTypeDTO> results = new ArrayList<>();
        List<PlaceType> placeTypes = placeTypeRepository.findAll();

        for (PlaceType item : placeTypes) {
            PlaceTypeDTO placeTypeDTO = placeTypeConverter.toDTO(item);
            results.add(placeTypeDTO);
        }
        return ResponseEntity.ok(results);
    }

    //xoa
    @Override
    public void delete(Long id) {
        placeTypeRepository.deleteById(id);
    }

    //thêm
    @Override
    public ResponseEntity<?> create(PlaceTypeDTO placeTypeDTO) {
        PlaceType placeType = placeTypeConverter.toPlaceType(placeTypeDTO);
        if (placeTypeRepository.findOneByTypeName(placeType.getTypeName()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PLACE_TYPE_EXISTED");
        }
        placeTypeRepository.save(placeType);
        return ResponseEntity.ok(placeTypeConverter.toDTO(placeType));
    }

    //sửa
    @Override
    public ResponseEntity<?> update(PlaceTypeDTO placeTypeDTO) {
        PlaceType placeType = new PlaceType();
        PlaceType placeTypeOld = placeTypeRepository.findById(placeTypeDTO.getId()).get();
        placeType = placeTypeConverter.toPlaceType(placeTypeDTO, placeTypeOld);
        if (placeTypeRepository.findOneByTypeName(placeType.getTypeName()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PLACE_TYPE_EXISTED");
        }
        placeTypeRepository.save(placeType);
        return ResponseEntity.ok(placeTypeConverter.toDTO(placeType));
    }

    @Override
    public ResponseEntity<?> findByMulParam(String typeName, Long limit, Long page) {
        Output results = placeTypeRepository.findByMulParam(typeName, limit, page);
        return ResponseEntity.ok(results);
    }


}
