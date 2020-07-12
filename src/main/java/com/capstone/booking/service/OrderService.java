package com.capstone.booking.service;

import com.capstone.booking.entity.dto.OrderDTO;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    //Thêm
    ResponseEntity<?> create(OrderDTO orderDTO);

    //sửa
    ResponseEntity<?> update(OrderDTO orderDTO);

    //delete
    ResponseEntity<?> delete(long id);

    //tim kiem Order theo status, & paging
    ResponseEntity<?> findByStatus(String status, String code);

    ResponseEntity<?> findByOrderId(Long id);

}
