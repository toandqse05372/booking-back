package com.capstone.booking.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;


@Data
@EqualsAndHashCode
public class TicketTypeDTO extends BaseDTO{
    private String typeName;
    private Set<Long> gameId;
    private Long placeId;
    private List<VisitorTypeDTO> visitorTypes;
    private String status;
}
