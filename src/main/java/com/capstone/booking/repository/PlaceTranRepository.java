package com.capstone.booking.repository;

import com.capstone.booking.entity.Language;
import com.capstone.booking.entity.Place;
import com.capstone.booking.entity.trans.PlaceTran;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceTranRepository extends JpaRepository<PlaceTran, Long> {
    PlaceTran findByPlaceAndLanguage(Place place, Language language);
}
