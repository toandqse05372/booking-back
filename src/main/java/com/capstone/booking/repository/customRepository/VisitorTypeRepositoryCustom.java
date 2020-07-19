package com.capstone.booking.repository.customRepository;

import com.capstone.booking.entity.VisitorType;

import java.util.List;
//customer query to visitor type table
public interface VisitorTypeRepositoryCustom {
    //find basic type of place
    VisitorType findByPlaceIdAndBasic(long placeId, boolean isBasic);

    //find all by place id
    List<VisitorType> findByPlaceId(long placeId);
}
