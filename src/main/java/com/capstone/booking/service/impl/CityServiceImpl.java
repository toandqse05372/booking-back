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

    //hien thị city
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

    @Override
    public ResponseEntity<?> getCity(Long id) {
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

    //thêm
    @Override
    public ResponseEntity<?> create(CityDTO cityDTO, MultipartFile file) {
        if (cityRepository.findByName(cityDTO.getName()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CITY_EXISTED");
        }
        City city = cityConverter.toCity(cityDTO);
        cityRepository.save(city);
        return setImageAndReturn(file, city);
    }

    //sửa
    @Override
    public ResponseEntity<?> update(CityDTO cityDTO, MultipartFile file) {
        City existedCity = cityRepository.findByName(cityDTO.getName());
        if (existedCity != null && existedCity.getId() != cityDTO.getId()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CITY_EXISTED");
        }
        City city = new City();
        City oldCity = cityRepository.findById(cityDTO.getId()).get();
        city = cityConverter.toCity(cityDTO, oldCity);
        cityRepository.save(city);
        return setImageAndReturn(file, city);
    }

    private ResponseEntity<?> setImageAndReturn(MultipartFile file, City city) {
        if(file != null){
            City saved = cityRepository.save(city);
            saved.setImageLink(uploadFile(file, saved.getId()));
            cityRepository.save(saved);
        }else{
            cityRepository.save(city);
        }
        return ResponseEntity.ok(cityConverter.toDTO(city));
    }

    //xóa
    @Override
    @Transactional
    public ResponseEntity<?> delete(long id) {
        if (!cityRepository.findById(id).isPresent()) {
            return new ResponseEntity("CITY_NOT_FOUND", HttpStatus.BAD_REQUEST);
        }
        cityRepository.deleteById(id);
        return new ResponseEntity("DELETE_SUCCESSFUL", HttpStatus.OK);
    }

    public String uploadFile(MultipartFile file, Long categoryId){
        String ext = "."+ FilenameUtils.getExtension(file.getOriginalFilename());
        String name = "City_"+categoryId;
        this.amazonS3ClientService.uploadFileToS3Bucket(categoryId, file, "City_" + categoryId, ext, true);
        return bucketLink + name + ext;
    }

}
