package ru.mayorov.deal.controller.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.mayorov.deal.controller.DealController;
import ru.mayorov.deal.dto.FinishRegistrationRequestDto;
import ru.mayorov.deal.dto.LoanOfferDto;
import ru.mayorov.deal.dto.LoanStatementRequestDto;
import ru.mayorov.deal.service.CalculateCalcService;
import ru.mayorov.deal.service.CalculateOfferService;
import ru.mayorov.deal.service.OfferSelectService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DealControllerImpl implements DealController {

    private final CalculateCalcService calculateCalcService;

    private final CalculateOfferService calculateOfferService;

    private final OfferSelectService offerSelectService;


    @Override
    public ResponseEntity<List<LoanOfferDto>> calculateOffer(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("Начало создания предложения для клиента и сохранения данных.");
        try {
            List<LoanOfferDto> offers = calculateOfferService.createdOffer(loanStatementRequestDto);
            log.info("Закончено формирование и сохранение данных.");
            return new ResponseEntity<>(offers, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Произошла ошибка создания предложения для клиента и сохранения данных.");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating offer", e);
        }
    }

    @Override
    public void offerSelect(LoanOfferDto loanOfferDto) {
        try {
            log.info("Начало выбор предложения из списка, сохранение заявки");
            offerSelectService.updateStatement(loanOfferDto);
            log.info("Закончен выбор предложения из списка и сохранение заявки");
        }catch (Exception e) {
            log.error("Произошла ошибка выбора предложения из списка и сохранения заявки");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving application", e);
        }
    }

    @Override
    public void calculateCalc(FinishRegistrationRequestDto finishRegistrationRequestDto, String statementId) {
        try {
            log.info("Начало рассмотрения и сохранения заявки");
            calculateCalcService.loanApproval(finishRegistrationRequestDto, statementId);
            log.info("Закончено рассмотрение и сохранение заявки");
        }catch (Exception e) {
            log.error("Произошла ошибка рассмотрения и сохранения заявки");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Loan approval error", e);
        }
    }
}
