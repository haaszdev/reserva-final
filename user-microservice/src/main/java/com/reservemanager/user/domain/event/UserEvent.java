package com.reservemanager.user.domain.event;
import com.reservemanager.user.domain.model.User;

public interface UserEvent {
    User getUser();
}
