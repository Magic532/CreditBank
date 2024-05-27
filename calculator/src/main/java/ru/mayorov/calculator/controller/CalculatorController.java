package ru.mayorov.calculator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Метод рассчета предложений", description = "Метод возвращает список предложений доступных для клиента")
    @PostMapping(value = "/offers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "A list of proposals has been generated",
                    content = {@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = LoanOfferDto.class))
                    )}),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
            @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
        }
    )
    ResponseEntity<List<LoanOfferDto>> calculateOffers(@RequestBody @Valid LoanStatementRequestDto loanStatementRequestDto);

    /**
     * Calculator calc credit dto.
     *
     * @param scoringDataDto the scoring data dto
     * @return the credit dto
     */

    @Operation(summary = "Метод рассчета скорринга", description = "Метод возвращает итоговые условия кредитования и график платежей")
    @PostMapping(value = "/calc")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Approved loan parameters",
                    content = {@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CreditDto.class))
                    )}),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
            @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
    }
    )
    CreditDto calculateCalc(@RequestBody @Valid ScoringDataDto scoringDataDto);
}
