package com.capstone.booking.service;

import com.capstone.booking.common.key.OrderStatus;
import com.capstone.booking.entity.dto.OrderDTO;
import com.itextpdf.text.DocumentException;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URISyntaxException;

public interface OrderService {
    //add
    ResponseEntity<?> create(OrderDTO orderDTO, OrderStatus status);

    //edit
    ResponseEntity<?> update(OrderDTO orderDTO);

    //delete
    ResponseEntity<?> delete(long id);

    //search Order by status, & paging
    ResponseEntity<?> findByStatus(String status, String code);

    //search by Id
    ResponseEntity<?> findByOrderId(Long id);

    ResponseEntity<?> sendTicket(long id) throws DocumentException, IOException, URISyntaxException, MessagingException;

    ResponseEntity<?> resendTicket(long orderId) throws IOException, MessagingException, URISyntaxException, DocumentException;

    ResponseEntity<?> getOrderByUid(long id);

    ResponseEntity<?> getOrderByUidTop3(long id);
}
