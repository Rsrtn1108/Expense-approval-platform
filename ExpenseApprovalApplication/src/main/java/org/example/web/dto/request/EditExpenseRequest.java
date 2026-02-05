package org.example.web.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public class EditExpenseRequest {

    private UUID expenseId;
    private UUID userId;
    private BigDecimal amount;
    private String description;

    public UUID getExpenseId() {
        return expenseId;
    }

    public UUID getUserId() {
        return userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

}
