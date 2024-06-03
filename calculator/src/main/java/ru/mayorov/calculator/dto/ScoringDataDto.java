package ru.mayorov.calculator.dto;

import lombok.Builder;
import lombok.Data;
import ru.mayorov.calculator.units.GenderEnum;
import ru.mayorov.calculator.units.MaritalStatusEnum;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ScoringDataDto {

    @Min(value = 30000, message = "Сумма кредита не может быть меньше 30 000 руб.")
    @Max(value = 5000000, message = "Сумма кредита не может быть больше 5 000 000 руб.")
    private BigDecimal amount;

    @Min(value = 6, message = "Срок кредита не может быть меньше 6 месяцев")
    @Max(value = 60, message = "Срок кредита не может быть больше 60 месяцев")
    private Integer term;

    @NotBlank(message = "Не указано имя клиента")
    @Size(min = 2, max = 30, message = "Имя не может быть меньше 2 или более 30 букв")
    private String firstName;

    @NotBlank(message = "Не указана фамилия клиента")
    @Size(min = 2, max = 30, message = "Фамилия не может быть меньше 2 или более 30 букв")
    private String lastName;

    @Size(min = 2, max = 30, message = "Отчество не может быть меньше 2 или более 30 букв")
    private String middleName;

    @NotNull
    private GenderEnum gender;

    @NotNull
    private LocalDate birthdate;

    @NotBlank
    @Size(min = 4, max = 4, message = "Не верно указана серия паспорта")
    private String passportSeries;

    @NotBlank
    @Size(min = 6, max = 6, message = "Не верно указан номер паспорта")
    private String passportNumber;

    @NotNull
    private LocalDate passportIssueDate;

    @NotBlank
    private String passportIssueBranch;

    @NotNull
    private MaritalStatusEnum maritalStatus;

    @Min(value = 0, message = "Не указано количество детей клиента")
    private Integer dependentAmount;

    @NotNull
    private EmploymentDto employment;

    @NotBlank
    private String accountNumber;

    @NotNull
    private Boolean isInsuranceEnabled;

    @NotNull
    private Boolean isSalaryClient;
}
