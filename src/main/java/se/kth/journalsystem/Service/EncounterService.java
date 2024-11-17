package se.kth.journalsystem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.journalsystem.DTO.EncounterDTO;
import se.kth.journalsystem.model.Encounter;
import se.kth.journalsystem.model.Patient;
import se.kth.journalsystem.model.Practitioner;
import se.kth.journalsystem.repository.EncounterRepository;
import se.kth.journalsystem.repository.PatientRepository;
import se.kth.journalsystem.repository.PractitionerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EncounterService {

    private final EncounterRepository encounterRepository;
    private final PatientRepository patientRepository;
    private final PractitionerRepository practitionerRepository;

    @Autowired
    public EncounterService(EncounterRepository encounterRepository, PatientRepository patientRepository, PractitionerRepository practitionerRepository) {
        this.encounterRepository = encounterRepository;
        this.patientRepository = patientRepository;
        this.practitionerRepository = practitionerRepository;
    }

    public List<EncounterDTO> getAllEncounters() {
        return encounterRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public EncounterDTO getEncounterById(Long id) {
        return encounterRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    public List<EncounterDTO> getEncountersByPatientId(Long patientId) {
        return encounterRepository.findByPatientId(patientId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<EncounterDTO> getEncountersByPractitionerId(Long practitionerId) {
        return encounterRepository.findByPractitionerId(practitionerId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public EncounterDTO createEncounter(EncounterDTO encounterDTO) {
        Encounter encounter = convertFromDTO(encounterDTO);
        Encounter savedEncounter = encounterRepository.save(encounter);
        return convertToDTO(savedEncounter);
    }

    public EncounterDTO updateEncounter(Long id, EncounterDTO encounterDetails) {
        Optional<Encounter> encounter = encounterRepository.findById(id);
        if (encounter.isPresent()) {
            Encounter existingEncounter = encounter.get();
            existingEncounter.setEncounterDate(encounterDetails.getEncounterDate());
            // Note: Patient and Practitioner should not change here
            Encounter updatedEncounter = encounterRepository.save(existingEncounter);
            return convertToDTO(updatedEncounter);
        }
        return null;
    }

    public boolean deleteEncounter(Long id) {
        if (encounterRepository.existsById(id)) {
            encounterRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Konverteringsmetoder
    private EncounterDTO convertToDTO(Encounter encounter) {
        EncounterDTO dto = new EncounterDTO();
        dto.setId(encounter.getId());
        dto.setEncounterDate(encounter.getEncounterDate());
        dto.setPatientId(encounter.getPatient().getId());
        dto.setPractitionerId(encounter.getPractitioner().getId());
        return dto;
    }

    private Encounter convertFromDTO(EncounterDTO dto) {
        Encounter encounter = new Encounter();
        encounter.setEncounterDate(dto.getEncounterDate());

        // Fetch Patient and Practitioner, ensuring they exist
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + dto.getPatientId()));
        encounter.setPatient(patient);

        Practitioner practitioner = practitionerRepository.findById(dto.getPractitionerId())
                .orElseThrow(() -> new RuntimeException("Practitioner not found with ID: " + dto.getPractitionerId()));
        encounter.setPractitioner(practitioner);

        return encounter;
    }
}
