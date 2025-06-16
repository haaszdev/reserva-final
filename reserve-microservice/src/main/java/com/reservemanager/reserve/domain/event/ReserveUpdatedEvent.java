package com.reservemanager.reserve.domain.event;
import com.reservemanager.reserve.domain.model.Reserve;

public class ReserveUpdatedEvent implements ReserveEvent {
    private final Reserve reserve;

    public ReserveUpdatedEvent(Reserve reserve) {
        this.reserve = reserve;
    }

    @Override
    public Reserve getReserve() {
        return reserve;
    }
}
