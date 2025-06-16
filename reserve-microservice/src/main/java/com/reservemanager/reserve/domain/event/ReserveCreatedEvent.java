package com.reservemanager.reserve.domain.event;
import com.reservemanager.reserve.domain.model.Reserve;

public class ReserveCreatedEvent implements ReserveEvent {
    private final Reserve reserve;

    public ReserveCreatedEvent(Reserve reserve) {
        this.reserve = reserve;
    }

    @Override
    public Reserve getReserve() {
        return reserve;
    }
}
