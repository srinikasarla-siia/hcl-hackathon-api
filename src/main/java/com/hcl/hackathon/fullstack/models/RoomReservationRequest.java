package com.hcl.hackathon.fullstack.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomReservationRequest {
    private Long roomId;
    @DateTimeFormat(pattern = "YYYY-mm-DDTHH:MM")
    private LocalDateTime startDateTime;
    @DateTimeFormat(pattern = "YYYY-mm-DDTHH:MM")
    private LocalDateTime endDateTime;
}
