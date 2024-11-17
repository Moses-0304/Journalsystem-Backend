package se.kth.journalsystem.repository;

import se.kth.journalsystem.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    // Hitta patient baserat på namn
    Optional<Patient> findByName(String name);
}
