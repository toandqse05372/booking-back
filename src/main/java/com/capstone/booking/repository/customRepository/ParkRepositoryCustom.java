package com.capstone.booking.repository.customRepository;
import com.capstone.booking.api.output.Output;


public interface ParkRepositoryCustom {
    Output findByMultiParam(String name, String address, Long cityId,
                            Long parkTypeId, Long limit, Long page);

}
