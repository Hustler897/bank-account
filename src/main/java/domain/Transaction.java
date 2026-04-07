package domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {

    private final LocalDate date;
    private final OperationType operation;
    private final BigDecimal amount;
    private final BigDecimal balance;

    public Transaction(LocalDate date, OperationType operation, BigDecimal amount, BigDecimal balance) {
        this.date = date;
        this.operation = operation;
        this.amount = amount;
        this.balance = balance;
    }

    public LocalDate getDate() {
        return date;
    }

    public OperationType getOperation() {
        return operation;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}