package se.kth.journalsystem.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.kth.journalsystem.DTO.LocationDTO;
import se.kth.journalsystem.Service.LocationService;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    // Get all locations
    @GetMapping
    public List<LocationDTO> getAllLocations() {
        return locationService.getAllLocations();
    }

    // Get a specific location by ID
    @GetMapping("/{id}")
    public ResponseEntity<LocationDTO> getLocationById(@PathVariable Long id) {
        LocationDTO locationDTO = locationService.getLocationById(id);
        return locationDTO != null ? ResponseEntity.ok(locationDTO) : ResponseEntity.notFound().build();
    }

    // Get locations by organization ID
    @GetMapping("/organization/{organizationId}")
    public List<LocationDTO> getLocationsByOrganizationId(@PathVariable Long organizationId) {
        return locationService.getLocationsByOrganizationId(organizationId);
    }

    // Create a new location
    @PostMapping
    public ResponseEntity<LocationDTO> createLocation(@RequestBody LocationDTO locationDTO) {
        LocationDTO createdLocation = locationService.createLocation(locationDTO);
        return new ResponseEntity<>(createdLocation, HttpStatus.CREATED);
    }

    // Update an existing location
    @PutMapping("/{id}")
    public ResponseEntity<LocationDTO> updateLocation(@PathVariable Long id, @RequestBody LocationDTO locationDetails) {
        LocationDTO updatedLocation = locationService.updateLocation(id, locationDetails);
        return updatedLocation != null ? ResponseEntity.ok(updatedLocation) : ResponseEntity.notFound().build();
    }

    // Delete a location
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        boolean deleted = locationService.deleteLocation(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
