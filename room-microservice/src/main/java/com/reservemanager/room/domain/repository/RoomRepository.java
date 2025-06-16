package com.reservemanager.room.domain.repository;
import com.reservemanager.room.domain.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository {
    List<Room> findAll();
    List<Room> findAll(Sort sort);
    Page<Room> findAll(Pageable pageable);
    List<Room> findAllById(Iterable<Long> longs);
    long count();
    void deleteById(Long id);
    void delete(Room entity);
    void deleteAllById(Iterable<? extends Long> longs);
    void deleteAll(Iterable<? extends Room> entities);
    void deleteAll();
    <S extends Room> S save(S entity);
    <S extends Room> List<S> saveAll(Iterable<S> entities);
    Optional<Room> findById(Long id);
    boolean existsById(Long id);
    void flush();
    <S extends Room> S saveAndFlush(S entity);
    <S extends Room> List<S> saveAllAndFlush(Iterable<S> entities);
    void deleteAllInBatch(Iterable<Room> entities);
    void deleteAllByIdInBatch(Iterable<Long> longs);
    void deleteAllInBatch();
    Room getOne(Long id);
    Room getById(Long id);
    Room getReferenceById(Long id);
    List<Room> findByActiveTrue();
}
