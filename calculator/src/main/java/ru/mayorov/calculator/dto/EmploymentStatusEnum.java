package ru.mayorov.calculator.dto;

import java.math.BigDecimal;

public enum EmploymentStatusEnum {
    MALE(new BigDecimal("3")),
    FEMALE(new BigDecimal("3")),
    NONBINARY(new BigDecimal("-7"));

    private BigDecimal discount;

    EmploymentStatusEnum(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getDiscount(){
        return discount;
    }
}
