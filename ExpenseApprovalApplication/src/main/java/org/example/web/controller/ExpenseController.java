package org.example.web.controller;

import org.example.domain.entity.Expense;
import org.example.domain.service.expense.ExpenseService;
import org.example.web.dto.request.ApproveExpenseRequest;
import org.example.web.dto.request.EditExpenseRequest;
import org.example.web.dto.request.RejectExpenseRequest;
import org.example.web.dto.request.SubmitExpenseRequest;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public Expense submitExpense(@RequestBody SubmitExpenseRequest request) {

        return expenseService.submitExpense(
                request.getUserId(),
                request.getAmount(),
                request.getDescription()
        );
    }

    @PostMapping("/{expenseId}/approve")
    public Expense approveExpense(@RequestBody ApproveExpenseRequest request) throws AccessDeniedException {

        return expenseService.approveExpense(
                request.getExpenseId(),
                request.getUserId(),
                request.getComment()
        );
    }

    @PostMapping("/{expenseId}/reject")
    public Expense rejectExpense(@RequestBody RejectExpenseRequest request) throws AccessDeniedException {

        return expenseService.rejectExpense(
                request.getExpenseId(),
                request.getUserId(),
                request.getComment()
        );
    }

    @PutMapping("/{expenseId}")
    public Expense editExpense(@RequestBody EditExpenseRequest request) throws AccessDeniedException {

        return expenseService.editExpense(
                request.getExpenseId(),
                request.getUserId(),
                request.getAmount(),
                request.getDescription()
        );
    }

}
