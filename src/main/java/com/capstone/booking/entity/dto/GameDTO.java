package com.capstone.booking.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode
public class GameDTO extends BaseDTO{
    private String gameName;
    private String gameDescription;
    private boolean ticketInventoryStatus;
    private String ticketTypeName;
    private Set<ParkDTO> park;
}
