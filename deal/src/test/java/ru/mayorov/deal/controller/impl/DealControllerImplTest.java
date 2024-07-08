package ru.mayorov.deal.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.mayorov.deal.dto.*;
import ru.mayorov.deal.service.CalculateCalcService;
import ru.mayorov.deal.service.CalculateOfferService;
import ru.mayorov.deal.service.OfferSelectService;
import ru.mayorov.deal.units.GenderEnum;
import ru.mayorov.deal.units.MaritalStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class DealControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalculateCalcService calculateCalcService;

    @MockBean
    private CalculateOfferService calculateOfferService;

    @MockBean
    private OfferSelectService offerSelectService;

    @InjectMocks
    private DealControllerImpl dealController;

    @Test
    void calculateOffer() throws Exception{
        LoanStatementRequestDto loanStatementRequestDto = new LoanStatementRequestDto(
                BigDecimal.valueOf(350000),
                60,
                "Иван",
                "Иванович",
                "Иванов",
                "ivanovii@email.com",
                LocalDate.of(2000, 1, 1),
                "6300",
                "000000"
        );

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        List<LoanOfferDto> mockOffers = new ArrayList<>();

        when(calculateOfferService.createdOffer(any())).thenReturn(mockOffers);

        mockMvc.perform(post("/calculator/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanStatementRequestDto))
                )
                .andExpect(status().isOk());

        verify(calculateOfferService, times(1)).createdOffer(any());
    }


    @Test
    void offerSelect() throws Exception{
        LoanOfferDto loanOfferDto = new LoanOfferDto(
                UUID.fromString("1d1466f0-9033-47bf-93ca-d08c40d6796a"),
                BigDecimal.valueOf(100000),
                BigDecimal.valueOf(110000),
                60,
                BigDecimal.valueOf(3500),
                BigDecimal.valueOf(0.15),
                true,
                false
        );

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc.perform(post("/offer/select")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanOfferDto))
                )
                .andExpect(status().isOk());

        verify(offerSelectService, times(1)).updateStatement(any());
    }

    @Test
    void calculateCalc() throws Exception{
        ScoringDataDto scoringDataDto = new ScoringDataDto();
        scoringDataDto.setAmount(BigDecimal.valueOf(350000));
        scoringDataDto.setTerm(60);
        scoringDataDto.setFirstName("Иван");
        scoringDataDto.setLastName("Иванов");
        scoringDataDto.setMiddleName("Иванович");
        scoringDataDto.setGender(GenderEnum.MALE);
        scoringDataDto.setBirthdate(LocalDate.of(2000, 1, 1));
        scoringDataDto.setPassportSeries("6300");
        scoringDataDto.setPassportNumber("000000");
        scoringDataDto.setPassportIssueDate(LocalDate.now());
        scoringDataDto.setPassportIssueBranch("Example Branch");
        scoringDataDto.setMaritalStatus(MaritalStatusEnum.SINGLE);
        scoringDataDto.setDependentAmount(0);
        scoringDataDto.setEmployment(new EmploymentDto());
        scoringDataDto.setAccountNumber("12345678901234567890");
        scoringDataDto.setIsInsuranceEnabled(true);
        scoringDataDto.setIsSalaryClient(true);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc.perform(post("/calculate/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(scoringDataDto))
                )
                .andExpect(status().isOk());

        verify(calculateCalcService, times(1)).loanApproval(any(), any());
    }
}
