package ru.mayorov.calculator.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.mayorov.calculator.dto.CreditDto;
import ru.mayorov.calculator.dto.ScoringDataDto;

import java.math.BigDecimal;

/**
 * The class of service responsible for performing scoring calculations to determine the cost of a loan.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ScoringService {

    private final CalculateService calculateService;

    /**
     * Scoring credit dto.
     *
     * @param scoringDataDto the scoring data dto
     * @return the credit dto
     */
    public CreditDto scorring(ScoringDataDto scoringDataDto){
        log.info("Начинается расчет стоимости кредита по данным: {}", scoringDataDto.toString());

        BigDecimal totalAmount = calculateService.calculateAmountScoring(
                scoringDataDto.getAmount(),
                scoringDataDto.getEmployment().getSalary(),
                scoringDataDto.getTerm(),
                scoringDataDto.getBirthdate(),
                scoringDataDto.getIsInsuranceEnabled(),
                scoringDataDto.getEmployment().getWorkExperienceTotal(),
                scoringDataDto.getEmployment().getWorkExperienceCurrent(),
                scoringDataDto.getEmployment().getPosition()
        );
        log.info("Расчет общей суммы кредита завершен: {}", totalAmount);

        BigDecimal rate = calculateService.calculateRateScoring(
                scoringDataDto.getAmount(),
                scoringDataDto.getIsSalaryClient(),
                scoringDataDto.getIsInsuranceEnabled(),
                scoringDataDto.getEmployment().getEmploymentStatus(),
                scoringDataDto.getEmployment().getPosition(),
                scoringDataDto.getMaritalStatus(),
                scoringDataDto.getGender(),
                scoringDataDto.getBirthdate()
        );
        log.info("Расчет процентной ставки завершен: {}", rate);

        Integer term = calculateService.calculateTermScoring(
                totalAmount,
                rate,
                scoringDataDto.getTerm(),
                scoringDataDto.getEmployment().getSalary()
        );
        log.info("Расчет срока кредита завершен: {}", term);

        BigDecimal monthlyPayment = calculateService.calculateMonthlyPayment(totalAmount, term, rate);
        log.info("Расчет ежемесячного платежа завершен: {}", monthlyPayment);

        BigDecimal psk = calculateService.calculatePsk(monthlyPayment, scoringDataDto.getAmount(), term);
        log.info("Расчет PSK завершен: {}", psk);

        CreditDto creditDto = CreditDto.builder()
                .amount(totalAmount)
                .term(term)
                .monthlyPayment(monthlyPayment)
                .rate(rate)
                .psk(psk)
                .isInsuranceEnabled(scoringDataDto.getIsInsuranceEnabled())
                .isSalaryClient(scoringDataDto.getIsSalaryClient())
                .paymentSchedule(calculateService.calculatePaymentSchedule(totalAmount, rate, term, monthlyPayment))
                .build();
        log.info("Расчет стоимости кредита завершен. Результат: {}", creditDto.toString());

        return creditDto;
    }
}
