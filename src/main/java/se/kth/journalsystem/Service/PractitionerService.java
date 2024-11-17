package se.kth.journalsystem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.journalsystem.DTO.PractitionerDTO;
import se.kth.journalsystem.model.Practitioner;
import se.kth.journalsystem.repository.PractitionerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PractitionerService {

    private final PractitionerRepository practitionerRepository;

    @Autowired
    public PractitionerService(PractitionerRepository practitionerRepository) {
        this.practitionerRepository = practitionerRepository;
    }

    public List<PractitionerDTO> getAllPractitioners() {
        return practitionerRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public PractitionerDTO getPractitionerById(Long id) {
        return practitionerRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    public PractitionerDTO getPractitionerByName(String name) {
        return practitionerRepository.findByName(name).map(this::convertToDTO).orElse(null);
    }

    public PractitionerDTO createPractitioner(PractitionerDTO practitionerDTO) {
        Practitioner practitioner = convertFromDTO(practitionerDTO);
        Practitioner savedPractitioner = practitionerRepository.save(practitioner);
        return convertToDTO(savedPractitioner);
    }

    public PractitionerDTO updatePractitioner(Long id, PractitionerDTO practitionerDetails) {
        Optional<Practitioner> practitioner = practitionerRepository.findById(id);
        if (practitioner.isPresent()) {
            Practitioner existingPractitioner = practitioner.get();
            existingPractitioner.setName(practitionerDetails.getName());
            existingPractitioner.setRole(practitionerDetails.getRole());
            Practitioner updatedPractitioner = practitionerRepository.save(existingPractitioner);
            return convertToDTO(updatedPractitioner);
        }
        return null;
    }

    public boolean deletePractitioner(Long id) {
        if (practitionerRepository.existsById(id)) {
            practitionerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Konverteringsmetoder
    private PractitionerDTO convertToDTO(Practitioner practitioner) {
        PractitionerDTO dto = new PractitionerDTO();
        dto.setId(practitioner.getId());
        dto.setName(practitioner.getName());
        dto.setRole(practitioner.getRole());
        dto.setEncounterIds(
                practitioner.getEncounters() != null ?
                        practitioner.getEncounters().stream().map(encounter -> encounter.getId()).collect(Collectors.toList()) :
                        null
        );
        return dto;
    }

    private Practitioner convertFromDTO(PractitionerDTO dto) {
        Practitioner practitioner = new Practitioner();
        practitioner.setName(dto.getName());
        practitioner.setRole(dto.getRole());
        return practitioner;
    }
}
