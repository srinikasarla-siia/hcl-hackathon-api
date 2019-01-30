package com.hcl.hackathon.fullstack.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer capacity;
    private String buildingName;
    private String floor;
    private String city;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "room_amenities")
    List<Amenities> amenities;
}
