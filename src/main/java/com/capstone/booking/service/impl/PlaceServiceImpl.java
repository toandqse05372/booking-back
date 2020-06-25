package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.PlaceConverter;
import com.capstone.booking.common.key.PlaceAndGameStatus;
import com.capstone.booking.config.aws.AmazonS3ClientService;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.PlaceDTOLite;
import com.capstone.booking.entity.dto.cmsDto.PlaceCmsDTO;
import com.capstone.booking.entity.trans.PlaceTran;
import com.capstone.booking.repository.*;
import com.capstone.booking.service.PlaceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private PlaceTranRepository placeTranRepository;

    @Autowired
    private PlaceConverter placeConverter;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AmazonS3ClientService amazonS3ClientService;

    @Autowired
    private ImagePlaceRepository imageRepository;
    //tim kiem place theo ten & address, description, cityId, categoryId, & paging
    @Override
    public ResponseEntity<?> findByMultipleParam(String name, String address, Long cityId,
                                                 Long categoryId, Long limit, Long page, String language) throws JsonProcessingException {
        Output results = placeRepository.findByMultiParam(name, address, cityId, categoryId, limit, page, language);
        return ResponseEntity.ok(results);
    }

    //search By placeId
    @Override
    public ResponseEntity<?> getPlace(Long id) throws JsonProcessingException {
        Optional<Place> places = placeRepository.findById(id);
        Place place = places.get();
        return ResponseEntity.ok(placeConverter.toCmsDTO(place));
    }

    //them place
    @Override
    public ResponseEntity<?> create(PlaceCmsDTO placeCmsDTO, MultipartFile[] files) throws JsonProcessingException {
        Place place = placeConverter.toPlace(placeCmsDTO);
        place.setStatus(PlaceAndGameStatus.ACTIVE.toString());
        placeRepository.save(place);
        for(PlaceTran placeTran : place.getPlaceTrans()){
            placeTran.setPlace(place);
            placeTranRepository.save(placeTran);
        }
        if(files != null){
            Place saved = placeRepository.save(place);
            uploadFile(files, saved.getId());
        }
        return ResponseEntity.ok(placeConverter.toCmsDTO(place));
    }

    //sưa place
    @Override
    public ResponseEntity<?> update(PlaceCmsDTO placeDTO, MultipartFile[] files) throws JsonProcessingException {
        Place place = new Place();
        Place oldplace = placeRepository.findById(placeDTO.getId()).get();
        place = placeConverter.toPlace(placeDTO, oldplace);
        if(files != null){
            Place saved = placeRepository.save(place);
            uploadFile(files, saved.getId());
        }

        return ResponseEntity.ok(placeConverter.toCmsDTO(place));
    }

    //xóa place
    @Override
    public ResponseEntity<?> delete(long id) {
        if (!placeRepository.findById(id).isPresent()) {
            return new ResponseEntity("Id already exists", HttpStatus.BAD_REQUEST);
        }
        placeRepository.deleteById(id);
        return new ResponseEntity("Delete Successful", HttpStatus.OK);
    }

    //change status
    @Override
    public ResponseEntity<?> changeStatus(Long id) throws JsonProcessingException {
        Place place = placeRepository.findById(id).get();
        if (place.getStatus().equals(PlaceAndGameStatus.ACTIVE.toString())) {
            place.setStatus(PlaceAndGameStatus.DEACTIVATE.toString());
        } else {
            place.setStatus(PlaceAndGameStatus.ACTIVE.toString());
        }
        place = placeRepository.save(place);
        return ResponseEntity.ok(placeConverter.toCmsDTO(place));
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<Place> placeList = placeRepository.findAll();
        List<PlaceDTOLite> liteList = new ArrayList<>();
        for(Place place: placeList){
            PlaceDTOLite lite = placeConverter.toPlaceLite(place);
            liteList.add(lite);
        }
        return ResponseEntity.ok(liteList);
    }

    public void uploadFile(MultipartFile[] files, Long placeId){
        int location = 1;
        for (MultipartFile file: files) {
            String ext = "."+ FilenameUtils.getExtension(file.getOriginalFilename());
            this.amazonS3ClientService.uploadFileToS3Bucket(placeId, file, placeId +"_"+ location , ext, true);
            location++;
        }
    }
}
