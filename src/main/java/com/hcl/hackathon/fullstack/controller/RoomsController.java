package com.hcl.hackathon.fullstack.controller;

import com.hcl.hackathon.fullstack.entities.Amenities;
import com.hcl.hackathon.fullstack.entities.Room;
import com.hcl.hackathon.fullstack.models.AvailableRoom;
import com.hcl.hackathon.fullstack.models.RoomRequest;
import com.hcl.hackathon.fullstack.models.RoomReservationRequest;
import com.hcl.hackathon.fullstack.service.RoomsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("api/rooms")
@AllArgsConstructor
public class RoomsController {

    private RoomsService roomsService;

    @RequestMapping(value = "available", method = POST)
    public List<AvailableRoom> availableRooms(@RequestBody RoomRequest roomRequest) {
        List<Room> rooms = roomsService.getAvailableRooms(roomRequest);
        List<AvailableRoom> availableRooms = rooms.stream().map(room -> AvailableRoom.builder()
                .name(room.getName())
                .buildingName(room.getBuildingName())
                .capacity(room.getCapacity())
                .city(room.getCity())
                .floor(room.getFloor())
                .roomId(room.getId())
                .amenities(room.getAmenities().stream()
                        .map(Amenities::getType)
                        .collect(Collectors.toList()))
                .build())
                .collect(Collectors.toList());
        return availableRooms;
    }

    @RequestMapping(value = "reserve", method = POST)
    public Long reserveARoom(@RequestBody RoomReservationRequest reservationRequest) {
        return roomsService.reserveRoom(reservationRequest);
    }
}
