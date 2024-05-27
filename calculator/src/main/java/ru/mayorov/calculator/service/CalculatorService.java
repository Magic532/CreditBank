package ru.mayorov.calculator.service;

import ru.mayorov.calculator.dto.CreditDto;
import ru.mayorov.calculator.dto.LoanOfferDto;
import ru.mayorov.calculator.dto.LoanStatementRequestDto;
import ru.mayorov.calculator.dto.ScoringDataDto;

import java.util.List;

public interface CalculatorService {
    public List<LoanOfferDto> prescorring(LoanStatementRequestDto loanStatementRequestDto);


    public CreditDto scorring(ScoringDataDto scoringDataDto);
}
