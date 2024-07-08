package ru.mayorov.deal.service;

import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mayorov.deal.calculatorapi.CalculatorServiceApi;
import ru.mayorov.deal.dto.LoanOfferDto;
import ru.mayorov.deal.dto.StatementStatusHistoryDto;
import ru.mayorov.deal.model.Statement;
import ru.mayorov.deal.repository.StatementRepository;
import ru.mayorov.deal.units.ApplicationStatusEnum;
import ru.mayorov.deal.units.ChangeTypeEnum;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

@Service
@Slf4j
@RequiredArgsConstructor
public class OfferSelectService {

    private final StatementRepository statementRepository;

    @Transactional
    public void updateStatement(LoanOfferDto loanOfferDto){
        log.info("Начинается получение из БД сущности statement");
        Statement statement = statementRepository.findById(Long.parseLong(valueOf(loanOfferDto.getStatementId())))
                .orElseThrow(() -> new RuntimeException("Statement not found"));

        log.info("Начинается наполнение данными сущности statement");
        statement.setStatus(ApplicationStatusEnum.PREAPPROVAL);

        StatementStatusHistoryDto statementStatusHistoryDto = new StatementStatusHistoryDto(
                ApplicationStatusEnum.PREAPPROVAL,
                new Timestamp(System.currentTimeMillis()),
                ChangeTypeEnum.AUTOMATIC
        );

        List<StatementStatusHistoryDto> statementStatusHistoryDtoList = new ArrayList<>();

        statementStatusHistoryDtoList.add(statementStatusHistoryDto);

        statement.setStatusHistory(statementStatusHistoryDtoList);

        statement.setAppliedOffer(loanOfferDto);

        log.info("Начинается сохранение в БД сущности statement");
        statementRepository.save(statement);
    }
}
