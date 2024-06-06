package ru.mayorov.calculator.units;

/**
 * The enum marital status.
 */
public enum MaritalStatusEnum {
    /**
     * Discount for Married marital status, value 3.
     */
    MARRIED(0.03),
    /**
     * Allowance for Divorced marital status, value 1.
     */
    DIVORCED(-0.01),
    /**
     * Single marital status, no discount.
     */
    SINGLE(0.0);

    /**
     * Constructor for initializing a discount.
     *
     * @param discount Marital status discount.
     */
    private Double discount;
   MaritalStatusEnum(Double discount){
       this.discount = discount;
    }


    /**
     * Get discount value.
     *
     * @return discount.
     */
    public Double getDiscount(){
       return discount;
    }
}
