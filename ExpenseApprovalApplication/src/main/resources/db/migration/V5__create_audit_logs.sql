CREATE TABLE audit_logs (
    id UUID PRIMARY KEY,
    expense_id UUID,
    performed_by UUID NOT NULL,
    action VARCHAR(100) NOT NULL,
    comment TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_audit_expense
        FOREIGN KEY (expense_id)
        REFERENCES expenses(id),

    CONSTRAINT fk_audit_user
        FOREIGN KEY (performed_by)
        REFERENCES users(id)
);

CREATE INDEX idx_audit_expense_id ON audit_logs(expense_id);
CREATE INDEX idx_audit_performed_by ON audit_logs(performed_by);
