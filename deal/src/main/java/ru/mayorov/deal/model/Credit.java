package ru.mayorov.deal.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.mayorov.deal.units.CreditStatusEnum;
import springfox.documentation.spring.web.json.Json;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "credit")
public class Credit {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "credit_id", updatable = false, nullable = false)
    private UUID creditId;

    @OneToOne(mappedBy = "credit", cascade = CascadeType.ALL)
    private Statement statement;

    @Column(name = "amount", columnDefinition = "decimal")
    private BigDecimal amount;

    @Column(name = "term", columnDefinition = "int")
    private Integer term;

    @Column(name = "monthly_payment", columnDefinition = "decimal")
    private BigDecimal monthlyPayment;

    @Column(name = "rate", columnDefinition = "decimal")
    private BigDecimal rate;

    @Column(name = "psk", columnDefinition = "decimal")
    private BigDecimal psk;

    @Column(name = "payment_schedule")
    private Json paymentSchedule; // todo доделать jsonb

    @Column(name = "insurence_enabled", columnDefinition = "boolean")
    private Boolean insurenceEnabled;

    @Column(name = "salary_client", columnDefinition = "boolean")
    private Boolean salaryClient;

    @Column(name = "credit_status", columnDefinition = "varchar")
    private CreditStatusEnum creditStatus;

    public Credit() {
    }

    public Credit(BigDecimal amount,
                  Integer term,
                  BigDecimal monthlyPayment,
                  BigDecimal rate,
                  BigDecimal psk,
                  Json paymentSchedule,
                  Boolean insurenceEnabled,
                  Boolean salaryClient) {
        this.amount = amount;
        this.term = term;
        this.monthlyPayment = monthlyPayment;
        this.rate = rate;
        this.psk = psk;
        this.paymentSchedule = paymentSchedule;
        this.insurenceEnabled = insurenceEnabled;
        this.salaryClient = salaryClient;
    }

    public UUID getCreditId() {
        return creditId;
    }

    public void setCreditId(UUID creditId) {
        this.creditId = creditId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public BigDecimal getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(BigDecimal monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getPsk() {
        return psk;
    }

    public void setPsk(BigDecimal psk) {
        this.psk = psk;
    }

    public Json getPaymentSchedule() {
        return paymentSchedule;
    }

    public void setPaymentSchedule(Json paymentSchedule) {
        this.paymentSchedule = paymentSchedule;
    }

    public Boolean getInsurenceEnabled() {
        return insurenceEnabled;
    }

    public void setInsurenceEnabled(Boolean insurenceEnabled) {
        this.insurenceEnabled = insurenceEnabled;
    }

    public Boolean getSalaryClient() {
        return salaryClient;
    }

    public void setSalaryClient(Boolean salaryClient) {
        this.salaryClient = salaryClient;
    }

    public CreditStatusEnum getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(CreditStatusEnum creditStatus) {
        this.creditStatus = creditStatus;
    }
}
