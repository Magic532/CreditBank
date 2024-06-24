package ru.mayorov.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO (Data Transfer Object) for credit information.
 */
@Data
@Schema(description = "Сущность параметры одобренного кредита")
public class CreditDto {

    @Schema(description = "Одобренная сумма")
    private BigDecimal amount;

    @Schema(description = "Срок кредита")
    private Integer term;

    @Schema(description = "Ежемесячный платеж")
    private BigDecimal monthlyPayment;

    @Schema(description = "Ставка")
    private BigDecimal rate;

    @Schema(description = "Полная стоимость кредита")
    private BigDecimal psk;

    @Schema(description = "Подключено ли страхование")
    private Boolean isInsuranceEnabled;

    @Schema(description = "Является ли зарплатным клиентом")
    private Boolean isSalaryClient;

    @Schema(description = "График платежей")
    private List<PaymentScheduleElementDto> paymentSchedule;

    public CreditDto(){}

    public CreditDto(BigDecimal amount,
                     Integer term,
                     BigDecimal monthlyPayment,
                     BigDecimal rate,
                     BigDecimal psk,
                     Boolean isInsuranceEnabled,
                     Boolean isSalaryClient,
                     List<PaymentScheduleElementDto> paymentSchedule) {
        this.amount = amount;
        this.term = term;
        this.monthlyPayment = monthlyPayment;
        this.rate = rate;
        this.psk = psk;
        this.isInsuranceEnabled = isInsuranceEnabled;
        this.isSalaryClient = isSalaryClient;
        this.paymentSchedule = paymentSchedule;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public BigDecimal getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(BigDecimal monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getPsk() {
        return psk;
    }

    public void setPsk(BigDecimal psk) {
        this.psk = psk;
    }

    public Boolean getIsInsuranceEnabled() {
        return isInsuranceEnabled;
    }

    public void setIsInsuranceEnabled(Boolean insuranceEnabled) {
        isInsuranceEnabled = insuranceEnabled;
    }

    public Boolean getIsSalaryClient() {
        return isSalaryClient;
    }

    public void setIsSalaryClient(Boolean salaryClient) {
        isSalaryClient = salaryClient;
    }

    public List<PaymentScheduleElementDto> getPaymentSchedule() {
        return paymentSchedule;
    }

    public void setPaymentSchedule(List<PaymentScheduleElementDto> paymentSchedule) {
        this.paymentSchedule = paymentSchedule;
    }
}