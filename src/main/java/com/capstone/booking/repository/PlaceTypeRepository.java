package com.capstone.booking.repository;

import com.capstone.booking.entity.PlaceType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceTypeRepository extends JpaRepository<PlaceType, Long> {
    PlaceType findOneByTypeName(String name);
}
