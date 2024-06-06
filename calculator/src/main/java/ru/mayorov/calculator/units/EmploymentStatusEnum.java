package ru.mayorov.calculator.units;

/**
 *The enum employee employment statuses.
 */
public enum EmploymentStatusEnum {
    /**
     * Discount for middle manager, value 2.
     */
    MIDDLEMANAGER(0.02),

    /**
     * Discount for TOP manager, value 3.
     */
    TOPMANAGER(0.03),

    /**
     * No discount for other, value 0.
     */
    OTHER(0.0);

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
