package com.reservemanager.reserve.interfaces.rest;
import com.reservemanager.reserve.application.service.ReserveService;
import com.reservemanager.reserve.domain.model.Reserve;
import com.reservemanager.reserve.interfaces.rest.dto.ReserveRequest;
import com.reservemanager.reserve.interfaces.rest.dto.ReserveResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reserves")
public class ReserveController {
    private final ReserveService reserveService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public ReserveController(ReserveService reserveService) {
        this.reserveService = reserveService;
    }

    @GetMapping
    public ResponseEntity<List<ReserveResponse>> listReserves() {
        List<Reserve> reserves = reserveService.listReserves();
        List<ReserveResponse> response = reserves.stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReserveResponse> getReserve(@PathVariable Long id) {
        return reserveService.getReserve(id)
            .map(this::toResponse)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReserveResponse> createReserve(@RequestBody ReserveRequest request) {
        LocalDateTime dateTime = LocalDateTime.parse(request.getDateTime(), formatter);
        Reserve reserve = reserveService.createReserve(
            request.getRoomId(),
            request.getUserId(),
            dateTime
        );
        return ResponseEntity.ok(toResponse(reserve));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReserve(@PathVariable Long id) {
        reserveService.cancelReserve(id);
        return ResponseEntity.noContent().build();
    }

    private ReserveResponse toResponse(Reserve reserve) {
        return new ReserveResponse(
            reserve.getId(),
            reserve.getRoomId(),
            reserve.getUserId(),
            reserve.getDateTime().format(formatter)
        );
    }
}
