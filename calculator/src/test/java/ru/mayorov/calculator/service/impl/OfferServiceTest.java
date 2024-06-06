package ru.mayorov.calculator.service.impl;

import lombok.extern.slf4j.Slf4j;
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

import static org.assertj.core.api.Assertions.assertThat;
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
    void generateOffer() {
        LoanStatementRequestDto request = new LoanStatementRequestDto();

        BigDecimal amount = new BigDecimal("750000");
        request = LoanStatementRequestDto.builder().amount(amount).birthdate(LocalDate.of(1986, 7, 16)).build();

        BigDecimal totalAmountInsFalse = new BigDecimal("750000");
        BigDecimal totalAmountInsTrue = new BigDecimal("850000");
        when(calculateService.calculateTotalAmountOffer(any(), any(), any())).thenReturn(totalAmountInsFalse, totalAmountInsFalse, totalAmountInsTrue, totalAmountInsTrue);

        BigDecimal rateInsFalseSalaryFalse = new BigDecimal("30");
        BigDecimal rateInsFalseSalaryTrue = new BigDecimal("28");
        BigDecimal rateInsTrueSalaryFalse = new BigDecimal("20");
        BigDecimal rateInsTrueSalaryTrue = new BigDecimal("18");
        when(calculateService.calculateRateOffer(any(), any(), any())).thenReturn(rateInsFalseSalaryFalse, rateInsFalseSalaryTrue, rateInsTrueSalaryFalse, rateInsTrueSalaryTrue);

        List<LoanOfferDto> loanOfferDtos = offerService.generateOffer(request);

        assertEquals(4, loanOfferDtos.size());

        assertThat(loanOfferDtos.get(0).getRequestedAmount()).isEqualByComparingTo(amount);

        verify(calculateService, times(4)).calculateTotalAmountOffer(any(), any(), any());
        verify(calculateService, times(4)).calculateRateOffer(any(), any(), any());

        assertThat(loanOfferDtos.get(0).getTotalAmount()).isEqualByComparingTo(totalAmountInsFalse);
        assertThat(loanOfferDtos.get(1).getTotalAmount()).isEqualByComparingTo(totalAmountInsFalse);
        assertThat(loanOfferDtos.get(2).getTotalAmount()).isEqualByComparingTo(totalAmountInsTrue);
        assertThat(loanOfferDtos.get(3).getTotalAmount()).isEqualByComparingTo(totalAmountInsTrue);

        assertThat(loanOfferDtos.get(0).getRate()).isEqualByComparingTo(rateInsFalseSalaryFalse);
        assertThat(loanOfferDtos.get(1).getRate()).isEqualByComparingTo(rateInsFalseSalaryTrue);
        assertThat(loanOfferDtos.get(2).getRate()).isEqualByComparingTo(rateInsTrueSalaryFalse);
        assertThat(loanOfferDtos.get(3).getRate()).isEqualByComparingTo(rateInsTrueSalaryTrue);
    }

    @Test
    void testGenerateOfferWithNullThrowsException() {
        assertThrows(NullPointerException.class, () -> offerService.generateOffer(null));

        verify(calculateService, times(0)).calculateTotalAmountOffer(any(), any(), any());
        verify(calculateService, times(0)).calculateRateOffer(any(), any(), any());


    }
}