package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.PlaceConverter;
import com.capstone.booking.common.key.PlaceAndGameStatus;
import com.capstone.booking.config.aws.AmazonS3ClientService;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.PlaceDTO;
import com.capstone.booking.entity.dto.PlaceDTOLite;
import com.capstone.booking.repository.*;
import com.capstone.booking.service.PlaceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Value("${aws.bucketLink}")
    private String bucketLink;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private PlaceConverter placeConverter;

    @Autowired
    private AmazonS3ClientService amazonS3ClientService;

    @Autowired
    private ImagePlaceRepository imagePlaceRepository;

    @Autowired
    private ImagePlaceRepository imageRepository;
    //tim kiem place theo ten & address, description, cityId, categoryId, & paging
    @Override
    public ResponseEntity<?> findByMultipleParam(String name, String address, Long cityId,
                                                 Long categoryId, Long limit, Long page){
        Output results = placeRepository.findByMultiParam(name, address, cityId, categoryId, limit, page);
        return ResponseEntity.ok(results);
    }

    //search By placeId
    @Override
    public ResponseEntity<?> getPlace(Long id){
        Optional<Place> places = placeRepository.findById(id);
        Place place = places.get();
        return ResponseEntity.ok(placeConverter.toDTO(place));
    }

    //them place
    @Override
    public ResponseEntity<?> create(PlaceDTO placeDTO, MultipartFile[] files) {
        if (placeRepository.findByName(placeDTO.getName()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PLACE_EXISTED");
        }
        Place place = placeConverter.toPlace(placeDTO);
        place.setStatus(PlaceAndGameStatus.ACTIVE.toString());
        placeRepository.save(place);
        if(files != null){
            Place saved = placeRepository.save(place);
            uploadFile(files, saved.getId());
        }
        return ResponseEntity.ok(placeConverter.toDTO(place));
    }

    //sưa place
    @Override
    public ResponseEntity<?> update(PlaceDTO placeDTO, MultipartFile[] files) {
        Place existedPlace = placeRepository.findByName(placeDTO.getName());
        if (existedPlace != null && existedPlace.getId() != placeDTO.getId()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PLACE_EXISTED");
        }
        Place place = new Place();
        Place oldPlace = placeRepository.findById(placeDTO.getId()).get();
        place = placeConverter.toPlace(placeDTO, oldPlace);
        if(files != null){
            Place saved = placeRepository.save(place);
            uploadFile(files, saved.getId());
        }

        return ResponseEntity.ok(placeConverter.toDTO(place));
    }

    //xóa place
    @Override
    @Transactional
    public ResponseEntity<?> delete(long id) {
        if (!placeRepository.findById(id).isPresent()) {
            return new ResponseEntity("PLACE_NOT_FOUND", HttpStatus.BAD_REQUEST);
        }
        Set<ImagePlace> imagePlaces = placeRepository.findById(id).get().getImagePlace();
        for(ImagePlace imagePlace: imagePlaces){
            imagePlaceRepository.delete(imagePlace);
        }
        placeRepository.deleteById(id);
        return new ResponseEntity("DELETE_SUCCESSFUL", HttpStatus.OK);
    }

    //change status
    @Override
    public ResponseEntity<?> changeStatus(Long id) {
        Place place = placeRepository.findById(id).get();
        if (place.getStatus().equals(PlaceAndGameStatus.ACTIVE.toString())) {
            place.setStatus(PlaceAndGameStatus.DEACTIVATE.toString());
        } else {
            place.setStatus(PlaceAndGameStatus.ACTIVE.toString());
        }
        place = placeRepository.save(place);
        return ResponseEntity.ok(placeConverter.toDTO(place));
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

    @Override
    public ResponseEntity<?> searchPlaceForClient(String name, Long minValue, Long maxValue, List<Long> cityId, List<Long> categoryId, Long limit, Long page) {
        Output results = placeRepository.findByMultiParamForClient(name, minValue, maxValue, cityId, categoryId, limit, page);
        return ResponseEntity.ok(results);
    }

    public void uploadFile(MultipartFile[] files, Long placeId){
        int location = 1;
        for (MultipartFile file: files) {
            String ext = "."+ FilenameUtils.getExtension(file.getOriginalFilename());
            String name = "Place_"+placeId +"_"+ location;
            this.amazonS3ClientService.uploadFileToS3Bucket(placeId, file, name , ext, true);
            location++;
            String fileName = name + ext;
            ImagePlace imagePlace = imagePlaceRepository.findByImageName(name);
            if(imagePlace != null){
                imagePlace.setImageLink(bucketLink+fileName);
            }else{
                imagePlace = new ImagePlace();
                imagePlace.setImageLink(bucketLink+fileName);
                imagePlace.setImageName(name);
                imagePlace.setPlace(placeRepository.findById(placeId).get());
            }
            imagePlaceRepository.save(imagePlace);
        }


    }
}
