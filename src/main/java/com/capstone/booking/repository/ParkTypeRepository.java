package com.capstone.booking.repository;

import com.capstone.booking.entity.ParkType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkTypeRepository extends JpaRepository<ParkType, Long> {
    ParkType findOneByTypeName(String name);
}
