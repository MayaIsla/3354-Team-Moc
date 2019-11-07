package com.moc.sharecalc.util;

/**
 * Enum of operators with arity 0 (no arguments)
 */
public enum BinaryOperator implements Operator {
    ADD(OperatorPriorities.ADD) {
        @Override
        public double operate(double param1, double param2) {
            return param1 + param2;
        }
    },
    MULTIPLY(OperatorPriorities.MULDIV) {
        @Override
        public double operate(double param1, double param2) {
            return param1 * param2;
        }
    },
    DIVIDE(OperatorPriorities.MULDIV) {
        @Override
        public double operate(double param1, double param2) {
            return param1 / param2;
        }
    },
    EXPONENTIATE(OperatorPriorities.EXP) {
        @Override
        public double operate(double param1, double param2) {
            return Math.pow(param1, param2);
        }
    };


    /**
     * Perfom the specified operation on two parameters and return the result
     * @return the result of performing the operation
     * @param param1 The first number to operate on
     * @param param2 The second number to operate on
     */
    abstract public double operate(double param1, double param2);

    /**
     * The priority of the operator
     */
    private int _priority;

    // See Operator interface
    public int getArity() {return 0;}
    public int getPriority() {return _priority;}

    // Private constructor to intialize a BinaryOperator with given priority
    BinaryOperator(int priority) {_priority = priority;}

}
