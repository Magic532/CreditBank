package ru.mayorov.calculator.controller.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.mayorov.calculator.controller.CalculatorController;
import ru.mayorov.calculator.dto.CreditDto;
import ru.mayorov.calculator.dto.LoanOfferDto;
import ru.mayorov.calculator.dto.LoanStatementRequestDto;
import ru.mayorov.calculator.dto.ScoringDataDto;
import ru.mayorov.calculator.service.impl.OfferService;
import ru.mayorov.calculator.service.impl.ScoringService;

import java.util.List;

/**
 * An implementation of the CalculatorController interface that provides specific implementations for creating loan proposals.
 *
 * This class handles incoming HTTP requests to the /calculator endpoint.
 */
@RestController
@Slf4j
public class CalculatorControllerImpl implements CalculatorController {

    private final OfferService offerService;
    private final ScoringService scoringService;

    /**
     * Instantiates a new Calculator controller.
     *
     * @param offerService   the offer service.
     * @param scoringService the scoring service.
     */
    public CalculatorControllerImpl(OfferService offerService, ScoringService scoringService) {
        this.offerService = offerService;
        this.scoringService = scoringService;
    }

    /**
     * Processes requests to create a list of loan offers.
     *
     * @param loanStatementRequestDto the loan statement request dto.
     * @return A {ResponseEntity} object containing a list of {LoanOfferDto} objects representing the generated loan offers.
     */
    @Override
    public ResponseEntity<List<LoanOfferDto>> calculateOffers(LoanStatementRequestDto loanStatementRequestDto) {
        log.info(("Начало обработки запроса калькуляции предложения"));
        try {
            List<LoanOfferDto> offers = offerService.generateOffers(loanStatementRequestDto);
            log.info("Закончена обработка запроса калькуляции предложения");
            return new ResponseEntity<>(offers, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Произошла ошибка при обработке запроса на калькуляцию предложения");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing loan offer calculation", ex);
        }
    }

    /**
     * Handles requests to calculate the cost of a credit based on the provided scoring data.
     *
     * @param scoringDataDto the scoring data dto.
     * @return {link CreditDto} object, to information about the estimated cost of the loan.
     */
    @Override
    public CreditDto calculateCalc(ScoringDataDto scoringDataDto) {
        log.info(("Начало обработки запроса калькуляции стоимости кредита"));
        try {
            CreditDto creditDto = scoringService.scorring((scoringDataDto));
            log.info("Закончена обработка запроса калькуляции стоимости кредита");
            return new ResponseEntity<>(creditDto, HttpStatus.OK).getBody();
        }catch (Exception ex) {
            log.error("Произошла ошибка при обработке запроса на калькуляцию стоимости кредита");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing loan scoring", ex);
        }

    }
}
