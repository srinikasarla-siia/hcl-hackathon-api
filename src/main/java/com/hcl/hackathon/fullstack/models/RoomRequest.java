package com.hcl.hackathon.fullstack.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequest {

    private String name;
    private Integer minimumCapacity;
    private String floor;
    private String buildingName;
    private String city;
    private List<String> amenities;
    @DateTimeFormat(pattern = "YYYY-mm-DDTHH:MM")
    private LocalDateTime startDateTime;
    @DateTimeFormat(pattern = "YYYY-mm-DDTHH:MM")
    private LocalDateTime endDateTime;
}
