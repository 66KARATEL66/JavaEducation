package org.example;

/**
 * Compatibility name for clients that used the original reader class.
 */
public abstract class TransactionReader {
    private TransactionReader() {
    }

    public static java.util.List<String> readData(String source) throws java.io.IOException {
        return TransactionCSVReader.readData(source);
    }

    public static java.util.List<Transaction> processData(java.util.List<String> data) {
        return TransactionCSVReader.processData(data);
    }

    public static java.util.List<Transaction> readTransactions(String source) throws java.io.IOException {
        return TransactionCSVReader.readTransactions(source);
    }
}
