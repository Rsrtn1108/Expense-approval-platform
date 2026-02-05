package org.example.web.dto.request;

import java.util.UUID;

public class RejectExpenseRequest {

    private UUID expenseId;
    private UUID userId;
    private String comment;

    public UUID getExpenseId() {
        return expenseId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getComment() {
        return comment;
    }
}
