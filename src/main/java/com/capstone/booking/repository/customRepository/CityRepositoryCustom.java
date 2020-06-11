package com.capstone.booking.repository.customRepository;

import com.capstone.booking.api.output.Output;

public interface CityRepositoryCustom {
    Output findByName(String name, Long limit, Long page);
}
