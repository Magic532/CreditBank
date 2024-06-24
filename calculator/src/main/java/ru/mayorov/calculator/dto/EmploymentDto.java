package ru.mayorov.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.mayorov.calculator.units.EmploymentStatusEnum;
import ru.mayorov.calculator.units.PositionEnum;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * DTO (Data Transfer Object) for employment data.
 */
@Data
@Schema(description = "Сущность место работы")
public class EmploymentDto {

    @NotNull
    @Schema(description = "Тип занятости")
    private EmploymentStatusEnum employmentStatus;

    @NotBlank
    @Size(min = 10, max = 12, message = "Не верно указан ИНН")
    @Schema(description = "ИНН работодателя, для Юр. лиц 10 цифр, для Физ. лиц 12")
    private String employerINN;

    @Min(value = 0, message = "Доход клиента не может быть меньше 0")
    @Schema(description = "Получаемая заработная плата")
    private BigDecimal salary;

    @NotNull
    @Schema(description = "Позиция на работе")
    private PositionEnum position;

    @Min(value = 0, message = "Общий стаж не может быть меньше 0 месяцев")
    @Schema(description = "Общий стаж трудоустройства")
    private Integer workExperienceTotal;

    @Min(value = 0, message = "Стаж на текущем месте работы не может быть меньше 0 месяцев")
    @Schema(description = "Стаж на текущем месте работы")
    private Integer workExperienceCurrent;

    public EmploymentDto(){}

    public EmploymentDto(EmploymentStatusEnum employmentStatus,
                         String employerINN,
                         BigDecimal salary,
                         PositionEnum position,
                         Integer workExperienceTotal,
                         Integer workExperienceCurrent) {
        this.employmentStatus = employmentStatus;
        this.employerINN = employerINN;
        this.salary = salary;
        this.position = position;
        this.workExperienceTotal = workExperienceTotal;
        this.workExperienceCurrent = workExperienceCurrent;
    }

    public EmploymentStatusEnum getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(EmploymentStatusEnum employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public String getEmployerINN() {
        return employerINN;
    }

    public void setEmployerINN(String employerINN) {
        this.employerINN = employerINN;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public PositionEnum getPosition() {
        return position;
    }

    public void setPosition(PositionEnum position) {
        this.position = position;
    }

    public Integer getWorkExperienceTotal() {
        return workExperienceTotal;
    }

    public void setWorkExperienceTotal(Integer workExperienceTotal) {
        this.workExperienceTotal = workExperienceTotal;
    }

    public Integer getWorkExperienceCurrent() {
        return workExperienceCurrent;
    }

    public void setWorkExperienceCurrent(Integer workExperienceCurrent) {
        this.workExperienceCurrent = workExperienceCurrent;
    }
}