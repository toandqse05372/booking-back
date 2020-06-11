package com.capstone.booking.repository.customRepository;

import com.capstone.booking.api.output.Output;

public interface GameRepositoryCustom {
    Output findByMulParam(String gameName, String parkName, Long limit, Long page);
}
