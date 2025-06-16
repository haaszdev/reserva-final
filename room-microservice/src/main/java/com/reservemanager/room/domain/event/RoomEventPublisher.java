package com.reservemanager.room.domain.event;

public interface RoomEventPublisher {
    void publish(RoomEvent event);
}
