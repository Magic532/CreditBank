package ru.mayorov.deal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.sql.Timestamp;

@Entity
public class StatusHistory {
    @Column(name = "status", columnDefinition = "varchar")
    private String status;

    @Column(name = "time", columnDefinition = "TIMESTAMP")
    private Timestamp time;

    @Column(name = "change_type", columnDefinition = "varchar")
    private ChangeType changeType;
}
