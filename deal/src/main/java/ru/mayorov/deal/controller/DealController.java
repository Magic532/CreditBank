package ru.mayorov.deal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mayorov.deal.dto.FinishRegistrationRequestDto;
import ru.mayorov.deal.dto.LoanOfferDto;
import ru.mayorov.deal.dto.LoanStatementRequestDto;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/deal")
@Validated
public interface DealController {

    @PostMapping(value = "/statement")
    public ResponseEntity<List<LoanOfferDto>> calculateOffer(@RequestBody @Valid LoanStatementRequestDto loanStatementRequestDto);

    @PostMapping(value = "/offer/select")
    public void offerSelect(@RequestBody @Valid LoanOfferDto loanOfferDto);

    @PostMapping(value = "/calculate/{statementId}")
    public  void calculateCalc (FinishRegistrationRequestDto finishRegistrationRequestDto, String statementId);

}
