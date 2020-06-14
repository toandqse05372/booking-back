package com.capstone.booking.repository.customRepository;

import com.capstone.booking.api.output.Output;

public interface GameRepositoryCustom {
    Output findByMulParam(String gameName, String placeName, Long limit, Long page);
}
