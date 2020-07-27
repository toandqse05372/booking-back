package com.capstone.booking.config;

import com.capstone.booking.common.key.MonoStatus;
import com.capstone.booking.common.key.OrderStatus;
import com.capstone.booking.entity.Order;
import com.capstone.booking.entity.Token;
import com.capstone.booking.repository.OrderRepository;
import com.capstone.booking.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class MySchedule {

    @Autowired
    TokenRepository tokenRepository;

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

}
