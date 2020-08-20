package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.PlaceConverter;
import com.capstone.booking.common.converter.TicketTypeConverter;
import com.capstone.booking.common.converter.VisitorTypeConverter;
import com.capstone.booking.common.key.MonoStatus;
import com.capstone.booking.config.aws.AmazonS3ClientService;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.*;
import com.capstone.booking.entity.dto.lite.PlaceDTOLite;
import com.capstone.booking.repository.*;
import com.capstone.booking.service.PlaceService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Value("${aws.bucketLink}")
    private String bucketLink;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Autowired
    private PlaceConverter placeConverter;

    @Autowired
    private AmazonS3ClientService amazonS3ClientService;

    @Autowired
    private ImagePlaceRepository imagePlaceRepository;

    @Autowired
    private VisitorTypeRepository visitorTypeRepository;

    @Autowired
    private TicketTypeConverter ticketTypeConverter;

    @Autowired
    private VisitorTypeConverter visitorTypeConverter;

    //search place by name & address, cityId, categoryId, & paging
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

    //find place by id for client
    @Override
    public ResponseEntity<?> getPlaceClient(Long id){
        Place place = placeRepository.findById(id).get();
        if(place == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PLACE_NOT_FOUND");
        }
        PlaceDTOClient client = placeConverter.toPlaceClient(place);
        List<TicketTypeDTO> list = new ArrayList<>();
        List<TicketType> ticketTypes = ticketTypeRepository.findByPlaceIdAndStatus(id, MonoStatus.ACTIVE.toString());
        if(ticketTypes.size() > 0){
            for(TicketType ticketType: ticketTypes){
                TicketTypeDTO ticketTypeDTO = ticketTypeConverter.toDTO(ticketType);
//                List<VisitorType> visitorTypes = visitorTypeRepository.
//                        findByTicketTypeAndStatus(ticketType, MonoStatus.ACTIVE.toString());
//                if(visitorTypes.size() > 0){
//                    List<VisitorTypeDTO> visitorTypeDTOS = new ArrayList<>();
//                    for(VisitorType type: visitorTypes){
//                        visitorTypeDTOS.add(visitorTypeConverter.toDTO(type));
//                    }
//                    ticketTypeDTO.setVisitorTypes(visitorTypeDTOS.stream().sorted(new Comparator<VisitorTypeDTO>() {
//                        @Override
//                        public int compare(VisitorTypeDTO o1, VisitorTypeDTO o2) {
//                            return o1.getId().compareTo(o2.getId());
//                        }
//                    }).collect(Collectors.toList()));
//                    //if place has ticket type and visitor type, add to list
//                    list.add(ticketTypeDTO);
//                }
                list.add(ticketTypeDTO);
            }
        }
        client.setTicketTypes(list);
        return ResponseEntity.ok(client);
    }

    @Override
    public ResponseEntity<?> getTop8PlaceByCityId(Long cityId) {
        List<PlaceDTO> results = new ArrayList<>();
        List<Place> place = placeRepository.getTop8Place(cityId);
        for (Place item : place) {
            PlaceDTO placeDTO = placeConverter.toDTO(item);
            results.add(placeDTO);
        }
        return ResponseEntity.ok(results);
    }

    //add place
    @Override
    public ResponseEntity<?> create(PlaceDTO placeDTO, MultipartFile[] files) {
        if (placeRepository.findByName(placeDTO.getName()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PLACE_EXISTED");
        }
        Place place = placeConverter.toPlace(placeDTO);
        place.setStatus(MonoStatus.ACTIVE.toString());
        placeRepository.save(place);
        if(files != null){
            Place saved = placeRepository.save(place);
            uploadFile(files, saved.getId());
        }
        return ResponseEntity.ok(placeConverter.toDTO(place));
    }

    //edit place
    @Override
    public ResponseEntity<?> update(PlaceDTO placeDTO, MultipartFile[] files) {
        Place existedPlace = placeRepository.findByName(placeDTO.getName());
        if (existedPlace != null && existedPlace.getId() != placeDTO.getId()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PLACE_EXISTED");
        }
        Place place = new Place();
        Optional<Place> placeOptional = placeRepository.findById(placeDTO.getId());
        Place oldPlace = placeOptional.get();
        place = placeConverter.toPlace(placeDTO, oldPlace);
        if(files != null){
            Place saved = placeRepository.save(place);
            uploadFile(files, saved.getId());
        }

        return ResponseEntity.ok(placeConverter.toDTO(place));
    }

    //delete place
    @Override
    @Transactional
    public ResponseEntity<?> delete(long id) {
        if (!placeRepository.findById(id).isPresent()) {
            return new ResponseEntity("PLACE_NOT_FOUND", HttpStatus.BAD_REQUEST);
        }
        Optional<Place> optionalPlace = placeRepository.findById(id);
        Set<ImagePlace> imagePlaces = optionalPlace.get().getImagePlace();
        if(imagePlaces != null){
            for(ImagePlace imagePlace: imagePlaces){
                imagePlaceRepository.delete(imagePlace);
            }
        }
        placeRepository.deleteById(id);
        return new ResponseEntity("DELETE_SUCCESSFUL", HttpStatus.OK);
    }

    //change status
    @Override
    public ResponseEntity<?> changeStatus(Long id) {
        Place place = placeRepository.findById(id).get();
        if (place.getStatus().equals(MonoStatus.ACTIVE.toString())) {
            place.setStatus(MonoStatus.DEACTIVATE.toString());
        } else {
            place.setStatus(MonoStatus.ACTIVE.toString());
        }
        place = placeRepository.save(place);
        return ResponseEntity.ok(placeConverter.toDTO(place));
    }

    //getAll place
    @Override
    public ResponseEntity<?> getAll() {
        List<Place> placeList = placeRepository.findAllByStatus(MonoStatus.ACTIVE.toString());
        List<PlaceDTOLite> liteList = new ArrayList<>();
        for(Place place: placeList){
            PlaceDTOLite lite = placeConverter.toPlaceLite(place);
            liteList.add(lite);
        }
        return ResponseEntity.ok(liteList);
    }

    //search place for client only
    @Override
    public ResponseEntity<?> searchPlaceForClient(String name, Long minValue, Long maxValue, List<Long> cityId,
                                                  List<Long> categoryId, Long limit, Long page) {
        if(name != null){
            name = name.trim();
        }
        Output results = placeRepository.findByMultiParamForClient(name, minValue, maxValue, cityId,
                categoryId, limit, page);
        return ResponseEntity.ok(results);
    }

    //upload file to amazon s3
    public void uploadFile(MultipartFile[] files, Long placeId){
        int location = 1;
        Optional<Place> placeOptional = placeRepository.findById(placeId);
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
                imagePlace.setPlace(placeOptional.get());
            }
            imagePlaceRepository.save(imagePlace);
        }


    }
}
