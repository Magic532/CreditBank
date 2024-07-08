package ru.mayorov.deal.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import ru.mayorov.deal.dto.LoanOfferDto;
import ru.mayorov.deal.dto.StatementStatusHistoryDto;
import ru.mayorov.deal.units.ApplicationStatusEnum;
import ru.mayorov.deal.сonverter.LoanOfferDtoConverter;
import ru.mayorov.deal.сonverter.StatementStatusHistoryDtoListConverter;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "statement")
@Getter
@Setter
public class Statement {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "statement_id", updatable = false, nullable = false)
    private UUID statementId;

    @OneToOne(mappedBy = "statement", optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client client;

    @OneToOne(mappedBy = "statement", optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "credit_id", referencedColumnName = "credit_id")
    private Credit credit;

    @Column(name = "status", columnDefinition = "varchar")
    private ApplicationStatusEnum status;

    @Column(name = "creation_date", columnDefinition = "TIMESTAMP")
    private Timestamp creationDate;

    @Column(name = "applied_offer", columnDefinition = "jsonb")
    @Convert(converter = LoanOfferDtoConverter.class)
    private LoanOfferDto appliedOffer;

    @Column(name = "sign_date", columnDefinition = "TIMESTAMP")
    private Timestamp signDate;

    @Column(name = "ses_code", columnDefinition = "varchar")
    private String sesCode;

    @Column(name = "status_history", columnDefinition = "jsonb")
    @Convert(converter = StatementStatusHistoryDtoListConverter.class)
    private List<StatementStatusHistoryDto> statusHistory;

    public Statement() {
    }

    public Statement(UUID statementId, Client client, Credit credit, ApplicationStatusEnum status, Timestamp creationDate, LoanOfferDto appliedOffer, Timestamp signDate, String sesCode, List<StatementStatusHistoryDto> statusHistory) {
        this.statementId = statementId;
        this.client = client;
        this.credit = credit;
        this.status = status;
        this.creationDate = creationDate;
        this.appliedOffer = appliedOffer;
        this.signDate = signDate;
        this.sesCode = sesCode;
        this.statusHistory = statusHistory;
    }
}
