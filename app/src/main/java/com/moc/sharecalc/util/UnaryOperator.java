package com.moc.sharecalc.util;

/**
 * Enum of operators with arity 0 (no arguments)
 */
public enum UnaryOperator implements Operator {
    SIN (OperatorPriorities.TRIG) {
        @Override
        public double operate(double param1) {
            return Math.sin(param1);
        }
    },
    COS (OperatorPriorities.TRIG) {
        @Override
        public double operate(double param1) {
            return Math.cos(param1);
        }
    },
    TAN (OperatorPriorities.TRIG) {
        @Override
        public double operate(double param1) {
            return Math.tan(param1);
        }
    };


    /**
     * Perfom the specified operation on one parameter and return the result
     * @return the result of performing the operation
     * @param param1 The number to operate on
     */
    abstract public double operate(double param1);

    /**
     * The priority of the operator
     */
    private int _priority;

    // See Operator interface
    public int getArity() {return 0;}
    public int getPriority() {return _priority;}

    // Private constructor to intialize a UnaryOperator with given priority
    UnaryOperator(int priority) {_priority = priority;}

}
