package ru.mayorov.calculator.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
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

    @ApiOperation(value = "Get example", notes = "This method returns an example object")
    @PostMapping(value = "/offers")
    public ResponseEntity<List<LoanOfferDto>> calculatorOffer(@RequestBody @Valid LoanStatementRequestDto loanStatementRequestDto);

    @ApiOperation(value = "Get example", notes = "This method returns an example object")
    @PostMapping(value = "/calc")
    public CreditDto calculatorCalc(@RequestBody @Valid ScoringDataDto scoringDataDto);
}
