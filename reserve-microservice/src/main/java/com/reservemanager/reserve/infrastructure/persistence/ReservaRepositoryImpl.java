package com.reservemanager.reserve.infrastructure.persistence;
import com.reservemanager.reserve.domain.model.Reserve;
import com.reservemanager.reserve.domain.repository.ReserveRepository;
import com.reservemanager.reserve.infrastructure.persistence.entity.ReserveJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
@Transactional
public class ReserveRepositoryImpl implements ReserveRepository {

    private final ReserveJpaRepository jpaRepository;

    public ReserveRepositoryImpl(ReserveJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Reserve> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Reserve> findAll(Sort sort) {
        return jpaRepository.findAll(sort).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Reserve> findAll(Pageable pageable) {
        var page = jpaRepository.findAll(pageable);
        return new PageImpl<>(
                page.getContent().stream()
                        .map(this::toDomain)
                        .collect(Collectors.toList()),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    public List<Reserve> findAllById(Iterable<Long> ids) {
        return jpaRepository.findAllById(ids).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public void delete(Reserve entity) {
        jpaRepository.delete(toEntity(entity));
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        jpaRepository.deleteAllById(ids);
    }

    @Override
    public void deleteAll(Iterable<? extends Reserve> entities) {
        jpaRepository.deleteAll(
                StreamSupport.stream(entities.spliterator(), false)
                        .map(this::toEntity)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public void deleteAll() {
        jpaRepository.deleteAll();
    }

    @Override
    public <S extends Reserve> S save(S entity) {
        var savedEntity = jpaRepository.save(toEntity(entity));
        return (S) toDomain(savedEntity);
    }

    @Override
    public <S extends Reserve> List<S> saveAll(Iterable<S> entities) {
        var savedEntities = jpaRepository.saveAll(
                StreamSupport.stream(entities.spliterator(), false)
                        .map(this::toEntity)
                        .collect(Collectors.toList())
        );
        return savedEntities.stream()
                .map(this::toDomain)
                .map(entity -> (S) entity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Reserve> findById(Long id) {
        return jpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public void flush() {
        jpaRepository.flush();
    }

    @Override
    public <S extends Reserve> S saveAndFlush(S entity) {
        var savedEntity = jpaRepository.saveAndFlush(toEntity(entity));
        return (S) toDomain(savedEntity);
    }

    @Override
    public <S extends Reserve> List<S> saveAllAndFlush(Iterable<S> entities) {
        var savedEntities = jpaRepository.saveAllAndFlush(
                StreamSupport.stream(entities.spliterator(), false)
                        .map(this::toEntity)
                        .collect(Collectors.toList())
        );
        return savedEntities.stream()
                .map(this::toDomain)
                .map(entity -> (S) entity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllInBatch(Iterable<Reserve> entities) {
        jpaRepository.deleteAllInBatch(
                StreamSupport.stream(entities.spliterator(), false)
                        .map(this::toEntity)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> ids) {
        jpaRepository.deleteAllByIdInBatch(ids);
    }

    @Override
    public void deleteAllInBatch() {
        jpaRepository.deleteAllInBatch();
    }

    @Override
    public Reserve getOne(Long id) {
        return toDomain(jpaRepository.getOne(id));
    }

    @Override
    public Reserve getById(Long id) {
        return toDomain(jpaRepository.getById(id));
    }

    @Override
    public Reserve getReferenceById(Long id) {
        return toDomain(jpaRepository.getReferenceById(id));
    }

    @Override
    public List<Reserve> findByRoomIdAndDateTimeBetween(Long roomId, LocalDateTime start, LocalDateTime end) {
        return jpaRepository.findByRoomIdAndDateTimeBetween(roomId, start, end).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Reserve> findByUserId(Long userId) {
        return jpaRepository.findByUserId(userId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Reserve> findByRoomId(Long roomId) {
        return jpaRepository.findByRoomId(roomId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private ReserveJpaEntity toEntity(Reserve reserve) {
        var entity = new ReserveJpaEntity();
        entity.setId(reserve.getId());
        entity.setRoomId(reserve.getRoomId());
        entity.setUserId(reserve.getUserId());
        entity.setDateTime(reserve.getDateTime());
        return entity;
    }

    private Reserve toDomain(ReserveJpaEntity entity) {
        return new Reserve(
                entity.getId(),
                entity.getRoomId(),
                entity.getUserId(),
                entity.getDateTime()
        );
    }
}