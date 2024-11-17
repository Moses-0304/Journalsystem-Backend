package se.kth.journalsystem.DTO;

public class UserDTO {
    private Long id;
    private String username;
    private String password; // Nytt fält för lösenord
    private String role; // Roll (PATIENT, DOCTOR, STAFF)
    private Long patientId; // Patient ID, om rollen är PATIENT
    private Long practitionerId; // Practitioner ID, om rollen är PRACTITIONER/STAFF

    // Patient-specifika fält
    private String patientName; // Namn på patient
    private String patientBirthDate; // Födelsedatum för patienten
    private String patientContactInfo; // Kontaktinformation för patienten

    // Getters och Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientBirthDate() {
        return patientBirthDate;
    }

    public void setPatientBirthDate(String patientBirthDate) {
        this.patientBirthDate = patientBirthDate;
    }

    public String getPatientContactInfo() {
        return patientContactInfo;
    }

    public void setPatientContactInfo(String patientContactInfo) {
        this.patientContactInfo = patientContactInfo;
    }
}

