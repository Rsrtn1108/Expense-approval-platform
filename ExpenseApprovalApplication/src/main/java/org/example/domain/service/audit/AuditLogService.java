package org.example.domain.service.audit;

import org.example.domain.entity.AuditLog;
import org.example.domain.entity.Expense;
import org.example.domain.entity.User;
import org.example.domain.repository.AuditLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
public class AuditLogService {

   private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository){
       this.auditLogRepository = auditLogRepository;
    }

    @Transactional
    public void logAction(Expense expense, User user, String action, String comment){
        AuditLog auditLog = new AuditLog(
                UUID.randomUUID(),
                expense,
                user,
                action,
                comment,
                Instant.now()
        );
        auditLogRepository.save(auditLog);
    }

}
