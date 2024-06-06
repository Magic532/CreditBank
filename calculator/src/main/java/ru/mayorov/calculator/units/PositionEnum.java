package ru.mayorov.calculator.units;

/**
 * The enum Position.
 */
public enum PositionEnum {
    /**
     * Unemployed position not credited. Refusal, value "null"
     */
    UNEMPLOYED(null),
    /**
     * Allowance for Selfemployed position, value 1.
     */
    SELFEMPLOYED(-0.01),
    /**
     * Allowance for Businessowner position, value 2.
     */
    BUSINESS_OWNER(-0.02),
    /**
     * No discount for Businessowner position, value 0.
     */
    OTHER(0.0);

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
