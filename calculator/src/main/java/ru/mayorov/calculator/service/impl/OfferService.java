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


@Service
@RequiredArgsConstructor
@Slf4j
public class OfferService {

    private final CalculateService calculateService;


    public List<LoanOfferDto> generateOffer(LoanStatementRequestDto loanStatementRequestDto) {
        List<LoanOfferDto> offer = new ArrayList<>();
        try {
            if (Period.between(loanStatementRequestDto.getBirthdate(), LocalDate.now()).getYears() >= 18) {
                offer.add(createOffer(false, false, loanStatementRequestDto));
                offer.add(createOffer(false, false, loanStatementRequestDto));
                offer.add(createOffer(false, false, loanStatementRequestDto));
                offer.add(createOffer(false, false, loanStatementRequestDto));
//                return List.of(
//                        createOffer(false, false, loanStatementRequestDto),
//                        createOffer(false, true, loanStatementRequestDto),
//                        createOffer(true, false, loanStatementRequestDto),
//                        createOffer(true, true, loanStatementRequestDto)
//                );
                return offer;
            }
        } catch (IllegalArgumentException exception) {
            System.out.println("Введена некорректная дата рождения");
        }
        return new ArrayList<>(); // Возвращаем пустой список
    }



    private LoanOfferDto createOffer(Boolean isInsuranceEnabled,
                                    Boolean isSalaryClient,
                                    LoanStatementRequestDto loanStatementRequestDto) {

        BigDecimal totalAmount = calculateService.calculateTotalAmount(loanStatementRequestDto.getAmount(), loanStatementRequestDto.getTerm(), isInsuranceEnabled);

        BigDecimal rate = calculateService.calculateRateOffer(loanStatementRequestDto.getAmount(), isInsuranceEnabled, isSalaryClient);

        return LoanOfferDto.builder()
            .requestedAmount(loanStatementRequestDto.getAmount())
            .totalAmount(totalAmount)
            .term(loanStatementRequestDto.getTerm())
            .monthlyPayment(calculateService.calculateMonthlyPayment(totalAmount, loanStatementRequestDto.getTerm(), rate))
            .rate(rate)
            .isInsuranceEnabled(isInsuranceEnabled)
            .isSalaryClient(isSalaryClient).build();
    }
}