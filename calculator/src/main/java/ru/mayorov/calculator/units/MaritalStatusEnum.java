package ru.mayorov.calculator.units;

public enum MaritalStatusEnum {
    MARRIED(3),
    DIVORCED(-1),
    SINGLE(0);

    private Integer discount;
   MaritalStatusEnum(Integer discount){
       this.discount = discount;
    }

    public Integer getDiscount(){
       return discount;
    }
}
