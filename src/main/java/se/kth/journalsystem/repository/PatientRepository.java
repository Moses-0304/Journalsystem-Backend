package se.kth.journalsystem.repository;

import se.kth.journalsystem.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Hitta patient baserat på namn
    Optional<Patient> findByName(String name);

    // Hitta patient baserat på födelsedatum
    Optional<Patient> findByBirthDate(String birthDate);

    // Hitta patient baserat på kontaktinformation
    Optional<Patient> findByContactInfo(String contactInfo);
}
