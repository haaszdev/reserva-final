package com.reservemanager.reserve.infrastructure.event;
import com.reservemanager.reserve.domain.event.ReserveEvent;
import com.reservemanager.reserve.domain.event.ReserveEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringReserveEventPublisher implements ReserveEventPublisher {

    private final ApplicationEventPublisher publisher;

    public SpringReserveEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publish(ReserveEvent event) {
        publisher.publishEvent(event);
    }
}