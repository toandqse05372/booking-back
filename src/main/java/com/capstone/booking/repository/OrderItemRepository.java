package com.capstone.booking.repository;

import com.capstone.booking.entity.Order;
import com.capstone.booking.entity.OrderItem;
import com.capstone.booking.entity.VisitorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

//not used
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllByOrder(Order order);

    @Query(value="SELECT coalesce(sum(ot.quantity),0) FROM t_order_item ot inner join t_order o " +
            " on o.id = ot.order_id where o.redemption_date = :date" +
            " and ot.visitor_type_id = :id" +
            " and o.status not like'EXPIRED'", nativeQuery = true)
    int findTotalBookedTicket(long id, Date date);

    void deleteAllByOrder(Order order);
}
