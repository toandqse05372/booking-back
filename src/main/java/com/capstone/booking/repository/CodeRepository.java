package com.capstone.booking.repository;

import com.capstone.booking.entity.Code;
import com.capstone.booking.entity.VisitorType;
import com.capstone.booking.repository.customRepository.CodeCustom;
import com.capstone.booking.repository.impl.CodeRepositoryImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//query to code table
public interface CodeRepository extends JpaRepository<Code, Long>, CodeCustom {

    //find all by visitor type
    List<Code> findByVisitorType(VisitorType type);

    //delete by visitor type
    void deleteByVisitorType(VisitorType type);
}
