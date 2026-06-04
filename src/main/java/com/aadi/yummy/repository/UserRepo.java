package com.aadi.yummy.repository;

import com.aadi.yummy.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,String> {
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
}
