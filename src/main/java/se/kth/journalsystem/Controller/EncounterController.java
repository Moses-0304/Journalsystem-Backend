package se.kth.journalsystem.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.kth.journalsystem.DTO.EncounterDTO;
import se.kth.journalsystem.Service.EncounterService;

import java.util.List;

@RestController
@RequestMapping("/api/encounters")
public class EncounterController {

    private final EncounterService encounterService;

    @Autowired
    public EncounterController(EncounterService encounterService) {
        this.encounterService = encounterService;
    }

    // Get all encounters
    @GetMapping
    public List<EncounterDTO> getAllEncounters() {
        return encounterService.getAllEncounters();
    }

    // Get a specific encounter by ID
    @GetMapping("/{id}")
    public ResponseEntity<EncounterDTO> getEncounterById(@PathVariable Long id) {
        EncounterDTO encounterDTO = encounterService.getEncounterById(id);
        return encounterDTO != null ? ResponseEntity.ok(encounterDTO) : ResponseEntity.notFound().build();
    }

    // Get all encounters for a specific patient by patient ID
    @GetMapping("/patient/{patientId}")
    public List<EncounterDTO> getEncountersByPatientId(@PathVariable Long patientId) {
        return encounterService.getEncountersByPatientId(patientId);
    }

    // Get all encounters for a specific practitioner by practitioner ID
    @GetMapping("/practitioner/{practitionerId}")
    public List<EncounterDTO> getEncountersByPractitionerId(@PathVariable Long practitionerId) {
        return encounterService.getEncountersByPractitionerId(practitionerId);
    }

    // Create a new encounter
    @PostMapping
    public ResponseEntity<EncounterDTO> createEncounter(@RequestBody EncounterDTO encounterDTO) {
        EncounterDTO createdEncounter = encounterService.createEncounter(encounterDTO);
        return new ResponseEntity<>(createdEncounter, HttpStatus.CREATED);
    }

    // Update an existing encounter
    @PutMapping("/{id}")
    public ResponseEntity<EncounterDTO> updateEncounter(@PathVariable Long id, @RequestBody EncounterDTO encounterDetails) {
        EncounterDTO updatedEncounter = encounterService.updateEncounter(id, encounterDetails);
        return updatedEncounter != null ? ResponseEntity.ok(updatedEncounter) : ResponseEntity.notFound().build();
    }

    // Delete an encounter
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEncounter(@PathVariable Long id) {
        boolean deleted = encounterService.deleteEncounter(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
