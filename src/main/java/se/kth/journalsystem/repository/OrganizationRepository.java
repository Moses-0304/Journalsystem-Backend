package se.kth.journalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.kth.journalsystem.model.Organization;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    // Hitta organisationer baserat på namn
    List<Organization> findByName(String name);
}
