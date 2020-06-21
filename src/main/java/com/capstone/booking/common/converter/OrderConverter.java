package com.capstone.booking.common.converter;

import com.capstone.booking.entity.Order;
import com.capstone.booking.entity.User;
import com.capstone.booking.entity.dto.OrderDTO;
import com.capstone.booking.entity.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter {
    public Order toOrder(OrderDTO dto) {
        Order order = new Order();
        order.setTotalTicket(dto.getTotalTicket());
        order.setTicketTypeId(dto.getTicketTypeId());

//        User user = new User();
//        UserDTO userDTO = dto.getUser();
//        user.setFirstName(userDTO.getFirstName());
//        user.setLastName(userDTO.getLastName());
//        user.setMail(userDTO.getMail());
//        user.setPhoneNumber(userDTO.getPhoneNumber());
//        order.setUser(user);

        order.setTotalPayment(dto.getTotalPayment());
        order.setPurchaseDay(dto.getPurchaseDay());
        return order;
    }

    public Order toOrder(OrderDTO dto, Order order) {
        order.setTotalTicket(dto.getTotalTicket());
        order.setTicketTypeId(dto.getTicketTypeId());
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

        UserDTO userDTO = new UserDTO();
        User user = order.getUser();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setMail(user.getMail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        dto.setUser(userDTO);

        dto.setTotalPayment(order.getTotalPayment());
        dto.setPurchaseDay(order.getPurchaseDay());
        dto.setStatus(order.getStatus());
        return dto;
    }
}
