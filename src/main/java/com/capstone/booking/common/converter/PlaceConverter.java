package com.capstone.booking.common.converter;

import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.*;
import com.capstone.booking.repository.CategoryRepository;
import com.capstone.booking.repository.CityRepository;
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

    public Place toPlace(PlaceDTO dto) {
        Place place = new Place();
        place.setName(dto.getName());
        place.setAddress(dto.getAddress());
        place.setShortDescription(dto.getShortDescription());
        place.setDetailDescription(dto.getDetailDescription());
        place.setMail(dto.getMail());
        Set<Category> categories = new HashSet<>();
        for(Long categoryId: dto.getCategoryId()){
            categories.add(categoryRepository.findById(categoryId).get());
        }
        place.setCategories(categories);
        place.setPhoneNumber(dto.getPhoneNumber());
        if(dto.getCityId() != null){
            place.setCity(cityRepository.findById(dto.getCityId()).get());
        }
        return place;
    }

    public PlaceDTOLite toPlaceLite(Place place){
        PlaceDTOLite lite = new PlaceDTOLite();
        lite.setId(place.getId());
        lite.setName(place.getName());
        return lite;
    }

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

    public Place toPlace(PlaceDTO dto, Place place) {
        place.setName(dto.getName());
        place.setAddress(dto.getAddress());
        place.setShortDescription(dto.getShortDescription());
        place.setDetailDescription(dto.getDetailDescription());
        place.setMail(dto.getMail());
        place.setPhoneNumber(dto.getPhoneNumber());
        Set<Category> categories = new HashSet<>();
        for(Long categoryId: dto.getCategoryId()){
            categories.add(categoryRepository.findById(categoryId).get());
        }
        place.setCategories(categories);
        if(dto.getCityId() != null){
            place.setCity(cityRepository.findById(dto.getCityId()).get());
        }
        return place;
    }
}
