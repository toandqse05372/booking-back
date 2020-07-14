package com.capstone.booking.service;

import com.capstone.booking.entity.dto.OrderDTO;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    //add
    ResponseEntity<?> create(OrderDTO orderDTO);

    //edit
    ResponseEntity<?> update(OrderDTO orderDTO);

    //delete
    ResponseEntity<?> delete(long id);

    //search Order by status, & paging
    ResponseEntity<?> findByStatus(String status, String code);

    //search by Id
    ResponseEntity<?> findByOrderId(Long id);

}
