package com.reservemanager.reserve.infrastructure.persistence;
import com.reservemanager.reserve.infrastructure.persistence.entity.ReserveJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReserveJpaRepository extends JpaRepository<ReserveJpaEntity, Long> {

    List<ReserveJpaEntity> findByRoomIdAndDateTimeBetween(Long roomId, LocalDateTime start, LocalDateTime end);

    List<ReserveJpaEntity> findByUserId(Long userId);

    List<ReserveJpaEntity> findByRoomId(Long roomId);
}