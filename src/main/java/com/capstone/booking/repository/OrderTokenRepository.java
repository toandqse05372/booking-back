package com.capstone.booking.repository;

import com.capstone.booking.entity.OrderToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface OrderTokenRepository extends JpaRepository<OrderToken, Long> {
    OrderToken findByOrderId(long id);

    @Query(value = "select ot.* from order_token ot where ot.token_exp_date <= :date", nativeQuery = true)
    List<OrderToken> findExpOrderToken(Date date);
}
