package com.reservemanager.user.domain.event;
import com.reservemanager.user.domain.model.User;

public class UserDeletedEvent implements UserEvent {
    private final User user;

    public UserDeletedEvent(User user) {
        this.user = user;
    }

    @Override
    public User getUser() {
        return user;
    }
}
