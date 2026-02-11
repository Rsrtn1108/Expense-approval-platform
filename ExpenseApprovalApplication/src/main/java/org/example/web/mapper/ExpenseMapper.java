package org.example.web.mapper;

import org.example.domain.entity.Expense;
import org.example.web.dto.response.ExpenseResponse;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {

    public ExpenseResponse mapToResponse(Expense expense){
        return new ExpenseResponse(
                expense.getId(),
                expense.getAmount(),
                expense.getDescription(),
                expense.getStatus().name(),
                expense.getSubmittedBy().getId(),
                expense.getCreatedAt()
        );
    }
}
