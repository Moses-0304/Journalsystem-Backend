package se.kth.journalsystem.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.kth.journalsystem.DTO.ConditionDTO;
import se.kth.journalsystem.Service.ConditionService;

import java.util.List;

@RestController
@RequestMapping("/api/conditions")
public class ConditionController {

    private final ConditionService conditionService;

    @Autowired
    public ConditionController(ConditionService conditionService) {
        this.conditionService = conditionService;
    }

    // Get all conditions
    @GetMapping
    public List<ConditionDTO> getAllConditions() {
        return conditionService.getAllConditions();
    }

    // Get a specific condition by ID
    @GetMapping("/{id}")
    public ResponseEntity<ConditionDTO> getConditionById(@PathVariable Long id) {
        ConditionDTO conditionDTO = conditionService.getConditionById(id);
        return conditionDTO != null ? ResponseEntity.ok(conditionDTO) : ResponseEntity.notFound().build();
    }

    // Get all conditions for a specific patient by patient ID
    @GetMapping("/patient/{patientId}")
    public List<ConditionDTO> getConditionsByPatientId(@PathVariable Long patientId) {
        return conditionService.getConditionsByPatientId(patientId);
    }

    // Create a new condition
    @PostMapping
    public ResponseEntity<ConditionDTO> createCondition(@RequestBody ConditionDTO conditionDTO) {
        ConditionDTO createdCondition = conditionService.createCondition(conditionDTO);
        return new ResponseEntity<>(createdCondition, HttpStatus.CREATED);
    }

    // Update an existing condition
    @PutMapping("/{id}")
    public ResponseEntity<ConditionDTO> updateCondition(@PathVariable Long id, @RequestBody ConditionDTO conditionDetails) {
        ConditionDTO updatedCondition = conditionService.updateCondition(id, conditionDetails);
        return updatedCondition != null ? ResponseEntity.ok(updatedCondition) : ResponseEntity.notFound().build();
    }

    // Delete a condition
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCondition(@PathVariable Long id) {
        boolean deleted = conditionService.deleteCondition(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
