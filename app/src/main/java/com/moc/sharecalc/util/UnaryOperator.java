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
    ARCSIN (OperatorPriorities.NAMED_FUNCTION) { // inverse sin
        @Override
        public double operate(double param1) {
            return Math.asin(param1);
        }
    },
    ARCCOS (OperatorPriorities.NAMED_FUNCTION) { // inverse cos
        @Override
        public double operate(double param1) {
            return Math.acos(param1);
        }
    },
    ARCTAN (OperatorPriorities.NAMED_FUNCTION) { // inverse tan
        @Override
        public double operate(double param1) {
            return Math.atan(param1);
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
    },
    /**
     * The factorial operator is special in that unlike other functions like sin, it comes after
     * the operand it acts on. Hence, whenever we are evaluating an expression and we encounter a
     * facotrial, we immediately pop the top of the operand stack and evaluate the factorial, rather
     * than push the operator on to the stack like we do with other operators.
     */
    FACTORIAL (OperatorPriorities.FACTORIAL) {
        @Override
        public double operate(double param1) {
            return factorial(param1);
        }

        /**
         * Returns the factorial of the floor of a number.
         * Internal function. Can be tested via FACTORIAL.operate(x)
         * @param x A non-negative real
         * @return ⌊x⌋!
         */
        private double factorial(double x) {
            if (x < 0)
                throw new IllegalArgumentException();
            else if (x == 0)
                return 1;
            else {
                int roundedX = (int) x;
                double accumulator = 1;
                for (int i=2; i<=roundedX; ++i)
                    accumulator *= i;
                return accumulator;
            }
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
    public int getArity() {return 1;}
    public int getPriority() {return _priority;}

    // Private constructor to intialize a UnaryOperator with given priority
    UnaryOperator(int priority) {_priority = priority;}

}
