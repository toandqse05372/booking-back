package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.ParkConverter;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.ParkDTO;
import com.capstone.booking.entity.dto.ParkTypeDTO;
import com.capstone.booking.repository.*;
import com.capstone.booking.service.ParkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ParkServiceImpl implements ParkService {

    @Autowired
    private ParkRepository parkRepository;

    @Autowired
    private ParkConverter parkConverter;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ParkTypeRepository parkTypeRepository;

    @Autowired
    private ImageRepository imageRepository;
    //tim kiem Park theo ten & address, description, cityId, parkTypeId, & paging
    @Override
    public ResponseEntity<?> findByMultipleParam(String name, String address, Long cityId,
                                                 Long parkTypeId, Long limit, Long page) {
        Output results = parkRepository.findByMultiParam(name, address, cityId, parkTypeId, limit, page);
        return ResponseEntity.ok(results);
    }

    //search By ParkId
    @Override
    public ResponseEntity<?> getPark(Long id) {
        Optional<Park> parks = parkRepository.findById(id);
        Park park = parks.get();
        return ResponseEntity.ok(parkConverter.toDTO(park));
    }

    //them Park
    @Override
    public ResponseEntity<?> create(ParkDTO parkDTO) {
        Park park = parkConverter.toPark(parkDTO);
        City cityName = cityRepository.findByName(parkDTO.getCity().getName());
        park.setCity(cityName);


        Set<ParkType> parkTypeSet = new HashSet<>();
        for(ParkTypeDTO parkTypeDTO: parkDTO.getParkType()){
            parkTypeSet.add(parkTypeRepository.findOneByTypeName(parkTypeDTO.getParkTypeName()));
        }
        park.setParkTypes(parkTypeSet);

        parkRepository.save(park);
        return ResponseEntity.ok(parkConverter.toDTO(park));
    }

    //sưa Park
    @Override
    public ResponseEntity<?> update(ParkDTO parkDTO) {
        Park park = new Park();
        Park oldPark = parkRepository.findById(parkDTO.getId()).get();
        park = parkConverter.toPark(parkDTO, oldPark);

        City cityName = cityRepository.findByName(parkDTO.getCity().getName());
        park.setCity(cityName);

        Set<ParkType> parkTypeSet = new HashSet<>();
        for(ParkTypeDTO parkTypeDTO: parkDTO.getParkType()){
            parkTypeSet.add(parkTypeRepository.findOneByTypeName(parkTypeDTO.getParkTypeName()));
        }
        park.setParkTypes(parkTypeSet);

        parkRepository.save(park);
        return ResponseEntity.ok(parkConverter.toDTO(park));
    }


    //xóa Park
    @Override
    public void delete(long id) {
        parkRepository.deleteById(id);
    }

    @Override
    public ResponseEntity<?> getAllParkType() {
        return null;
    }


}
