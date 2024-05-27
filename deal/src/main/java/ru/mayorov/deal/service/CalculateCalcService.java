package ru.mayorov.deal.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.mayorov.deal.calculatorapi.CalculatorServiceApi;
import ru.mayorov.deal.dto.CreditDto;
import ru.mayorov.deal.dto.FinishRegistrationRequestDto;
import ru.mayorov.deal.dto.ScoringDataDto;
import ru.mayorov.deal.model.Client;
import ru.mayorov.deal.model.Credit;
import ru.mayorov.deal.model.CreditStatus;
import ru.mayorov.deal.model.Statement;
import ru.mayorov.deal.repository.ClientRepository;
import ru.mayorov.deal.repository.CreditRepository;
import ru.mayorov.deal.repository.StatementRepository;
import ru.mayorov.deal.units.CreditStatusEnum;


public class CalculateCalcService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private StatementRepository statementRepository;

    @Autowired
    private CreditRepository creditRepository;
    @Autowired
    private CalculatorServiceApi calculatorServiceApi;

    public CalculateCalcService loanApproval(FinishRegistrationRequestDto finishRegistrationRequestDto, String statementId){
        Statement statement = statementRepository.findById(statementId).orElseThrow(() -> new RuntimeException("Statement not found"));

        Client client = statement.getClient();

        ScoringDataDto scoringDataDto = new ScoringDataDto(statement.getAppliedOffer().getRequestedAmount(),
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

        CreditDto creditDto = calculatorServiceApi.getCalc(scoringDataDto);

        Credit credit = new Credit(creditDto.getAmount(),
                creditDto.getTerm(),
                creditDto.getMonthlyPayment(),
                creditDto.getRate(),
                creditDto.getPsk(),
                creditDto.getPaymentSchedule(),
                creditDto.getInsuranceEnabled(),
                creditDto.getSalaryClient()
            );

        //TODO добавить исторический статус, наполнить лист?

        //creditRepository.save(credit);

        return null;
    }
}
