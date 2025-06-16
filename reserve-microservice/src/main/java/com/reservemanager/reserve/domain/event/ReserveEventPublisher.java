package com.reservemanager.reserve.domain.event;

public interface ReserveEventPublisher {
    void publish(ReserveEvent event);
}
