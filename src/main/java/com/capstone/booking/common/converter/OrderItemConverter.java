package com.capstone.booking.common.converter;

import com.capstone.booking.entity.OrderItem;
import com.capstone.booking.entity.dto.OrderItemDTO;
import com.capstone.booking.repository.VisitorTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

//convert order item
@Component
public class OrderItemConverter {

    //convert from entity to dto
    @Autowired
    VisitorTypeRepository visitorTypeRepository;

    public OrderItemDTO toDTO(OrderItem orderItem) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setQuantity(orderItem.getQuantity());
        dto.setVisitorTypeId(orderItem.getVisitorType().getId());
        dto.setVisitorTypeName(orderItem.getVisitorType().getTypeName());
        dto.setVisitorTypeKey(orderItem.getVisitorType().getTypeKey());
        dto.setId(orderItem.getId());
        return dto;
    }

    //convert from dto to entity
    public OrderItem toItem(OrderItemDTO dto) {
        OrderItem item = new OrderItem();
        item.setQuantity(dto.getQuantity());
        item.setVisitorType(visitorTypeRepository.findById(dto.getVisitorTypeId()).get());
        return item;
    }
}
