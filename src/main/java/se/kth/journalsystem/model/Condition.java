package se.kth.journalsystem.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "`condition`")
public class Condition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diagnosis;
    private LocalDate diagnosisDate;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // Getter och setter för id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter och setter för diagnosis
    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    // Getter och setter för diagnosisDate
    public LocalDate getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(LocalDate diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    // Getter och setter för patient
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
