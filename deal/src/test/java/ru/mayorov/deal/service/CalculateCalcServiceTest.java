package ru.mayorov.deal.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.mayorov.deal.calculatorapi.CalculatorServiceApi;
import ru.mayorov.deal.dto.CreditDto;
import ru.mayorov.deal.dto.EmploymentDto;
import ru.mayorov.deal.dto.FinishRegistrationRequestDto;
import ru.mayorov.deal.dto.ScoringDataDto;
import ru.mayorov.deal.model.Credit;
import ru.mayorov.deal.model.Statement;
import ru.mayorov.deal.repository.CreditRepository;
import ru.mayorov.deal.repository.StatementRepository;
import ru.mayorov.deal.units.GenderEnum;
import ru.mayorov.deal.units.MaritalStatusEnum;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CalculateCalcServiceTest {
    @InjectMocks
    private CalculateCalcService calculateCalcService;

    @MockBean
    private StatementRepository statementRepository;

    @MockBean
    private CreditRepository creditRepository;

    @MockBean
    private CalculatorServiceApi calculatorServiceApi;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    public void loanApproval(){

        FinishRegistrationRequestDto finishRegistrationRequestDto = new FinishRegistrationRequestDto(
                GenderEnum.MALE,
                MaritalStatusEnum.SINGLE,
                0,
                LocalDate.of(2010, 7, 16),
                "Отделом",
                new EmploymentDto(),
                "40817810500000000000"
        );

        String statementId = "1d1466f0-9033-47bf-93ca-d08c40d6796a";

//        when(statementRepository.findById(anyString())).thenReturn(Optional.of(new Statement()));

        ScoringDataDto scoringDataDto = new ScoringDataDto();

        CreditDto expectedCreditDto = new CreditDto();

        when(calculatorServiceApi.getCalc(scoringDataDto)).thenReturn(expectedCreditDto);

        calculateCalcService.loanApproval(finishRegistrationRequestDto, statementId);

        verify(calculatorServiceApi, times(1)).getCalc(scoringDataDto);
        verify(creditRepository, times(1)).save(any(Credit.class));
        verify(statementRepository, times(1)).save(any(Statement.class));
    }
}