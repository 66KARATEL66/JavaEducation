package org.example;

import java.util.Comparator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class TransactionAnalyzer {
    private static final List<DateTimeFormatter> DATE_FORMATTERS = List.of(
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ISO_LOCAL_DATE);

    private TransactionAnalyzer() {
    }

    public static double calculateTotalBalance(List<Transaction> transactions) {
        return transactions.stream().mapToDouble(Transaction::getAmount).sum();
    }

    public static int countTransactionsByMonth(List<Transaction> transactions, String monthYear) {
        return (int) transactions.stream()
                .filter(transaction -> parseDate(transaction.getDate())
                        .format(DateTimeFormatter.ofPattern("MM-yyyy")).equals(monthYear))
                .count();
    }

    public static List<Transaction> findTopExpenses(List<Transaction> transactions) {
        return transactions.stream()
                .filter(transaction -> transaction.getAmount() < 0)
                .sorted(Comparator.comparingDouble(Transaction::getAmount))
                .limit(10)
                .collect(Collectors.toList());
    }

    public static Optional<Transaction> findHighestExpense(
            List<Transaction> transactions, LocalDate start, LocalDate end) {
        return expensesInPeriod(transactions, start, end)
                .min(Comparator.comparingDouble(Transaction::getAmount));
    }

    public static Optional<Transaction> findLowestExpense(
            List<Transaction> transactions, LocalDate start, LocalDate end) {
        return expensesInPeriod(transactions, start, end)
                .max(Comparator.comparingDouble(Transaction::getAmount));
    }

    private static java.util.stream.Stream<Transaction> expensesInPeriod(
            List<Transaction> transactions, LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Period start must not be after period end");
        }
        return transactions.stream()
                .filter(transaction -> transaction.getAmount() < 0)
                .filter(transaction -> {
                    LocalDate date = parseDate(transaction.getDate());
                    return !date.isBefore(start) && !date.isAfter(end);
                });
    }

    static LocalDate parseDate(String value) {
        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                return LocalDate.parse(value, formatter);
            } catch (DateTimeParseException ignored) {
                // Try the next supported CSV date format.
            }
        }
        throw new IllegalArgumentException("Unsupported transaction date: " + value);
    }
}
