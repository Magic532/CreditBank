package ru.mayorov.calculator.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.mayorov.calculator.config.CalcProperties;
import ru.mayorov.calculator.dto.PaymentScheduleElementDto;
import ru.mayorov.calculator.units.EmploymentStatusEnum;
import ru.mayorov.calculator.units.GenderEnum;
import ru.mayorov.calculator.units.MaritalStatusEnum;
import ru.mayorov.calculator.units.PositionEnum;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculateServiceTest {

    @Mock
    CalcProperties calcProperties = new CalcProperties(
                new BigDecimal(0.02),
                new BigDecimal(0.1),
                new BigDecimal(20000.0),
                new BigDecimal(0.004),
                new BigDecimal(0.0036),
                new BigDecimal(0.0032),
                new BigDecimal(0.003),
                new BigDecimal(0.25),
                new BigDecimal(0.24),
                new BigDecimal(0.23),
                new BigDecimal(25.0)
        );

    @InjectMocks
    CalculateService calculateService = new CalculateService(calcProperties);

    @Test
    void testCalculateTotalAmountOffer() {
        BigDecimal amount = new BigDecimal("100000");
        Integer term = 60;
        Boolean isInsFalse = false;
        Boolean isInsTrue = true;

        BigDecimal expectedAmountInsFalse = new BigDecimal("100000.00");
        BigDecimal expectedAmountInsTrue = new BigDecimal("124000.00");

        BigDecimal actualAmountInsFalse = calculateService.calculateTotalAmountOffer(amount, term, isInsFalse)
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal actualAmountInsTrue = calculateService.calculateTotalAmountOffer(amount, term, isInsTrue)
                .setScale(2, RoundingMode.HALF_UP);

        assertEquals(expectedAmountInsFalse, actualAmountInsFalse);
        assertEquals(expectedAmountInsTrue, actualAmountInsTrue);
    }

    @Test
    void testCalculateRateOffer() {
        BigDecimal amount = new BigDecimal("100000");
        Boolean isInsFalse = false;
        Boolean isInsTrue = true;
        Boolean isSalaryFalse = false;
        Boolean isSalaryTrue = true;

        BigDecimal expectedRateInsFalseSalaryFalse = new BigDecimal("0.25");
        BigDecimal expectedRateInsFalseSalaryTrue = new BigDecimal("0.23");
        BigDecimal expectedRateInsTrueSalaryFalse = new BigDecimal("0.15");
        BigDecimal expectedRateInsTrueSalaryTrue = new BigDecimal("0.13");

        BigDecimal actualRateInsFalseSalaryFalse = calculateService.calculateRateOffer(amount, isInsFalse, isSalaryFalse);
        BigDecimal actualRateInsFalseSalaryTrue = calculateService.calculateRateOffer(amount, isInsFalse, isSalaryTrue);
        BigDecimal actualRateInsTrueSalaryFalse = calculateService.calculateRateOffer(amount, isInsTrue, isSalaryFalse);
        BigDecimal actualRateInsTrueSalaryTrue = calculateService.calculateRateOffer(amount, isInsTrue, isSalaryTrue);

        assertEquals(expectedRateInsFalseSalaryFalse, actualRateInsFalseSalaryFalse);
        assertEquals(expectedRateInsFalseSalaryTrue, actualRateInsFalseSalaryTrue);
        assertEquals(expectedRateInsTrueSalaryFalse, actualRateInsTrueSalaryFalse);
        assertEquals(expectedRateInsTrueSalaryTrue, actualRateInsTrueSalaryTrue);

    }

    @Test
    void testCalculateMonthlyPayment() {
        BigDecimal totalAmount = new BigDecimal("100000");
        Integer term = 60;
        BigDecimal rate = new BigDecimal("0.24");

        BigDecimal expectedMonthlyPayment = new BigDecimal("2876.80");
        BigDecimal actualMonthlyPayment = calculateService.calculateMonthlyPayment(totalAmount,term,rate);

        assertEquals(expectedMonthlyPayment, actualMonthlyPayment);
    }

    @Test
    void testCalculateAmountScoring() {
        BigDecimal amount = new BigDecimal("100000");
        BigDecimal salary = new BigDecimal("30000");
        BigDecimal salaryLow = new BigDecimal("2500");
        Integer term = 60;
        LocalDate birthdate = LocalDate.of(1990, 1, 1);
        LocalDate birthdateLow = LocalDate.of(2010, 1, 1);
        Boolean isInsuranceEnabledTrue = true;
        Boolean isInsuranceEnabledFalse = false;
        Integer workExperienceTotal = 22;
        Integer workExperienceTotalLow = 15;
        Integer workExperienceCurrent = 5;
        Integer workExperienceCurrentLow = 2;
        EmploymentStatusEnum employmentStatus = EmploymentStatusEnum.EMPLOYED;
        EmploymentStatusEnum employmentStatusUnemployed = EmploymentStatusEnum.UNEMLOYED;

        BigDecimal expectedAmountInsTrue = new BigDecimal("124000.00");
        BigDecimal expectedAmountInsFalse = new BigDecimal("100000.00");
        BigDecimal expectedAmountSalaryLow = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

        BigDecimal expectedAmountBirthdateLow = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedAmountWorkExperienceTotalLow = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedAmountWorkExperienceCurrentLow = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedAmountEmploymentStatusUnemployed = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

        BigDecimal actualAmountInsTrue = calculateService.calculateAmountScoring(
                amount, salary, term, birthdate, isInsuranceEnabledTrue,workExperienceTotal,
                workExperienceCurrent, employmentStatus).setScale(2, RoundingMode.HALF_UP);
        BigDecimal actualAmountInsFalse = calculateService.calculateAmountScoring(
                amount, salary, term, birthdate, isInsuranceEnabledFalse,workExperienceTotal,
                workExperienceCurrent, employmentStatus).setScale(2, RoundingMode.HALF_UP);
        BigDecimal actualAmountSalaryLow = calculateService.calculateAmountScoring(
                amount, salaryLow, term, birthdate, isInsuranceEnabledTrue,workExperienceTotal,
                workExperienceCurrent, employmentStatus).setScale(2, RoundingMode.HALF_UP);
        BigDecimal actualAmountBirthdateLow = calculateService.calculateAmountScoring(
                amount, salary, term, birthdateLow, isInsuranceEnabledTrue,workExperienceTotal,
                workExperienceCurrent, employmentStatus).setScale(2, RoundingMode.HALF_UP);
        BigDecimal actualAmountWorkExperienceTotalLow = calculateService.calculateAmountScoring(
                amount, salary, term, birthdate, isInsuranceEnabledTrue,workExperienceTotalLow,
                workExperienceCurrent, employmentStatus).setScale(2, RoundingMode.HALF_UP);
        BigDecimal actualAmountWorkExperienceCurrentLow = calculateService.calculateAmountScoring(
                amount, salary, term, birthdate, isInsuranceEnabledTrue,workExperienceTotal,
                workExperienceCurrentLow, employmentStatus).setScale(2, RoundingMode.HALF_UP);
        BigDecimal actualAmountEmploymentStatusUnemployed = calculateService.calculateAmountScoring(
                amount, salary, term, birthdate, isInsuranceEnabledTrue,workExperienceTotal,
                workExperienceCurrent, employmentStatusUnemployed).setScale(2, RoundingMode.HALF_UP);

        assertEquals(expectedAmountInsFalse, actualAmountInsFalse);
        assertEquals(expectedAmountInsTrue, actualAmountInsTrue);
        assertEquals(expectedAmountSalaryLow, actualAmountSalaryLow);
        assertEquals(expectedAmountBirthdateLow, actualAmountBirthdateLow);
        assertEquals(expectedAmountWorkExperienceTotalLow, actualAmountWorkExperienceTotalLow);
        assertEquals(expectedAmountWorkExperienceCurrentLow, actualAmountWorkExperienceCurrentLow);
        assertEquals(expectedAmountEmploymentStatusUnemployed, actualAmountEmploymentStatusUnemployed);
    }

    @Test
    void testCalculateTermScoring() {
        BigDecimal amount = new BigDecimal("100000");
        BigDecimal rate = new BigDecimal("0.24");
        Integer term = 60;
        BigDecimal salary = new BigDecimal("50000");
        BigDecimal salaryLow = new BigDecimal("10000");

        Integer expectedTerm = 60;
        Integer expectedTermSalaryLow = 0;

        Integer actualTerm = calculateService.calculateTermScoring(amount,rate,term,salary);
        Integer actualTermSalaryLow = calculateService.calculateTermScoring(amount,rate,term,salaryLow);

        assertEquals(expectedTerm, actualTerm);
        assertEquals(expectedTermSalaryLow, actualTermSalaryLow);
    }

    @Test
    void testCalculateRateScoring() {
        BigDecimal amount = new BigDecimal("100000");
        Boolean isSalaryClient = true;
        Boolean isInsuranceEnabled = true;
        PositionEnum position = PositionEnum.MID_MANAGER;
        EmploymentStatusEnum employmentStatus = EmploymentStatusEnum.BUSINESS_OWNER;
        MaritalStatusEnum maritalStatus = MaritalStatusEnum.DIVORCED;
        GenderEnum gender = GenderEnum.MALE;
        LocalDate birthdate = LocalDate.of(1980, 1, 1);

        BigDecimal expectedRateScoring = new BigDecimal("0.11");

        BigDecimal actualRateScoring = calculateService.calculateRateScoring(
                amount, isSalaryClient, isInsuranceEnabled, employmentStatus, position,
                maritalStatus, gender, birthdate).setScale(2, RoundingMode.HALF_UP);

        assertEquals(expectedRateScoring, actualRateScoring);
    }

    @Test
    void testCalculatePsk() {
        BigDecimal monthlyPayment = new BigDecimal("3000");
        BigDecimal amount = new BigDecimal("120000");
        Integer term = 60;

        BigDecimal expectedPsk = new BigDecimal("60000");

        BigDecimal actualPsk = calculateService.calculatePsk(monthlyPayment, amount, term);

        assertEquals(expectedPsk, actualPsk);
            }

    @Test
    void testCalculatePaymentSchedule() {
        BigDecimal totalAmount = new BigDecimal("100000");
        BigDecimal rate = new BigDecimal("0.12");
        Integer term = 60;
        BigDecimal monthlyPayment = new BigDecimal("2224.44");

        List<PaymentScheduleElementDto> actualPaymentSchedule = calculateService.calculatePaymentSchedule(totalAmount, rate, term, monthlyPayment);

        assertEquals(60, actualPaymentSchedule.size());

        PaymentScheduleElementDto firstElement = actualPaymentSchedule.get(0);

        assertNotNull(firstElement);
        assertEquals(new BigDecimal("986.30"), actualPaymentSchedule.get(0).getInterestPayment());
    }
}