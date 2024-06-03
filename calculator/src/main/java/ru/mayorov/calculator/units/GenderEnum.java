package ru.mayorov.calculator.units;

public enum GenderEnum {
    MALE(3),
    FEMALE(3),
    NONBINARY(-7);

    private Integer discount;

    GenderEnum(Integer discount) {
        this.discount = discount;
    }

    public Integer getDiscount(){
        return discount;
    }
}
