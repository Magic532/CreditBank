package ru.mayorov.calculator.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.mayorov.calculator.dto.LoanOfferDto;
import ru.mayorov.calculator.dto.LoanStatementRequestDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * The class of service, calculates various loan offer schemes taking into account various factors.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OfferService {

    private final CalculateService calculateService;

    /**
     * Generate offer list.
     *
     * @param loanStatementRequestDto the loan statement request dto
     * @return the list
     */
    public List<LoanOfferDto> generateOffer(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("Генерация предложения для {}", loanStatementRequestDto.toString());
        List<LoanOfferDto> offer = new ArrayList<>();
        try {
            int yearsOld = Period.between(loanStatementRequestDto.getBirthdate(), LocalDate.now()).getYears();
            log.warn("Возраст клиента {} лет", yearsOld);

            if (yearsOld >= 18) {
                offer.add(createOffer(false, false, loanStatementRequestDto));
                offer.add(createOffer(false, true, loanStatementRequestDto));
                offer.add(createOffer(true, false, loanStatementRequestDto));
                offer.add(createOffer(true, true, loanStatementRequestDto));

                log.info("Предложение сгенерировано");
                return offer;
            }
        } catch (IllegalArgumentException exception) {
            log.error("Введена некорректная дата рождения. Предложение не сгенерировано", exception);
        }
        log.info("Возраст менее 18 лет. Сгенерирован пустой список");
        return new ArrayList<>();
    }



    private LoanOfferDto createOffer(Boolean isInsuranceEnabled,
                                    Boolean isSalaryClient,
                                    LoanStatementRequestDto loanStatementRequestDto) {

        BigDecimal totalAmount = calculateService.calculateTotalAmountOffer(loanStatementRequestDto.getAmount(), loanStatementRequestDto.getTerm(), isInsuranceEnabled);

        BigDecimal rate = calculateService.calculateRateOffer(loanStatementRequestDto.getAmount(), isInsuranceEnabled, isSalaryClient);

        return LoanOfferDto.builder()
            .statementId(UUID.randomUUID())
            .requestedAmount(loanStatementRequestDto.getAmount())
            .totalAmount(totalAmount)
            .term(loanStatementRequestDto.getTerm())
            .monthlyPayment(calculateService.calculateMonthlyPayment(totalAmount, loanStatementRequestDto.getTerm(), rate))
            .rate(rate)
            .isInsuranceEnabled(isInsuranceEnabled)
            .isSalaryClient(isSalaryClient).build();
    }
}