package ru.practicum.shareit.request;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByUserId(Long userId);

//    Request findById(Long id);

    List<Request> findAll();

    @Query(value = "select * from requests", nativeQuery = true)
    List<Request> findAll2();

//    @Query(value = "select * from requests", nativeQuery = true)
//    Page<Request> findAll3(Pageable pageable);

    Page<Request> findAllByUserIdNot(Long userId, Pageable pageable);

    List<Request> findAllByUserIdNot(Long userId);

}
