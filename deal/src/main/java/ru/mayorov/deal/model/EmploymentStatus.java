package ru.mayorov.deal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import ru.mayorov.deal.units.EmploymentStatusEnum;

@Entity
public class EmploymentStatus {
    @Enumerated(EnumType.STRING)
    private EmploymentStatusEnum employmentStatusEnum;

    public EmploymentStatus(EmploymentStatusEnum employmentStatusEnum) {
        this.employmentStatusEnum = employmentStatusEnum;
    }

    public EmploymentStatusEnum getEmploymentStatusEnum() {
        return employmentStatusEnum;
    }

    public void setEmploymentStatusEnum(EmploymentStatusEnum employmentStatusEnum) {
        this.employmentStatusEnum = employmentStatusEnum;
    }
}

