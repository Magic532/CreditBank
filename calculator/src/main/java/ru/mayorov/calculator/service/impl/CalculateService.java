package ru.mayorov.calculator.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.mayorov.calculator.dto.PaymentScheduleElementDto;
import ru.mayorov.calculator.units.EmploymentStatusEnum;
import ru.mayorov.calculator.units.GenderEnum;
import ru.mayorov.calculator.units.MaritalStatusEnum;
import ru.mayorov.calculator.units.PositionEnum;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CalculateService {

    private final static BigDecimal LIVINGWAGE = new BigDecimal("20000");
    private final static BigDecimal DISCOUNTSALARYCLIENT = new BigDecimal("2");
    private final static BigDecimal DISCOUNTINSURANCEENABLED = new BigDecimal("10");

    @Value("$insurancePercentPerMonthMax100")
    private String insurancePercentPerMonthMax100;

    @Value("$insurancePercentPerMonthMax500")
    private String insurancePercentPerMonthMax500;

    @Value("$insurancePercentPerMonthMax2000")
    private String insurancePercentPerMonthMax2000;

    @Value("$insurancePercentPerMonth5000")
    private String insurancePercentPerMonthMax5000;

    @Value("rateMax500")
    private String rateMax500;

    @Value("rateMax2000")
    private String rateMax2000;

    @Value("rateMax5000")
    private String rateMax5000;

    public BigDecimal calculateTotalAmountOffer(BigDecimal amount, Integer term, Boolean isInsuranceEnabled){
        log.info("Начинается расчет общей суммы кредита предложения для суммы {}, сроком {} месяц(ов), страхование включено: {}", amount, term, isInsuranceEnabled);

        BigDecimal insurancePercentPerMonth = new BigDecimal(insurancePercentPerMonthMax100);

        if (isInsuranceEnabled) {
            if (amount.compareTo(new BigDecimal("100000")) > 0){
                insurancePercentPerMonth = new BigDecimal(insurancePercentPerMonthMax500);
            }
            if (amount.compareTo(new BigDecimal("500000")) > 0){
                insurancePercentPerMonth = new BigDecimal(insurancePercentPerMonthMax2000);
            }
            if (amount.compareTo(new BigDecimal("2000000")) > 0){
                insurancePercentPerMonth = new BigDecimal(insurancePercentPerMonthMax5000);
            }
            return amount.add(amount.multiply(insurancePercentPerMonth)
                            .multiply(new BigDecimal(term)))
                    .setScale(2, RoundingMode.HALF_UP);
        }else{
            return amount;
        }
    }

    public BigDecimal calculateRateOffer(BigDecimal amount, Boolean isInsuranceEnabled, Boolean isSalaryClient){
        log.info("Начинается расчет процентной ставки предложения для суммы {}, страхование включено: {}, зарплатный клиент: {}", amount, isInsuranceEnabled, isSalaryClient);

        BigDecimal rate = new BigDecimal(rateMax500);
        if (amount.compareTo(new BigDecimal("500000")) > 0){
            rate = new BigDecimal(rateMax2000);
        }
        if (amount.compareTo(new BigDecimal("2000000")) > 0){
            rate = new BigDecimal(rateMax5000);
        }

        if (isInsuranceEnabled){
            rate = rate.subtract(DISCOUNTINSURANCEENABLED);
        }
        if (isSalaryClient){
            rate = rate.subtract(DISCOUNTSALARYCLIENT);
        }
        return  rate;
    }

    public BigDecimal calculateMonthlyPayment(BigDecimal totalAmount, Integer term, BigDecimal rate){
        log.info("Начинается расчет ежемесячного платежа для общей суммы {}, срок {} месяц(ов), процентная ставка: {}", totalAmount, term, rate);

        rate = rate.divide(new BigDecimal("12"),10, RoundingMode.HALF_UP);

        BigDecimal annuityСoefficient = (rate
                        .multiply(new BigDecimal("1")
                        .add(rate)).pow(term))
                        .divide((new BigDecimal("1")
                        .add(rate))
                        .pow(term))
                        .subtract(new BigDecimal("1"));

        return annuityСoefficient.multiply(totalAmount).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateAmountScoring(BigDecimal amount, BigDecimal salary, Integer term, LocalDate birthdate, Boolean isInsuranceEnabled, Integer workExperienceTotal, Integer workExperienceCurrent, PositionEnum position) {
        log.info("Начинается расчет общей суммы кредита для скоринга: сумма {}, зарплата {}, срок {} месяц(ов), дата рождения {}, страхование включено: {}, общий трудовой стаж {}, стаж на текущем месте работы {}, вид занятости: {}", amount, salary, term, birthdate, isInsuranceEnabled, workExperienceTotal, workExperienceCurrent, position);

        BigDecimal insurancePercentPerMonth = new BigDecimal(insurancePercentPerMonthMax100);

        int age = Period.between(birthdate, LocalDate.now()).getYears();

        if ((amount.divide(salary).compareTo(new BigDecimal("25")) <= 0) && (age >= 20) && (age <= 65) && (workExperienceTotal >= 18) && (workExperienceCurrent >= 3) && position.getDiscount() != null)   {
            if (isInsuranceEnabled) {
                if (amount.compareTo(new BigDecimal("100000")) > 0) {
                    insurancePercentPerMonth = new BigDecimal(insurancePercentPerMonthMax500);
                }
                if (amount.compareTo(new BigDecimal("500000")) > 0) {
                    insurancePercentPerMonth = new BigDecimal(insurancePercentPerMonthMax2000);
                }
                if (amount.compareTo(new BigDecimal("2000000")) > 0) {
                    insurancePercentPerMonth = new BigDecimal(insurancePercentPerMonthMax5000);
                }
                return amount.add(amount.multiply(insurancePercentPerMonth)
                                .multiply(new BigDecimal(term)))
                        .setScale(2, RoundingMode.HALF_UP);
            } else {
                return amount;
            }
        }
        return BigDecimal.ZERO;
    }


    public Integer calculateTermScoring(BigDecimal amount, BigDecimal rate, Integer term, BigDecimal salary){
        log.info("Начинается расчет срока кредита для скоринга: сумма {}, процентная ставка {}, срок {} месяц(ов), зарплата: {}", amount, rate, term, salary);

        if (salary.subtract(calculateMonthlyPayment(amount, term, rate)).compareTo(LIVINGWAGE) > 0){
            return term;
        }else{
            return 0;
        }
    }

    public BigDecimal calculateRateScoring(BigDecimal amount, Boolean isSalaryClient, Boolean isInsuranceEnabled, EmploymentStatusEnum employmentStatus, PositionEnum position, MaritalStatusEnum maritalStatus, GenderEnum gender, LocalDate birthdate){
        log.info("Начинается расчет процентной ставки для скоринга: сумма {}, страхование включено: {}, зарплатный клиент: {}, уровень занимаемой должности: {}, вид занятости: {}, семейное положение: {}, пол: {}, дата рождения: {}", amount, isInsuranceEnabled, isSalaryClient, employmentStatus, position, maritalStatus, gender, birthdate);

        int age = Period.between(birthdate, LocalDate.now()).getYears();

        BigDecimal rate = new BigDecimal(rateMax500);

        if (amount.compareTo(new BigDecimal("500000")) > 0){
            rate = new BigDecimal(rateMax2000);
        }
        if (amount.compareTo(new BigDecimal("2000000")) > 0){
            rate = new BigDecimal(rateMax5000);
        }

        if (isInsuranceEnabled) {
            rate = rate.subtract(DISCOUNTINSURANCEENABLED);
        }

        if (isSalaryClient){
            rate.subtract(DISCOUNTSALARYCLIENT);
        }

        rate.subtract(new BigDecimal(employmentStatus.getDiscount()))
            .subtract(new BigDecimal(position.getDiscount()))
            .subtract(new BigDecimal(maritalStatus.getDiscount()));

        switch (gender) {
            case MALE:
                if ((age >= 30) && (age <= 55)) {
                    rate.subtract(new BigDecimal(gender.getDiscount()));
                }
                break;

            case FEMALE:
                if ((age >= 32) && (age <= 60)) {
                    rate.subtract(new BigDecimal(gender.getDiscount()));
                }
                break;

            case NONBINARY:
                rate.subtract(new BigDecimal(gender.getDiscount()));
                break;
        }
        return rate;
    }

    public BigDecimal calculatePsk(BigDecimal monthlyPayment, BigDecimal amount, Integer term){
        log.info("Начинается расчет ПСК для: ежемесячный платеж {}, общая сумма {}, срок {} месяц(ов)", monthlyPayment, amount, term);

        return monthlyPayment
                .multiply(new BigDecimal(term))
                .subtract(amount);
    }

    public List<PaymentScheduleElementDto> calculatePaymentSchedule(BigDecimal totalAmount, BigDecimal rate, Integer term, BigDecimal monthlyPayment){
        log.info("Начинается расчет графика платежей для: общая сумма {}, процентная ставка {}, срок {} месяц(ов), ежемесячный платеж: {}", totalAmount, rate, term, monthlyPayment);

        LocalDate today = LocalDate.now();
        List<PaymentScheduleElementDto> paymentSchedule = new ArrayList<>();

        for (int i = 1; i < term; i++ ){
            PaymentScheduleElementDto paymentScheduleElementDto = createPaymentSchedule(i,today, totalAmount, rate, term, monthlyPayment);
            totalAmount = paymentScheduleElementDto.getRemainingDebt();
            paymentSchedule.add(paymentScheduleElementDto);
        }

        monthlyPayment = totalAmount.
                add(totalAmount
                .multiply(rate)
                .divide(new BigDecimal("365"), 10, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(ChronoUnit.DAYS.between(today.plusMonths(term-1), today.plusMonths(term))))
                .setScale(2,RoundingMode.HALF_UP));

        PaymentScheduleElementDto paymentScheduleElementDto = createPaymentSchedule(term,today, totalAmount, rate, term, monthlyPayment);
        paymentSchedule.add(paymentScheduleElementDto);

        return paymentSchedule;
    }

    private PaymentScheduleElementDto createPaymentSchedule(int number,LocalDate date, BigDecimal totalAmount, BigDecimal rate, Integer term, BigDecimal monthlyPayment){

        long amountDay = ChronoUnit.DAYS.between(date.plusMonths(number-1), date.plusMonths(number));

        BigDecimal interestPayment = totalAmount
                .multiply(rate)
                .divide(new BigDecimal("365"), 10, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(amountDay))
                .setScale(2,RoundingMode.HALF_UP);

        BigDecimal debtPayment = monthlyPayment.subtract(interestPayment);

        return PaymentScheduleElementDto.builder()
                .number(number)
                .date(date.plusMonths(number))
                .totalPayment(monthlyPayment)
                .interestPayment(interestPayment)
                .debtPayment(debtPayment)
                .remainingDebt(totalAmount.subtract(debtPayment))
                .build();
    }
}

