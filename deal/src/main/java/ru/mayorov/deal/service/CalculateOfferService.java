package ru.mayorov.deal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mayorov.deal.calculatorapi.CalculatorServiceApi;
import ru.mayorov.deal.dto.LoanOfferDto;
import ru.mayorov.deal.dto.LoanStatementRequestDto;
import ru.mayorov.deal.model.Client;
import ru.mayorov.deal.model.Passport;
import ru.mayorov.deal.model.Statement;
import ru.mayorov.deal.repository.ClientRepository;
import ru.mayorov.deal.repository.StatementRepository;
import ru.mayorov.deal.units.ApplicationStatusEnum;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CalculateOfferService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private StatementRepository statementRepository;

    @Autowired
    private CalculatorServiceApi calculatorServiceApi;

    public List<LoanOfferDto> createdOffer(LoanStatementRequestDto loanStatementRequestDto){
        Passport passport = new Passport();
        passport.setPassportUUID(passport.getPassportUUID());
        passport.setSeries(loanStatementRequestDto.getPassportSeries());
        passport.setNumber(loanStatementRequestDto.getPassportNumber());


        Client client = new Client();
        client.setFirstName(loanStatementRequestDto.getFirstName());
        client.setLastName(loanStatementRequestDto.getLastName());
        client.setMiddleName(loanStatementRequestDto.getMiddleName());
        client.setBirthDate(loanStatementRequestDto.getBirthdate());
        client.setEmail(loanStatementRequestDto.getEmail());
        client.setPassportId(passport);

        clientRepository.save(client);

        Statement statement = new Statement();
        statement.setClient(client);
        statement.setStatus(ApplicationStatusEnum.PREAPPROVAL);
        statement.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));

        statementRepository.save(statement);

        List<LoanOfferDto> offersList = calculatorServiceApi.getOffers(loanStatementRequestDto);
        for (LoanOfferDto offer : offersList) {
            offer.setStatementId(statement.getStatementId());
        }
    return offersList;
    }
}
