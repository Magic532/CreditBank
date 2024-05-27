package ru.mayorov.deal.units;

/**
 * The enum Position.
 */
public enum PositionEnum {
    /**
     * No discount for owner position, value 0.
     */
    OWNER(0.00),

    /**
     * Discount for middle manager position, value 2.
     */
    MID_MANAGER(0.02),

    /**
     * Discount for TOP manager position, value 3.
     */
    TOP_MANAGER(0.03),

    /**
     * No discount for worker position, value 0.
     */
    WORKER(0.0);

    /**
     * Constructor for initializing a discount.
     *
     * @param discount Position discount.
     */
    private Double discount;
    PositionEnum(Double discount) {
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
