package se.kth.journalsystem.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.kth.journalsystem.DTO.OrganizationDTO;
import se.kth.journalsystem.Service.OrganizationService;

import java.util.List;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    // Get all organizations
    @GetMapping
    public List<OrganizationDTO> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

    // Get a specific organization by ID
    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDTO> getOrganizationById(@PathVariable Long id) {
        OrganizationDTO organizationDTO = organizationService.getOrganizationById(id);
        return organizationDTO != null ? ResponseEntity.ok(organizationDTO) : ResponseEntity.notFound().build();
    }

    // Get organizations by name
    @GetMapping("/name/{name}")
    public List<OrganizationDTO> getOrganizationsByName(@PathVariable String name) {
        return organizationService.getOrganizationsByName(name);
    }

    // Create a new organization
    @PostMapping
    public ResponseEntity<OrganizationDTO> createOrganization(@RequestBody OrganizationDTO organizationDTO) {
        OrganizationDTO createdOrganization = organizationService.createOrganization(organizationDTO);
        return new ResponseEntity<>(createdOrganization, HttpStatus.CREATED);
    }

    // Update an existing organization
    @PutMapping("/{id}")
    public ResponseEntity<OrganizationDTO> updateOrganization(@PathVariable Long id, @RequestBody OrganizationDTO organizationDetails) {
        OrganizationDTO updatedOrganization = organizationService.updateOrganization(id, organizationDetails);
        return updatedOrganization != null ? ResponseEntity.ok(updatedOrganization) : ResponseEntity.notFound().build();
    }

    // Delete an organization
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
        boolean deleted = organizationService.deleteOrganization(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
