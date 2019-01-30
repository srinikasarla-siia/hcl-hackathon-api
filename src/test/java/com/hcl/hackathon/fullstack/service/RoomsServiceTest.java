package com.hcl.hackathon.fullstack.service;

import com.hcl.hackathon.fullstack.entities.Room;
import com.hcl.hackathon.fullstack.entities.RoomReservation;
import com.hcl.hackathon.fullstack.models.RoomRequest;
import com.hcl.hackathon.fullstack.models.RoomReservationRequest;
import com.hcl.hackathon.fullstack.persistance.RoomReservationsRepository;
import com.hcl.hackathon.fullstack.persistance.RoomsRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoomsServiceTest {
    private RoomsService subject;
    @Mock
    private RoomsRepository roomsRepository;
    @Mock
    private RoomReservationsRepository roomReservationsRepository;
    @Captor
    private ArgumentCaptor<RoomReservation> roomReservationArgumentCaptor;
    private Random random;


    @Before
    public void setUp() throws Exception {
        subject = new RoomsService(roomsRepository, roomReservationsRepository);
        random = new Random();
    }

    @Test
    public void getAvailableRooms_callsRepository() throws Exception {
        RoomRequest roomRequest = RoomRequest.builder()
                .startDateTime(LocalDateTime.now())

                .endDateTime(LocalDateTime.now())
                .buildingName("Cypress" + random.nextInt(99))
                .amenities(Lists.newArrayList("AppleTV" + random.nextInt(99)))
                .minimumCapacity(random.nextInt(99))
                .city("Irving" + random.nextInt(99))
                .floor("Third Floor" + random.nextInt(99))
                .name("Death Star" + random.nextInt(99))
                .build();

        List<Room> rooms = Lists.newArrayList(Room.builder().build());
        when(roomsRepository.findAvailableRooms(anyString(), anyInt(), anyString(), anyString(),
                anyString(), anyList(), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(rooms);

        List<Room> availableRooms = subject.getAvailableRooms(roomRequest);

        verify(roomsRepository).findAvailableRooms(roomRequest.getName(), roomRequest.getMinimumCapacity(), roomRequest.getFloor(),
                roomRequest.getBuildingName(), roomRequest.getCity(), roomRequest.getAmenities(), roomRequest.getStartDateTime(), roomRequest.getEndDateTime());

        assertEquals(rooms, availableRooms);
    }

    @Test
    public void reserveRoom_callsRepositorySave() {
        Long reservationId = random.nextLong();
        RoomReservationRequest roomReservationRequest = RoomReservationRequest.builder()
                .roomId(random.nextLong())
                .startDateTime(LocalDateTime.now())
                .endDateTime(LocalDateTime.now())
                .build();

        when(roomReservationsRepository.save(any(RoomReservation.class)))
                .thenReturn(RoomReservation.builder().id(reservationId).build());

        Long actualReservationId = subject.reserveRoom(roomReservationRequest);
        verify(roomReservationsRepository).save(roomReservationArgumentCaptor.capture());

        RoomReservation roomReservation = roomReservationArgumentCaptor.getValue();
        assertEquals(roomReservationRequest.getRoomId(), roomReservation.getRoomId());
        assertEquals(roomReservationRequest.getStartDateTime(), roomReservation.getStartTime());
        assertEquals(roomReservationRequest.getEndDateTime(), roomReservation.getEndTime());
        assertEquals(reservationId, actualReservationId);
    }
}