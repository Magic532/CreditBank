package ru.mayorov.calculator.dto;

import lombok.Builder;
import lombok.Data;
import ru.mayorov.calculator.units.EmploymentStatusEnum;
import ru.mayorov.calculator.units.PositionEnum;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
public class EmploymentDto {

    @NotNull
    private EmploymentStatusEnum employmentStatus;

    @NotBlank
    @Size(min = 12, max = 12, message = "Не верно указана ИНН")
    private String employerINN;

    @Min(value = 0, message = "Доход елиента не может быть меньше 0")
    private BigDecimal salary;

    @NotNull
    private PositionEnum position;

    @Min(value = 0, message = "Общий стаж не может быть меньше 0 месяцев")
    private Integer workExperienceTotal;

    @Min(value = 0, message = "Стаж на текущем месте работы не может быть меньше 0 месяцев")
    private Integer workExperienceCurrent;
}