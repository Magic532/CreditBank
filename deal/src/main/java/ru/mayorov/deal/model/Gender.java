package ru.mayorov.deal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import ru.mayorov.deal.units.GenderEnum;

@Entity
public class Gender {
    @Enumerated(EnumType.STRING)
    private GenderEnum genderEnum;

    public Gender(GenderEnum genderEnum) {
        this.genderEnum = genderEnum;
    }

    public GenderEnum getGenderEnum() {
        return genderEnum;
    }

    public void setGenderEnum(GenderEnum genderEnum) {
        this.genderEnum = genderEnum;
    }
}
