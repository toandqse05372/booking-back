package com.capstone.booking.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Data
@EqualsAndHashCode
public class PlaceDTO extends BaseDTO{
    private String name;
    private String address;
    private String shortDescription;
    private String detailDescription;
    private String mail;
    private String phoneNumber;
    private Set<String> placeImageLink;
    private Long cityId;
    private String cityName;
    private Set<Long> categoryId;
    private Set<OpeningHoursDTO> openingHours;
    private String status;
}
