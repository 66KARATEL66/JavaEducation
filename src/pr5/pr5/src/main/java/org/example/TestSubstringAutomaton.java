package org.example;

/**
 * Finite automaton that searches for the substring "TEST".
 */
public class TestSubstringAutomaton {
    private State state = State.START;

    /**
     * Processes the input one character at a time and returns the final state.
     *
     * @param input input string to process
     * @return "0", "1", "2", "3", or "F"
     */
    public String search(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input must not be null");
        }

        reset();
        for (char symbol : input.toCharArray()) {
            process(symbol);
        }
        return state.label;
    }

    public void reset() {
        state = State.START;
    }

    public String process(char symbol) {
        if (state == State.FOUND) {
            return state.label;
        }

        state = switch (state) {
            case START -> symbol == 'T' ? State.T : State.START;
            case T -> switch (symbol) {
                case 'E' -> State.TE;
                case 'T' -> State.T;
                default -> State.START;
            };
            case TE -> switch (symbol) {
                case 'S' -> State.TES;
                case 'T' -> State.T;
                default -> State.START;
            };
            case TES -> symbol == 'T' ? State.FOUND : State.START;
            case FOUND -> State.FOUND;
        };
        return state.label;
    }

    public String getCurrentState() {
        return state.label;
    }

    private enum State {
        START("0"),
        T("1"),
        TE("2"),
        TES("3"),
        FOUND("F");

        private final String label;

        State(String label) {
            this.label = label;
        }
    }
}
