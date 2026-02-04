package org.example.domain.repository;

import org.example.domain.entity.AuditLog;
import org.example.domain.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {

    List<AuditLog> findByExpense(Expense expense);
}
