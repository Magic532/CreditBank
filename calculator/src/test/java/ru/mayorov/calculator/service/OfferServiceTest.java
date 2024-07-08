package ru.mayorov.calculator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.mayorov.calculator.dto.LoanOfferDto;
import ru.mayorov.calculator.dto.LoanStatementRequestDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OfferServiceTest {

    @Mock
    CalculateService calculateService;

    @InjectMocks
    OfferService offerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateOffers() {
        LoanStatementRequestDto request = new LoanStatementRequestDto();

        BigDecimal amount = new BigDecimal("750000");
        request.setAmount(amount);
        request.setBirthdate(LocalDate.of(1986, 7, 16));

        BigDecimal totalAmountInsFalse = new BigDecimal("750000");
        BigDecimal totalAmountInsTrue = new BigDecimal("850000");
        when(calculateService.calculateTotalAmountOffer(any(), any(), any())).thenReturn(totalAmountInsFalse, totalAmountInsFalse, totalAmountInsTrue, totalAmountInsTrue);

        BigDecimal rateInsFalseSalaryFalse = new BigDecimal("30");
        BigDecimal rateInsFalseSalaryTrue = new BigDecimal("28");
        BigDecimal rateInsTrueSalaryFalse = new BigDecimal("20");
        BigDecimal rateInsTrueSalaryTrue = new BigDecimal("18");
        when(calculateService.calculateRateOffer(any(), any(), any())).thenReturn(rateInsFalseSalaryFalse, rateInsFalseSalaryTrue, rateInsTrueSalaryFalse, rateInsTrueSalaryTrue);

        List<LoanOfferDto> loanOfferDtos = offerService.generateOffers(request);

        assertEquals(4, loanOfferDtos.size());

        assertEquals(amount, loanOfferDtos.get(0).getRequestedAmount());

        verify(calculateService, times(4)).calculateTotalAmountOffer(any(), any(), any());
        verify(calculateService, times(4)).calculateRateOffer(any(), any(), any());

        assertEquals(totalAmountInsFalse, loanOfferDtos.get(0).getTotalAmount());
        assertEquals(totalAmountInsFalse, loanOfferDtos.get(1).getTotalAmount());
        assertEquals(totalAmountInsTrue, loanOfferDtos.get(2).getTotalAmount());
        assertEquals(totalAmountInsTrue, loanOfferDtos.get(3).getTotalAmount());

        assertEquals(loanOfferDtos.get(0).getRate(), rateInsFalseSalaryFalse);
        assertEquals(loanOfferDtos.get(1).getRate(), rateInsFalseSalaryTrue);
        assertEquals(loanOfferDtos.get(2).getRate(), rateInsTrueSalaryFalse);
        assertEquals(loanOfferDtos.get(3).getRate(), rateInsTrueSalaryTrue);
    }

    @Test
    void testGenerateOfferWithNullThrowsException() {
        assertThrows(NullPointerException.class, () -> offerService.generateOffers(null));

        verify(calculateService, times(0)).calculateTotalAmountOffer(any(), any(), any());
        verify(calculateService, times(0)).calculateRateOffer(any(), any(), any());


    }
}