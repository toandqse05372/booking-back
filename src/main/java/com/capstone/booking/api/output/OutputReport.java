package com.capstone.booking.api.output;

import lombok.Data;

import java.util.List;

@Data
public class OutputReport {
    List<ReportItem> reportItemList;
    Long startDate;
    Long endDate;
    Long reportType;
    Long placeId;
    int totalRevenue;
}
