package com.reservemanager.room.domain.event;
import com.reservemanager.room.domain.model.Room;

public class RoomDeletedEvent implements RoomEvent {
    private final Room room;

    public RoomDeletedEvent(Room room) {
        this.room = room;
    }

    @Override
    public Room getRoom() {
        return room;
    }
}
