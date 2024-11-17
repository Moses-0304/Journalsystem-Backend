package se.kth.journalsystem.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.kth.journalsystem.DTO.PatientDTO;
import se.kth.journalsystem.Service.PatientService;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // Get all patients
    @GetMapping
    public List<PatientDTO> getAllPatients() {
        return patientService.getAllPatients();
    }

    // Get a specific patient by ID
    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id) {
        PatientDTO patientDTO = patientService.getPatientById(id);
        return patientDTO != null ? ResponseEntity.ok(patientDTO) : ResponseEntity.notFound().build();
    }

    // Get a patient by name
    @GetMapping("/name/{name}")
    public ResponseEntity<PatientDTO> getPatientByName(@PathVariable String name) {
        PatientDTO patientDTO = patientService.getPatientByName(name);
        return patientDTO != null ? ResponseEntity.ok(patientDTO) : ResponseEntity.notFound().build();
    }

    // Create a new patient
    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientDTO patientDTO) {
        PatientDTO createdPatient = patientService.createPatient(patientDTO);
        return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
    }

    // Update an existing patient
    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable Long id, @RequestBody PatientDTO patientDetails) {
        PatientDTO updatedPatient = patientService.updatePatient(id, patientDetails);
        return updatedPatient != null ? ResponseEntity.ok(updatedPatient) : ResponseEntity.notFound().build();
    }

    // Delete a patient
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        boolean deleted = patientService.deletePatient(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
