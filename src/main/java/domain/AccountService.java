package domain;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class AccountService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final Account account;
    private final Clock clock;

    public AccountService(Account account, Clock clock) {
        this.account = account;
        this.clock = clock;
    }

    public void deposit(BigDecimal amount) {
        validateAmount(amount);
        account.deposit(amount, LocalDate.now(clock));
    }
    
    public void withdraw(BigDecimal amount) {
        validateAmount(amount);
        if (!account.hasSufficientBalance(amount)) {
            throw new IllegalStateException("Solde insuffisant");
        }
        account.withdraw(amount, LocalDate.now(clock));
    }

    public String printStatement() {
        String header = "Date       | Operation  | Amount | Balance";
        String lines = account.getTransactions().stream()
                .map(this::formatLine)
                .collect(Collectors.joining("\n"));
        return lines.isEmpty() ? header : header + "\n" + lines;
    }

    private String formatLine(Transaction transaction) {
        BigDecimal displayAmount = transaction.getOperation() == OperationType.WITHDRAWAL
                ? transaction.getAmount().negate()
                : transaction.getAmount();
    
        return String.format("%s | %-10s | %6s | %7s",
                transaction.getDate().format(FORMATTER),
                transaction.getOperation(),
                displayAmount,
                transaction.getBalance());
    }

    private void validateAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant doit être positif");
        }
    }
}