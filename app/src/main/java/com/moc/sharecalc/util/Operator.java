package com.moc.sharecalc.util;


public interface Operator {
    /**
     * Get the priority value of the operator.
     * @return the priority of the operator
     */
    int getPriority();

    /**
     * Get the arity of the operator
     * Nullary = 0
     * Unary = 1
     * Binary = 2
     * @return the arity of the operator
     */
    int getArity();


    @Override
    String toString();
}
