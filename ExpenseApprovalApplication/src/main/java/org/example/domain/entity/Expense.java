package org.example.domain.entity;

import jakarta.persistence.*;
import org.example.domain.enums.ExpenseStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "expenses",
        indexes = {
                @Index(name = "idx_expenses_status", columnList = "status"),
                @Index(name = "idx_expenses_submitted_by", columnList = "submitted_by")
        }
)
public class Expense {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(name = "description", length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ExpenseStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "submitted_by", nullable = false)
    private User submittedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    protected Expense() {
        // JPA only
    }

    public Expense(
            UUID id,
            BigDecimal amount,
            String description,
            User submittedBy,
            Instant createdAt
    ) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.submittedBy = submittedBy;
        this.status = ExpenseStatus.SUBMITTED;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public ExpenseStatus getStatus() {
        return status;
    }

    public User getSubmittedBy() {
        return submittedBy;
    }

    public User getApprovedBy() {
        return approvedBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
