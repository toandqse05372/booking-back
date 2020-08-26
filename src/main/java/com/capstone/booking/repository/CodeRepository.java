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

    //count remaining code by date
    @Query(value = "select count(c) from Code c where c.visitorType = :visitorType and c.redemptionDate = :date ")
    int countByVisitorTypeReaming(@Param("visitorType") VisitorType type, @Param("date")Date date);

    //count remaining code by date
    @Query(value = "select count(c.id) from t_code c where c.visitor_type_id = :id and c.redemption_date = :date ", nativeQuery = true)
    int countByVisitorTypeIdAndDate(long id, Date date);

    //find all tickets between dates
    @Query(value = "select count(c) from Code c where c.visitorType = :visitorType And c.redemptionDate " +
            "BETWEEN :startDate AND :endDate")
    int getAllBetweenDates
    (@Param("visitorType") VisitorType visitorType, @Param("startDate") Date startDate,
     @Param("endDate")Date endDate);
}
