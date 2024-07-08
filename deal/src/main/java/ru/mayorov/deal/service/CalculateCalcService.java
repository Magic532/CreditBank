package ru.mayorov.deal.service;


import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ru.mayorov.deal.calculatorapi.CalculatorServiceApi;
import ru.mayorov.deal.dto.CreditDto;
import ru.mayorov.deal.dto.FinishRegistrationRequestDto;
import ru.mayorov.deal.dto.ScoringDataDto;
import ru.mayorov.deal.dto.StatementStatusHistoryDto;
import ru.mayorov.deal.model.*;
import ru.mayorov.deal.repository.CreditRepository;
import ru.mayorov.deal.repository.StatementRepository;
import ru.mayorov.deal.units.ApplicationStatusEnum;
import ru.mayorov.deal.units.ChangeTypeEnum;
import ru.mayorov.deal.units.CreditStatusEnum;

import java.sql.Timestamp;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class CalculateCalcService {

    private final StatementRepository statementRepository;

    private final CreditRepository creditRepository;

    private final CalculatorServiceApi calculatorServiceApi;

    private final CreditStatus creditStatus;

    private final ApplicationStatus applicationStatus;

    @Transactional
    public CalculateCalcService loanApproval(FinishRegistrationRequestDto finishRegistrationRequestDto, String statementId){
        log.info("Начинается получение из БД сущности statement");
        Statement statement = statementRepository.findById(Long.parseLong(statementId))
                .orElseThrow(() -> new RuntimeException("Statement not found"));

        Client client = statement.getClient();

        log.info("Начинается наполнение данными ScoringDataDto для предложения {}", statement.getAppliedOffer());
        ScoringDataDto scoringDataDto = new ScoringDataDto(
                statement.getAppliedOffer().getRequestedAmount(),
                statement.getAppliedOffer().getTerm(),
                client.getFirstName(),
                client.getLastName(),
                client.getMiddleName(),
                finishRegistrationRequestDto.getGender(),
                client.getBirthDate(),
                client.getPassportId().getSeries(),
                client.getPassportId().getNumber(),
                finishRegistrationRequestDto.getPassportIssueDate(),
                finishRegistrationRequestDto.getPassportIssueBranch(),
                finishRegistrationRequestDto.getMaritalStatus(),
                client.getDependentAmount(),
                finishRegistrationRequestDto.getEmployment(),
                finishRegistrationRequestDto.getAccountNumber(),
                statement.getAppliedOffer().getInsuranceEnabled(),
                statement.getAppliedOffer().getIsSalaryClient()
        );

        log.info("Начинается вызов метода одобрения кредита");
        CreditDto creditDto = calculatorServiceApi.getCalc(scoringDataDto);

        creditStatus.setCreditStatusEnum(CreditStatusEnum.CALCULATED);

        log.info("Начинается наполнение данными сущности credit");
        Credit credit = new Credit(
                creditDto.getAmount(),
                creditDto.getTerm(),
                creditDto.getMonthlyPayment(),
                creditDto.getRate(),
                creditDto.getPsk(),
                creditDto.getPaymentSchedule(),
                creditDto.getInsuranceEnabled(),
                creditDto.getSalaryClient(),
                creditStatus.getCreditStatusEnum()
            );

        log.info("Начинается сохранение в базу данных сущности credit");
        creditRepository.save(credit);

        log.info("Начинается наполнение данными сущности statement");
        applicationStatus.setApplicationStatusEnum(ApplicationStatusEnum.APPROVED);

        List<StatementStatusHistoryDto> updatedStatusHistory = statement.getStatusHistory();

        updatedStatusHistory.add(new StatementStatusHistoryDto(
                ApplicationStatusEnum.APPROVED,
                new Timestamp(System.currentTimeMillis()),
                ChangeTypeEnum.AUTOMATIC));

        statement.setStatusHistory(updatedStatusHistory);

        log.info("Начинается сохранение в базу данных сущности statement");
        statementRepository.save(statement);

        log.info("Завершено одобрение кредита сущности statement и credit сохранены в БД");
        return null;
    }
}
