package com.capstone.booking.api.output;

import lombok.Data;

@Data
public class ReportItem {
    String ticketTypeName;
    int quantity;
    int total;
    int remaining;
}
