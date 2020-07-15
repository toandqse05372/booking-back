package com.capstone.booking.repository;

import com.capstone.booking.entity.Code;
import com.capstone.booking.entity.VisitorType;
import com.capstone.booking.repository.customRepository.CodeCustom;
import com.capstone.booking.repository.impl.CodeRepositoryImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeRepository extends JpaRepository<Code, Long>, CodeCustom {
    List<Code> findByVisitorType(VisitorType type);

    void deleteByVisitorType(VisitorType type);

    Code findFirstByVisitorType(VisitorType type);
}
