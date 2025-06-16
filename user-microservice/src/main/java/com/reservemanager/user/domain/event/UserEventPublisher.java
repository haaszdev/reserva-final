package com.reservemanager.user.domain.event;

public interface UserEventPublisher {
    void publish(UserEvent event);
}
