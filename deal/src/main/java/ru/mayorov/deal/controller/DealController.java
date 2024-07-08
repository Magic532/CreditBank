package ru.mayorov.deal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mayorov.deal.dto.FinishRegistrationRequestDto;
import ru.mayorov.deal.dto.LoanOfferDto;
import ru.mayorov.deal.dto.LoanStatementRequestDto;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/deal")
@Validated
public interface DealController {

    @Operation(summary = "Метод расчёта возможных условий кредита", description = "Метод возвращает список предложений доступных для клиента")
    @PostMapping(value = "/statement")
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
    public ResponseEntity<List<LoanOfferDto>> calculateOffer(@RequestBody @Valid LoanStatementRequestDto loanStatementRequestDto);

    @Operation(summary = "Метод выбора одного из предложений", description = "Метод сохраняет выбранное предложение в базе данных")
    @PostMapping(value = "/offer/select")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Save the selected offer in the database",
                    content = {@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = LoanOfferDto.class))
                    )}),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
            @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
        }
    )
    public void offerSelect(@RequestBody @Valid LoanOfferDto loanOfferDto);

    @Operation(summary = "Метод завершения регистрации и полный подсчёт кредита", description = "Метод производит рассчет одобренных условий по заявке")
    @PostMapping(value = "/calculate/{statementId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Calculation of approved conditions for the application",
                    content = {@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = LoanOfferDto.class))
                    )}),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
            @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
        }
    )
    public  void calculateCalc (@RequestBody @Valid FinishRegistrationRequestDto finishRegistrationRequestDto, @PathVariable String statementId);

}
