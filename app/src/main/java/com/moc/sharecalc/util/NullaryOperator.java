package com.moc.sharecalc.util;

/**
 * Enum of operators with arity 0 (no arguments)
 */
public enum NullaryOperator implements Operator {
    L_PAREN (OperatorPriorities.L_PAREN), // ( operator
    R_PAREN (OperatorPriorities.R_PAREN), // ) operator
    TERMINATOR (OperatorPriorities.TERMINATOR); // Final token in valid expression that indicates end


    /**
     * The priority of the operator
     */
    private int _priority;

    // See Operator interface
    public int getArity() {return 0;}
    public int getPriority() {return _priority;}

    // Private constructor to intialize a NullaryOperator with given priority
    NullaryOperator(int priority) {_priority = priority;}

}
