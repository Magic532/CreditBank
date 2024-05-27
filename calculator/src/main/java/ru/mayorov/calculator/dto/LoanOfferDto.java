package ru.mayorov.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO (Data Transfer Object) for offer information.
 */
@Data
@Schema(description = "Доступное предложение для клиента")
public class LoanOfferDto {
    @Schema(description = "Уникальный идентификатор заявки")
    private UUID statementId;

    @Schema(description = "Запрашиваемая сумма кредита")
    private BigDecimal requestedAmount;

    @Schema(description = "Общая сумма кредита")
    private BigDecimal totalAmount;

    @Schema(description = "Срок кредита")
    private Integer term;

    @Schema(description = "Ежемесячный платеж")
    private BigDecimal monthlyPayment;

    @Schema(description = "Ставка")
    private BigDecimal rate;

    @Schema(description = "Подключено ли страхование")
    private Boolean isInsuranceEnabled;

    @Schema(description = "Является ли зарплатным клиентом")
    private Boolean isSalaryClient;

    public LoanOfferDto() {}

    public LoanOfferDto(BigDecimal requestedAmount,
                        BigDecimal totalAmount,
                        Integer term,
                        BigDecimal monthlyPayment,
                        BigDecimal rate,
                        Boolean isInsuranceEnabled,
                        Boolean isSalaryClient) {
        this.requestedAmount = requestedAmount;
        this.totalAmount = totalAmount;
        this.term = term;
        this.monthlyPayment = monthlyPayment;
        this.rate = rate;
        this.isInsuranceEnabled = isInsuranceEnabled;
        this.isSalaryClient = isSalaryClient;
    }

    public UUID getStatementId() {
        return statementId;
    }

    public void setStatementId(UUID statementId) {
        this.statementId = statementId;
    }

    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(BigDecimal requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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
}