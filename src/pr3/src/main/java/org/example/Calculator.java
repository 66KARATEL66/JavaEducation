package org.example;

public class Calculator {
    public double add(double first, double second) throws InvalidInputException {
        validateInputs(first, second);
        return first + second;
    }

    public double subtract(double first, double second) throws InvalidInputException {
        validateInputs(first, second);
        return first - second;
    }

    public double multiply(double first, double second) throws InvalidInputException {
        validateInputs(first, second);
        return first * second;
    }

    public double divide(double first, double second)
            throws InvalidInputException, ArithmeticException {
        validateInputs(first, second);
        if (second == 0.0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }
        return first / second;
    }

    private void validateInputs(double first, double second) throws InvalidInputException {
        if (!Double.isFinite(first) || !Double.isFinite(second)) {
            throw new InvalidInputException("Input values must be finite numbers");
        }
    }
}
