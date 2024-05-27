package ru.mayorov.deal.units;

/**
 * The enum gender.
 */
public enum GenderEnum {
    /**
     * Discount for Male gender, value 3.
     */
    MALE(0.03),

    /**
     * Discount for Female gender, value 3.
     */
    FEMALE(0.03),

    /**
     * Allowance for Non-binary gender, value (7).
     */
    NONBINARY(-0.07);

    private Double discount;

    /**
     * Constructor for initializing a discount.
     *
     * @param discount Gender discount.
     */
    GenderEnum(Double discount) {
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
