package com.capstone.booking.repository.customRepository;
import com.capstone.booking.api.output.Output;


public interface PlaceRepositoryCustom {
    Output findByMultiParam(String name, String address, Long cityId,
                            Long categoryId, Long limit, Long page);

}
