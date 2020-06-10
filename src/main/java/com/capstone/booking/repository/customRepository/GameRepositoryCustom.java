package com.capstone.booking.repository.customRepository;

import com.capstone.booking.api.output.Output;

public interface GameRepositoryCustom {
    Output findByMulParam(String gameName, Long parkId, Long limit, Long page);
}
