package se.kth.journalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.kth.journalsystem.model.Encounter;

import java.util.List;

public interface EncounterRepository extends JpaRepository<Encounter, Long> {
    List<Encounter> findByPatientId(Long patientId);
    List<Encounter> findByPractitionerId(Long practitionerId);
}
