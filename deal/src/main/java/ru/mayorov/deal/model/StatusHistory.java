package ru.mayorov.deal.model;


import jakarta.persistence.*;
import ru.mayorov.deal.сonverter.StatementStatusHistoryDtoListConverter;

@Entity
public class StatusHistory {

    @OneToOne(optional = false)
    @JoinColumn(name = "status_history")
    @Convert(converter = StatementStatusHistoryDtoListConverter.class)
    @Column(name = "status_history", columnDefinition = "jsonb")
    private StatementStatusHistoryDtoListConverter statementStatusHistoryDtoListConverter;

    public StatusHistory(StatementStatusHistoryDtoListConverter statementStatusHistoryDtoListConverter) {
        this.statementStatusHistoryDtoListConverter = statementStatusHistoryDtoListConverter;
    }

    public StatementStatusHistoryDtoListConverter getStatementStatusHistoryDtoList() {
        return statementStatusHistoryDtoListConverter;
    }

    public void setStatementStatusHistoryDtoList(StatementStatusHistoryDtoListConverter statementStatusHistoryDtoListConverter) {
        this.statementStatusHistoryDtoListConverter = statementStatusHistoryDtoListConverter;
    }
}
