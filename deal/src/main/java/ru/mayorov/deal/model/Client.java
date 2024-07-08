package ru.mayorov.deal.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import ru.mayorov.deal.сonverter.EmploymentAttributeConverter;
import ru.mayorov.deal.сonverter.PassportAttributeConverter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "client")
@Setter
@Getter
public class Client {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "client_id", updatable = false, nullable = false)
    private UUID clientId;

    @OneToOne(mappedBy = "client", optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Statement statement;

    @Column(name = "last_name", columnDefinition = "varchar")
    private String lastName;

    @Column(name = "first_name", columnDefinition = "varchar")
    private String firstName;

    @Column(name = "middle_name", columnDefinition = "varchar")
    private String middleName;

    @Column(name = "birth_date", columnDefinition = "DATE")
    private LocalDate birthDate;

    @Column(name = "email", columnDefinition = "varchar")
    private String email;

    @Column(name = "gender", columnDefinition = "varchar")
    private Gender gender;

    @Column(name = "marital_status", columnDefinition = "varchar")
    private MaritalStatus maritalStatus;

    @Column(name = "dependent_amount", columnDefinition = "int")
    private Integer dependentAmount;

    @OneToOne(optional = false)
    @JoinColumn(name = "passport_id", referencedColumnName = "passportUUID")
    @Convert(converter = PassportAttributeConverter.class)
    @Column(name = "passport_id", columnDefinition = "jsonb")
    private Passport passportId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employment_uuid", referencedColumnName = "employmentUUID")
    @Convert(converter = EmploymentAttributeConverter.class)
    @Column(name = "employment_id", columnDefinition = "jsonb")
    private Employment employmentId;

    @Column(name = "account_number", columnDefinition = "varchar")
    private String accountNumber;

    public Client() {}

    public Client(Statement statement,
                  String lastName,
                  String firstName,
                  String middleName,
                  LocalDate birthDate,
                  String email,
                  Gender gender,
                  MaritalStatus maritalStatus,
                  Integer dependentAmount,
                  Passport passportId,
                  Employment employmentId,
                  String accountNumber) {
        this.statement = statement;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.email = email;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.dependentAmount = dependentAmount;
        this.passportId = passportId;
        this.employmentId = employmentId;
        this.accountNumber = accountNumber;
    }
}
