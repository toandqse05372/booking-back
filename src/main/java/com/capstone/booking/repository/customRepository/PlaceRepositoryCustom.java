package com.capstone.booking.repository.customRepository;
import com.capstone.booking.api.output.Output;


public interface PlaceRepositoryCustom {
    Output findByMultiParam(String name, String address, Long cityId,
                            Long placeTypeId, Long limit, Long page);

}
