package ru.mayorov.deal.service;


import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
public class CalculateOfferService {

    private final ClientRepository clientRepository;

    private final StatementRepository statementRepository;

    private final CalculatorServiceApi calculatorServiceApi;

    @Transactional
    public List<LoanOfferDto> createdOffer(LoanStatementRequestDto loanStatementRequestDto){

        log.info("Начинается наполнение сущности passport");
        Passport passport = new Passport();
        passport.setPassportUUID(passport.getPassportUUID());
        passport.setSeries(loanStatementRequestDto.getPassportSeries());
        passport.setNumber(loanStatementRequestDto.getPassportNumber());

        log.info("Начинается наполнение сущности client");
        Client client = new Client();
        client.setFirstName(loanStatementRequestDto.getFirstName());
        client.setLastName(loanStatementRequestDto.getLastName());
        client.setMiddleName(loanStatementRequestDto.getMiddleName());
        client.setBirthDate(loanStatementRequestDto.getBirthdate());
        client.setEmail(loanStatementRequestDto.getEmail());
        client.setPassportId(passport);

        log.info("Начинается сохранение в БД сущности client");
        clientRepository.save(client);

        log.info("Начинается наполнение сущности statement");
        Statement statement = new Statement();
        statement.setClient(client);
        statement.setStatus(ApplicationStatusEnum.PREAPPROVAL);
        statement.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));

        log.info("Начинается сохранение в БД сущности statement");
        statementRepository.save(statement);

        log.info("Начинается формирование списка предложений клиенту по запросу {}", loanStatementRequestDto);
        List<LoanOfferDto> offersList = calculatorServiceApi.getOffers(loanStatementRequestDto);
        for (LoanOfferDto offer : offersList) {
            offer.setStatementId(statement.getStatementId());
        }
        log.info("Завершено формирования списка предложений");
    return offersList;
    }
}
