package com.capstone.booking.repository.customRepository;

import com.capstone.booking.api.output.Output;

//customer query to city table
public interface CityRepositoryCustom {
    //find by name of city with paging
    Output findByName(String name, Long limit, Long page);
}
