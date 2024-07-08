package ru.mayorov.deal.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import ru.mayorov.deal.dto.PaymentScheduleElementDto;
import ru.mayorov.deal.units.CreditStatusEnum;
import ru.mayorov.deal.сonverter.PaymentScheduleConverter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "credit")
@Setter
@Getter
public class Credit {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "credit_id", updatable = false, nullable = false)
    private UUID creditId;

    @OneToOne(mappedBy = "credit", optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "credit_id", referencedColumnName = "credit_id")
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

    @Column(name = "payment_schedule", columnDefinition = "jsonb")
    @Convert(converter = PaymentScheduleConverter.class)
    private List<PaymentScheduleElementDto> paymentSchedule;

    @Column(name = "insurance_enabled", columnDefinition = "boolean")
    private Boolean insuranceEnabled;

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
                  List<PaymentScheduleElementDto> paymentSchedule,
                  Boolean insuranceEnabled,
                  Boolean salaryClient,
                  CreditStatusEnum creditStatus
    ) {
        this.amount = amount;
        this.term = term;
        this.monthlyPayment = monthlyPayment;
        this.rate = rate;
        this.psk = psk;
        this.paymentSchedule = paymentSchedule;
        this.insuranceEnabled = insuranceEnabled;
        this.salaryClient = salaryClient;
    }
}
