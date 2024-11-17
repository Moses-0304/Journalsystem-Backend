package se.kth.journalsystem.DTO;

import java.time.LocalDateTime;

public class ObservationDTO {
    private Long id;
    private String description;
    private LocalDateTime observationDate;
    private Long patientId;

    // Getters och setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getObservationDate() {
        return observationDate;
    }

    public void setObservationDate(LocalDateTime observationDate) {
        this.observationDate = observationDate;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
}
