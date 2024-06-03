package ru.mayorov.calculator.controller.impl;

import lombok.RequiredArgsConstructor;
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

@RestController
@Slf4j
public class CalculatorControllerImpl implements CalculatorController {

    private final OfferService offerService;
    private final ScoringService scoringService;

    public CalculatorControllerImpl(OfferService offerService, ScoringService scoringService) {
        this.offerService = offerService;
        this.scoringService = scoringService;
    }

    @Override
    public ResponseEntity<List<LoanOfferDto>> calculatorOffer(LoanStatementRequestDto loanStatementRequestDto) {
        log.info(("Начало обработки запроса калькуляции предложения"));
        try {
            List<LoanOfferDto> offers = offerService.generateOffer(loanStatementRequestDto);
            log.info("Закончена обработка запроса калькуляции предложения");
            return new ResponseEntity<>(offers, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Произошла ошибка при обработке запроса на калькуляцию предложения");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing loan offer calculation", ex);
        }
    }

    @Override
    public CreditDto calculatorCalc(ScoringDataDto scoringDataDto) {
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
