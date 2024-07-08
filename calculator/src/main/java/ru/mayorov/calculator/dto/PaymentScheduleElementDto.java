package ru.mayorov.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) for loan information
 */
@Data
@Schema(description = "Сущность график платежей")
public class PaymentScheduleElementDto {

    @Schema(description = "Порядковый номер платежа")
    private Integer number;

    @Schema(description = "Дата платежа")
    private LocalDate date;

    @Schema(description = "Общая сумма платежа ")
    private BigDecimal totalPayment;

    @Schema(description = "Сумма уплаченных процентов")
    private BigDecimal interestPayment;

    @Schema(description = "Сумма уплаченного основного долга")
    private BigDecimal debtPayment;

    @Schema(description = "Оставшийся основной долг")
    private BigDecimal remainingDebt;

    public PaymentScheduleElementDto() {
    }

    public PaymentScheduleElementDto(Integer number,
                                     LocalDate date,
                                     BigDecimal totalPayment,
                                     BigDecimal interestPayment,
                                     BigDecimal debtPayment,
                                     BigDecimal remainingDebt) {
        this.number = number;
        this.date = date;
        this.totalPayment = totalPayment;
        this.interestPayment = interestPayment;
        this.debtPayment = debtPayment;
        this.remainingDebt = remainingDebt;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(BigDecimal totalPayment) {
        this.totalPayment = totalPayment;
    }

    public BigDecimal getInterestPayment() {
        return interestPayment;
    }

    public void setInterestPayment(BigDecimal interestPayment) {
        this.interestPayment = interestPayment;
    }

    public BigDecimal getDebtPayment() {
        return debtPayment;
    }

    public void setDebtPayment(BigDecimal debtPayment) {
        this.debtPayment = debtPayment;
    }

    public BigDecimal getRemainingDebt() {
        return remainingDebt;
    }

    public void setRemainingDebt(BigDecimal remainingDebt) {
        this.remainingDebt = remainingDebt;
    }
}

