package com.capstone.booking.repository.customRepository;

import com.capstone.booking.api.output.Output;

//customer query to category table
public interface CategoryCustom {
    //find by category name with paging
    Output findByMulParam(String typeName, Long limit, Long page);
}
