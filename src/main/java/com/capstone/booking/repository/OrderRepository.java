package com.capstone.booking.repository;

import com.capstone.booking.entity.City;
import com.capstone.booking.entity.Order;
import com.capstone.booking.entity.User;
import com.capstone.booking.repository.customRepository.OrderRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//customer query to order table
public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
    //find newest order
    Order findTopByOrderByIdDesc();

    List<Order> findAllByUserOrderByCreatedAtDesc(User user);

    //getTop3
    @Query(value="SELECT * FROM t_order o where o.user_id = :uid ORDER BY o.created_at desc LIMIT 3", nativeQuery = true)
    List<Order> getTop3(@Param("uid") long uid);
}
