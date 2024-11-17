package se.kth.journalsystem.DTO;

import java.util.List;

public class PractitionerDTO {
    private Long id;
    private String name;
    private String role;
    private List<Long> encounterIds;

    // Getters och setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Long> getEncounterIds() {
        return encounterIds;
    }

    public void setEncounterIds(List<Long> encounterIds) {
        this.encounterIds = encounterIds;
    }
}
