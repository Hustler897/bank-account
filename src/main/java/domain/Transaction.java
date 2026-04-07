package domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Transaction(
    LocalDate date,
    OperationType operation,
    BigDecimal amount,
    BigDecimal balance
) {}