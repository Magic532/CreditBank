package ru.mayorov.calculator.controller.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.mayorov.calculator.dto.CreditDto;
import ru.mayorov.calculator.dto.LoanOfferDto;
import ru.mayorov.calculator.dto.LoanStatementRequestDto;
import ru.mayorov.calculator.dto.ScoringDataDto;
import ru.mayorov.calculator.service.impl.OfferService;
import ru.mayorov.calculator.service.impl.ScoringService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CalculatorControllerImplTest {

    @Mock
    private OfferService offerService;

    @Mock
    private ScoringService scoringService;

    @InjectMocks
    private CalculatorControllerImpl calculatorController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculatorOffer() {
        LoanStatementRequestDto loanStatementRequestDto = new LoanStatementRequestDto();
        List<LoanOfferDto> expectedOffers = new ArrayList<>();

        when(offerService.generateOffer(any())).thenReturn(expectedOffers);

        ResponseEntity<List<LoanOfferDto>> result = calculatorController.calculatorOffer(loanStatementRequestDto);
        assertEquals(expectedOffers, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());

    }

    @Test
    void calculatorCalc() {
        ScoringDataDto scoringDataDto = new ScoringDataDto();
        CreditDto expectedCreditDto = new CreditDto();

        when(scoringService.scorring(any())).thenReturn(expectedCreditDto);

        CreditDto result = calculatorController.calculatorCalc(scoringDataDto);
        assertEquals(expectedCreditDto, result);
    }
}