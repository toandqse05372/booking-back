package com.capstone.booking.common.converter;

import com.capstone.booking.common.converter.trans.PlaceTransConvert;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.*;
import com.capstone.booking.entity.dto.cmsDto.PlaceCmsDTO;
import com.capstone.booking.entity.dto.transDto.PlaceTransDto;
import com.capstone.booking.entity.trans.PlaceTran;
import com.capstone.booking.repository.CategoryRepository;
import com.capstone.booking.repository.CityRepository;
import com.capstone.booking.repository.LanguageRepository;
import com.capstone.booking.repository.trans.PlaceTranRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
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

    @Autowired
    private PlaceTranRepository placeTranRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private PlaceTransConvert placeTransConvert;

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



    public PlaceDTO toDTO(Place place, String language) throws JsonProcessingException {
        PlaceDTO dto = new PlaceDTO();
        if (place.getId() != null) {
            dto.setId(place.getId());
        }
//        LanguageChanger languageChanger = new LanguageChanger();

//        languageChanger = xmlConverter.toLanguageChanger(place.getName());
//        dto.setName(xmlConverter.getLangauge(language, languageChanger));
//
//        dto.setAddress(place.getAddress());
//
//        languageChanger = xmlConverter.toLanguageChanger(place.getDetailDescription());
//        dto.setDetailDescription(xmlConverter.getLangauge(language, languageChanger));
//        dto.setMail(place.getMail());
//        dto.setPhoneNumber(place.getPhoneNumber());
//
//        languageChanger = xmlConverter.toLanguageChanger(place.getShortDescription());
//        dto.setShortDescription(xmlConverter.getLangauge(language, languageChanger));

        PlaceTran placeTran = placeTranRepository.findByPlaceAndLanguage(place, languageRepository.findByCode(language));

        dto.setName(placeTran.getName());
        dto.setPhoneNumber(place.getPhoneNumber());
        dto.setShortDescription(placeTran.getShortDescription());
        dto.setDetailDescription(placeTran.getDetailDescription());
        dto.setMail(place.getMail());
        dto.setAddress(place.getAddress());
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

    public PlaceCmsDTO toCmsDTO(Place place) throws JsonProcessingException{
        PlaceCmsDTO dto = new PlaceCmsDTO();
        dto.setId(place.getId());
        dto.setAddress(place.getAddress());
        dto.setMail(place.getMail());
        dto.setPhoneNumber(place.getPhoneNumber());
        Set<PlaceTransDto> transSet = new HashSet<>();
        for(PlaceTran placeTran : place.getPlaceTrans()){
            transSet.add(placeTransConvert.toDTO(placeTran));
        }
        dto.setPlaceTrans(transSet);
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
        Set<PlaceTran> transSet = new HashSet<>();
        for(PlaceTransDto placeTransDto : placeCmsDTO.getPlaceTrans()){
            transSet.add(placeTransConvert.toPlaceTrans(placeTransDto));
        }
        place.setPlaceTrans(transSet);
        place.setAddress(placeCmsDTO.getAddress());
        Set<Category> categorySet = new HashSet<>();
        for(Long categoryId: placeCmsDTO.getCategoryId()){
            categorySet.add(categoryRepository.findById(categoryId).get());
        }
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
        Set<PlaceTran> transSet = new HashSet<>();
        for(PlaceTransDto placeTransDto : placeCmsDTO.getPlaceTrans()){
            transSet.add(placeTransConvert.toPlaceTrans(placeTransDto));
        }
        place.setPlaceTrans(transSet);
        place.setAddress(placeCmsDTO.getAddress());
        Set<Category> categorySet = new HashSet<>();
        for(Long categoryId: placeCmsDTO.getCategoryId()){
            Category category = categoryRepository.findById(categoryId).get();
            categorySet.add(category);
        }
        place.setCategories(categorySet);
        place.setMail(placeCmsDTO.getMail());
        place.setPhoneNumber(placeCmsDTO.getPhoneNumber());
        place.setCity(cityRepository.findById(placeCmsDTO.getCityId()).get());
        return place;
    }
}
