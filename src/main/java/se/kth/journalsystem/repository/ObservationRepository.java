package se.kth.journalsystem.repository;

import se.kth.journalsystem.model.Observation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ObservationRepository extends JpaRepository<Observation, Long> {
    List<Observation> findByPatientId(Long patientId);
}
