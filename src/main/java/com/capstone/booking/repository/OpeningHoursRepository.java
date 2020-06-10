package com.capstone.booking.repository;

import com.capstone.booking.entity.OpeningHours;
import com.capstone.booking.entity.Park;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpeningHoursRepository extends JpaRepository<OpeningHours, Long> {
    List<OpeningHours> findByPark(Park park);

    OpeningHours findByOpenHours(String hours);
}
