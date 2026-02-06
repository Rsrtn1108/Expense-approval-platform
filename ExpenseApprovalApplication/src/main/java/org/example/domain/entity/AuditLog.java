package org.example.domain.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "audit_logs",
        indexes = {
                @Index(name = "idx_audit_logs_expense_id", columnList = "expense_id"),
                @Index(name = "idx_audit_logs_performed_by", columnList = "performed_by")
        }
)
public class AuditLog {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expense_id")
    private Expense expense;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "performed_by", nullable = false)
    private User actor;

    @Column(name = "action", nullable = false, length = 50)
    private String action;

    @Column(name = "comment", length = 500)
    private String comment;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected AuditLog() {
        // JPA only
    }

    public AuditLog(
            UUID id,
            Expense expense,
            User actor,
            String action,
            String comment,
            Instant createdAt
    ) {
        this.id = id;
        this.expense = expense;
        this.actor = actor;
        this.action = action;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public Expense getExpense() {
        return expense;
    }

    public User getActor() {
        return actor;
    }

    public String getAction() {
        return action;
    }

    public String getComment() {
        return comment;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}

