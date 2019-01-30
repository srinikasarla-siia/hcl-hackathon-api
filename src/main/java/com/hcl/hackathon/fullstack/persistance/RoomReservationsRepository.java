package com.hcl.hackathon.fullstack.persistance;

import com.hcl.hackathon.fullstack.entities.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomReservationsRepository extends JpaRepository<RoomReservation, Long> {
}
