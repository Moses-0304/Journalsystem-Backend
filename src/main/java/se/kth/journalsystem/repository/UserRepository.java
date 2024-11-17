package se.kth.journalsystem.repository;

import se.kth.journalsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Hitta user baserat på användarnamn
    Optional<User> findByUsername(String username);
}
