package ru.mayorov.deal.dto;

import lombok.Data;
import ru.mayorov.deal.units.EmploymentStatusEnum;
import ru.mayorov.deal.units.PositionEnum;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * DTO (Data Transfer Object) for employment data.
 */
@Data
public class EmploymentDto {

    @NotNull
    private EmploymentStatusEnum employmentStatus;

    @NotBlank
    @Size(min = 10, max = 12, message = "Не верно указан ИНН")
    private String employerINN;

    @Min(value = 0, message = "Доход клиента не может быть меньше 0")
    private BigDecimal salary;

    @NotNull
    private PositionEnum position;

    @Min(value = 0, message = "Общий стаж не может быть меньше 0 месяцев")
    private Integer workExperienceTotal;

    @Min(value = 0, message = "Стаж на текущем месте работы не может быть меньше 0 месяцев")
    private Integer workExperienceCurrent;

    public EmploymentDto(){}

    public EmploymentDto(EmploymentStatusEnum employmentStatus, String employerINN, BigDecimal salary, PositionEnum position, Integer workExperienceTotal, Integer workExperienceCurrent) {
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