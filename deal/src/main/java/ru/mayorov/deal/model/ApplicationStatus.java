package ru.mayorov.deal.model;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import ru.mayorov.deal.units.ApplicationStatusEnum;

@Entity
public class ApplicationStatus {
    @Enumerated(EnumType.STRING)
    private ApplicationStatusEnum applicationStatus;

    public ApplicationStatusEnum getApplicationStatusEnum() {
        return applicationStatus;
    }

    public void setApplicationStatusEnum(ApplicationStatusEnum applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
}
