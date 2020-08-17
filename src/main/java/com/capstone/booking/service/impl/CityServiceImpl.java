package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.CityConverter;
import com.capstone.booking.config.aws.AmazonS3ClientService;
import com.capstone.booking.entity.Category;
import com.capstone.booking.entity.City;
import com.capstone.booking.entity.dto.CityDTO;
import com.capstone.booking.repository.CityRepository;
import com.capstone.booking.service.CityService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CityServiceImpl implements CityService {

    @Value("${aws.bucketLink}")
    private String bucketLink;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityConverter cityConverter;

    @Autowired
    private AmazonS3ClientService amazonS3ClientService;

    //get all city
    @Override
    public ResponseEntity<?> findAllCity() {
        List<CityDTO> results = new ArrayList<>();
        List<City> city = cityRepository.findAll();
        for (City item : city) {
            CityDTO cityDTO = cityConverter.toDTO(item);
            results.add(cityDTO);
        }
        return ResponseEntity.ok(results);
    }

    //get Top 3
    @Override
    public ResponseEntity<?> getTop3() {
        List<CityDTO> results = new ArrayList<>();
        List<City> city = cityRepository.getTop3();
        for (City item : city) {
            CityDTO cityDTO = cityConverter.toDTO(item);
            results.add(cityDTO);
        }
        return ResponseEntity.ok(results);
    }

    //search By Id
    @Override
    public ResponseEntity<?> getCity(Long id) {
        Optional<City> cities = cityRepository.findById(id);
        City city = cities.get();
        return ResponseEntity.ok(cityConverter.toDTO(city));
    }

    //search by cityName & paging
    @Override
    public ResponseEntity<?> findByName(String name, Long limit, Long page) {
        Output results = cityRepository.findByName(name, limit, page);
        return ResponseEntity.ok(results);
    }

    //add new city to db
    @Override
    public ResponseEntity<?> create(CityDTO cityDTO, MultipartFile file) {
        if (cityRepository.findByName(cityDTO.getName()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CITY_EXISTED");
        }
        City city = cityConverter.toCity(cityDTO);
        if(file != null){
            City saved = cityRepository.save(city);
            saved.setImageLink(uploadFile(file, saved.getId()));
            cityRepository.save(saved);
        }else{
            cityRepository.save(city);
        }
        return ResponseEntity.ok(cityConverter.toDTO(city));
    }

    //edit
    @Override
    public ResponseEntity<?> update(CityDTO cityDTO, MultipartFile file) {
        City existedCity = cityRepository.findByName(cityDTO.getName());
        if (existedCity != null && existedCity.getId() != cityDTO.getId()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CITY_EXISTED");
        }
        City city = new City();
        Optional<City> optional = cityRepository.findById(cityDTO.getId());
        City oldCity = optional.get();
        city = cityConverter.toCity(cityDTO, oldCity);
        if(file != null){
            City saved = cityRepository.save(city);
            saved.setImageLink(uploadFile(file, saved.getId()));
            cityRepository.save(saved);
        }else{
            cityRepository.save(city);
        }
        return ResponseEntity.ok(cityConverter.toDTO(city));
    }

    //delete
    @Override
    @Transactional
    public ResponseEntity<?> delete(long id) {
        if (!cityRepository.findById(id).isPresent()) {
            return new ResponseEntity("CITY_NOT_FOUND", HttpStatus.BAD_REQUEST);
        }
        cityRepository.deleteById(id);
        return new ResponseEntity("DELETE_SUCCESSFUL", HttpStatus.OK);
    }

    //upload file to s3
    public String uploadFile(MultipartFile file, Long cityId){
        String ext = "."+ FilenameUtils.getExtension(file.getOriginalFilename());
        String name = "City_"+cityId;
        this.amazonS3ClientService.uploadFileToS3Bucket(cityId, file, "City_" + cityId, ext, true);
        return bucketLink + name + ext;
    }

}
