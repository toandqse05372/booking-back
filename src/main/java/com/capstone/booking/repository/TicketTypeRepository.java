package com.capstone.booking.repository;

import com.capstone.booking.entity.TicketType;
import com.capstone.booking.repository.customRepository.TicketTypeCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//customer query to ticket type table
public interface TicketTypeRepository extends JpaRepository<TicketType, Long>, TicketTypeCustom {

    //find  by type name ( not used )
    TicketType findByTypeName(String name);

    //find all by place id
    List<TicketType> findByPlaceId(Long placeId);

    //not used
    TicketType findByTypeNameAndPlaceId(String name, Long id);

}
