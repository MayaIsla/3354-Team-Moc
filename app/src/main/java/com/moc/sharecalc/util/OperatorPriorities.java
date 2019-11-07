package com.moc.sharecalc.util;

/**
 * A common place for priorities of each operator
 */
class OperatorPriorities {
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
     * Addition and subtraction priority
     */
    static final int ADDSUB = 1;

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
}
