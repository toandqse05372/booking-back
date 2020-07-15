package com.capstone.booking.repository;

import com.capstone.booking.entity.TicketType;
import com.capstone.booking.entity.VisitorType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitorTypeRepository extends JpaRepository<VisitorType, Long> {
    List<VisitorType> findByTicketType(TicketType ticketType);

    List<VisitorType> findByTypeName(String name);

    VisitorType findByTypeKey(String key);

    VisitorType findByIsBasicType(boolean isBasic);
}
