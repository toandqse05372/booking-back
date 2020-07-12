package com.capstone.booking.repository;

import com.capstone.booking.entity.Code;
import com.capstone.booking.entity.VisitorType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeRepository extends JpaRepository<Code, Long> {
    List<Code> findByVisitorType(VisitorType type);

    void deleteByVisitorType(VisitorType type);
}
