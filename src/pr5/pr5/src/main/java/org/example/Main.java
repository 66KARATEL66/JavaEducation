package org.example;

public class Main {
    public static void main(String[] args) {
        String input = args.length == 0 ? "" : args[0];
        TestSubstringAutomaton automaton = new TestSubstringAutomaton();
        System.out.println(automaton.search(input));
    }
}
