package se.kth.journalsystem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.journalsystem.DTO.ConditionDTO;
import se.kth.journalsystem.model.Condition;
import se.kth.journalsystem.model.Patient;
import se.kth.journalsystem.repository.ConditionRepository;
import se.kth.journalsystem.repository.PatientRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConditionService {

    private final ConditionRepository conditionRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public ConditionService(ConditionRepository conditionRepository, PatientRepository patientRepository) {
        this.conditionRepository = conditionRepository;
        this.patientRepository = patientRepository;
    }

    public List<ConditionDTO> getAllConditions() {
        return conditionRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ConditionDTO getConditionById(Long id) {
        return conditionRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    public List<ConditionDTO> getConditionsByPatientId(Long patientId) {
        return conditionRepository.findByPatientId(patientId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ConditionDTO createCondition(ConditionDTO conditionDTO) {
        Condition condition = convertFromDTO(conditionDTO);

        // Set the patient based on patientId
        if (conditionDTO.getPatientId() != null) {
            Patient patient = patientRepository.findById(conditionDTO.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + conditionDTO.getPatientId()));
            condition.setPatient(patient);
        }

        Condition savedCondition = conditionRepository.save(condition);
        return convertToDTO(savedCondition);
    }

    public ConditionDTO updateCondition(Long id, ConditionDTO conditionDetails) {
        Optional<Condition> condition = conditionRepository.findById(id);
        if (condition.isPresent()) {
            Condition existingCondition = condition.get();
            existingCondition.setDiagnosis(conditionDetails.getDiagnosis());
            existingCondition.setDiagnosisDate(conditionDetails.getDiagnosisDate());
            // Note: Patient should not change here

            Condition updatedCondition = conditionRepository.save(existingCondition);
            return convertToDTO(updatedCondition);
        }
        return null;
    }

    public boolean deleteCondition(Long id) {
        if (conditionRepository.existsById(id)) {
            conditionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Conversion methods
    private ConditionDTO convertToDTO(Condition condition) {
        ConditionDTO dto = new ConditionDTO();
        dto.setId(condition.getId());
        dto.setDiagnosis(condition.getDiagnosis());
        dto.setDiagnosisDate(condition.getDiagnosisDate());
        dto.setPatientId(condition.getPatient().getId());
        return dto;
    }

    private Condition convertFromDTO(ConditionDTO dto) {
        Condition condition = new Condition();
        condition.setDiagnosis(dto.getDiagnosis());
        condition.setDiagnosisDate(dto.getDiagnosisDate());
        return condition;
    }
}
