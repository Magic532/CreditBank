package ru.mayorov.deal.calculatorapi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.mayorov.deal.dto.CreditDto;
import ru.mayorov.deal.dto.LoanOfferDto;
import ru.mayorov.deal.dto.LoanStatementRequestDto;
import ru.mayorov.deal.dto.ScoringDataDto;

import java.util.List;

@FeignClient(name = "calculator", url = "http://localhost:8080")
public interface CalculatorServiceApi {

    @PostMapping("/calculator/offers")
    List<LoanOfferDto> getOffers(@RequestBody LoanStatementRequestDto loanStatementRequestDto);

    @PostMapping("/calculator/calc")
    CreditDto getCalc(@RequestBody ScoringDataDto scoringDataDto);
}
