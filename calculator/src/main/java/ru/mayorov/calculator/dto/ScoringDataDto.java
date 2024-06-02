package ru.mayorov.calculator.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    private Enum gender;

    private LocalDate birthdate;

    private String passportSeries;

    private String passportNumber;

    private LocalDate passportIssueDate;

    private String passportIssueBranch;
    private Enum maritalStatus;
    private Integer dependentAmount;
    private EmploymentDto employment;
    private String accountNumber;
    private Boolean isInsuranceEnabled;
    private Boolean isSalaryClient;
}
