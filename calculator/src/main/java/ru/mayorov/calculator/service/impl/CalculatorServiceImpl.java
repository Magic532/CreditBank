package ru.mayorov.calculator.service.impl;

import org.springframework.stereotype.Service;
import ru.mayorov.calculator.dto.CreditDto;
import ru.mayorov.calculator.dto.LoanOfferDto;
import ru.mayorov.calculator.dto.LoanStatementRequestDto;
import ru.mayorov.calculator.dto.ScoringDataDto;
import ru.mayorov.calculator.service.CalculatorService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalculatorServiceImpl implements CalculatorService {
    @Override
    public List<LoanOfferDto> prescorring(LoanStatementRequestDto loanStatementRequestDto) {
        return null;
    }

    @Override
    public CreditDto scorring(ScoringDataDto scoringDataDto) {
        return null;
    }
}