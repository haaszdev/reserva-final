package com.reservemanager.reserve.domain.repository;
import com.reservemanager.reserve.domain.model.Reserve;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReserveRepository {
    List<Reserve> findAll();
    List<Reserve> findAll(Sort sort);
    Page<Reserve> findAll(Pageable pageable);
    List<Reserve> findAllById(Iterable<Long> ids);
    long count();
    void deleteById(Long id);
    void delete(Reserve entity);
    void deleteAllById(Iterable<? extends Long> ids);
    void deleteAll(Iterable<? extends Reserve> entities);
    void deleteAll();
    <S extends Reserve> S save(S entity);
    <S extends Reserve> List<S> saveAll(Iterable<S> entities);
    Optional<Reserve> findById(Long id);
    boolean existsById(Long id);
    void flush();
    <S extends Reserve> S saveAndFlush(S entity);
    <S extends Reserve> List<S> saveAllAndFlush(Iterable<S> entities);
    void deleteAllInBatch(Iterable<Reserve> entities);
    void deleteAllByIdInBatch(Iterable<Long> ids);
    void deleteAllInBatch();
    Reserve getOne(Long id);
    Reserve getById(Long id);
    Reserve getReferenceById(Long id);
    List<Reserve> findByRoomIdAndDateTimeBetween(Long roomId, LocalDateTime start, LocalDateTime end);
    List<Reserve> findByUserId(Long userId);
    List<Reserve> findByRoomId(Long roomId);
}