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
    ResponseEntity<?> create(OrderDTO orderDTO, OrderStatus status, String paymentIntentId);

    //add
    ResponseEntity<?> update(OrderDTO orderDTO, OrderStatus status, String paymentIntentId);

    //delete
    ResponseEntity<?> delete(long id);

    //search Order by status, & paging
    ResponseEntity<?> findByStatus(String status, String code, Long placeId, Long page, Long limit);

    //search by Id
    ResponseEntity<?> findByOrderId(Long id, Long uid);

    ResponseEntity<?> sendTicket(long id) throws DocumentException, IOException, URISyntaxException, MessagingException;

    ResponseEntity<?> resendTicket(long orderId) throws IOException, MessagingException, URISyntaxException, DocumentException;

    ResponseEntity<?> getOrderByUid(long id, Long uid);

    ResponseEntity<?> getOrderByUid(long id, Long uid, int limit, int page);

    ResponseEntity<?> getOrderByUidTop3(long id, Long uid);

    ResponseEntity<?> findByOrderId(long id);
}
