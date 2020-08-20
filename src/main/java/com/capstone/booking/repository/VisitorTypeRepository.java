package com.capstone.booking.repository;

import com.capstone.booking.entity.TicketType;
import com.capstone.booking.entity.VisitorType;
import com.capstone.booking.repository.customRepository.VisitorTypeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

//customer query to visitor type table
public interface VisitorTypeRepository extends JpaRepository<VisitorType, Long>, VisitorTypeRepositoryCustom {

    //find all by ticket type
    List<VisitorType> findByTicketType(TicketType ticketType);

    //find by ticket type and active
    List<VisitorType> findByTicketTypeAndStatus(TicketType ticketType, String status);

    List<VisitorType> findByTypeName(String name);

    VisitorType findByTypeKey(String key);

}
