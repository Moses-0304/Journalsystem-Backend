package se.kth.journalsystem.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.kth.journalsystem.DTO.ObservationDTO;
import se.kth.journalsystem.Service.ObservationService;

import java.util.List;

@RestController
@RequestMapping("/api/observations")
public class ObservationController {

    private final ObservationService observationService;

    @Autowired
    public ObservationController(ObservationService observationService) {
        this.observationService = observationService;
    }

    // Get all observations
    @GetMapping
    public List<ObservationDTO> getAllObservations() {
        return observationService.getAllObservations();
    }

    // Get a specific observation by ID
    @GetMapping("/{id}")
    public ResponseEntity<ObservationDTO> getObservationById(@PathVariable Long id) {
        ObservationDTO observationDTO = observationService.getObservationById(id);
        return observationDTO != null ? ResponseEntity.ok(observationDTO) : ResponseEntity.notFound().build();
    }

    // Get all observations by patient ID
    @GetMapping("/patient/{patientId}")
    public List<ObservationDTO> getObservationsByPatientId(@PathVariable Long patientId) {
        return observationService.getObservationsByPatientId(patientId);
    }

    // Create a new observation
    @PostMapping
    public ResponseEntity<ObservationDTO> createObservation(@RequestBody ObservationDTO observationDTO) {
        ObservationDTO createdObservation = observationService.createObservation(observationDTO);
        return new ResponseEntity<>(createdObservation, HttpStatus.CREATED);
    }

    // Update an existing observation
    @PutMapping("/{id}")
    public ResponseEntity<ObservationDTO> updateObservation(@PathVariable Long id, @RequestBody ObservationDTO observationDetails) {
        ObservationDTO updatedObservation = observationService.updateObservation(id, observationDetails);
        return updatedObservation != null ? ResponseEntity.ok(updatedObservation) : ResponseEntity.notFound().build();
    }

    // Delete an observation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteObservation(@PathVariable Long id) {
        boolean deleted = observationService.deleteObservation(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
