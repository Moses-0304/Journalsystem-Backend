package se.kth.journalsystem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.journalsystem.DTO.OrganizationDTO;
import se.kth.journalsystem.model.Organization;
import se.kth.journalsystem.repository.OrganizationRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public List<OrganizationDTO> getAllOrganizations() {
        return organizationRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public OrganizationDTO getOrganizationById(Long id) {
        return organizationRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    public List<OrganizationDTO> getOrganizationsByName(String name) {
        return organizationRepository.findByName(name).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public OrganizationDTO createOrganization(OrganizationDTO organizationDTO) {
        Organization organization = convertFromDTO(organizationDTO);
        Organization savedOrganization = organizationRepository.save(organization);
        return convertToDTO(savedOrganization);
    }

    public OrganizationDTO updateOrganization(Long id, OrganizationDTO organizationDetails) {
        Optional<Organization> organization = organizationRepository.findById(id);
        if (organization.isPresent()) {
            Organization existingOrganization = organization.get();
            existingOrganization.setName(organizationDetails.getName());
            existingOrganization.setContactInfo(organizationDetails.getContactInfo());
            Organization updatedOrganization = organizationRepository.save(existingOrganization);
            return convertToDTO(updatedOrganization);
        }
        return null;
    }

    public boolean deleteOrganization(Long id) {
        if (organizationRepository.existsById(id)) {
            organizationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Konverteringsmetoder
    private OrganizationDTO convertToDTO(Organization organization) {
        OrganizationDTO dto = new OrganizationDTO();
        dto.setId(organization.getId());
        dto.setName(organization.getName());
        dto.setContactInfo(organization.getContactInfo());
        dto.setLocationIds(
                organization.getLocations() != null ?
                        organization.getLocations().stream().map(location -> location.getId()).collect(Collectors.toList()) :
                        null
        );
        return dto;
    }

    private Organization convertFromDTO(OrganizationDTO dto) {
        Organization organization = new Organization();
        organization.setName(dto.getName());
        organization.setContactInfo(dto.getContactInfo());
        return organization;
    }
}
