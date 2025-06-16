package com.reservemanager.room.infrastructure.event;
import com.reservemanager.room.domain.event.RoomEvent;
import com.reservemanager.room.domain.event.RoomEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringRoomEventPublisher implements RoomEventPublisher {

    private final ApplicationEventPublisher publisher;

    public SpringRoomEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publish(RoomEvent event) {
        publisher.publishEvent(event);
    }
}
