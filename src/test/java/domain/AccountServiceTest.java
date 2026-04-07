package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceTest {

    private AccountService accountService;
    private Clock clock;

    @BeforeEach
    void setUp() {
        clock = Clock.fixed(Instant.parse("2026-04-15T00:00:00Z"), ZoneId.systemDefault());
        accountService = new AccountService(new Account(), clock);
    }

    @Test
    void should_not_allow_zero_deposit() {
        assertThrows(IllegalArgumentException.class, () -> accountService.deposit(BigDecimal.ZERO));
    }

    @Test
    void should_not_allow_zero_withdrawal() {
        assertThrows(IllegalArgumentException.class, () -> accountService.withdraw(BigDecimal.ZERO));
    }

    @Test
    void should_not_allow_negative_deposit() {
        assertThrows(IllegalArgumentException.class, () -> accountService.deposit(new BigDecimal("-100")));
    }

    @Test
    void should_not_allow_negative_withdrawal() {
        assertThrows(IllegalArgumentException.class, () -> accountService.withdraw(new BigDecimal("-100")));
    }

    @Test
    void should_not_allow_withdrawal_exceeding_balance() {
        accountService.deposit(new BigDecimal("500"));
        assertThrows(IllegalStateException.class, () -> accountService.withdraw(new BigDecimal("600")));
    }
    
    @Test
    void should_print_statement_after_deposit() {
        accountService.deposit(new BigDecimal("500"));

        String statement = accountService.printStatement();

        assertTrue(statement.contains("DEPOSIT"));
        assertTrue(statement.contains("500"));
    }

    @Test
    void should_print_statement_after_deposit_and_withdrawal() {
        accountService.deposit(new BigDecimal("500"));
        accountService.withdraw(new BigDecimal("100"));

        String statement = accountService.printStatement();

        List<String> lines = Arrays.asList(statement.split("\n"));
        assertEquals(3, lines.size()); 
        assertTrue(lines.get(1).contains("DEPOSIT"));
        assertTrue(lines.get(1).contains("500"));
        assertTrue(lines.get(2).contains("WITHDRAWAL"));
        assertTrue(lines.get(2).contains("-100"));
        assertTrue(lines.get(2).contains("400"));
    }

    @Test
    void should_print_statement_in_chronological_order() {
        accountService.deposit(new BigDecimal("500"));
        accountService.withdraw(new BigDecimal("100"));
        accountService.deposit(new BigDecimal("200"));

        String statement = accountService.printStatement();
        List<String> lines = Arrays.asList(statement.split("\n"));

        assertEquals(4, lines.size());
        assertTrue(lines.get(1).contains("DEPOSIT"));
        assertTrue(lines.get(2).contains("WITHDRAWAL"));
        assertTrue(lines.get(3).contains("DEPOSIT"));
    }
    @Test
    void should_print_header_only_when_no_transactions() {
        String statement = accountService.printStatement();

        assertEquals("Date       | Operation  | Amount | Balance", statement);
    }
    
}