package com.reservemanager.reserve.domain.event;
import com.reservemanager.reserve.domain.model.Reserve;

public interface ReserveEvent {
    Reserve getReserve();
}
