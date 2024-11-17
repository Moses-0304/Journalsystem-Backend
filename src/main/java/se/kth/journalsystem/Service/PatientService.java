package se.kth.journalsystem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.journalsystem.DTO.PatientDTO;
import se.kth.journalsystem.model.Patient;
import se.kth.journalsystem.repository.PatientRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public PatientDTO getPatientById(Long id) {
        return patientRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    public PatientDTO getPatientByName(String name) {
        return patientRepository.findByName(name).map(this::convertToDTO).orElse(null);
    }

    public PatientDTO createPatient(PatientDTO patientDTO) {
        Patient patient = convertFromDTO(patientDTO);
        Patient savedPatient = patientRepository.save(patient);
        return convertToDTO(savedPatient);
    }

    public PatientDTO updatePatient(Long id, PatientDTO patientDetails) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            Patient existingPatient = patient.get();
            existingPatient.setName(patientDetails.getName());
            existingPatient.setBirthDate(patientDetails.getBirthDate());
            existingPatient.setContactInfo(patientDetails.getContactInfo());
            Patient updatedPatient = patientRepository.save(existingPatient);
            return convertToDTO(updatedPatient);
        }
        return null;
    }

    public boolean deletePatient(Long id) {
        if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Konverteringsmetoder
    private PatientDTO convertToDTO(Patient patient) {
        PatientDTO dto = new PatientDTO();
        dto.setId(patient.getId());
        dto.setName(patient.getName());
        dto.setBirthDate(patient.getBirthDate());
        dto.setContactInfo(patient.getContactInfo());
        dto.setObservationIds(
                patient.getObservations() != null ?
                        patient.getObservations().stream().map(observation -> observation.getId()).collect(Collectors.toList()) :
                        null
        );
        dto.setEncounterIds(
                patient.getEncounters() != null ?
                        patient.getEncounters().stream().map(encounter -> encounter.getId()).collect(Collectors.toList()) :
                        null
        );
        dto.setConditionIds(
                patient.getConditions() != null ?
                        patient.getConditions().stream().map(condition -> condition.getId()).collect(Collectors.toList()) :
                        null
        );
        return dto;
    }

    private Patient convertFromDTO(PatientDTO dto) {
        Patient patient = new Patient();
        patient.setName(dto.getName());
        patient.setBirthDate(dto.getBirthDate());
        patient.setContactInfo(dto.getContactInfo());
        return patient;
    }
}
