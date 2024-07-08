package ru.mayorov.deal.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.mayorov.deal.calculatorapi.CalculatorServiceApi;
import ru.mayorov.deal.dto.LoanOfferDto;
import ru.mayorov.deal.model.Statement;
import ru.mayorov.deal.repository.ClientRepository;
import ru.mayorov.deal.repository.StatementRepository;
import ru.mayorov.deal.units.ApplicationStatusEnum;

import java.util.Optional;

import static java.lang.String.valueOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.postgresql.hostchooser.HostRequirement.any;

@SpringBootTest
class OfferSelectServiceTest {

    @InjectMocks
    private OfferSelectService offerSelectService;

    @MockBean
    private StatementRepository statementRepository;

    @MockBean
    private CalculatorServiceApi calculatorServiceApi;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateStatement() {
        LoanOfferDto loanOfferDto = new LoanOfferDto();

       // when(statementRepository.findById(any)).thenReturn(Optional.of(new Statement()));

        offerSelectService.updateStatement(loanOfferDto);

//        verify(statementRepository, times(1)).findById(valueOf(loanOfferDto.getStatementId()));
        verify(statementRepository, times(1)).save(any(Statement.class));
    }
}