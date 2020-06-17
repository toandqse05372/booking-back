package com.capstone.booking.service;

import com.capstone.booking.entity.dto.TicketTypeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface TicketTypeService {
    //get All
    ResponseEntity<?> findAll();

    //xóa
    void delete(Long id);

    //them
    ResponseEntity<?> create(TicketTypeDTO ticketTypeDTO);

    //sưa
    ResponseEntity<?> update(TicketTypeDTO ticketTypeDTO);

    //tim kiem theo tên loại vé
    ResponseEntity<?> findByTypeName(String typeName, Long limit, Long page);

    ResponseEntity<?> addCodeForTicketType(MultipartFile file, String codeType);
}
