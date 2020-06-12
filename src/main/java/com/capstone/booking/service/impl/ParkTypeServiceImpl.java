package com.capstone.booking.service.impl;

import com.capstone.booking.common.converter.ParkTypeConverter;
import com.capstone.booking.entity.ParkType;
import com.capstone.booking.entity.dto.ParkTypeDTO;
import com.capstone.booking.repository.ParkTypeRepository;
import com.capstone.booking.service.ParkTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkTypeServiceImpl implements ParkTypeService {

    @Autowired
    private ParkTypeRepository parkTypeRepository;

    @Autowired
    private ParkTypeConverter parkTypeConverter;

    //get All
    @Override
    public ResponseEntity<?> getAllParkType() {
        List<ParkTypeDTO> results = new ArrayList<>();
        List<ParkType> parkTypes = parkTypeRepository.findAll();

        for (ParkType item : parkTypes) {
            ParkTypeDTO parkTypeDTO = parkTypeConverter.toDTO(item);
            results.add(parkTypeDTO);
        }
        return ResponseEntity.ok(results);
    }

    //xoa
    @Override
    public void delete(Long id) {
        parkTypeRepository.deleteById(id);
    }

    //thêm
    @Override
    public ResponseEntity<?> create(ParkTypeDTO parkTypeDTO) {
        ParkType parkType = parkTypeConverter.toParkType(parkTypeDTO);
        if (parkTypeRepository.findOneByTypeName(parkType.getTypeName()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PARK_TYPE_EXISTED");
        }
        parkTypeRepository.save(parkType);
        return ResponseEntity.ok(parkTypeConverter.toDTO(parkType));
    }

    //sửa
    @Override
    public ResponseEntity<?> update(ParkTypeDTO parkTypeDTO) {
        ParkType parkType = new ParkType();
        ParkType parkTypeOld = parkTypeRepository.findById(parkTypeDTO.getId()).get();
        parkType = parkTypeConverter.toParkType(parkTypeDTO, parkTypeOld);
        if (parkTypeRepository.findOneByTypeName(parkType.getTypeName()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PARK_TYPE_EXISTED");
        }
        parkTypeRepository.save(parkType);
        return ResponseEntity.ok(parkTypeConverter.toDTO(parkType));
    }


}
