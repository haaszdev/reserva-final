package com.reservemanager.room.infrastructure.persistence;
import com.reservemanager.room.domain.model.Room;
import com.reservemanager.room.domain.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
@Transactional
public class RoomRepositoryImpl implements RoomRepository {

    private final RoomJpaRepository jpaRepository;

    public RoomRepositoryImpl(RoomJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Room> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Room> findAll(Sort sort) {
        return jpaRepository.findAll(sort).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Room> findAll(Pageable pageable) {
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
    public List<Room> findAllById(Iterable<Long> ids) {
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
    public void delete(Room entity) {
        jpaRepository.delete(toEntity(entity));
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        jpaRepository.deleteAllById(ids);
    }

    @Override
    public void deleteAll(Iterable<? extends Room> entities) {
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
    public <S extends Room> S save(S entity) {
        var savedEntity = jpaRepository.save(toEntity(entity));
        return (S) toDomain(savedEntity);
    }

    @Override
    public <S extends Room> List<S> saveAll(Iterable<S> entities) {
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
    public Optional<Room> findById(Long id) {
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
    public <S extends Room> S saveAndFlush(S entity) {
        var savedEntity = jpaRepository.saveAndFlush(toEntity(entity));
        return (S) toDomain(savedEntity);
    }

    @Override
    public <S extends Room> List<S> saveAllAndFlush(Iterable<S> entities) {
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
    public void deleteAllInBatch(Iterable<Room> entities) {
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
    public Room getOne(Long id) {
        return toDomain(jpaRepository.getOne(id));
    }

    @Override
    public Room getById(Long id) {
        return toDomain(jpaRepository.getById(id));
    }

    @Override
    public Room getReferenceById(Long id) {
        return toDomain(jpaRepository.getReferenceById(id));
    }

    @Override
    public List<Room> findByActiveTrue() {
        return jpaRepository.findByActiveTrue().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private RoomJpaEntity toEntity(Room room) {
        var entity = new RoomJpaEntity();
        entity.setId(room.getId());
        entity.setName(room.getName());
        entity.setCapacity(room.getCapacity());
        entity.setLocation(room.getLocation());
        entity.setActive(room.getActive());
        return entity;
    }

    private Room toDomain(RoomJpaEntity entity) {
        return new Room(
                entity.getId(),
                entity.getName(),
                entity.getCapacity(),
                entity.getLocation(),
                entity.getActive()
        );
    }
}
