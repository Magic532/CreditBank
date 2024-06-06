package ru.mayorov.calculator.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) for loan information
 */
@Data
@Builder
public class PaymentScheduleElementDto {
    private Integer number;
    private LocalDate date;
    private BigDecimal totalPayment;
    private BigDecimal interestPayment;
    private BigDecimal debtPayment;
    private BigDecimal remainingDebt;

    public PaymentScheduleElementDto() {}

    public PaymentScheduleElementDto(Integer number, LocalDate date, BigDecimal totalPayment, BigDecimal interestPayment, BigDecimal debtPayment, BigDecimal remainingDebt) {
        this.number = number;
        this.date = date;
        this.totalPayment = totalPayment;
        this.interestPayment = interestPayment;
        this.debtPayment = debtPayment;
        this.remainingDebt = remainingDebt;
    }
}
