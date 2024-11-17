package se.kth.journalsystem.DTO;

import java.time.LocalDateTime;

public class EncounterDTO {
    private Long id;
    private LocalDateTime encounterDate;
    private Long patientId;
    private Long practitionerId;

    // Getters och setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getEncounterDate() {
        return encounterDate;
    }

    public void setEncounterDate(LocalDateTime encounterDate) {
        this.encounterDate = encounterDate;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getPractitionerId() {
        return practitionerId;
    }

    public void setPractitionerId(Long practitionerId) {
        this.practitionerId = practitionerId;
    }
}
