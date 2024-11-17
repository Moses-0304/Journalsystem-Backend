package se.kth.journalsystem.repository;

import se.kth.journalsystem.model.Practitioner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PractitionerRepository extends JpaRepository<Practitioner, Long> {
    // Hitta practitioner baserat på namn
    Optional<Practitioner> findByName(String name);
}
