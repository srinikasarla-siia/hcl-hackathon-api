package com.hcl.hackathon.fullstack.persistance;

import com.hcl.hackathon.fullstack.entities.Amenities;
import com.hcl.hackathon.fullstack.entities.Room;
import com.hcl.hackathon.fullstack.entities.RoomReservation;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class RoomsRepositoryTest {

    @Autowired
    private RoomsRepository subject;
    @Autowired
    private RoomReservationsRepository roomReservationsRepository;
    private Room room;
    private List<String> amenities;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Before
    public void setUp() throws Exception {
        subject.deleteAll();
        amenities = Lists.newArrayList("AppleTV");
        room = Room.builder()
                .name("Death Star")
                .capacity(2)
                .floor("Second")
                .buildingName("Cypress")
                .city("Irving")
                .build();
        room.setAmenities(Lists.newArrayList(Amenities.builder()
                .type(amenities.get(0))
                .build()));
        startTime = LocalDateTime.of(2018, 11, 06, 11, 30);
        endTime = LocalDateTime.of(2018, 11, 06, 12, 30);


    }

    @Test
    public void findAvailableRooms_includes_whenAllMatch() {
        subject.save(room);
        List<Room> availableRooms = subject.findAvailableRooms("Death Star", 2, "Second", "Cypress", "Irving", amenities, startTime, endTime);
        assertThat(availableRooms).hasSize(1);
    }

    @Test
    public void findAvailableRooms_includes_whenAllNull() {
        subject.save(room);
        List<Room> availableRooms = subject.findAvailableRooms(null, null, null, null, null, null, startTime, endTime);
        assertThat(availableRooms).hasSize(1);
    }

    @Test
    public void findAvailableRooms_doesNotInclude_whenDifferentRoomName() {
        subject.save(room);
        List<Room> availableRooms = subject.findAvailableRooms("Not Death Star", 2, "Third", "NotCypress", "Irving", amenities, startTime, endTime);
        assertThat(availableRooms).isEmpty();
    }

    @Test
    public void findAvailableRooms_doesNotInclude_whenDifferentCityName() {
        subject.save(room);
        List<Room> availableRooms = subject.findAvailableRooms("Not Death Star", 2, "Second", "Cypress","Irving", amenities, startTime, endTime);
        assertThat(availableRooms).isEmpty();
    }

    @Test
    public void findAvailableRooms_doesNotIncludeWitLessCapacity() {
        room.setCapacity(1);
        subject.save(room);
        List<Room> availableRooms = subject.findAvailableRooms("Death Star", 2, "Second", "Cypress", "Irving", amenities, startTime, endTime);
        assertThat(availableRooms).isEmpty();
    }

    @Test
    public void findAvailableRooms_doesNotInclude_whenDifferentFloorName() {
        subject.save(room);
        List<Room> availableRooms = subject.findAvailableRooms("Death Star", 2, "Third", "Cypress", "Irving", amenities, startTime, endTime);
        assertThat(availableRooms).isEmpty();
    }

    @Test
    public void findAvailableRooms_doesNotInclude_whenDifferentBuildingName() {
        subject.save(room);
        List<Room> availableRooms = subject.findAvailableRooms("Death Star", 2, "Second", "NotCypress", "Irving", amenities, startTime, endTime);
        assertThat(availableRooms).isEmpty();
    }

    @Test
    public void findAvailableRooms_doesNotInclude_whenRoomIsBooked() {
        Room save = subject.save(room);
        roomReservationsRepository.save(RoomReservation.builder()
                .roomId(save.getId())
                .startTime(startTime)
                .endTime(endTime)
                .build());

        List<Room> availableRooms = subject.findAvailableRooms("Death Star", 2, "Second", "Cypress", "Irving", amenities, startTime, endTime);
        assertThat(availableRooms).isEmpty();
    }

    @Test
    public void findAvailableRooms_doesNotInclude_whenRoomIsPartiallyBooked() {
        Room save = subject.save(room);
        roomReservationsRepository.save(RoomReservation.builder()
                .roomId(save.getId())
                .startTime(startTime.minusHours(1))
                .endTime(endTime.plusMinutes(65))
                .build());

        List<Room> availableRooms = subject.findAvailableRooms("Death Star", 2, "Second", "Cypress", "Irving", amenities, startTime, endTime);
        assertThat(availableRooms).isEmpty();
    }

    @Test
    public void findAvailableRooms_includesRoom_whenRoomIsNotBookedAtThatTime() {
        Room save = subject.save(room);
        roomReservationsRepository.save(RoomReservation.builder()
                .roomId(save.getId())
                .startTime(startTime.plusHours(1))
                .endTime(endTime.plusHours(1))
                .build());

        List<Room> availableRooms = subject.findAvailableRooms("Death Star", 2, "Second", "Cypress", "Irving", amenities, startTime, endTime);
        assertThat(availableRooms).hasSize(1);
    }

    @Test
    public void findAvailableRooms_includesAmenities() {
        subject.save(room);
        List<Room> availableRooms = subject.findAvailableRooms("Death Star", 2, "Second", "Cypress", "Irving", amenities, startTime, endTime);
        assertThat(availableRooms).hasSize(1);
    }

    @Test
    public void findAvailableRooms_doesNotIncludeRoom_whenRequestedAmenitiesNotThere() {
        room.setAmenities(Lists.newArrayList(Amenities.builder()
                .type(amenities.get(0))
                .build()));
        subject.save(room);
        List<Room> availableRooms = subject.findAvailableRooms("Death Star", 2, "Second", "Cypress", "Irving", Lists.newArrayList("NotAppleTV"), startTime, endTime);
        assertThat(availableRooms).isEmpty();
    }

}