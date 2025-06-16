package com.reservemanager.reserve.domain.event;
import com.reservemanager.reserve.domain.model.Reserve;


public class ReserveDeletedEvent implements ReserveEvent {
    private final Reserve reserve;

    public ReserveDeletedEvent(Reserve reserve) {
        this.reserve = reserve;
    }

    @Override
    public Reserve getReserve() {
        return reserve;
    }
}
