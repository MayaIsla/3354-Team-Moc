package com.moc.sharecalc.util;

/**
 * A Token is the atomic unit of an expression and can be either an Operator or operand (Double)
 */
public class Token {
    private Double _operandValue;
    private Operator _operatorValue;
    private boolean _isOperator;

    /**
     * Constructs a Token as an Operator.
     * @param operator The operator to be represented by the Token
     */
    Token(Operator operator) {
        _operatorValue = operator;
        _isOperator = true;
    }

    /**
     * Constructs a Token as an operand (Double)
     * @param operand a double to represent the operand
     */
    Token(Double operand) {
        _operandValue = operand;
        _isOperator = false;
    }

    /**
     * Returns the Operator value
     * PRECONDITION: The Token was initialized as an Operator
     * @return the Operator stored
     */
    Operator getOperator() {
        if (!_isOperator)
            throw new ClassCastException(); // Can't return double value as Operator
        else
            return _operatorValue;
    }

    /**
     * Returns the operand (double) value
     * PRECONDITION: The Token was initialized as an operand
     * @return the operand (double) stored
     */
    double getOperand() {
        if (_isOperator)
            throw new ClassCastException(); // Can't return Operator as double
        else
            return _operandValue;
    }


}
