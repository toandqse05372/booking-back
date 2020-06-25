package com.capstone.booking.common.converter.trans;


import com.capstone.booking.entity.dto.transDto.PlaceTransDto;
import com.capstone.booking.entity.trans.PlaceTran;
import com.capstone.booking.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlaceTransConvert {

    @Autowired
    LanguageRepository languageRepository;

    public PlaceTransDto toDTO(PlaceTran placeTran) {
        PlaceTransDto dto = new PlaceTransDto();
        dto.setId(placeTran.getId());
        dto.setName(placeTran.getName());
        dto.setDetailDescription(placeTran.getDetailDescription());
        dto.setShortDescription(placeTran.getShortDescription());
        dto.setLangId(placeTran.getLanguage().getId());
        return dto;
    }

    public PlaceTran toPlaceTrans(PlaceTransDto dto) {
        PlaceTran placeTran = new PlaceTran();
        placeTran.setName(dto.getName());
        placeTran.setDetailDescription(dto.getDetailDescription());
        placeTran.setShortDescription(dto.getShortDescription());
        placeTran.setLanguage(languageRepository.findById(dto.getLangId()).get());
        return placeTran;
    }

    public PlaceTran toPlaceTrans(PlaceTransDto dto, PlaceTran placeTran) {
        placeTran.setName(dto.getName());
        placeTran.setDetailDescription(dto.getDetailDescription());
        placeTran.setShortDescription(dto.getShortDescription());
        dto.setLangId(placeTran.getLanguage().getId());
        return placeTran;
    }
}
