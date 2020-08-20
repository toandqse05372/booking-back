package com.capstone.booking.repository;

import com.capstone.booking.entity.City;
import com.capstone.booking.entity.Order;
import com.capstone.booking.entity.User;
import com.capstone.booking.repository.customRepository.OrderRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;

//customer query to order table
public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {

    //find newest order
    Order findTopByOrderByIdDesc();

    @Query(value="select * from t_order o where o.user_id = :uid order by o.created_at desc limit :from, :limit", nativeQuery = true)
    List<Order> findAllByUserPaging(@Param("uid")long uid, @Param("limit")int limit, @Param("from")int page);

    List<Order> findAllByUserOrderByCreatedAtDesc(User user);

    int countByUser(User user);

    //getTop3
    @Query(value="SELECT * FROM t_order o where o.user_id = :uid ORDER BY o.created_at desc LIMIT 3", nativeQuery = true)
    List<Order> getTop3(@Param("uid") long uid);

    @Query(value="SELECT * FROM t_order o where o.redemption_date < :date and " +
            "o.status not in (SELECT os.status FROM t_order os where os.status = :status)", nativeQuery = true)
    List<Order> findAllByRedemptionDateBeforeAndStatus(@Param("date")Date date, @Param("status")String status);
}
