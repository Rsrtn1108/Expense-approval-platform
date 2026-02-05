package org.example.web.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public class SubmitExpenseRequest {

    private UUID userId;
    private BigDecimal amount;
    private String description;

    public UUID getUserId() {
        return userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
