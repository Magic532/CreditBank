package ru.mayorov.calculator.units;

public enum EmploymentStatusEnum {
    MIDDLEMANAGER(2),
    TOPMANAGER(3);

    private Integer discount;

    EmploymentStatusEnum(Integer discount){
        this.discount = discount;
    }
    public Integer getDiscount(){
        return discount;
    }
}
