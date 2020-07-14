package com.capstone.booking.api.output;

import lombok.Data;
import org.springframework.http.ResponseEntity;

import java.util.List;


@Data
public class Output {
    private int page;
    private int totalPage;
    private List<?> listResult;
    private int totalItems;
}
