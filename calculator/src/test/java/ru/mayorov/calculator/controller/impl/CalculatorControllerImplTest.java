package ru.mayorov.calculator.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.mayorov.calculator.dto.*;
import ru.mayorov.calculator.service.OfferService;
import ru.mayorov.calculator.service.ScoringService;
import ru.mayorov.calculator.units.GenderEnum;
import ru.mayorov.calculator.units.MaritalStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class CalculatorControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfferService offerService;

    @MockBean
    private ScoringService scoringService;

    @InjectMocks
    private CalculatorControllerImpl calculatorController;

    @Test
    void testCalculatorOffer() throws Exception {
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

        when(offerService.generateOffers(any())).thenReturn(mockOffers);

        mockMvc.perform(post("/calculator/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanStatementRequestDto))
                        )
                .andExpect(status().isOk());

        verify(offerService, times(1)).generateOffers(any());
    }

    @Test
    void testCalculatorCalc() throws Exception {

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
        CreditDto creditDto = new CreditDto();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        when(scoringService.scorring(any())).thenReturn(creditDto);

        MvcResult mvcResult = mockMvc.perform(post("/calculator/calc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(scoringDataDto))
                )
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        String actualJsonResponse = response.getContentAsString();
        String expectedJson = objectMapper.writeValueAsString(creditDto);

        assertEquals(expectedJson, actualJsonResponse);

        verify(scoringService, times(1)).scorring(any());
    }
}