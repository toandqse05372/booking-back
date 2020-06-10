package com.capstone.booking.repository;

import com.capstone.booking.entity.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketTypeRepository extends JpaRepository<TicketType, Long> {
    TicketType findOneByTypeName(String name);
}
