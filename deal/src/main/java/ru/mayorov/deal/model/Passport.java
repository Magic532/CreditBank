package ru.mayorov.deal.model;


import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Passport {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "passport_uuid", updatable = false, nullable = false)
    private UUID passportUUID;

    @OneToOne(mappedBy = "passport", optional = false)
    private Client client;

    @Column(name = "series", columnDefinition = "varchar")
    private String series;

    @Column(name = "number", columnDefinition = "varchar")
    private String number;

    @Column(name = "issue_branch", columnDefinition = "varchar")
    private String issueBranch;

    @Column(name = "issue_date", columnDefinition = "DATE")
    private LocalDate issueDate;

    public Passport() {
    }

    public Passport(UUID passportUUID, Client client, String series, String number, String issueBranch, LocalDate issueDate) {
        this.passportUUID = passportUUID;
        this.client = client;
        this.series = series;
        this.number = number;
        this.issueBranch = issueBranch;
        this.issueDate = issueDate;
    }

    public UUID getPassportUUID() {
        return passportUUID;
    }

    public void setPassportUUID(UUID passportUUID) {
        this.passportUUID = passportUUID;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIssueBranch() {
        return issueBranch;
    }

    public void setIssueBranch(String issueBranch) {
        this.issueBranch = issueBranch;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }
}
