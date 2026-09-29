package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class TransactionCSVReader {
    private TransactionCSVReader() {
    }

    public static List<String> readData(String source) throws IOException {
        Objects.requireNonNull(source, "source must not be null");
        URI uri = URI.create(source);
        if (uri.getScheme() == null) {
            return readData(Path.of(source));
        }
        return readData(uri);
    }

    public static List<String> readData(Path source) throws IOException {
        Objects.requireNonNull(source, "source must not be null");
        try (BufferedReader reader = Files.newBufferedReader(source, StandardCharsets.UTF_8)) {
            return readData(reader);
        }
    }

    public static List<String> readData(URI source) throws IOException {
        Objects.requireNonNull(source, "source must not be null");
        return readData(source.toURL());
    }

    public static List<String> readData(URL source) throws IOException {
        Objects.requireNonNull(source, "source must not be null");
        try (InputStream inputStream = source.openStream()) {
            return readData(inputStream);
        }
    }

    public static List<String> readData(InputStream source) throws IOException {
        Objects.requireNonNull(source, "source must not be null");
        return readData(new InputStreamReader(source, StandardCharsets.UTF_8));
    }

    /**
     * Reads CSV lines from any character source. The caller retains ownership of the Reader.
     */
    public static List<String> readData(Reader source) throws IOException {
        Objects.requireNonNull(source, "source must not be null");
        BufferedReader reader = source instanceof BufferedReader bufferedReader
                ? bufferedReader
                : new BufferedReader(source);
        List<String> data = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            data.add(line);
        }
        return data;
    }

    public static List<Transaction> processData(List<String> data) {
        List<Transaction> transactions = new ArrayList<>();
        for (String line : data) {
            if (line.isBlank()) {
                continue;
            }

            String[] values = line.split(",", 3);
            if (values.length != 3) {
                throw new IllegalArgumentException("Invalid transaction data: " + line);
            }

            try {
                transactions.add(new Transaction(
                        values[0].trim(),
                        Double.parseDouble(values[1].trim()),
                        values[2].trim()));
            } catch (NumberFormatException exception) {
                throw new IllegalArgumentException("Invalid transaction amount: " + line, exception);
            }
        }
        return transactions;
    }

    public static List<Transaction> readTransactions(String source) throws IOException {
        return processData(readData(source));
    }

    public static List<Transaction> readTransactions(Path source) throws IOException {
        return processData(readData(source));
    }

    public static List<Transaction> readTransactions(URI source) throws IOException {
        return processData(readData(source));
    }

    public static List<Transaction> readTransactions(URL source) throws IOException {
        return processData(readData(source));
    }

    public static List<Transaction> readTransactions(InputStream source) throws IOException {
        return processData(readData(source));
    }

    public static List<Transaction> readTransactions(Reader source) throws IOException {
        return processData(readData(source));
    }
}
