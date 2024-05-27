package ru.mayorov.calculator.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.mayorov.calculator.dto.CreditDto;
import ru.mayorov.calculator.dto.ScoringDataDto;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ScoringServiceTest {
    @Mock
    CalculateService calculateService;

    @InjectMocks
    ScoringService scoringService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void scoring() {
        ScoringDataDto request = new ScoringDataDto();
        request.setAmount(new BigDecimal("750000"));
        request.setBirthdate(LocalDate.of(1986, 7, 16));

        BigDecimal totalAmount = new BigDecimal("750000");
        BigDecimal rate = new BigDecimal("25");
        Integer term = 60;
        BigDecimal monthlyPayment = new BigDecimal("20000");
        BigDecimal psk = new BigDecimal("500000");

        when(calculateService.calculateAmountScoring(any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(totalAmount);
        when(calculateService.calculateRateScoring(any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(rate);
        when(calculateService.calculateTermScoring(any(), any(), any(), any())).thenReturn(term);
        when(calculateService.calculateMonthlyPayment(any(), any(), any())).thenReturn(monthlyPayment);
        when(calculateService.calculatePsk(any(), any(), any())).thenReturn(psk);

        CreditDto creditDto = new CreditDto();
        creditDto.setAmount(totalAmount);

        assertEquals(request.getAmount(), creditDto.getAmount());
    }

    @Test
    void testGenerateOfferWithNullThrowsException() {

        assertThrows(NullPointerException.class, () -> scoringService.scorring(null));

        verify(calculateService, times(0)).calculateAmountScoring(any(), any(), any(), any(), any(), any(), any(), any());
        verify(calculateService, times(0)).calculateRateScoring(any(), any(), any(), any(), any(), any(), any(), any());
        verify(calculateService, times(0)).calculateTermScoring(any(), any(), any(), any());
        verify(calculateService, times(0)).calculateMonthlyPayment(any(), any(), any());
        verify(calculateService, times(0)).calculatePsk(any(), any(), any());
    }
}