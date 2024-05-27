package ru.mayorov.deal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import ru.mayorov.deal.units.PositionEnum;

@Entity
public class EmploymentPosition {
    @Enumerated(EnumType.STRING)
    private PositionEnum positionEnum;

    public EmploymentPosition(PositionEnum positionEnum) {
        this.positionEnum = positionEnum;
    }

    public PositionEnum getPositionEnum() {
        return positionEnum;
    }

    public void setPositionEnum(PositionEnum positionEnum) {
        this.positionEnum = positionEnum;
    }
}


