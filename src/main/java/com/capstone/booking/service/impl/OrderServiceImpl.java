package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.OrderConverter;
import com.capstone.booking.common.key.OrderStatus;
import com.capstone.booking.entity.Order;
import com.capstone.booking.entity.User;
import com.capstone.booking.entity.dto.OrderDTO;
import com.capstone.booking.repository.OrderRepository;
import com.capstone.booking.repository.UserRepository;
import com.capstone.booking.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderConverter orderConverter;

    @Override
    public ResponseEntity<?> create(OrderDTO orderDTO) {
        Order order = orderConverter.toOrder(orderDTO);

        User user = userRepository.findById(orderDTO.getUserId()).get();
        order.setUser(user);

        order.setStatus(OrderStatus.UNPAID.toString());
        orderRepository.save(order);
        return ResponseEntity.ok(orderConverter.toDTO(order));
    }

    @Override
    public ResponseEntity<?> update(OrderDTO orderDTO) {
        Order order = new Order();
        Order oldOrder = orderRepository.findById(orderDTO.getId()).get();
        order = orderConverter.toOrder(orderDTO, oldOrder);

        User user = userRepository.findById(orderDTO.getUserId()).get();
        order.setUser(user);

        orderRepository.save(order);
        return ResponseEntity.ok(orderConverter.toDTO(order));
    }

    @Override
    public ResponseEntity<?> delete(long id) {
        if (!orderRepository.findById(id).isPresent()) {
            return new ResponseEntity("ORDER_NOT_FOUND", HttpStatus.BAD_REQUEST);
        }
        orderRepository.deleteById(id);
        return new ResponseEntity("DELETE_SUCCESSFUL", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findByStatus(String status, Long limit, Long page) {
        Output results = orderRepository.findByStatus(status, limit, page);
        return ResponseEntity.ok(results);
    }
}
