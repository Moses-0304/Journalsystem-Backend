package se.kth.journalsystem.DTO;

import java.util.List;

public class PatientDTO {
    private Long id;
    private String name;
    private String birthDate;
    private String contactInfo;
    private List<Long> observationIds;
    private List<Long> encounterIds;
    private List<Long> conditionIds;

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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<Long> getObservationIds() {
        return observationIds;
    }

    public void setObservationIds(List<Long> observationIds) {
        this.observationIds = observationIds;
    }

    public List<Long> getEncounterIds() {
        return encounterIds;
    }

    public void setEncounterIds(List<Long> encounterIds) {
        this.encounterIds = encounterIds;
    }

    public List<Long> getConditionIds() {
        return conditionIds;
    }

    public void setConditionIds(List<Long> conditionIds) {
        this.conditionIds = conditionIds;
    }
}
