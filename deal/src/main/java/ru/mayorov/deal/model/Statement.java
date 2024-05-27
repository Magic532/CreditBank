package ru.mayorov.deal.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.mayorov.deal.dto.LoanOfferDto;
import ru.mayorov.deal.units.ApplicationStatusEnum;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "statement")
public class Statement {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "statement_id", updatable = false, nullable = false)
    private UUID statementId;

    @OneToOne(mappedBy = "statement", optional = false)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client client;

    @OneToOne(mappedBy = "statement", optional = false)
    @JoinColumn(name = "credit_id", referencedColumnName = "credit_id")
    private Credit credit;

    @Column(name = "status", columnDefinition = "varchar")
    private ApplicationStatusEnum status;

    @Column(name = "creation_date", columnDefinition = "TIMESTAMP")
    private Timestamp creationDate;

    @Column(name = "applied_offer", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private LoanOfferDto appliedOffer;

    @Column(name = "sign_date", columnDefinition = "TIMESTAMP")
    private Timestamp signDate;

    @Column(name = "ses_code", columnDefinition = "varchar")
    private String sesCode;

    @Column(name = "status_history", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private StatusHistory statusHistory;

    public UUID getStatementId() {
        return statementId;
    }

    public void setStatementId(UUID statementId) {
        this.statementId = statementId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public ApplicationStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatusEnum status) {
        this.status = status;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public LoanOfferDto getAppliedOffer() {
        return appliedOffer;
    }

    public void setAppliedOffer(LoanOfferDto appliedOffer) {
        this.appliedOffer = appliedOffer;
    }

    public Timestamp getSignDate() {
        return signDate;
    }

    public void setSignDate(Timestamp signDate) {
        this.signDate = signDate;
    }

    public String getSesCode() {
        return sesCode;
    }

    public void setSesCode(String sesCode) {
        this.sesCode = sesCode;
    }

    public StatusHistory getStatusHistory() {
        return statusHistory;
    }

    public void setStatusHistory(StatusHistory statusHistory) {
        this.statusHistory = statusHistory;
    }
}
