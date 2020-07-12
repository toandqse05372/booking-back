package com.capstone.booking.common.converter;

import com.capstone.booking.entity.OrderItem;
import com.capstone.booking.entity.dto.OrderItemDTO;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class OrderItemConverter {
    public OrderItemDTO toDTO(OrderItem orderItem) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setQuantity(orderItem.getQuantity());
        dto.setVisitorTypeId(orderItem.getVisitorType().getId());
        dto.setVisitorTypeName(orderItem.getVisitorType().getTypeName());
        dto.setId(orderItem.getId());
        return dto;
    }
}
