package com.capstone.booking.repository;

import com.capstone.booking.entity.TicketType;
import com.capstone.booking.repository.customRepository.TicketTypeCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//customer query to ticket type table
public interface TicketTypeRepository extends JpaRepository<TicketType, Long>, TicketTypeCustom {

    //find  by type name ( not used )
    TicketType findByTypeName(String name);
//
//    //find all by place id
//    @Query(value = "from TicketType tt inner join t_game_ticketType gtt on gtt.ticketType_id where tt.placeId = :placeId ")
//    List<TicketType> findByPlaceId(@Param("placeId") Long placeId);

    //not used
    TicketType findByTypeNameAndPlaceId(String name, Long id);

}
