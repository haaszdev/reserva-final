package com.reservemanager.room.domain.event;
import com.reservemanager.room.domain.model.Room;

public interface RoomEvent {
    Room getRoom();
}
