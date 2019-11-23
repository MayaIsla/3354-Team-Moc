package com.moc.sharecalc.util;

/**
 * Enum of operators with arity 0 (no arguments)
 */
public enum UnaryOperator implements Operator {
    SIN (OperatorPriorities.NAMED_FUNCTION) {
        @Override
        public double operate(double param1) {
            return Math.sin(param1);
        }
    },
    COS (OperatorPriorities.NAMED_FUNCTION) {
        @Override
        public double operate(double param1) {
            return Math.cos(param1);
        }
    },
    TAN (OperatorPriorities.NAMED_FUNCTION) {
        @Override
        public double operate(double param1) {
            return Math.tan(param1);
        }
    },
    LOG_10(OperatorPriorities.NAMED_FUNCTION) { // Log base 10
        @Override
        public double operate(double param1) { return Math.log10(param1); }
    },
    LOG_E(OperatorPriorities.NAMED_FUNCTION) { // Log base e / natural log
        @Override
        public double operate(double param1) { return Math.log(param1); }
    },
    LOG_2(OperatorPriorities.NAMED_FUNCTION) { // Log base 2
        // Using the identity log_b(a) = log a / log b,
        // log_2(x) = log x / log 2
        @Override
        public double operate(double param1) { return Math.log(param1) / Math.log(2); }
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
    public int getArity() {return 1;}
    public int getPriority() {return _priority;}

    // Private constructor to intialize a UnaryOperator with given priority
    UnaryOperator(int priority) {_priority = priority;}

}
