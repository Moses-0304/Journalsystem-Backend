package se.kth.journalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.kth.journalsystem.model.Location;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByOrganizationId(Long organizationId);
}
