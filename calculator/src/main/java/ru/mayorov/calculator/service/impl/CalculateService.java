package ru.mayorov.calculator.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.mayorov.calculator.dto.EmploymentStatusEnum;
import ru.mayorov.calculator.dto.PaymentScheduleElementDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class CalculateService {

    private final static BigDecimal ONE = new BigDecimal("1");
    private final static BigDecimal LIVINGWAGE = new BigDecimal("20000");

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

    public BigDecimal calculateTotalAmount(BigDecimal amount, Integer term, Boolean isInsuranceEnabled){

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
        BigDecimal rate = new BigDecimal(rateMax500);
        if (amount.compareTo(new BigDecimal("500000")) > 0){
            rate = new BigDecimal(rateMax2000);
        }
        if (amount.compareTo(new BigDecimal("2000000")) > 0){
            rate = new BigDecimal(rateMax5000);
        }

        if (isInsuranceEnabled){
            rate = rate.subtract(new BigDecimal("10"));
        }
        if (isSalaryClient){
            rate = rate.subtract(new BigDecimal("2"));
        }
        return  rate;
    }

    public BigDecimal calculateMonthlyPayment(BigDecimal totalAmount, Integer term, BigDecimal rate){

        rate = rate.divide(new BigDecimal("12"),10, RoundingMode.HALF_UP);

        BigDecimal annuityСoefficient = (rate.multiply(ONE.add(rate)).pow(term))
                        .divide((ONE.add(rate)).pow(term)).subtract(ONE);

        return annuityСoefficient.multiply(totalAmount).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateAmountScoring(BigDecimal amount, BigDecimal salary, LocalDate birthdate){
        int age = Period.between(birthdate, LocalDate.now()).getYears();
        if ((amount.divide(salary).compareTo(new BigDecimal("25")) <= 0) && (age >= 20) && (age <= 65)) {
            return amount;
        }else {
            return null;
        }
    }

    public Integer calculateTermScoring(BigDecimal amount, BigDecimal rate, Integer term, BigDecimal salary){
        while ((salary.subtract(calculateMonthlyPayment(amount, term, rate)).compareTo(LIVINGWAGE) < 0) && (term <= 60)){
            term++;
        }
        if (salary.subtract(calculateMonthlyPayment(amount, term, rate)).compareTo(LIVINGWAGE) > 0){
            return term;
        }else{
            return null;
        }
    }

    public BigDecimal calculateRateScoring(EmploymentStatusEnum employmentStatus, Enum position, Enum maritalStatus, Enum gender, LocalDate birthdate){

        BigDecimal rate = new BigDecimal("15");
//        rate.add(employmentStatus.getDiscount())
        return null;
    }

    public BigDecimal calculatePsk(BigDecimal monthlyPayment, BigDecimal amount, Integer term){
//todo исправить пск
        return (monthlyPayment.multiply(new BigDecimal(term)).divide(amount).subtract(ONE)).divide(new BigDecimal(term).divide(new BigDecimal("12")));
    }

    public List<PaymentScheduleElementDto> calculatePaymentSchedule(BigDecimal amount, BigDecimal rate, Integer term, BigDecimal monthlyPayment){

        LocalDate today = LocalDate.now();
        List<PaymentScheduleElementDto> paymentSchedule = new ArrayList<>();

        for (int i = 1; i <= term; i++ ){
            PaymentScheduleElementDto paymentScheduleElementDto = createPaymentSchedule(i,today, amount, rate, term, monthlyPayment);
            amount = paymentScheduleElementDto.getRemainingDebt();
            paymentSchedule.add(paymentScheduleElementDto);
        }
        return paymentSchedule;
    }

    private PaymentScheduleElementDto createPaymentSchedule(int number,LocalDate date, BigDecimal amount, BigDecimal rate, Integer term, BigDecimal monthlyPayment){

        long amountDay = ChronoUnit.DAYS.between(date.plusMonths(number-1), date.plusMonths(number));
//        BigDecimal interestPayment = ;
        return PaymentScheduleElementDto.builder()
                .number(number)
                .date(date.plusMonths(number))
                .totalPayment(monthlyPayment)
//                .interestPayment()
//                .debtPayment()
//                .remainingDebt()
                .build();
    }
}

