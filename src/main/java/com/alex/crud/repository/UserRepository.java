package com.alex.crud.repository;

import com.alex.crud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByEmail(String email);

    Optional<User> findFirstByName(String name);

    @Query(value = "SELECT * FROM users WHERE name = 'Alex' OR name = 'Tolya'", nativeQuery = true)
    List<User> findAllAlexAndTolya();
}
