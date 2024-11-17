package se.kth.journalsystem.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Practitioner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String role; // Exempel: "Läkare", "Sjuksköterska"

    @OneToMany(mappedBy = "practitioner")
    private List<Encounter> encounters;

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

    public List<Encounter> getEncounters() {
        return encounters;
    }

    public void setEncounters(List<Encounter> encounters) {
        this.encounters = encounters;
    }
}
