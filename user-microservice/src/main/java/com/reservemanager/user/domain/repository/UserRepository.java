package com.reservemanager.user.domain.repository;
import com.reservemanager.user.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository {
    List<User> findAll();
    List<User> findAll(Sort sort);
    Page<User> findAll(Pageable pageable);
    List<User> findAllById(Iterable<Long> ids);
    long count();
    void deleteById(Long id);
    void delete(User entity);
    void deleteAllById(Iterable<? extends Long> ids);
    void deleteAll(Iterable<? extends User> entities);
    void deleteAll();
    <S extends User> S save(S entity);
    <S extends User> List<S> saveAll(Iterable<S> entities);
    Optional<User> findById(Long id);
    boolean existsById(Long id);
    void flush();
    <S extends User> S saveAndFlush(S entity);
    <S extends User> List<S> saveAllAndFlush(Iterable<S> entities);
    void deleteAllInBatch(Iterable<User> entities);
    void deleteAllByIdInBatch(Iterable<Long> ids);
    void deleteAllInBatch();
    User getOne(Long id);
    User getById(Long id);
    User getReferenceById(Long id);
    List<User> findByActiveTrue();
    Optional<User> findByRegistrationNumber(String registrationNumber);
}
