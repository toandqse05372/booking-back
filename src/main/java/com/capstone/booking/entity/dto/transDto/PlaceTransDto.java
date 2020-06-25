package com.capstone.booking.entity.dto.transDto;

import com.capstone.booking.entity.dto.BaseDTO;
import lombok.Data;

@Data
public class PlaceTransDto extends BaseDTO {
    private Long langId;
    private String name;
    private String shortDescription;
    private String detailDescription;
}
