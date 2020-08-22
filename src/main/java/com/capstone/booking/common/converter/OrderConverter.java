package com.capstone.booking.common.converter;

import com.capstone.booking.entity.Order;
import com.capstone.booking.entity.OrderItem;
import com.capstone.booking.entity.dto.OrderDTO;
import com.capstone.booking.entity.dto.OrderItemDTO;
import com.capstone.booking.repository.OrderItemRepository;
import com.capstone.booking.repository.TicketTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

//convert order
@Component
public class OrderConverter {

    @Autowired
    OrderItemConverter orderItemConverter;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    //convert from dto to entity (for add)
    public Order toOrder(OrderDTO dto) {
        Order order = new Order();
        order.setTicketTypeId(dto.getTicketTypeId());
        order.setFirstName(dto.getFirstName());
        order.setLastName(dto.getLastName());
        order.setMail(dto.getMail());
        order.setPhoneNumber(dto.getPhoneNumber());
        order.setTotalPayment(dto.getTotalPayment());
        order.setPurchaseDay(dto.getPurchaseDay());
        order.setPlaceId(dto.getPlaceId());
        return order;
    }
    //convert from dto to entity (for update)
    public Order toOrder(OrderDTO dto, Order order) {
        order.setTicketTypeId(dto.getTicketTypeId());
        order.setFirstName(dto.getFirstName());
        order.setLastName(dto.getLastName());
        order.setMail(dto.getMail());
        order.setPhoneNumber(dto.getPhoneNumber());
        order.setTotalPayment(dto.getTotalPayment());
        order.setPurchaseDay(dto.getPurchaseDay());
        order.setPlaceId(dto.getPlaceId());
        return order;
    }

    //convert from entity to dto
    public OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setTicketTypeId(order.getTicketTypeId());
        dto.setUserId(order.getUser().getId());
        dto.setFirstName(order.getUser().getFirstName());
        dto.setLastName(order.getUser().getLastName());
        dto.setMail(order.getMail());
        dto.setPhoneNumber(order.getUser().getPhoneNumber());
        dto.setOrderCode(order.getOrderCode());
        dto.setTotalPayment(order.getTotalPayment());
        dto.setPurchaseDay(order.getPurchaseDay());
        dto.setRedemptionDate(order.getRedemptionDate());
        dto.setStatus(order.getStatus());
        dto.setPlaceId(order.getPlaceId());
        dto.setTicketTypeName(ticketTypeRepository.findById(order.getTicketTypeId()).get().getTypeName());
        Set<OrderItemDTO> orderItemDTOS = new HashSet<>();
        for (OrderItem orderItem : orderItemRepository.findAllByOrder(order)) {
            orderItemDTOS.add(orderItemConverter.toDTO(orderItem));
        }
        dto.setOrderItems(orderItemDTOS);
        return dto;
    }

    public OrderDTO toDTOLite(Order order){
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setPlaceId(order.getPlaceId());
        dto.setOrderCode(order.getOrderCode());
        dto.setTotalPayment(order.getTotalPayment());
        dto.setPurchaseDay(order.getPurchaseDay());
        dto.setRedemptionDate(order.getRedemptionDate());
        return dto;
    }

}
