package com.capstone.booking.repository;

import com.capstone.booking.entity.PlaceType;
import com.capstone.booking.repository.customRepository.PlaceTypeCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceTypeRepository extends JpaRepository<PlaceType, Long>, PlaceTypeCustom {
    PlaceType findOneByTypeName(String name);
}
