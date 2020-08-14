package com.capstone.booking.repository;

import com.capstone.booking.entity.Code;
import com.capstone.booking.entity.Ticket;
import com.capstone.booking.entity.VisitorType;
import com.capstone.booking.repository.customRepository.CodeCustom;
import com.capstone.booking.repository.impl.CodeRepositoryImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

//query to code table
public interface CodeRepository extends JpaRepository<Code, Long>, CodeCustom {

    //find all by visitor type
    List<Code> findByVisitorType(VisitorType type);

    //delete by visitor type
    void deleteByVisitorType(VisitorType type);

    //count remaining code
    @Query(value = "select count(c) from Code c where c.visitorType = :visitorType and c.createdAt > :date ")
    int countByVisitorTypeReaming(@Param("visitorType") VisitorType type, @Param("date")Date date);

    //find all tickets between dates
    @Query(value = "select count(c) from Code c where c.visitorType = :visitorType And c.createdAt " +
            "BETWEEN :startDate AND :endDate")
    int getAllBetweenDates
    (@Param("visitorType") VisitorType visitorType, @Param("startDate") Date startDate,
     @Param("endDate")Date endDate);
}
