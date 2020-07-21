package com.capstone.booking.common.helper.pdf;

import lombok.Data;

@Data
public class PrintTicketRequest {
    private long orderId;
    private int type;
}
