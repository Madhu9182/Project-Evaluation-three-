package io.capgemini.com.repository;

import io.capgemini.com.model.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserRegistration,Long> {
    Optional<UserRegistration> findByUsername(String username);
}
