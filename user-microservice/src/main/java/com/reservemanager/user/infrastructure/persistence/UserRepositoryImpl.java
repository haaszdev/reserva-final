package com.reservemanager.user.infrastructure.persistence;
import com.reservemanager.user.domain.model.User;
import com.reservemanager.user.domain.repository.UserRepository;
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
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    public UserRepositoryImpl(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<User> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findAll(Sort sort) {
        return jpaRepository.findAll(sort).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
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
    public List<User> findAllById(Iterable<Long> ids) {
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
    public void delete(User entity) {
        jpaRepository.delete(toEntity(entity));
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        jpaRepository.deleteAllById(ids);
    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {
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
    public <S extends User> S save(S entity) {
        var savedEntity = jpaRepository.save(toEntity(entity));
        return (S) toDomain(savedEntity);
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
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
    public Optional<User> findById(Long id) {
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
    public <S extends User> S saveAndFlush(S entity) {
        var savedEntity = jpaRepository.saveAndFlush(toEntity(entity));
        return (S) toDomain(savedEntity);
    }

    @Override
    public <S extends User> List<S> saveAllAndFlush(Iterable<S> entities) {
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
    public void deleteAllInBatch(Iterable<User> entities) {
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
    public User getOne(Long id) {
        return toDomain(jpaRepository.getOne(id));
    }

    @Override
    public User getById(Long id) {
        return toDomain(jpaRepository.getById(id));
    }

    @Override
    public User getReferenceById(Long id) {
        return toDomain(jpaRepository.getReferenceById(id));
    }

    @Override
    public List<User> findByActiveTrue() {
        return jpaRepository.findByActiveTrue().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findByRegistration(String registration) {
        return jpaRepository.findByRegistration(registration)
                .map(this::toDomain);
    }

    private UserJpaEntity toEntity(User user) {
        var entity = new UserJpaEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setRegistration(user.getRegistration());
        entity.setActive(user.getActive());
        return entity;
    }

    private User toDomain(UserJpaEntity entity) {
        return new User(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getRegistration(),
                entity.getActive()
        );
    }
}
