package com.capstone.booking.service;

import com.capstone.booking.entity.dto.OrderDTO;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    //Thêm
    ResponseEntity<?> create(OrderDTO orderDTO);

    //sửa
    ResponseEntity<?> update(OrderDTO orderDTO);

    //delete
    void delete(long id);
}
