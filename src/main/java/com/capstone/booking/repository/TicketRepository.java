package com.capstone.booking.repository;

import com.capstone.booking.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query(value = "from Ticket t where t.visitorTypeId = :visitorTypeId And t.createdAt BETWEEN :startDate AND :endDate")
    public List<Ticket> getAllBetweenDates
            (@Param("visitorTypeId") Long id, @Param("startDate") Date startDate, @Param("endDate")Date endDate);
}
