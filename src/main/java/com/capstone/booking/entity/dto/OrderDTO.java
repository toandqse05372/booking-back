package com.capstone.booking.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import java.util.Set;

@Data
@EqualsAndHashCode
public class OrderDTO extends BaseDTO{
    private Long ticketTypeId;
    private String ticketTypeName;
    private Long userId;
    private String firstName;
    private String lastName;
    private String mail;
    private String phoneNumber;
    private String status;
    private String orderCode;
    private int totalPayment;
    private Date purchaseDay;
    private Date redemptionDate;
    private Set<OrderItemDTO> orderItems;
}
