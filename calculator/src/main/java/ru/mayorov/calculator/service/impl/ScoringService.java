package ru.mayorov.calculator.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mayorov.calculator.dto.CreditDto;
import ru.mayorov.calculator.dto.ScoringDataDto;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ScoringService {

    private final CalculateService calculateService;

    public CreditDto scorring(ScoringDataDto scoringDataDto){

        BigDecimal totalAmount = calculateService.calculateAmountScoring(
                scoringDataDto.getAmount(),
                scoringDataDto.getEmployment().getSalary(),
                scoringDataDto.getTerm(),
                scoringDataDto.getBirthdate(),
                scoringDataDto.getIsInsuranceEnabled());

        BigDecimal rate = calculateService.calculateRateScoring(
                scoringDataDto.getEmployment().getEmploymentStatus(),
                scoringDataDto.getEmployment().getPosition(),
                scoringDataDto.getMaritalStatus(),
                scoringDataDto.getGender(),
                scoringDataDto.getBirthdate());

        Integer term = calculateService.calculateTermScoring(
                totalAmount,
                rate,
                scoringDataDto.getTerm(),
                scoringDataDto.getEmployment().getSalary());

        BigDecimal monthlyPayment = calculateService.calculateMonthlyPayment(totalAmount, term, rate);

        BigDecimal psk = calculateService.calculatePsk(monthlyPayment, scoringDataDto.getAmount(), term);

        return CreditDto.builder()
                .amount(totalAmount)
                .term(term)
                .monthlyPayment(monthlyPayment)
                .rate(rate)
                .psk(psk)
                .isInsuranceEnabled(scoringDataDto.getIsInsuranceEnabled())
                .isSalaryClient(scoringDataDto.getIsSalaryClient())
                .paymentSchedule(calculateService.calculatePaymentSchedule(totalAmount, rate, term, monthlyPayment))
                .build();
    }
}
