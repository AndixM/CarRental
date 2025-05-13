package com.example.carrental.repository;

import com.example.carrental.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.firstName=:name")
    Optional<User> findUserByName(String name);
    @Query("select u from User u where u.username=:username")
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
}
