package com.moc.sharecalc.util;

/**
 * Enum of operators with arity 0 (no arguments)
 */
public enum Arity0Operator implements Operator {
    L_PAREN (OperatorPriorities.L_PAREN), // ( operator
    R_PAREN (OperatorPriorities.R_PAREN); // ) operator



    public int getArity() {return 0;}
    private int _priority;
    private String _stringRepresentation;
    public int getPriority() {return _priority;}
    private Arity0Operator (int priority) {_priority = priority;}

}
