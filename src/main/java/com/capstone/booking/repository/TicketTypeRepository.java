package com.capstone.booking.repository;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.entity.TicketType;
import com.capstone.booking.repository.customRepository.TicketTypeCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketTypeRepository extends JpaRepository<TicketType, Long>, TicketTypeCustom {
    TicketType findOneByTypeName(String name);

}
