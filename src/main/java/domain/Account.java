package domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account {

    private BigDecimal balance = BigDecimal.ZERO;
    private final List<Transaction> transactions = new ArrayList<>();

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    void deposit(BigDecimal amount, LocalDate date) {
        this.balance = this.balance.add(amount);
        this.transactions.add(new Transaction(date, OperationType.DEPOSIT, amount, this.balance));
    }

    void withdraw(BigDecimal amount, LocalDate date) {
        this.balance = this.balance.subtract(amount);
        this.transactions.add(new Transaction(date, OperationType.WITHDRAWAL, amount, this.balance));
    }

    public boolean hasSufficientBalance(BigDecimal amount) {
        return amount.compareTo(this.balance) <= 0;
    }

}