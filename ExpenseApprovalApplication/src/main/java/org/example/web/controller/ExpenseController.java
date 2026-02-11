package org.example.web.controller;

import org.example.domain.entity.Expense;
import org.example.domain.service.expense.ExpenseService;
import org.example.web.dto.request.*;
import org.example.web.dto.response.ExpenseResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ExpenseResponse submitExpense(@RequestBody SubmitExpenseRequest request) {

        return expenseService.submitExpense(
                request.getUserId(),
                request.getAmount(),
                request.getDescription()
        );
    }

    @PostMapping("/{expenseId}/approve")
    public ExpenseResponse approveExpense(@RequestBody ApproveExpenseRequest request) throws AccessDeniedException {

        return expenseService.approveExpense(
                request.getExpenseId(),
                request.getUserId(),
                request.getComment()
        );
    }

    @PostMapping("/{expenseId}/reject")
    public ExpenseResponse rejectExpense(@RequestBody RejectExpenseRequest request) throws AccessDeniedException {

        return expenseService.rejectExpense(
                request.getExpenseId(),
                request.getUserId(),
                request.getComment()
        );
    }

    @PutMapping("/{expenseId}")
    public ExpenseResponse editExpense(@RequestBody EditExpenseRequest request) throws AccessDeniedException {

        return expenseService.editExpense(
                request.getExpenseId(),
                request.getUserId(),
                request.getAmount(),
                request.getDescription()
        );
    }

    @GetMapping("/expenses/{expenseId}")
    public ExpenseResponse getExpense(@PathVariable UUID expenseId, @RequestParam UUID userId) throws AccessDeniedException{

        return expenseService.getExpenseById(expenseId, userId);
    }

    @GetMapping("/expenses/{userId}")
    public List<ExpenseResponse> getExpenses(@PathVariable UUID userId, @RequestParam UUID finderID) throws AccessDeniedException {

        return expenseService.getExpenseByUser(userId, finderID);
    }

    @GetMapping
    public List<ExpenseResponse> getAllExpenses(@RequestParam UUID finderID) throws AccessDeniedException {
        return expenseService.getAllExpenses(finderID);
    }

    @GetMapping("/{viewerUserId}/getAllPending")
    public List<ExpenseResponse> getAllPendingExpenses(@PathVariable UUID viewerUserId) throws AccessDeniedException {
        return expenseService.getPendingExpenses(viewerUserId);
    }

    @GetMapping("/{viewerUserId}/getAllApproved")
    public List<ExpenseResponse> getAllApprovedExpenses(@PathVariable UUID viewerUserId) throws AccessDeniedException {
        return expenseService.getApprovedExpenses(viewerUserId);
    }

    @GetMapping("/{viewerUserId}/getAllRejected")
    public List<ExpenseResponse> getAllRejectedExpenses(@PathVariable UUID viewerUserId) throws AccessDeniedException {
        return expenseService.getRejectedExpenses(viewerUserId);
    }
}
