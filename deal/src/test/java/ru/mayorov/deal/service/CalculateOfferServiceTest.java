package ru.mayorov.deal.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.mayorov.deal.calculatorapi.CalculatorServiceApi;
import ru.mayorov.deal.dto.LoanOfferDto;
import ru.mayorov.deal.dto.LoanStatementRequestDto;
import ru.mayorov.deal.model.Client;
import ru.mayorov.deal.model.Credit;
import ru.mayorov.deal.model.Statement;
import ru.mayorov.deal.repository.ClientRepository;
import ru.mayorov.deal.repository.StatementRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CalculateOfferServiceTest {

    @InjectMocks
    private CalculateOfferService calculateOfferService;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private StatementRepository statementRepository;

    @MockBean
    private CalculatorServiceApi calculatorServiceApi;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createdOffer() {
        LoanStatementRequestDto loanStatementRequestDto = new LoanStatementRequestDto();
        BigDecimal amount = new BigDecimal("750000");
        loanStatementRequestDto.setAmount(amount);
        loanStatementRequestDto.setBirthdate(LocalDate.of(1986, 7, 16));

        List<LoanOfferDto> expectedOffersList = Arrays.asList(new LoanOfferDto(), new LoanOfferDto(), new LoanOfferDto(), new LoanOfferDto());
        when(calculatorServiceApi.getOffers(loanStatementRequestDto)).thenReturn(expectedOffersList);

        List<LoanOfferDto> result = calculateOfferService.createdOffer(loanStatementRequestDto);

        verify(calculatorServiceApi, times(1)).getOffers(loanStatementRequestDto);
        verify(clientRepository, times(1)).save(any(Client.class));
        verify(statementRepository, times(1)).save(any(Statement.class));

        assertEquals(expectedOffersList.size(), result.size());
        for (int i = 0; i < expectedOffersList.size(); i++) {
            assertEquals(expectedOffersList.get(i), result.get(i));
        }
    }
}