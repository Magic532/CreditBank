package ru.mayorov.deal.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.mayorov.deal.сonverter.PassportAttributeConverter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "client_id", updatable = false, nullable = false)
    private UUID clientId;

    @OneToOne(mappedBy = "statement", optional = false)
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
    @Column(name = "passport_id")
    private Passport passportId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employment_uuid", referencedColumnName = "employmentUUID")
    @Column(name = "employment_id", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Employment employmentId;

    @Column(name = "account_number", columnDefinition = "varchar")
    private String accountNumber;

    public Client() {}

    public Client(Statement statement, String lastName, String firstName, String middleName, LocalDate birthDate, String email, Gender gender, MaritalStatus maritalStatus, Integer dependentAmount, Passport passportId, Employment employmentId, String accountNumber) {
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

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Integer getDependentAmount() {
        return dependentAmount;
    }

    public void setDependentAmount(Integer dependentAmount) {
        this.dependentAmount = dependentAmount;
    }

    public Passport getPassportId() {
        return passportId;
    }

    public void setPassportId(Passport passportId) {
        this.passportId = passportId;
    }

    public Employment getEmploymentId() {
        return employmentId;
    }

    public void setEmploymentId(Employment employmentId) {
        this.employmentId = employmentId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
