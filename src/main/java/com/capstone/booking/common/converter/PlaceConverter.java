package com.capstone.booking.common.converter;

import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.*;
import com.capstone.booking.entity.dto.lite.PlaceDTOLite;
import com.capstone.booking.repository.CategoryRepository;
import com.capstone.booking.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//conver place
@Component
public class PlaceConverter {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageConverter imageConverter;

    @Autowired
    private CityRepository cityRepository;

    //convert from dto to entity (for add)
    public Place toPlace(PlaceDTO dto) {
        Place place = new Place();
        place.setName(dto.getName());
        place.setPlaceKey(dto.getPlaceKey());
        place.setAddress(dto.getAddress());
        place.setShortDescription(dto.getShortDescription());
        place.setDetailDescription(dto.getDetailDescription());
        place.setMail(dto.getMail());
        Set<Category> categories = new HashSet<>();
        if(dto.getCategoryId() != null){
            for(Long categoryId: dto.getCategoryId()){
                categories.add(categoryRepository.findById(categoryId).get());
            }
        }
        if(dto.getPlaceKey() != null){
            place.setPlaceKey(dto.getPlaceKey());
        }
        place.setCategories(categories);
        place.setPhoneNumber(dto.getPhoneNumber());
        place.setCancelPolicy(dto.getCancelPolicy());
        place.setCity(cityRepository.findById(dto.getCityId()).get());
        place.setOpeningHours(dto.getOpeningHours());
        String weekdays = "";
        List<Integer> dayList = dto.getWeekDays();
        if(dto.getWeekDays() != null){
            for(int i = 0; i < dayList.size(); i++){
                if(i < dto.getWeekDays().size() - 1){
                    weekdays = weekdays + dayList.get(i) +",";
                }else{
                    weekdays = weekdays + dayList.get(i);
                }
            }
        }
        place.setWeekDays(weekdays);
        return place;
    }

    //convert from dto to entity (for update)
    public Place toPlace(PlaceDTO dto, Place place) {
        place.setName(dto.getName());
        place.setPlaceKey(dto.getPlaceKey());
        place.setAddress(dto.getAddress());
        place.setShortDescription(dto.getShortDescription());
        place.setDetailDescription(dto.getDetailDescription());
        place.setMail(dto.getMail());
        place.setPhoneNumber(dto.getPhoneNumber());
        place.setCancelPolicy(dto.getCancelPolicy());
        place.setBasicPrice(place.getBasicPrice());
        if(dto.getCategoryId() != null){
            Set<Category> categories = new HashSet<>();
            for(Long categoryId: dto.getCategoryId()){
                categories.add(categoryRepository.findById(categoryId).get());
            }
            place.setCategories(categories);
        }
        if(dto.getPlaceKey() != null){
            place.setPlaceKey(dto.getPlaceKey());
        }
        place.setCity(cityRepository.findById(dto.getCityId()).get());
        place.setOpeningHours(dto.getOpeningHours());
        String weekdays = "";
        List<Integer> dayList = dto.getWeekDays();
        for(int i = 0; i < dayList.size(); i++){
            if(i < dto.getWeekDays().size() - 1){
                weekdays = weekdays + dayList.get(i) +",";
            }else{
                weekdays = weekdays + dayList.get(i);
            }
        }
        place.setWeekDays(weekdays);
        return place;
    }

    //convert from entity to dto (id and name only)
    public PlaceDTOLite toPlaceLite(Place place){
        PlaceDTOLite lite = new PlaceDTOLite();
        lite.setId(place.getId());
        lite.setName(place.getName());
        return lite;
    }

    //convert from entity to dto
    public PlaceDTO toDTO(Place place) {
        PlaceDTO dto = new PlaceDTO();
        dto.setId(place.getId());
        dto.setName(place.getName());
        dto.setPlaceKey(place.getPlaceKey());
        dto.setAddress(place.getAddress());
        dto.setDetailDescription(place.getDetailDescription());
        dto.setMail(place.getMail());
        dto.setPhoneNumber(place.getPhoneNumber());
        dto.setOpeningHours(place.getOpeningHours());
        dto.setShortDescription(place.getShortDescription());
        dto.setCancelPolicy(place.getCancelPolicy());
        if(place.getBasicPrice() != null){
            dto.setBasicPrice(place.getBasicPrice());
        }
        if(place.getPlaceKey() != null){
            dto.setPlaceKey(place.getPlaceKey());
        }
        //set image link
        if(place.getImagePlace() != null){
            Set<String> imageLinks = new HashSet<>();
            for (ImagePlace image : place.getImagePlace()) {
                imageLinks.add(image.getImageLink());
            }
            dto.setPlaceImageLink(imageLinks);
        }
        City city = place.getCity();
        if(city != null){
            dto.setCityId(city.getId());
            dto.setCityName(city.getName());
        }
        Set<Long> categorySet = new HashSet<>();
        for (Category category : place.getCategories()) {
            categorySet.add(category.getId());
        }
        dto.setCategoryId(categorySet);
        String weekdayStr = place.getWeekDays();
        String[] days = weekdayStr.split(",");
        List<Integer> weekDays = new ArrayList<>();
        for(String day: days){
            weekDays.add(Integer.parseInt(day));
        }
        dto.setWeekDays(weekDays);
        dto.setCancelPolicy(place.getCancelPolicy());
        dto.setStatus(place.getStatus());
        return dto;
    }

    //convert from entity to dto (more information for client)
    public PlaceDTOClient toPlaceClient(Place place){
        PlaceDTOClient dto = new PlaceDTOClient();
        dto.setId(place.getId());
        dto.setName(place.getName());
        dto.setPlaceKey(place.getPlaceKey());
        dto.setAddress(place.getAddress());
        dto.setDetailDescription(place.getDetailDescription());
        dto.setMail(place.getMail());
        dto.setPhoneNumber(place.getPhoneNumber());
        dto.setOpeningHours(place.getOpeningHours());
        dto.setShortDescription(place.getShortDescription());
        dto.setCancelPolicy(place.getCancelPolicy());
        if(place.getBasicPrice() != null){
            dto.setBasicPrice(place.getBasicPrice());
        }
        if(place.getImagePlace() != null){
            Set<String> imageLinks = new HashSet<>();
            for (ImagePlace image : place.getImagePlace()) {
                imageLinks.add(image.getImageLink());
            }
            dto.setPlaceImageLink(imageLinks);
        }

        City city = place.getCity();
        if(city != null){
            dto.setCityId(city.getId());
            dto.setCityName(city.getName());
        }


        Set<Long> categorySet = new HashSet<>();
        for (Category category : place.getCategories()) {
            categorySet.add(category.getId());
        }
        dto.setCategoryId(categorySet);
        //set weekdays
        String weekdayStr = place.getWeekDays();
        String[] days = weekdayStr.split(",");
        List<Integer> weekDays = new ArrayList<>();
        for(String day: days){
            weekDays.add(Integer.parseInt(day));
        }
        dto.setCancelPolicy(place.getCancelPolicy());
        dto.setWeekDays(weekDays);
        dto.setStatus(place.getStatus());
        return dto;
    }
}
