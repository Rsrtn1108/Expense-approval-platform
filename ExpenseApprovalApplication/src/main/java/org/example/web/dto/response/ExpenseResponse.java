package org.example.web.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class ExpenseResponse {

    private UUID id;
    private BigDecimal amount;
    private String description;
    private String status;
    private UUID submittedBy;
    private Instant createdAt;

    public ExpenseResponse(UUID id,
                           BigDecimal amount,
                           String description,
                           String status,
                           UUID submittedBy,
                           Instant createdAt) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.submittedBy = submittedBy;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public BigDecimal getAmount() { return amount; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public UUID getSubmittedBy() { return submittedBy; }
    public Instant getCreatedAt() { return createdAt; }
}
