package com.moc.sharecalc.util;

/**
 * Enum of operators with arity 0 (no arguments)
 */
public enum Arity0Operator implements Operator {
    L_PAREN (-1), // L_PAREN has negative priority so that every operator can stack on it.
    // Note that it would normally be impossible to ever put L_PAREN on the stack because of its
    // low priority, so the evaluator explicitly will always push on a L_PAREN when it in
    // particular is encountered.

    R_PAREN (-2); // R_PAREN has even smaller priority so that when it is encountered,
    // every operator gets popped off the stack, including the L_PAREN.
    // This would normally pop the entire operator stack, so the evaluator explictly stops popping
    // when it encounters a L_PAREN in particular



    public int getArity() {return 0;}
    private int _priority;
    private String _stringRepresentation;
    public int getPriority() {return _priority;}
    private Arity0Operator (int priority) {_priority = priority;}

}
