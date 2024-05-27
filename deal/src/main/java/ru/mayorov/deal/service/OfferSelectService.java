package ru.mayorov.deal.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.mayorov.deal.calculatorapi.CalculatorServiceApi;
import ru.mayorov.deal.dto.LoanOfferDto;
import ru.mayorov.deal.model.Statement;
import ru.mayorov.deal.repository.ClientRepository;
import ru.mayorov.deal.repository.StatementRepository;
import ru.mayorov.deal.units.ApplicationStatusEnum;

import static java.lang.String.valueOf;

public class OfferSelectService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private StatementRepository statementRepository;
    @Autowired
    private CalculatorServiceApi calculatorServiceApi;

    public void updateStatement(LoanOfferDto loanOfferDto){

        Statement statement = statementRepository.findById(valueOf(loanOfferDto.getStatementId())).orElseThrow(() -> new RuntimeException("Statement not found"));
        statement.setStatus(ApplicationStatusEnum.APPROVED);

        //TODO добавить исторический статус, наполнить лист?
        statement.setAppliedOffer(loanOfferDto);
    }

}
