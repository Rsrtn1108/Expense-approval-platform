package org.example.domain.repository;

import org.example.domain.entity.Expense;
import org.example.domain.entity.User;
import org.example.domain.enums.ExpenseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    List<Expense> findBySubmittedBy(User user);

    List<Expense> findByStatus(ExpenseStatus status);
}
