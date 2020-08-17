package com.capstone.booking.entity.dto.lite;

import com.capstone.booking.entity.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Data
@EqualsAndHashCode
public class PlaceDTOLite extends BaseDTO {
    private String name;
    private String imageLink;
}
