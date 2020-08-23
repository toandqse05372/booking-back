package com.capstone.booking.config;

import com.capstone.booking.common.key.MonoStatus;
import com.capstone.booking.common.key.OrderStatus;
import com.capstone.booking.entity.*;
import com.capstone.booking.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Component
public class MySchedule {

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    OrderTokenRepository orderTokenRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    RemainingRepository remainingRepository;

    @Autowired
    OrderRepository orderRepository;

    @Scheduled(cron = "5 0 0 * * *")
    public void deleteExpiredToken(){
        List<Token> tokenExpired = tokenRepository.findByTokenExpDateBefore(new Date());
        tokenRepository.deleteAll(tokenExpired);
    }
    @Scheduled(cron = "0 0 0 * * *")
    public void setExpiredOrder(){
        List<Order> orderExpired = orderRepository.findAllByRedemptionDateBeforeAndStatus(new Date(), OrderStatus.EXPIRED.toString());
        List<Order> updateOrders = new ArrayList<>();
        for(Order order: orderExpired){
            order.setStatus(OrderStatus.EXPIRED.toString());
            updateOrders.add(order);
        }
        orderRepository.saveAll(updateOrders);
    }

    //make unpaid order be expired if after 2 hours user not paid for it every 15 minutes
    @Scheduled(cron="0 0/15 * * * ?")
    public void expiredOrderToken(){
        List<OrderToken> orderTokens = orderTokenRepository.findExpOrderToken(new Date());
        for(OrderToken token: orderTokens){
            Order order = orderRepository.findById(token.getOrderId()).get();
            order.setStatus(OrderStatus.EXPIRED.toString());
            orderRepository.save(order);
            for(OrderItem orderItem: orderItemRepository.findAllByOrder(order)){
                Remaining remaining = remainingRepository.findByRedemptionDateAndVisitorTypeId(
                        returnToMidnight(order.getRedemptionDate()), orderItem.getVisitorType().getId());
                if(remaining != null){
                    remaining.setTotal(remaining.getTotal()+orderItem.getQuantity());
                    remainingRepository.save(remaining);
                }
            }
        }
        orderTokenRepository.deleteAll(orderTokens);
    }

    private Date returnToMidnight(Date redemptionDate) {
        Instant inst = redemptionDate.toInstant();
        LocalDate localDate = inst.atZone(ZoneId.systemDefault()).toLocalDate();
        Instant dayInst = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Date day = Date.from(dayInst);
        TimeZone tz = TimeZone.getDefault();
        day = new Date(day.getTime() + tz.getRawOffset());
        return day;
    }

}
