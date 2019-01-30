package com.hcl.hackathon.fullstack.persistance;

import com.hcl.hackathon.fullstack.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RoomsRepository extends JpaRepository<Room, Long> {
    @Query(" select r from Room r " +
            "left join r.amenities a " +
            "where (r.name = :name or :name is null)" +
            "and (r.capacity >= :capacity or :capacity is null)" +
            "and (r.floor = :floor or :floor is null)" +
            "and (r.buildingName = :buildingName or :buildingName is null)" +
            "and (r.city = :city or :city is null)" +
            "and r.id not in (select rr.roomId from RoomReservation rr where rr.startTime < :endTime and rr.endTime > :startTime) " +
            "and (a.type in (:amenities) or :amenities is null)")
    List<Room> findAvailableRooms(@Param("name") String name,
                                  @Param("capacity") Integer capacity,
                                  @Param("floor") String floor,
                                  @Param("buildingName") String buildingName,
                                  @Param("city") String city,
                                  @Param("amenities") List<String> amenities,
                                  @Param("startTime") LocalDateTime startTime,
                                  @Param("endTime") LocalDateTime endTime);
}
