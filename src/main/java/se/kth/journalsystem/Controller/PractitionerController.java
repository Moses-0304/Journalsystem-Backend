package se.kth.journalsystem.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.kth.journalsystem.DTO.PractitionerDTO;
import se.kth.journalsystem.Service.PractitionerService;

import java.util.List;

@RestController
@RequestMapping("/api/practitioners")
public class PractitionerController {

    private final PractitionerService practitionerService;

    @Autowired
    public PractitionerController(PractitionerService practitionerService) {
        this.practitionerService = practitionerService;
    }

    // Get all practitioners
    @GetMapping
    public List<PractitionerDTO> getAllPractitioners() {
        return practitionerService.getAllPractitioners();
    }

    // Get a specific practitioner by ID
    @GetMapping("/{id}")
    public ResponseEntity<PractitionerDTO> getPractitionerById(@PathVariable Long id) {
        PractitionerDTO practitionerDTO = practitionerService.getPractitionerById(id);
        return practitionerDTO != null ? ResponseEntity.ok(practitionerDTO) : ResponseEntity.notFound().build();
    }

    // Get a practitioner by name
    @GetMapping("/name/{name}")
    public ResponseEntity<PractitionerDTO> getPractitionerByName(@PathVariable String name) {
        PractitionerDTO practitionerDTO = practitionerService.getPractitionerByName(name);
        return practitionerDTO != null ? ResponseEntity.ok(practitionerDTO) : ResponseEntity.notFound().build();
    }

    // Create a new practitioner
    @PostMapping
    public ResponseEntity<PractitionerDTO> createPractitioner(@RequestBody PractitionerDTO practitionerDTO) {
        PractitionerDTO createdPractitioner = practitionerService.createPractitioner(practitionerDTO);
        return new ResponseEntity<>(createdPractitioner, HttpStatus.CREATED);
    }

    // Update an existing practitioner
    @PutMapping("/{id}")
    public ResponseEntity<PractitionerDTO> updatePractitioner(@PathVariable Long id, @RequestBody PractitionerDTO practitionerDetails) {
        PractitionerDTO updatedPractitioner = practitionerService.updatePractitioner(id, practitionerDetails);
        return updatedPractitioner != null ? ResponseEntity.ok(updatedPractitioner) : ResponseEntity.notFound().build();
    }

    // Delete a practitioner
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePractitioner(@PathVariable Long id) {
        boolean deleted = practitionerService.deletePractitioner(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
