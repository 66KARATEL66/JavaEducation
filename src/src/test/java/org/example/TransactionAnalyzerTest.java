package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionAnalyzerTest {
    @Test
    void calculatesTotalBalance() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("2023-01-01", 100.0, "Дохід"),
                new Transaction("2023-01-02", -50.0, "Витрата"),
                new Transaction("2023-01-03", 150.0, "Дохід"));

        assertEquals(200.0, TransactionAnalyzer.calculateTotalBalance(transactions));
    }

    @Test
    void countsTransactionsByMonthForBothSupportedDateFormats() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("01-02-2023", 50.0, "Дохід"),
                new Transaction("15-02-2023", -20.0, "Витрата"),
                new Transaction("2023-03-05", 100.0, "Дохід"));

        assertEquals(2, TransactionAnalyzer.countTransactionsByMonth(transactions, "02-2023"));
        assertEquals(1, TransactionAnalyzer.countTransactionsByMonth(transactions, "03-2023"));
    }

    @Test
    void readsTransactionsFromCsvFile(@TempDir Path temporaryDirectory) throws Exception {
        Path csv = temporaryDirectory.resolve("transactions.csv");
        Files.writeString(csv, "05-12-2023,-7850,Сільпо\n10-12-2023,80000,Зарплата\n");

        List<Transaction> transactions = TransactionCSVReader.readTransactions(csv);

        assertEquals(2, transactions.size());
        assertEquals("Сільпо", transactions.get(0).getDescription());
        assertEquals(-7850.0, transactions.get(0).getAmount());
        assertEquals("10-12-2023", transactions.get(1).getDate());
    }

    @Test
    void identifiesTenLargestExpenses() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("01-01-2024", -100.0, "Expense 1"),
                new Transaction("02-01-2024", -1100.0, "Expense 2"),
                new Transaction("03-01-2024", -200.0, "Expense 3"),
                new Transaction("04-01-2024", -900.0, "Expense 4"),
                new Transaction("05-01-2024", -300.0, "Expense 5"),
                new Transaction("06-01-2024", -800.0, "Expense 6"),
                new Transaction("07-01-2024", -400.0, "Expense 7"),
                new Transaction("08-01-2024", -700.0, "Expense 8"),
                new Transaction("09-01-2024", -500.0, "Expense 9"),
                new Transaction("10-01-2024", -600.0, "Expense 10"),
                new Transaction("11-01-2024", -1200.0, "Expense 11"),
                new Transaction("12-01-2024", 5000.0, "Income"));

        List<Transaction> topExpenses = TransactionAnalyzer.findTopExpenses(transactions);

        assertEquals(10, topExpenses.size());
        assertEquals(-1200.0, topExpenses.get(0).getAmount());
        assertEquals(-200.0, topExpenses.get(9).getAmount());
        assertTrue(topExpenses.stream().noneMatch(transaction -> transaction.getAmount() == -100.0));
    }

    @Test
    void findsHighestAndLowestExpensesInPeriod() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("01-01-2024", -5000.0, "Large"),
                new Transaction("15-01-2024", -200.0, "Small"),
                new Transaction("01-02-2024", -9000.0, "Outside"));

        assertEquals(-5000.0, TransactionAnalyzer.findHighestExpense(
                transactions, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31))
                .orElseThrow().getAmount());
        assertEquals(-200.0, TransactionAnalyzer.findLowestExpense(
                transactions, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31))
                .orElseThrow().getAmount());
    }
}
