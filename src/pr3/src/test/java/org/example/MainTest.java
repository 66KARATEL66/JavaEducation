package org.example;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {
    @Test
    void reportsInvalidNumberInputAndAlwaysCompletesRequest() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOutput = System.out;
        InputStream originalInput = System.in;

        try {
            System.setIn(new ByteArrayInputStream(
                    "not-a-number\n2\n+\n".getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));

            Main.main(new String[0]);

            String consoleOutput = output.toString(StandardCharsets.UTF_8);
            assertTrue(consoleOutput.contains("please enter valid numbers"));
            assertTrue(consoleOutput.contains("Request processing completed."));
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
    }

    @Test
    void quitsWhenQuitIsEnteredAsOperation() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOutput = System.out;
        InputStream originalInput = System.in;

        try {
            System.setIn(new ByteArrayInputStream(
                    "1\n2\nquit\n".getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));

            Main.main(new String[0]);

            String consoleOutput = output.toString(StandardCharsets.UTF_8);
            assertTrue(consoleOutput.contains("Calculator stopped."));
            assertTrue(!consoleOutput.contains("Unsupported operation"));
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
    }
}
