package ru.mayorov.deal.dto;

import lombok.Data;
import ru.mayorov.deal.units.ChangeTypeEnum;
import ru.mayorov.deal.units.CreditStatusEnum;

import java.time.LocalDateTime;

@Data
public class StatementStatusHistoryDto {
    private CreditStatusEnum status;
    private LocalDateTime time;
    private ChangeTypeEnum changeType;

    public StatementStatusHistoryDto(CreditStatusEnum status) {
        this.status = status;
    }

    public StatementStatusHistoryDto(CreditStatusEnum status, LocalDateTime time, ChangeTypeEnum changeType) {
        this.status = status;
        this.time = time;
        this.changeType = changeType;
    }

    public CreditStatusEnum getStatus() {
        return status;
    }

    public void setStatus(CreditStatusEnum status) {
        this.status = status;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public ChangeTypeEnum getChangeType() {
        return changeType;
    }

    public void setChangeType(ChangeTypeEnum changeType) {
        this.changeType = changeType;
    }
}
