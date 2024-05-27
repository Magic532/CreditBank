package ru.mayorov.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.mayorov.calculator.units.GenderEnum;
import ru.mayorov.calculator.units.MaritalStatusEnum;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) for loan approval.
 */
@Data
@Schema(description = "Сущность данные для одобрения кредита")
public class ScoringDataDto {

    @Schema(description = "Запрашиваемая сумма кредита")
    @Min(value = 30000, message = "Сумма кредита не может быть меньше 30 000 руб.")
    @Max(value = 5000000, message = "Сумма кредита не может быть больше 5 000 000 руб.")
    private BigDecimal amount;

    @Schema(description = "Запрашиваемый срок кредита")
    @Min(value = 6, message = "Срок кредита не может быть меньше 6 месяцев")
    @Max(value = 60, message = "Срок кредита не может быть больше 60 месяцев")
    private Integer term;

    @Schema(description = "Имя", example = "Иван")
    @NotBlank(message = "Не указано имя клиента")
    @Size(min = 2, max = 30, message = "Имя не может быть меньше 2 или более 30 букв")
    private String firstName;

    @Schema(description = "Фамилия", example = "Иванов")
    @NotBlank(message = "Не указана фамилия клиента")
    @Size(min = 2, max = 30, message = "Фамилия не может быть меньше 2 или более 30 букв")
    private String lastName;

    @Schema(description = "Отчество", example = "Иванович")
    @Size(min = 2, max = 30, message = "Отчество не может быть меньше 2 или более 30 букв")
    private String middleName;

    @Schema(description = "Пол")
    @NotNull
    private GenderEnum gender;

    @Schema(description = "Дата рождения")
    @NotNull
    private LocalDate birthdate;

    @Schema(description = "Серия паспорта", example = "6300")
    @NotBlank
    @Size(min = 4, max = 4, message = "Не верно указана серия паспорта")
    private String passportSeries;

    @Schema(description = "Номер паспорта", example = "000000")
    @NotBlank
    @Size(min = 6, max = 6, message = "Не верно указан номер паспорта")
    private String passportNumber;

    @Schema(description = "Дата выдачи паспорта")
    @NotNull
    private LocalDate passportIssueDate;

    @Schema(description = "Место выдачи паспорта")
    @NotBlank
    private String passportIssueBranch;

    @Schema(description = "Семейное положение")
    @NotNull
    private MaritalStatusEnum maritalStatus;

    @Schema(description = "Количество иждевенцев")
    @Min(value = 0, message = "Не указано количество детей клиента")
    private Integer dependentAmount;

    @Schema(description = "Место работы")
    @NotNull
    private EmploymentDto employment;

    @Schema(description = "Номер счета")
    @NotBlank
    private String accountNumber;

    @Schema(description = "Подключено ли страхование")
    @NotNull
    private Boolean isInsuranceEnabled;

    @Schema(description = "Является ли зарплатным клиентом")
    @NotNull
    private Boolean isSalaryClient;

    public ScoringDataDto(){}

    public ScoringDataDto(BigDecimal amount,
                          Integer term,
                          String firstName,
                          String lastName,
                          String middleName,
                          GenderEnum gender,
                          LocalDate birthdate,
                          String passportSeries,
                          String passportNumber,
                          LocalDate passportIssueDate,
                          String passportIssueBranch,
                          MaritalStatusEnum maritalStatus,
                          Integer dependentAmount,
                          EmploymentDto employment,
                          String accountNumber,
                          Boolean isInsuranceEnabled,
                          Boolean isSalaryClient) {
        this.amount = amount;
        this.term = term;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.passportSeries = passportSeries;
        this.passportNumber = passportNumber;
        this.passportIssueDate = passportIssueDate;
        this.passportIssueBranch = passportIssueBranch;
        this.maritalStatus = maritalStatus;
        this.dependentAmount = dependentAmount;
        this.employment = employment;
        this.accountNumber = accountNumber;
        this.isInsuranceEnabled = isInsuranceEnabled;
        this.isSalaryClient = isSalaryClient;
    }

    public ScoringDataDto(BigDecimal bigDecimal, LocalDate of) {
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getPassportSeries() {
        return passportSeries;
    }

    public void setPassportSeries(String passportSeries) {
        this.passportSeries = passportSeries;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public LocalDate getPassportIssueDate() {
        return passportIssueDate;
    }

    public void setPassportIssueDate(LocalDate passportIssueDate) {
        this.passportIssueDate = passportIssueDate;
    }

    public String getPassportIssueBranch() {
        return passportIssueBranch;
    }

    public void setPassportIssueBranch(String passportIssueBranch) {
        this.passportIssueBranch = passportIssueBranch;
    }

    public MaritalStatusEnum getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatusEnum maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Integer getDependentAmount() {
        return dependentAmount;
    }

    public void setDependentAmount(Integer dependentAmount) {
        this.dependentAmount = dependentAmount;
    }

    public EmploymentDto getEmployment() {
        return employment;
    }

    public void setEmployment(EmploymentDto employment) {
        this.employment = employment;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Boolean getInsuranceEnabled() {
        return isInsuranceEnabled;
    }

    public void setInsuranceEnabled(Boolean insuranceEnabled) {
        isInsuranceEnabled = insuranceEnabled;
    }

    public Boolean getSalaryClient() {
        return isSalaryClient;
    }

    public void setSalaryClient(Boolean salaryClient) {
        isSalaryClient = salaryClient;
    }
}