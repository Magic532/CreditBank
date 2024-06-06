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

/**
 * The class of service responsible for calculating parameters.
 */
@Service
@Slf4j
public class CalculateService {

    private final static BigDecimal MINIMUM_LIVING_WAGE = new BigDecimal("20000");
    private final static BigDecimal DISCOUNT_SALARY_CLIENT = new BigDecimal("0.02");
    private final static BigDecimal DISCOUNT_INSURANCE_ENABLED = new BigDecimal("0.1");

    // @Value("${insurancePercentPerMonthMax100}")
    private String insurancePercentPerMonthMax100 = "0.004";  // value 0,004

    // @Value("${insurancePercentPerMonthMax500}")
    private String insurancePercentPerMonthMax500 = "0.0036";  // value 0,0036

    // @Value("${insurancePercentPerMonthMax2000}")
    private String insurancePercentPerMonthMax2000 = "0.0032";  // value 0,0032

    // @Value("${insurancePercentPerMonthMax5000}")
    private String insurancePercentPerMonthMax5000 = "0.003";  // value 0,003

    // @Value("${rateMax500}")
    private String rateMax500 = "0.25";  // value 0,25

    // @Value("${rateMax2000}")
    private String rateMax2000 = "0.24";  // value 0,24

    // @Value("${rateMax5000}")
    private String rateMax5000 = "0.23";  // value 0,23


