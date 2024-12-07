package com.vofaone.Task_Manager.repository;

import com.vofaone.Task_Manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findOptionalUserByEmail(String email);

    @Query(value = "SELECT u FROM User u WHERE u.email = :email")
    User findUserByEmail(String email);
}
