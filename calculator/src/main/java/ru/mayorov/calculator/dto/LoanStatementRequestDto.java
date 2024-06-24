package ru.mayorov.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) to request for offer approval
 */
@Data
@Schema(description = "Сущность предварительный запрос на кредит")
public class LoanStatementRequestDto {

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

    @Schema(description = "Отчество", example = "Иванович")
    @Size(min = 2, max = 30, message = "Отчество не может быть меньше 2 или более 30 букв")
    private String middleName;

    @Schema(description = "Фамилия", example = "Иванов")
    @NotBlank(message = "Не указана фамилия клиента")
    @Size(min = 2, max = 30, message = "Фамилия не может быть меньше 2 или более 30 букв")
    private String lastName;

    @Schema(description = "Электронная почта", example = "ivanovii@email.com")
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "не соответствует формату e-mail")
    private String email;

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


    public LoanStatementRequestDto(){}

    public LoanStatementRequestDto(BigDecimal amount,
                                   Integer term,
                                   String firstName,
                                   String middleName,
                                   String lastName,
                                   String email,
                                   LocalDate birthdate,
                                   String passportSeries,
                                   String passportNumber) {
        this.amount = amount;
        this.term = term;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.birthdate = birthdate;
        this.passportSeries = passportSeries;
        this.passportNumber = passportNumber;
    }

    public LoanStatementRequestDto(BigDecimal amount, LocalDate of) {
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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}