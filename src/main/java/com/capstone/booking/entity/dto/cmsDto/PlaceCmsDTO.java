package com.capstone.booking.entity.dto.cmsDto;

import com.capstone.booking.entity.dto.BaseDTO;
import com.capstone.booking.entity.dto.OpeningHoursDTO;
import com.capstone.booking.entity.dto.transDto.PlaceTransDto;
import lombok.Data;

import java.util.Set;

@Data
public class PlaceCmsDTO extends BaseDTO {
    private Set<PlaceTransDto> placeTrans;
    private String address;
    private String mail;
    private String phoneNumber;
    private Long cityId;
    private String cityName;
    private Set<Long> categoryId;
    private Set<OpeningHoursDTO> openingHours;
    private String status;
}
