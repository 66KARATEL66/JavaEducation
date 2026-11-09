package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorTest {
    private final Calculator calculator = new Calculator();

    @Test
    void addsNumbers() throws InvalidInputException {
        assertEquals(7.5, calculator.add(3.0, 4.5));
    }

    @Test
    void subtractsNumbers() throws InvalidInputException {
        assertEquals(-1.5, calculator.subtract(3.0, 4.5));
    }

    @Test
    void multipliesNumbers() throws InvalidInputException {
        assertEquals(13.5, calculator.multiply(3.0, 4.5));
    }

    @Test
    void dividesNumbers() throws InvalidInputException {
        assertEquals(2.0, calculator.divide(9.0, 4.5));
    }

    @Test
    void rejectsDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> calculator.divide(10.0, 0.0));
    }

    @Test
    void rejectsNonFiniteInputs() {
        assertThrows(InvalidInputException.class, () -> calculator.add(Double.NaN, 1.0));
        assertThrows(InvalidInputException.class,
                () -> calculator.subtract(1.0, Double.POSITIVE_INFINITY));
        assertThrows(InvalidInputException.class,
                () -> calculator.multiply(Double.NEGATIVE_INFINITY, 1.0));
        assertThrows(InvalidInputException.class, () -> calculator.divide(1.0, Double.NaN));
    }
}
