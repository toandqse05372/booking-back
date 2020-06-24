package com.capstone.booking.entity.dto.cmsDto;

import com.capstone.booking.entity.dto.BaseDTO;
import com.capstone.booking.entity.dto.LanguageChanger;
import com.capstone.booking.entity.dto.OpeningHoursDTO;
import lombok.Data;

import java.util.Set;

@Data
public class PlaceCmsDTO extends BaseDTO {
    private LanguageChanger name;
    private String address;
    private LanguageChanger shortDescription;
    private LanguageChanger detailDescription;
    private String mail;
    private String phoneNumber;
    private Long cityId;
    private String cityName;
    private Set<Long> categoryId;
    private Set<OpeningHoursDTO> openingHours;
    private String status;
}
