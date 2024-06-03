package ru.mayorov.calculator.units;

public enum PositionEnum {
    UNEMPLOYED(null),
    SELFEMPLOYED(-1),
    BUSINESSOWNER(-2);

    private Integer discount;
    PositionEnum(Integer discount) {
        this.discount = discount;
    }

    public Integer getDiscount(){
        return discount;
    }
}