    /**
     * Calculate total amount offer big decimal.
     *
     * @param amount             the amount
     * @param term               the term
     * @param isInsuranceEnabled the is insurance enabled
     * @return the big decimal
     */
    public BigDecimal calculateTotalAmountOffer(BigDecimal amount, Integer term, Boolean isInsuranceEnabled) {
        log.info("Начинается расчет общей суммы кредита предложения для суммы {}, сроком {} месяц(ов), страхование включено: {}", amount, term, isInsuranceEnabled.toString());

        BigDecimal insurancePercentPerMonth = new BigDecimal(insurancePercentPerMonthMax100);

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

    /**
     * Calculate rate offer big decimal.
     *
     * @param amount             the amount
     * @param isInsuranceEnabled the is insurance enabled
     * @param isSalaryClient     the is salary client
     * @return the big decimal
     */
    public BigDecimal calculateRateOffer(BigDecimal amount, Boolean isInsuranceEnabled, Boolean isSalaryClient) {
        log.info("Начинается расчет процентной ставки предложения для суммы {}, страхование включено: {}, зарплатный клиент: {}", amount, isInsuranceEnabled.toString(), isSalaryClient.toString());

        BigDecimal rate = new BigDecimal(rateMax500);

        if (amount.compareTo(new BigDecimal("500000")) > 0) {
            rate = new BigDecimal(rateMax2000);
        }
        if (amount.compareTo(new BigDecimal("2000000")) > 0) {
            rate = new BigDecimal(rateMax5000);
        }

        if (isInsuranceEnabled) {
            rate = rate.subtract(DISCOUNT_INSURANCE_ENABLED);
        }
        if (isSalaryClient) {
            rate = rate.subtract(DISCOUNT_SALARY_CLIENT);
        }
        return rate;
    }

    /**
     * Calculate monthly payment big decimal.
     *
     * @param totalAmount the total amount
     * @param term        the term
     * @param rate        the rate
     * @return the big decimal
     */
    public BigDecimal calculateMonthlyPayment(BigDecimal totalAmount, Integer term, BigDecimal rate) {
        log.info("Начинается расчет ежемесячного платежа для общей суммы {}, срок {} месяц(ов), процентная ставка: {}", totalAmount, term, rate);

        rate = rate.divide(new BigDecimal("12"), 10, RoundingMode.HALF_UP);  // rate/12

        BigDecimal numerator = rate.multiply(new BigDecimal(1).add(rate).pow(term));  //  rate * (1 + rate) ^ term

        BigDecimal denominator = new BigDecimal(1).add(rate).pow(term).subtract(new BigDecimal(1));  //  (1 + rate) ^ term — 1

        BigDecimal annuityСoefficient = numerator.divide(denominator, 8, RoundingMode.HALF_UP );

        return annuityСoefficient.multiply(totalAmount).setScale(2, RoundingMode.HALF_UP);  //  monthlyPayment = totalAmount * (rate * (1 + rate) ^ term) / ((1 + rate) ^ term — 1)
    }

    /**
     * Calculate amount scoring big decimal.
     *
     * @param amount                the amount
     * @param salary                the salary
     * @param term                  the term
     * @param birthdate             the birthdate
     * @param isInsuranceEnabled    the is insurance enabled
     * @param workExperienceTotal   the work experience total
     * @param workExperienceCurrent the work experience current
     * @param position              the position
     * @return the big decimal
     */
    public BigDecimal calculateAmountScoring(BigDecimal amount,
                                             BigDecimal salary,
                                             Integer term,
                                             LocalDate birthdate,
                                             Boolean isInsuranceEnabled,
                                             Integer workExperienceTotal,
                                             Integer workExperienceCurrent,
                                             PositionEnum position) {
        log.info("Начинается расчет общей суммы кредита для скоринга: сумма {}, зарплата {}, срок {} месяц(ов), дата рождения {}, страхование включено: {}, общий трудовой стаж {}, стаж на текущем месте работы {}, вид занятости: {}", amount, salary, term, birthdate, isInsuranceEnabled, workExperienceTotal, workExperienceCurrent, position);

        BigDecimal insurancePercentPerMonth = new BigDecimal(insurancePercentPerMonthMax100);

        int age = Period.between(birthdate, LocalDate.now()).getYears();

        if ((amount.divide(salary, 2, RoundingMode.HALF_UP).compareTo(new BigDecimal("25")) <= 0) && (age >= 20) && (age <= 65) && (workExperienceTotal >= 18) && (workExperienceCurrent >= 3) && position.getDiscount() != null) {
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

    /**
     * Calculate term scoring integer.
     *
     * @param amount the amount
     * @param rate   the rate
     * @param term   the term
     * @param salary the salary
     * @return the integer
     */
    public Integer calculateTermScoring(BigDecimal amount, BigDecimal rate, Integer term, BigDecimal salary) {
        log.info("Начинается расчет срока кредита для скоринга: сумма {}, процентная ставка {}, срок {} месяц(ов), зарплата: {}", amount, rate, term, salary);
        if (salary.subtract(calculateMonthlyPayment(amount, term, rate)).compareTo(MINIMUM_LIVING_WAGE) > 0) {
            return term;
        } else {
            return 0;
        }
    }

    /**
     * Calculate rate scoring big decimal.
     *
     * @param amount             the amount
     * @param isSalaryClient     the is salary client
     * @param isInsuranceEnabled the is insurance enabled
     * @param employmentStatus   the employment status
     * @param position           the position
     * @param maritalStatus      the marital status
     * @param gender             the gender
     * @param birthdate          the birthdate
     * @return the big decimal
     */
    public BigDecimal calculateRateScoring(BigDecimal amount, Boolean isSalaryClient, Boolean isInsuranceEnabled, EmploymentStatusEnum employmentStatus, PositionEnum position, MaritalStatusEnum maritalStatus, GenderEnum gender, LocalDate birthdate) {
        log.info("Начинается расчет процентной ставки для скоринга: сумма {}, страхование включено: {}, зарплатный клиент: {}, уровень занимаемой должности: {}, вид занятости: {}, семейное положение: {}, пол: {}, дата рождения: {}", amount, isInsuranceEnabled, isSalaryClient, employmentStatus, position, maritalStatus, gender, birthdate);

        int age = Period.between(birthdate, LocalDate.now()).getYears();

        BigDecimal rate = new BigDecimal(rateMax500);

        if (amount.compareTo(new BigDecimal("500000")) > 0) {
            rate = new BigDecimal(rateMax2000);
        }

        if (amount.compareTo(new BigDecimal("2000000")) > 0) {
            rate = new BigDecimal(rateMax5000);
        }

        if (isInsuranceEnabled) {
            rate = rate.subtract(DISCOUNT_INSURANCE_ENABLED);
        }

        if (isSalaryClient) {
            rate = rate.subtract(DISCOUNT_SALARY_CLIENT);
        }

        rate = rate.subtract(new BigDecimal(employmentStatus.getDiscount()))
                .subtract(new BigDecimal(position.getDiscount()))
                .subtract(new BigDecimal(maritalStatus.getDiscount()));

        switch (gender) {
            case MALE:
                if ((age >= 30) && (age <= 55)) {
                    rate = rate.subtract(new BigDecimal(gender.getDiscount()));
                }
                break;

            case FEMALE:
                if ((age >= 32) && (age <= 60)) {
                    rate = rate.subtract(new BigDecimal(gender.getDiscount()));
                }
                break;

            case NONBINARY:
                rate = rate.subtract(new BigDecimal(gender.getDiscount()));
                break;
        }

        return rate.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calculate psk big decimal.
     *
     * @param monthlyPayment the monthly payment
     * @param amount         the amount
     * @param term           the term
     * @return the big decimal
     */
    public BigDecimal calculatePsk(BigDecimal monthlyPayment, BigDecimal amount, Integer term) {
        log.info("Начинается расчет ПСК для: ежемесячный платеж {}, общая сумма {}, срок {} месяц(ов)", monthlyPayment, amount, term);

        return monthlyPayment
                .multiply(new BigDecimal(term))
                .subtract(amount);
    }

    /**
     * Calculate payment schedule list.
     *
     * @param totalAmount    the total amount
     * @param rate           the rate
     * @param term           the term
     * @param monthlyPayment the monthly payment
     * @return the list
     */
    public List<PaymentScheduleElementDto> calculatePaymentSchedule(BigDecimal totalAmount, BigDecimal rate, Integer term, BigDecimal monthlyPayment) {
        log.info("Начинается расчет графика платежей для: общая сумма {}, процентная ставка {}, срок {} месяц(ов), ежемесячный платеж: {}", totalAmount, rate, term, monthlyPayment);

        LocalDate today = LocalDate.now();
        List<PaymentScheduleElementDto> paymentSchedule = new ArrayList<>();

        for (int i = 1; i < term; i++) {
            PaymentScheduleElementDto paymentScheduleElementDto = createPaymentSchedule(i, today, totalAmount, rate, term, monthlyPayment);
            totalAmount = paymentScheduleElementDto.getRemainingDebt();
            paymentSchedule.add(paymentScheduleElementDto);
        }

        monthlyPayment = totalAmount.
                add(totalAmount
                        .multiply(rate)
                        .divide(new BigDecimal("365"), 10, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal(ChronoUnit.DAYS.between(today.plusMonths(term - 1), today.plusMonths(term))))
                        .setScale(2, RoundingMode.HALF_UP));

        PaymentScheduleElementDto paymentScheduleElementDto = createPaymentSchedule(term, today, totalAmount, rate, term, monthlyPayment);
        paymentSchedule.add(paymentScheduleElementDto);

        return paymentSchedule;
    }

    private PaymentScheduleElementDto createPaymentSchedule(int number, LocalDate date, BigDecimal totalAmount, BigDecimal rate, Integer term, BigDecimal monthlyPayment) {

        long amountDay = ChronoUnit.DAYS.between(date.plusMonths(number - 1), date.plusMonths(number));

        BigDecimal interestPayment = totalAmount
                .multiply(rate)
                .divide(new BigDecimal("365"), 10, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(amountDay))
                .setScale(2, RoundingMode.HALF_UP);

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

