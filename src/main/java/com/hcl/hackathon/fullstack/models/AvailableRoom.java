package com.hcl.hackathon.fullstack.models;

import lombok.Builder;
import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableRoom {
    private Long roomId;
    private String name;
    private Integer capacity;
    private String buildingName;
    private String floor;
    private String city;
    private List<String> amenities;
}
