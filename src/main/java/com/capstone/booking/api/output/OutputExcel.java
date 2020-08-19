package com.capstone.booking.api.output;

import lombok.Data;

import java.util.List;

@Data
public class OutputExcel {
    private List<?> listResult;
    private boolean importExcel = false;
    private List<Integer> weekdays;
}
