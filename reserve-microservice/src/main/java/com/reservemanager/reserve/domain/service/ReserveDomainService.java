package com.reservemanager.reserve.domain.service;
import com.reservemanager.reserve.domain.event.ReserveEventPublisher;
import com.reservemanager.reserve.domain.event.ReserveCreatedEvent;
import com.reservemanager.reserve.domain.event.ReserveUpdatedEvent;
import com.reservemanager.reserve.domain.event.ReserveDeletedEvent;
import com.reservemanager.reserve.domain.model.Reserve;
import com.reservemanager.reserve.domain.repository.ReserveRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReserveDomainService {

    private final ReserveRepository reserveRepository;
    private final ReserveEventPublisher eventPublisher;

    public ReserveDomainService(ReserveRepository reserveRepository,
                                 ReserveEventPublisher eventPublisher) {
        this.reserveRepository = reserveRepository;
        this.eventPublisher = eventPublisher;
    }

    public List<Reserve> listAllReserves() {
        return reserveRepository.findAll();
    }

    public Optional<Reserve> findReserveById(Long id) {
        return reserveRepository.findById(id);
    }

    public Reserve createReserve(Reserve reserve) {
        Reserve savedReserve = reserveRepository.save(reserve);
        eventPublisher.publish(new ReserveCreatedEvent(savedReserve));
        return savedReserve;
    }

    public void updateReserve(Reserve reserve) {
        Reserve updatedReserve = reserveRepository.save(reserve);
        eventPublisher.publish(new ReserveUpdatedEvent(updatedReserve));
    }

    public void deleteReserve(Long id) {
        reserveRepository.findById(id).ifPresent(reserve -> {
            reserveRepository.deleteById(id);
            eventPublisher.publish(new ReserveDeletedEvent(reserve));
        });
    }

    public List<Reserve> listReservesByUser(Long userId) {
        return reserveRepository.findByUserId(userId);
    }

    public List<Reserve> listReservesByRoom(Long roomId) {
        return reserveRepository.findByRoomId(roomId);
    }
}