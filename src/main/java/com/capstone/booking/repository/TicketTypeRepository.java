package com.capstone.booking.repository;

import com.capstone.booking.entity.TicketType;
import com.capstone.booking.entity.dto.TicketTypeDTO;
import com.capstone.booking.repository.customRepository.TicketTypeCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketTypeRepository extends JpaRepository<TicketType, Long>, TicketTypeCustom {
    TicketType findByTypeName(String name);

    TicketType findOneByTypeName(String name);

    List<TicketType> findByPlaceId(Long placeId);

}
