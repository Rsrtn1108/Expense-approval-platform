CREATE TABLE expenses (
    id UUID PRIMARY KEY,
    amount NUMERIC(10, 2) NOT NULL CHECK (amount > 0),
    description TEXT NOT NULL,
    status expense_status NOT NULL,
    submitted_by UUID NOT NULL,
    approved_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_expense_submitter
        FOREIGN KEY (submitted_by)
        REFERENCES users(id),

    CONSTRAINT fk_expense_approver
        FOREIGN KEY (approved_by)
        REFERENCES users(id)
);

CREATE INDEX idx_expenses_status ON expenses(status);
CREATE INDEX idx_expenses_submitted_by ON expenses(submitted_by);
CREATE INDEX idx_expenses_created_at ON expenses(created_at);
