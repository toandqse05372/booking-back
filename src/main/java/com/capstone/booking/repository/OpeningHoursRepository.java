package com.capstone.booking.repository;

import com.capstone.booking.entity.OpeningHours;
import com.capstone.booking.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//not used
public interface OpeningHoursRepository extends JpaRepository<OpeningHours, Long> {
    List<OpeningHours> findByPlace(Place place);

    OpeningHours findByOpenHours(String hours);
}
