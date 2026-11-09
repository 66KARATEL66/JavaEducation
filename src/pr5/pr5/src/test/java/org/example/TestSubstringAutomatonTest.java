package org.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestSubstringAutomatonTest {
    @ParameterizedTest
    @CsvSource({
            "'', 0",
            "abc, 0",
            "T, 1",
            "TE, 2",
            "TES, 3",
            "abcTES, 3",
            "abcTESTabc, F",
            "TEST, F",
            "abcTEST, F",
            "TESTabc, F",
            "TET, 1"
    })
    void returnsExpectedState(String input, String expectedState) {
        assertEquals(expectedState, new TestSubstringAutomaton().search(input));
    }

    @ParameterizedTest
    @MethodSource("stringsContainingTest")
    void findsTestWithPrefixesSuffixesAndMultipleOccurrences(String input) {
        assertEquals("F", new TestSubstringAutomaton().search(input));
    }

    private static Stream<Arguments> stringsContainingTest() {
        return Stream.of(
                Arguments.of("TTEST"),
                Arguments.of("TETEST"),
                Arguments.of("TESTTEST"),
                Arguments.of("TTTEST"),
                Arguments.of("abcTTESTabc"),
                Arguments.of("prefixTESTsuffix"),
                Arguments.of("TESTmiddleTEST")
        );
    }

    @ParameterizedTest
    @CsvSource({
            "'', 0",
            "T, 1",
            "TE, 2",
            "TES, 3",
            "TEST, F",
            "TESTXYZ, F"
    })
    void processesSymbolsIncrementally(String input, String expectedState) {
        TestSubstringAutomaton automaton = new TestSubstringAutomaton();
        for (char symbol : input.toCharArray()) {
            automaton.process(symbol);
        }

        assertEquals(expectedState, automaton.getCurrentState());
    }
}
