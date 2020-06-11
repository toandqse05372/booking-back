package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.CityConverter;
import com.capstone.booking.entity.City;
import com.capstone.booking.entity.dto.CityDTO;
import com.capstone.booking.repository.CityRepository;
import com.capstone.booking.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityConverter cityConverter;

    //hien thị city
    @Override
    public ResponseEntity<?> findAllCity() {
        List<CityDTO> results = new ArrayList<>();
        List<City> city = cityRepository.findAll();
        for(City item : city) {
            CityDTO cityDTO = cityConverter.toDTO(item);
            results.add(cityDTO);
        }
        return ResponseEntity.ok(results);
    }

    @Override
    public ResponseEntity<?>  getCity(Long id) {
        Optional<City> cities = cityRepository.findById(id);
        City city = cities.get();
        return ResponseEntity.ok(cityConverter.toDTO(city));
    }

    //search cityName & paging
    @Override
    public ResponseEntity<?> findByName(String name, Long limit, Long page) {
        Output results = cityRepository.findByName(name, limit, page);
        return ResponseEntity.ok(results);
    }

}
