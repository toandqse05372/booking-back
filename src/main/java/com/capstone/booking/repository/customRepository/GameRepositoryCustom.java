package com.capstone.booking.repository.customRepository;

import com.capstone.booking.api.output.Output;

//customer query to game table
public interface GameRepositoryCustom {
    //find by game name and place name with paging
    Output findByMulParam(String gameName, String placeName, Long limit, Long page);

    //find all by place id
    Output findByPlaceId(Long placeId, Long limit, Long page);
}
