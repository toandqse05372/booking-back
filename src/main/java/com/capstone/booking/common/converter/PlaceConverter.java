package com.capstone.booking.common.converter;

import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.*;
import com.capstone.booking.entity.dto.cmsDto.PlaceCmsDTO;
import com.capstone.booking.repository.CategoryRepository;
import com.capstone.booking.repository.CityRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
public class PlaceConverter {
    @Autowired
    private GameConverter gameConverter;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageConverter imageConverter;

    @Autowired
    private CityConverter cityConverter;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private OpeningHoursConverter hoursConverter;

    @Autowired
    private XmlConverter xmlConverter;

//    public Place toPlace(PlaceDTO dto) {
//        Place place = new Place();
//        place.setName(dto.getName());
//        place.setAddress(dto.getAddress());
//        place.setShortDescription(dto.getShortDescription());
//        place.setDetailDescription(dto.getDetailDescription());
//        place.setMail(dto.getMail());
//        place.setPhoneNumber(dto.getPhoneNumber());
//        if(dto.getCityId() != null){
//            place.setCity(cityRepository.findById(dto.getCityId()).get());
//        }
//        return place;
//    }

    public PlaceDTO toDTO(Place place) {
        PlaceDTO dto = new PlaceDTO();
        if (place.getId() != null) {
            dto.setId(place.getId());
        }
        dto.setName(place.getName());
        dto.setAddress(place.getAddress());
        dto.setDetailDescription(place.getDetailDescription());
        dto.setMail(place.getMail());
        dto.setPhoneNumber(place.getPhoneNumber());
        dto.setShortDescription(place.getShortDescription());

        if(place.getImagePlace() != null){
            Set<ImageDTO> imageSet = new HashSet<>();
            for (ImagePlace image : place.getImagePlace()) {
                imageSet.add(imageConverter.toDTO(image));
            }
        }

        City city = place.getCity();
        dto.setCityId(city.getId());
        dto.setCityName(city.getName());

        Set<Long> categorySet = new HashSet<>();
        for (Category category : place.getCategories()) {
            categorySet.add(category.getId());
        }
        dto.setCategoryId(categorySet);

        Set<OpeningHoursDTO> openingHoursSet = new HashSet<>();
        for (OpeningHours hours : place.getOpeningHours()) {
            openingHoursSet.add(hoursConverter.toDTO(hours));
        }
        dto.setOpeningHours(openingHoursSet);

        dto.setStatus(place.getStatus());
        return dto;
    }

    public Place toPlace(PlaceCmsDTO placeCmsDTO, Place place) throws JsonProcessingException {
        place.setName(placeCmsDTO.getName());
        place.setAddress(placeCmsDTO.getAddress());
        Set<Category> categorySet = new HashSet<>();
        for(Long categoryId: placeCmsDTO.getCategoryId()){
            categorySet.add(categoryRepository.findById(categoryId).get());
        }
        place.setShortDescription(xmlConverter.toXmlString(placeCmsDTO.getShortDescription()));
        place.setDetailDescription(xmlConverter.toXmlString(placeCmsDTO.getDetailDescription()));
        place.setMail(placeCmsDTO.getMail());
        place.setPhoneNumber(placeCmsDTO.getPhoneNumber());
        place.setCity(cityRepository.findById(placeCmsDTO.getCityId()).get());
        return place;
    }

    public PlaceDTOLite toPlaceLite(Place place){
        PlaceDTOLite lite = new PlaceDTOLite();
        lite.setId(place.getId());
        lite.setName(place.getName());
        return lite;
    }

    public Place toPlace(PlaceCmsDTO placeCmsDTO) throws JsonProcessingException {
        Place place = new Place();
        place.setName(placeCmsDTO.getName());
        place.setAddress(placeCmsDTO.getAddress());
        Set<Category> categorySet = new HashSet<>();
        for(Long categoryId: placeCmsDTO.getCategoryId()){
            categorySet.add(categoryRepository.findById(categoryId).get());
        }
        place.setCategories(categorySet);
        place.setShortDescription(xmlConverter.toXmlString(placeCmsDTO.getShortDescription()));
        place.setDetailDescription(xmlConverter.toXmlString(placeCmsDTO.getDetailDescription()));
        place.setMail(placeCmsDTO.getMail());
        place.setPhoneNumber(placeCmsDTO.getPhoneNumber());
        place.setCity(cityRepository.findById(placeCmsDTO.getCityId()).get());
        return place;
    }
}
