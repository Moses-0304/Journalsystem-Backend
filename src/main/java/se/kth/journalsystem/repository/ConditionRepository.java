package se.kth.journalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.kth.journalsystem.model.Condition;

import java.util.List;

public interface ConditionRepository extends JpaRepository<Condition, Long> {
    List<Condition> findByPatientId(Long patientId);
}
