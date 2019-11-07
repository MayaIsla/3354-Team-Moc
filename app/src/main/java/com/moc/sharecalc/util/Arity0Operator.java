package com.moc.sharecalc.util;

/**
 * Enum of operators with arity 0 (no arguments)
 */
public enum Arity0Operator implements Operator {
    L_PAREN (OperatorPriorities.L_PAREN),
    R_PAREN (OperatorPriorities.R_PAREN);



    public int getArity() {return 0;}
    private int _priority;
    private String _stringRepresentation;
    public int getPriority() {return _priority;}
    private Arity0Operator (int priority) {_priority = priority;}

}
