package com.capstone.booking.repository;

import com.capstone.booking.entity.Remaining;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface RemainingRepository extends JpaRepository<Remaining, Long> {
    Remaining findByRedemptionDateAndVisitorTypeId(Date date, long id);

    List<Remaining> findByRedemptionDate(Date date);

    List<Remaining> findByRedemptionDateBefore(Date date);
}
