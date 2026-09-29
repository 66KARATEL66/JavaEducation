package org.example;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {
        String filePath = "https://informer.com.ua/dut/java/pr2.csv";
        List<Transaction> transactions = TransactionCSVReader.readTransactions(filePath);


        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }

        double totalBalance = TransactionAnalyzer.calculateTotalBalance(transactions);

        System.out.println("Загальний баланс: " + totalBalance);

        String monthYear = "01-2024";
        int transactionsCount = TransactionAnalyzer.countTransactionsByMonth(transactions, monthYear);
        System.out.println("Кількість транзакцій за " + monthYear + ": " + transactionsCount);

        List<Transaction> topExpenses = TransactionAnalyzer.findTopExpenses(transactions);
        TransactionReportGenerator.printTopExpensesReport(topExpenses);
        System.out.println(TransactionReportGenerator.generateReport(
                transactions, "expense-report.txt"));

    }
}