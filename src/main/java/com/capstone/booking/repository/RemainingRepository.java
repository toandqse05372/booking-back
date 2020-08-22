package com.capstone.booking.repository;

import com.capstone.booking.entity.Remaining;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface RemainingRepository extends JpaRepository<Remaining, Long> {
    Remaining findByRedemptionDateAndVisitorTypeId(Date date, long id);
}
