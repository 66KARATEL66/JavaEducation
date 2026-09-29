package org.example;

import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public abstract class TransactionReportGenerator {
    private static final double AMOUNT_PER_SYMBOL = 1_000.0;

    private TransactionReportGenerator() {
    }

    public static String generateReport(List<Transaction> transactions) {
        StringBuilder report = new StringBuilder();
        report.append("Витрати за категоріями:\n");
        appendSection(report, expensesByCategory(transactions));
        report.append("\nВитрати за місяцями:\n");
        appendSection(report, expensesByMonth(transactions));
        return report.toString();
    }

    public static String generateReport(List<Transaction> transactions, String outputFile)
            throws IOException {
        return generateReport(transactions, Path.of(outputFile));
    }

    public static String generateReport(List<Transaction> transactions, Path outputFile)
            throws IOException {
        String report = generateReport(transactions);
        Files.writeString(outputFile, report, StandardCharsets.UTF_8);
        return report;
    }

    public static void printTopExpensesReport(List<Transaction> topExpenses) {
        System.out.println("10 найбільших витрат:");
        topExpenses.forEach(expense ->
                System.out.println(expense.getDescription() + ": " + expense.getAmount()));
    }

    private static Map<String, Double> expensesByCategory(List<Transaction> transactions) {
        return transactions.stream()
                .filter(transaction -> transaction.getAmount() < 0)
                .collect(Collectors.groupingBy(
                        Transaction::getDescription,
                        TreeMap::new,
                        Collectors.summingDouble(transaction -> -transaction.getAmount())));
    }

    private static Map<String, Double> expensesByMonth(List<Transaction> transactions) {
        return transactions.stream()
                .filter(transaction -> transaction.getAmount() < 0)
                .collect(Collectors.groupingBy(
                        transaction -> TransactionAnalyzer.parseDate(transaction.getDate())
                                .format(DateTimeFormatter.ofPattern("MM-yyyy")),
                        TreeMap::new,
                        Collectors.summingDouble(transaction -> -transaction.getAmount())));
    }

    private static void appendSection(StringBuilder report, Map<String, Double> totals) {
        totals.forEach((name, total) -> report
                .append(name)
                .append(": ")
                .append("*".repeat((int) Math.round(total / AMOUNT_PER_SYMBOL)))
                .append(" (")
                .append(total)
                .append(" UAH)\n"));
    }
}
