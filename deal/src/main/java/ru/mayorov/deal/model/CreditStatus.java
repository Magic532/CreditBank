package ru.mayorov.deal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import ru.mayorov.deal.units.CreditStatusEnum;

@Entity
public class CreditStatus {
    @Enumerated(EnumType.STRING)
    private CreditStatusEnum creditStatusEnum;

    public CreditStatus(CreditStatusEnum creditStatusEnum) {
        this.creditStatusEnum = creditStatusEnum;
    }

    public CreditStatusEnum getCreditStatusEnum() {
        return creditStatusEnum;
    }

    public void setCreditStatusEnum(CreditStatusEnum creditStatusEnum) {
        this.creditStatusEnum = creditStatusEnum;
    }
}

