package com.capstone.booking.repository.customRepository;

import com.capstone.booking.entity.Code;
import com.capstone.booking.entity.VisitorType;

import java.util.Date;
import java.util.List;

//customer query to code table
public interface CodeCustom {
    //find by visitor type limit by quantity from order item
    List<Code> findByVisitorTypeIdLimitTo(int limit, VisitorType visitorType, Date date);
}
