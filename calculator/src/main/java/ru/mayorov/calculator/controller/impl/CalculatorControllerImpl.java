package ru.mayorov.calculator.controller.impl;

import org.springframework.web.bind.annotation.RestController;
import ru.mayorov.calculator.controller.CalculatorController;
import ru.mayorov.calculator.dto.CreditDto;
import ru.mayorov.calculator.dto.LoanOfferDto;
import ru.mayorov.calculator.dto.LoanStatementRequestDto;
import ru.mayorov.calculator.dto.ScoringDataDto;
import ru.mayorov.calculator.service.CalculatorService;

import java.util.List;

@RestController
public class CalculatorControllerImpl implements CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorControllerImpl(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @Override
    public List<LoanOfferDto> calculatorOffer(LoanStatementRequestDto loanStatementRequestDto) {
        return calculatorService.prescorring(loanStatementRequestDto);
    }

    @Override
    public CreditDto calculatorCalc(ScoringDataDto scoringDataDto) {
        return calculatorService.scorring(scoringDataDto);
    }
}
