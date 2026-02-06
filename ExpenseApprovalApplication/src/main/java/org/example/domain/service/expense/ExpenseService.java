package org.example.domain.service.expense;

import org.example.domain.entity.AuditLog;
import org.example.domain.entity.Expense;
import org.example.domain.entity.User;
import org.example.domain.enums.ExpenseStatus;
import org.example.domain.repository.AuditLogRepository;
import org.example.domain.repository.ExpenseRepository;
import org.example.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final AuditLogRepository auditLogRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository, AuditLogRepository auditLogRepository){
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.auditLogRepository = auditLogRepository;
    }

    @Transactional
    public Expense submitExpense(UUID userID, BigDecimal amount, String description){

        //load user that submitted expense
        User submitter = userRepository.findById(userID).orElseThrow(()-> new IllegalArgumentException("User not found"));

        //check expense is valid
        if(amount == null || amount.signum() <= 0){
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        if(description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }

        //generate new expense
        Expense expense = new Expense(
                UUID.randomUUID(),
                amount,
                description,
                submitter,
                Instant.now()
        );

        //save expense to DB
        expenseRepository.save(expense);

        //generate log of action
        AuditLog auditLog = new AuditLog(
                UUID.randomUUID(),
                expense,
                submitter,
                "SUBMITTED",
                null,
                Instant.now()
        );

        //save the log to DB
        auditLogRepository.save(auditLog);

        //return expense
        return expense;
    }

    @Transactional
    public Expense approveExpense(UUID expenseID, UUID userID, String comment) throws AccessDeniedException {

        //load expense and approval user
        Expense expense = expenseRepository.findById(expenseID).orElseThrow(()-> new IllegalArgumentException("Expense not found"));
        User approver = userRepository.findById(userID).orElseThrow(()-> new IllegalArgumentException("User not found"));

        //check action is valid
        if(!approver.getRole().getName().equals("Manager")){
            throw new AccessDeniedException("You do not have the permission to approve this expense");
        }

        //update expense to approved
        expense.approve();

        //generate log of action
        AuditLog auditLog = new AuditLog(
                UUID.randomUUID(),
                expense,
                approver,
                "APPROVED",
                comment,
                Instant.now()
        );

        //save log
        auditLogRepository.save(auditLog);

        //return expense
        return expense;
    }

    @Transactional
    public Expense rejectExpense (UUID expenseID, UUID userID, String comment) throws AccessDeniedException {

        //load expense and user
        Expense expense = expenseRepository.findById(expenseID).orElseThrow(()-> new IllegalArgumentException("Expense not found"));
        User rejector = userRepository.findById(userID).orElseThrow(()-> new IllegalArgumentException("User not found"));

        //check action is valid
        if(!rejector.getRole().getName().equals("Manager")){
            throw new AccessDeniedException("You do not have permission to reject this expense");
        }

        //update expense
        expense.reject();

        //generate log of action
        AuditLog auditLog = new AuditLog(
                UUID.randomUUID(),
                expense,
                rejector,
                "REJECTED",
                comment,
                Instant.now()
        );

        //save log
        auditLogRepository.save(auditLog);

        //return expense
        return expense;
    }

    @Transactional
    public Expense editExpense(UUID expenseID, UUID userID, BigDecimal newAmount, String newDesc) throws AccessDeniedException {

        //load expense and user
        Expense expense = expenseRepository.findById(expenseID).orElseThrow(()-> new IllegalArgumentException("Expense not found"));
        User editor = userRepository.findById(userID).orElseThrow(()-> new IllegalArgumentException("User not found"));

        //check action is valid
        UUID submitterID = expense.getSubmittedBy().getId();
        if(!editor.getId().equals(submitterID)){
            throw new AccessDeniedException("Only the user who submitted this expense can edit it");
        }

        //edit expense
        expense.updateDetails(newAmount, newDesc);

        //log the action
        AuditLog auditLog = new AuditLog(
                UUID.randomUUID(),
                expense,
                editor,
                "UPDATED",
                null,
                Instant.now()
        );

        //save log
        auditLogRepository.save(auditLog);

        //return expense
        return expense;
    }

    @Transactional
    public Expense getExpenseById(UUID expenseId, UUID userId) throws AccessDeniedException {
        //load expense and user
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(()-> new IllegalArgumentException("Expense not found"));
        User finder = userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("User not found"));

        //check against rules
        boolean isManager = finder.getRole().getName().equals("Manager");
        boolean isFinance = finder.getRole().getName().equals("Finance");
        boolean isSubmitter = finder.getId().equals(expense.getSubmittedBy().getId());
        if(!isManager && !isFinance && !isSubmitter){
            throw new AccessDeniedException("You do not have permission to view this expense");
        }

        // log action
        AuditLog auditLog = new AuditLog(
                UUID.randomUUID(),
                expense,
                finder,
                "VIEWED",
                null,
                Instant.now()
        );

        //save log
        auditLogRepository.save(auditLog);

        //return expense
        return expense;
    }

    @Transactional
    public List<Expense> getExpenseByUser(UUID userID, UUID finderID) throws AccessDeniedException {

        //load user id
        User user = userRepository.findById(userID).orElseThrow(()-> new IllegalArgumentException("User not found"));
        User finder = userRepository.findById(finderID).orElseThrow(()-> new IllegalArgumentException("User not found"));

        //check rules
        boolean isManager = finder.getRole().getName().equals("Manager");
        boolean isFinance = finder.getRole().getName().equals("Finance");
        boolean isSubmitter = finder.getId().equals(userID);

        if(!isManager && !isFinance && !isSubmitter){
            throw new AccessDeniedException("You do not have permission to view this Users expenses");
        }

        //audit logging
        AuditLog auditLog = new AuditLog(
                UUID.randomUUID(),
                null,
                finder,
                "VIEWED USERS EXPENSES",
                null,
                Instant.now()
        );

        //save log
        auditLogRepository.save(auditLog);

        // return list
        return expenseRepository.findBySubmittedBy(user);
    }

    @Transactional
    public List<Expense> getAllExpenses(UUID finderId) throws AccessDeniedException {
        //load user ID
        User finder = userRepository.findById(finderId).orElseThrow(()-> new IllegalArgumentException("User not found"));

        //check rules
        boolean isManager = finder.getRole().getName().equals("Manager");
        boolean isFinance = finder.getRole().getName().equals("Finance");

        if(isManager && !isFinance){
            throw new AccessDeniedException("You do not have permission to view all expenses");
        }

        //audit logging
        AuditLog auditLog = new AuditLog(
                UUID.randomUUID(),
                null,
                finder,
                "VIEWED ALL EXPENSES",
                null,
                Instant.now()
        );

        //save log
        auditLogRepository.save(auditLog);

        //return expenses
        return expenseRepository.findAll();
    }

    @Transactional
    public List<Expense> getPendingExpenses(UUID userID) throws AccessDeniedException {
        //load user ID
        User user = userRepository.findById(userID).orElseThrow(()-> new IllegalArgumentException("User not found"));

        //check rules
        boolean isManager = user.getRole().getName().equals("Manager");
        boolean isFinance = user.getRole().getName().equals("Finance");

        if(!isManager && !isFinance){
            throw new AccessDeniedException("You do not have permission to view pending expenses");
        }

        //audit logging
        AuditLog auditLog = new AuditLog(
                UUID.randomUUID(),
                null,
                user,
                "VIEWED PENDING EXPENSES",
                null,
                Instant.now()
        );

        //save log
        auditLogRepository.save(auditLog);

        return expenseRepository.findByStatus(ExpenseStatus.SUBMITTED);
    }

    @Transactional
    public List<Expense> getApprovedExpenses(UUID userID) throws AccessDeniedException {
        //load user ID
        User user = userRepository.findById(userID).orElseThrow(()-> new IllegalArgumentException("User not found"));

        //check rules
        boolean isManager = user.getRole().getName().equals("Manager");
        boolean isFinance = user.getRole().getName().equals("Finance");

        if(!isManager && !isFinance){
            throw new AccessDeniedException("You do not have permission to view approved expenses");
        }

        //audit logging
        AuditLog auditLog = new AuditLog(
                UUID.randomUUID(),
                null,
                user,
                "VIEWED APPROVED EXPENSES",
                null,
                Instant.now()
        );

        //save log
        auditLogRepository.save(auditLog);

        return expenseRepository.findByStatus(ExpenseStatus.APPROVED);
    }

    @Transactional
    public List<Expense> getRejectedExpenses(UUID userID) throws AccessDeniedException {
        //load user ID
        User user = userRepository.findById(userID).orElseThrow(()-> new IllegalArgumentException("User not found"));

        //check rules
        boolean isManager = user.getRole().getName().equals("Manager");
        boolean isFinance = user.getRole().getName().equals("Finance");

        if(!isManager && !isFinance){
            throw new AccessDeniedException("You do not have permission to view rejected expenses");
        }

        //audit logging
        AuditLog auditLog = new AuditLog(
                UUID.randomUUID(),
                null,
                user,
                "VIEWED REJECTED EXPENSES",
                null,
                Instant.now()
        );

        //save log
        auditLogRepository.save(auditLog);

        return expenseRepository.findByStatus(ExpenseStatus.REJECTED);
    }

}
