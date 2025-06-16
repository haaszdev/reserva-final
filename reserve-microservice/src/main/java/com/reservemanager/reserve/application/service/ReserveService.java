package com.reservemanager.reserve.application.service;
import com.reservemanager.reserve.domain.model.Reserve;
import com.reservemanager.reserve.domain.repository.ReserveRepository;
import com.reservemanager.reserve.infrastructure.messaging.KafkaReserveEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReserveService {
    private final ReserveRepository reserveRepository;
    private final KafkaReserveEventPublisher eventPublisher;

    public ReserveService(ReserveRepository reserveRepository, KafkaReserveEventPublisher eventPublisher) {
        this.reserveRepository = reserveRepository;
        this.eventPublisher = eventPublisher;
    }

    public Reserve createReserve(Long roomId, Long userId, LocalDateTime dateTime) {
        List<Reserve> existingReserves = reserveRepository.findByRoomIdAndDateTimeBetween(
            roomId, dateTime, dateTime.plusHours(1));

        if (!existingReserves.isEmpty()) {
            throw new RuntimeException("There is already a reservation for this room at this time");
        }

        Reserve reserve = new Reserve();
        reserve.setRoomId(roomId);
        reserve.setUserId(userId);
        reserve.setDateTime(dateTime);

        reserve = reserveRepository.save(reserve);
        eventPublisher.publishReserveCreated(reserve);

        return reserve;
    }

    public void cancelReserve(Long id) {
        Optional<Reserve> reserveOpt = reserveRepository.findById(id);
        if (reserveOpt.isPresent()) {
            Reserve reserve = reserveOpt.get();
            reserveRepository.delete(reserve);
            eventPublisher.publishReserveDeleted(reserve);
        }
    }

    public List<Reserve> listReserves() {
        return reserveRepository.findAll();
    }

    public Optional<Reserve> findReserve(Long id) {
        return reserveRepository.findById(id);
    }
}
