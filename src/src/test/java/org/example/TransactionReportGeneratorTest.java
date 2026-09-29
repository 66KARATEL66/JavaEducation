package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionReportGeneratorTest {
    @Test
    void generatesCategoryAndMonthReportWithSymbols() {
        List<Transaction> transactions = List.of(
                new Transaction("05-01-2024", -2500.0, "Food"),
                new Transaction("07-01-2024", -500.0, "Food"),
                new Transaction("05-02-2024", -1000.0, "Transport"));

        String report = TransactionReportGenerator.generateReport(transactions);

        assertTrue(report.contains("Витрати за категоріями:"));
        assertTrue(report.contains("Food: *** (3000.0 UAH)"));
        assertTrue(report.contains("Transport: * (1000.0 UAH)"));
        assertTrue(report.contains("Витрати за місяцями:"));
        assertTrue(report.contains("01-2024: *** (3000.0 UAH)"));
        assertTrue(report.contains("02-2024: * (1000.0 UAH)"));
    }
}
