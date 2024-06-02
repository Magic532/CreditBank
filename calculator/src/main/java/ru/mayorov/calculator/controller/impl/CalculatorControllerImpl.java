package ru.mayorov.calculator.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.mayorov.calculator.controller.CalculatorController;
import ru.mayorov.calculator.dto.CreditDto;
import ru.mayorov.calculator.dto.LoanOfferDto;
import ru.mayorov.calculator.dto.LoanStatementRequestDto;
import ru.mayorov.calculator.dto.ScoringDataDto;
import ru.mayorov.calculator.service.impl.OfferService;
import ru.mayorov.calculator.service.impl.ScoringService;

import java.util.List;

@RestController
public class CalculatorControllerImpl implements CalculatorController {

    private final OfferService offerService;
    private final ScoringService scoringService;

    public CalculatorControllerImpl(OfferService offerService, ScoringService scoringService) {
        this.offerService = offerService;
        this.scoringService = scoringService;
    }

    @Override
    public List<LoanOfferDto> calculatorOffer(LoanStatementRequestDto loanStatementRequestDto) {
        return offerService.generateOffer(loanStatementRequestDto);
    }

    @Override
    public CreditDto calculatorCalc(ScoringDataDto scoringDataDto) {
        return scoringService.scorring(scoringDataDto);
    }
}
