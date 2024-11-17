package se.kth.journalsystem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.journalsystem.DTO.LocationDTO;
import se.kth.journalsystem.model.Location;
import se.kth.journalsystem.repository.LocationRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<LocationDTO> getAllLocations() {
        return locationRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public LocationDTO getLocationById(Long id) {
        return locationRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    public List<LocationDTO> getLocationsByOrganizationId(Long organizationId) {
        return locationRepository.findByOrganizationId(organizationId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public LocationDTO createLocation(LocationDTO locationDTO) {
        Location location = convertFromDTO(locationDTO);
        Location savedLocation = locationRepository.save(location);
        return convertToDTO(savedLocation);
    }

    public LocationDTO updateLocation(Long id, LocationDTO locationDetails) {
        Optional<Location> location = locationRepository.findById(id);
        if (location.isPresent()) {
            Location existingLocation = location.get();
            existingLocation.setAddress(locationDetails.getAddress());
            existingLocation.setDepartment(locationDetails.getDepartment());
            // Organization-ID bör inte ändras här
            Location updatedLocation = locationRepository.save(existingLocation);
            return convertToDTO(updatedLocation);
        }
        return null;
    }

    public boolean deleteLocation(Long id) {
        if (locationRepository.existsById(id)) {
            locationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Konverteringsmetoder
    private LocationDTO convertToDTO(Location location) {
        LocationDTO dto = new LocationDTO();
        dto.setId(location.getId());
        dto.setAddress(location.getAddress());
        dto.setDepartment(location.getDepartment());
        dto.setOrganizationId(location.getOrganization() != null ? location.getOrganization().getId() : null);
        return dto;
    }

    private Location convertFromDTO(LocationDTO dto) {
        Location location = new Location();
        location.setAddress(dto.getAddress());
        location.setDepartment(dto.getDepartment());
        // Organization måste sättas med ett externt service- eller repository-anrop om organisationen är en entitet
        // location.setOrganization(organizationService.findById(dto.getOrganizationId()).orElseThrow(...));
        return location;
    }
}
