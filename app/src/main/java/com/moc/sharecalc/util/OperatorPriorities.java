package com.moc.sharecalc.util;

/**
 * A common place for priorities of each operator
 */
class OperatorPriorities {

    /**
     * The terminator operator ($) will always be the last token in a valid expression
     * to indicate the end. It has the lowest priority to force all remaining operations
     * to execute before thee end result is returned
     */
    static final int TERMINATOR = -3;

    /**
     * Right parentheses priority
     * <p>
     * R_PAREN has even smaller priority so that when it is encountered,
     * every operator gets popped off the stack, including the L_PAREN.
     * This would normally pop the entire operator stack, so the evaluator explictly stops popping
     * when it encounters a L_PAREN in particular
     */
    static final int R_PAREN = -2;

    /**
     * Left parentheses priority
     * <p>
     * L_PAREN has negative priority so that every operator can stack on it.
     * Note that it would normally be impossible to ever put L_PAREN on the stack because of its
     * low priority, so the evaluator explicitly will always push on a L_PAREN when it in
     * particular is encountered.
     */
    static final int L_PAREN = -1; // Right parentheses

    /**
     * Addition priority
     * Note: there is no subtraction operator. Instead, subtraction is converted to
     * addition with a negative number (e.g. 5-2 -> 5+(-2) ) to simplify parsing
     */
    static final int ADD = 1;

    /**
     * Multiplication and division priority
     */
    static final int MULDIV = 2;

    /**
     * Exponentiation priority
     */
    static final int EXP = 3;

    /**
     * Trig functions (sin, cos, etc.) priority
     */
    static final int TRIG = 4;



    // Priorities for bitwise operators
    static final int OR = 1; // Bitwise OR (|)
    static final int XOR = 2; // Bitwise XOR (^)
    static final int AND = 3; // Bitwise AND (&)
    static final int BITSHIFT = 4; // Bitshifts (<<, >>)
}
