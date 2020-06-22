package com.capstone.booking.common.converter;

import com.capstone.booking.entity.Order;
import com.capstone.booking.entity.dto.OrderDTO;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter {
    public Order toOrder(OrderDTO dto) {
        Order order = new Order();
        order.setTotalTicket(dto.getTotalTicket());
        order.setTicketTypeId(dto.getTicketTypeId());

        order.setFirstName(dto.getFirstName());
        order.setLastName(dto.getLastName());
        order.setMail(dto.getMail());
        order.setPhoneNumber(dto.getPhoneNumber());

        order.setOrderCode(dto.getOrderCode());
        order.setTotalPayment(dto.getTotalPayment());
        order.setPurchaseDay(dto.getPurchaseDay());
        return order;
    }

    public Order toOrder(OrderDTO dto, Order order) {
        order.setTotalTicket(dto.getTotalTicket());
        order.setTicketTypeId(dto.getTicketTypeId());
        order.setFirstName(dto.getFirstName());
        order.setLastName(dto.getLastName());
        order.setMail(dto.getMail());
        order.setPhoneNumber(dto.getPhoneNumber());
        order.setOrderCode(dto.getOrderCode());
        order.setTotalPayment(dto.getTotalPayment());
        order.setPurchaseDay(dto.getPurchaseDay());
        return order;
    }

    public OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        if (order.getId() != null) {
            dto.setId(order.getId());
        }
        dto.setTotalTicket(order.getTotalTicket());
        dto.setTicketTypeId(order.getTicketTypeId());

        dto.setUserId(order.getUser().getId());
        dto.setFirstName(order.getUser().getFirstName());
        dto.setLastName(order.getUser().getLastName());
        dto.setMail(order.getUser().getMail());
        dto.setPhoneNumber(order.getUser().getPhoneNumber());

        dto.setOrderCode(order.getOrderCode());
        dto.setTotalPayment(order.getTotalPayment());
        dto.setPurchaseDay(order.getPurchaseDay());
        dto.setStatus(order.getStatus());
        return dto;
    }
}
