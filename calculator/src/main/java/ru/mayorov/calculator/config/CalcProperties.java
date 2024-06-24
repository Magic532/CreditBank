package ru.mayorov.calculator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.context.annotation.Configuration;



import java.math.BigDecimal;


@Configuration
@ConfigurationProperties(prefix = "calculateproperties")
public class CalcProperties {
    private final BigDecimal discountSalaryClient;
    private final BigDecimal discountInsuranceEnabled;
    private final BigDecimal minimumLivingWage;
    private final BigDecimal insurancePercentPerMonthMax100;
    private final BigDecimal insurancePercentPerMonthMax500;
    private final BigDecimal insurancePercentPerMonthMax2000;
    private final BigDecimal insurancePercentPerMonthMax5000;
    private final BigDecimal rateMax500;
    private final BigDecimal rateMax2000;
    private final BigDecimal rateMax5000;
    private final BigDecimal maxAmountToSalaryRatio;

    public CalcProperties(@Value("${calcproperties.discountSalaryClient}") BigDecimal discountSalaryClient,
                          @Value("${calcproperties.discountInsuranceEnabled}") BigDecimal discountInsuranceEnabled,
                          @Value("${calcproperties.minimumLivingWage}") BigDecimal minimumLivingWage,
                          @Value("${calcproperties.insurancePercentPerMonthMax100}") BigDecimal insurancePercentPerMonthMax100,
                          @Value("${calcproperties.insurancePercentPerMonthMax500}") BigDecimal insurancePercentPerMonthMax500,
                          @Value("${calcproperties.insurancePercentPerMonthMax2000}") BigDecimal insurancePercentPerMonthMax2000,
                          @Value("${calcproperties.insurancePercentPerMonthMax5000}") BigDecimal insurancePercentPerMonthMax5000,
                          @Value("${calcproperties.rateMax500}") BigDecimal rateMax500,
                          @Value("${calcproperties.rateMax2000}") BigDecimal rateMax2000,
                          @Value("${calcproperties.rateMax5000}") BigDecimal rateMax5000,
                          @Value("${calcproperties.maxAmountToSalaryRatio}") BigDecimal maxAmountToSalaryRatio) {
        this.discountSalaryClient = discountSalaryClient;
        this.discountInsuranceEnabled = discountInsuranceEnabled;
        this.minimumLivingWage = minimumLivingWage;
        this.insurancePercentPerMonthMax100 = insurancePercentPerMonthMax100;
        this.insurancePercentPerMonthMax500 = insurancePercentPerMonthMax500;
        this.insurancePercentPerMonthMax2000 = insurancePercentPerMonthMax2000;
        this.insurancePercentPerMonthMax5000 = insurancePercentPerMonthMax5000;
        this.rateMax500 = rateMax500;
        this.rateMax2000 = rateMax2000;
        this.rateMax5000 = rateMax5000;
        this.maxAmountToSalaryRatio = maxAmountToSalaryRatio;
    }

    public BigDecimal getDiscountSalaryClient() {
        return discountSalaryClient;
    }

    public BigDecimal getDiscountInsuranceEnabled() {
        return discountInsuranceEnabled;
    }

    public BigDecimal getMinimumLivingWage() {
        return minimumLivingWage;
    }

    public BigDecimal getInsurancePercentPerMonthMax100() {
        return insurancePercentPerMonthMax100;
    }

    public BigDecimal getInsurancePercentPerMonthMax500() {
        return insurancePercentPerMonthMax500;
    }

    public BigDecimal getInsurancePercentPerMonthMax2000() {
        return insurancePercentPerMonthMax2000;
    }

    public BigDecimal getInsurancePercentPerMonthMax5000() {
        return insurancePercentPerMonthMax5000;
    }

    public BigDecimal getRateMax500() {
        return rateMax500;
    }

    public BigDecimal getRateMax2000() {
        return rateMax2000;
    }

    public BigDecimal getRateMax5000() {
        return rateMax5000;
    }

    public BigDecimal getMaxAmountToSalaryRatio() {
        return maxAmountToSalaryRatio;
    }
}
