package com.reservemanager.room.domain.event;
import com.reservemanager.room.domain.model.Room;

public class RoomCreatedEvent implements RoomEvent {
    private final Room room;

    public RoomCreatedEvent(Room room) {
        this.room = room;
    }

    @Override
    public Room getRoom() {
        return room;
    }
}
