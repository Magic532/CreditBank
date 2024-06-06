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

/**
 * The interface Calculator controller.
 */
@RequestMapping("/calculator")
@Validated
public interface CalculatorController {

    /**
     * Calculator offer response entity.
     *
     * @param loanStatementRequestDto the loan statement request dto
     * @return the response entity
     */
    @ApiOperation(value = "Get offers", notes = "This method returns an list offers")
    @PostMapping(value = "/offers")
    public ResponseEntity<List<LoanOfferDto>> calculatorOffer(@RequestBody @Valid LoanStatementRequestDto loanStatementRequestDto);

    /**
     * Calculator calc credit dto.
     *
     * @param scoringDataDto the scoring data dto
     * @return the credit dto
     */
    @ApiOperation(value = "Get calc", notes = "This method returns the calculation of loan terms")
    @PostMapping(value = "/calc")
    public CreditDto calculatorCalc(@RequestBody @Valid ScoringDataDto scoringDataDto);
}
