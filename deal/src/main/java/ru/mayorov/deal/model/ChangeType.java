package ru.mayorov.deal.model;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import ru.mayorov.deal.units.ChangeTypeEnum;

@Entity
public class ChangeType {
    @Enumerated(EnumType.STRING)
    private ChangeTypeEnum changeType;

    public ChangeTypeEnum getChangeTypeEnum() {
        return changeType;
    }

    public void setChangeTypeEnum(ChangeTypeEnum changeType) {
        this.changeType = changeType;
    }
}
