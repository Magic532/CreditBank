package ru.mayorov.deal.controller.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.mayorov.deal.dto.LoanStatementRequestDto;
import ru.mayorov.deal.service.CalculateCalcService;
import ru.mayorov.deal.service.CalculateOfferService;
import ru.mayorov.deal.service.OfferSelectService;

class DealControllerImplTest {

    @Mock
    private CalculateCalcService calculateCalcService;

    @Mock
    private CalculateOfferService calculateOfferService;

    @Mock
    private OfferSelectService offerSelectService;

    @InjectMocks
    private DealControllerImpl dealController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void calculateOffer() {
        LoanStatementRequestDto loanStatementRequestDto = new LoanStatementRequestDto();

    }

    @Test
    void offerSelect() {
    }

    @Test
    void calculateCalc() {
    }
}