package com.reservemanager.reserve.domain.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateTime;
    private Long roomId;
    private Long userId;

    public Reserve(Long id, Long roomId, Long userId, LocalDateTime dateTime) {
        this.id = id;
        this.roomId = roomId;
        this.userId = userId;
        this.dateTime = dateTime;
    }
}
