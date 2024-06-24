package ru.mayorov.calculator.units;

/**
 *The enum employee employment statuses.
 */
public enum EmploymentStatusEnum {
    /**
     * Refusal for the unemployed
     */
    UNEMLOYED(null),

    /**
     * Allowance for selfemployed, value 1.
     */

    SELF_EMPLOYED(-0.01),

    /**
     * Allowance for business owner, value 2.
     */
    BUSINESS_OWNER(-0.02),

    /**
     * No discount for employed, value 0.
     */
    EMPLOYED(0.00);

    private Double discount;

    /**
     * Constructor for initializing a discount.
     *
     * @param discount Employment status discount.
     */
    EmploymentStatusEnum(Double discount){
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
