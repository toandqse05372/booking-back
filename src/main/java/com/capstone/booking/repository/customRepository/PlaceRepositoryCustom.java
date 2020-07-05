package com.capstone.booking.repository.customRepository;
import com.capstone.booking.api.output.Output;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Set;


public interface PlaceRepositoryCustom {
    Output findByMultiParam(String name, String address, Long cityId,
                            Long categoryId, Long limit, Long page);

    Output findByMultiParamForClient(String name, Long minValue, Long maxValue, List<Long> cityId,
                                     List<Long> categoryId, Long limit, Long page);
}
