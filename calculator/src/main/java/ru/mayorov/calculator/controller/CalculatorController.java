package ru.mayorov.calculator.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.mayorov.calculator.dto.CreditDto;
import ru.mayorov.calculator.dto.LoanOfferDto;
import ru.mayorov.calculator.dto.LoanStatementRequestDto;
import ru.mayorov.calculator.dto.ScoringDataDto;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/calculator")
@Validated
public interface CalculatorController {
    @PostMapping(value = "/offers")
    public List<LoanOfferDto> calculatorOffer(@RequestBody @Valid LoanStatementRequestDto loanStatementRequestDto);

    @PostMapping(value = "/calc")
    public CreditDto calculatorCalc(@RequestBody ScoringDataDto scoringDataDto);
}
