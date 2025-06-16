package com.reservemanager.reserve.infrastructure.messaging;
import com.reservemanager.reserve.domain.event.ReserveCreatedEvent;
import com.reservemanager.reserve.domain.event.ReserveDeletedEvent;
import com.reservemanager.reserve.domain.model.Reserve;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaReserveEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC_RESERVE_CREATED = "reserve-created";
    private static final String TOPIC_RESERVE_DELETED = "reserve-deleted";

    public KafkaReserveEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishReserveCreated(Reserve reserve) {
        ReserveCreatedEvent event = new ReserveCreatedEvent(reserve);
        kafkaTemplate.send(TOPIC_RESERVE_CREATED, event);
    }

    public void publishReserveDeleted(Reserve reserve) {
        ReserveDeletedEvent event = new ReserveDeletedEvent(reserve);
        kafkaTemplate.send(TOPIC_RESERVE_DELETED, event);
    }
}