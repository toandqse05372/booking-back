package com.capstone.booking.repository.customRepository;

import com.capstone.booking.entity.VisitorType;

import java.util.List;

public interface VisitorTypeRepositoryCustom {
    VisitorType findByPlaceIdAndBasic(long placeId, boolean isBasic);

    List<VisitorType> findByPlaceId(long placeId);
}
