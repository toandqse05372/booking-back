package com.capstone.booking.service;

import com.capstone.booking.entity.dto.OrderDTO;
import com.itextpdf.text.DocumentException;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URISyntaxException;

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

    ResponseEntity<?> sendTicket(long id) throws DocumentException, IOException, URISyntaxException;
}
