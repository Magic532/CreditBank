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

        BigDecimal amount = calculateService.calculateAmountScoring(scoringDataDto.getAmount(), scoringDataDto.getEmployment().getSalary(), scoringDataDto.getBirthdate());

        BigDecimal rate = calculateService.calculateRateScoring(scoringDataDto.getEmployment().getEmploymentStatus(),
                                                                        scoringDataDto.getEmployment().getPosition(),
                                                                        scoringDataDto.getMaritalStatus(),
                                                                        scoringDataDto.getGender(),
                                                                        scoringDataDto.getBirthdate());
        Integer term = calculateService.calculateTermScoring(amount, rate, scoringDataDto.getTerm(), scoringDataDto.getEmployment().getSalary());

        BigDecimal monthlyPayment = calculateService.calculateMonthlyPayment(amount, term, rate);

        BigDecimal psk = calculateService.calculatePsk(monthlyPayment, amount, term);

        return CreditDto.builder()
                .amount(amount)
                .term(term)
                .monthlyPayment(monthlyPayment)
                .rate(rate)
                .psk(psk)
                .isInsuranceEnabled(scoringDataDto.getIsInsuranceEnabled())
                .isSalaryClient(scoringDataDto.getIsSalaryClient())
//                .paymentSchedule(calculateService.calculatePaymentSchedule())
                .build();
    }


}
