package ru.mayorov.deal.model;


import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
public class Employment {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "employment_uuid", updatable = false, nullable = false)
    private UUID employmentUUID;

    @OneToOne(mappedBy = "employment", optional = false)
    private Client client;

    @Column(name = "status", columnDefinition = "varchar")
    private EmploymentStatus status;

    @Column(name = "employer_inn", columnDefinition = "varchar")
    private String employerINN;

    @Column(name = "position", columnDefinition = "varchar")
    private EmploymentPosition position;

    @Column(name = "work_experience_total", columnDefinition = "int")
    private  Integer workExperienceTotal;

    @Column(name = "work_experience_current", columnDefinition = "int")
    private  Integer workExperienceCurrent;

    public Employment(EmploymentStatus status, String employerINN, EmploymentPosition position, Integer workExperienceTotal, Integer workExperienceCurrent) {
        this.status = status;
        this.employerINN = employerINN;
        this.position = position;
        this.workExperienceTotal = workExperienceTotal;
        this.workExperienceCurrent = workExperienceCurrent;
    }

    public UUID getEmploymentUUID() {
        return employmentUUID;
    }

    public void setEmploymentUUID(UUID employmentUUID) {
        this.employmentUUID = employmentUUID;
    }

    public EmploymentStatus getStatus() {
        return status;
    }

    public void setStatus(EmploymentStatus status) {
        this.status = status;
    }

    public String getEmployerINN() {
        return employerINN;
    }

    public void setEmployerINN(String employerINN) {
        this.employerINN = employerINN;
    }

    public EmploymentPosition getPosition() {
        return position;
    }

    public void setPosition(EmploymentPosition position) {
        this.position = position;
    }

    public Integer getWorkExperienceTotal() {
        return workExperienceTotal;
    }

    public void setWorkExperienceTotal(Integer workExperienceTotal) {
        this.workExperienceTotal = workExperienceTotal;
    }

    public Integer getWorkExperienceCurrent() {
        return workExperienceCurrent;
    }

    public void setWorkExperienceCurrent(Integer workExperienceCurrent) {
        this.workExperienceCurrent = workExperienceCurrent;
    }
}
