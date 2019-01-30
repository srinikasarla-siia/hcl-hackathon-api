package com.hcl.hackathon.fullstack.service;

import com.hcl.hackathon.fullstack.entities.Room;
import com.hcl.hackathon.fullstack.entities.RoomReservation;
import com.hcl.hackathon.fullstack.models.RoomRequest;
import com.hcl.hackathon.fullstack.models.RoomReservationRequest;
import com.hcl.hackathon.fullstack.persistance.RoomReservationsRepository;
import com.hcl.hackathon.fullstack.persistance.RoomsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class RoomsService {
    private RoomsRepository roomsRepository;
    private RoomReservationsRepository roomReservationsRepository;

    @Transactional(readOnly = true)
    public List<Room> getAvailableRooms(RoomRequest roomRequest) {
        return roomsRepository.findAvailableRooms(roomRequest.getName(), roomRequest.getMinimumCapacity(), roomRequest.getFloor(),
                roomRequest.getBuildingName(), roomRequest.getCity(), roomRequest.getAmenities(), roomRequest.getStartDateTime(), roomRequest.getEndDateTime());
    }

    public Long reserveRoom(RoomReservationRequest reservationRequest) {
        RoomReservation reservation = roomReservationsRepository.save(RoomReservation.builder()
                .roomId(reservationRequest.getRoomId())
                .startTime(reservationRequest.getStartDateTime())
                .endTime(reservationRequest.getEndDateTime())
                .build());
        return reservation.getId();
    }
}
