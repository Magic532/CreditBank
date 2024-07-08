package ru.mayorov.deal.dto;

import lombok.Data;
import ru.mayorov.deal.units.ApplicationStatusEnum;
import ru.mayorov.deal.units.ChangeTypeEnum;

import java.sql.Timestamp;

@Data
public class StatementStatusHistoryDto {
    private ApplicationStatusEnum status;
    private Timestamp time;
    private ChangeTypeEnum changeType;

    public StatementStatusHistoryDto(ApplicationStatusEnum status) {
        this.status = status;
    }

    public StatementStatusHistoryDto(ApplicationStatusEnum status, Timestamp time, ChangeTypeEnum changeType) {
        this.status = status;
        this.time = time;
        this.changeType = changeType;
    }

    public ApplicationStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatusEnum status) {
        this.status = status;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public ChangeTypeEnum getChangeType() {
        return changeType;
    }

    public void setChangeType(ChangeTypeEnum changeType) {
        this.changeType = changeType;
    }
}
