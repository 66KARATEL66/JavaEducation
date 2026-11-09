package org.example;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                try {
                    System.out.print("Enter the first number: ");
                    double first = Double.parseDouble(scanner.nextLine());

                    System.out.print("Enter the second number: ");
                    double second = Double.parseDouble(scanner.nextLine());

                    System.out.print("Enter an operation (+, -, *, /): ");
                    String operation = scanner.nextLine().trim();

                    if (operation.equalsIgnoreCase("quit")) {
                        System.out.println("Calculator stopped.");
                        break;
                    }

                    double result = switch (operation) {
                        case "+" -> calculator.add(first, second);
                        case "-" -> calculator.subtract(first, second);
                        case "*" -> calculator.multiply(first, second);
                        case "/" -> calculator.divide(first, second);
                        default -> throw new InvalidInputException(
                                "Unsupported operation: " + operation);
                    };

                    System.out.println("Result: " + result);
                } catch (NumberFormatException exception) {
                    System.out.println("Error: please enter valid numbers.");
                } catch (ArithmeticException exception) {
                    System.out.println("Error: " + exception.getMessage());
                } catch (InvalidInputException exception) {
                    System.out.println("Error: " + exception.getMessage());
                } catch (NoSuchElementException exception) {
                    System.out.println("Input ended.");
                    break;
                } finally {
                    System.out.println("Request processing completed.");
                }
            }
        }
    }
}
