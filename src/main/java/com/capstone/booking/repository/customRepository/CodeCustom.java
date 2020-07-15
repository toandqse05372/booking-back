package com.capstone.booking.repository.customRepository;

import com.capstone.booking.entity.Code;
import com.capstone.booking.entity.VisitorType;

import java.util.List;

public interface CodeCustom {
    List<Code> findByVisitorTypeIdLimitTo(int limit, VisitorType visitorType);
}
