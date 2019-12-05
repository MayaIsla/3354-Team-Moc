package com.moc.sharecalc.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionTests {

    /**
     * Unit test for explicit multiplication of a negative expression. Compares the output of two multiplication
     *expressions, one with explicit multiplication (i.e. -1*log10) and the other with implicit (i.e. -log10).
     *
     * @param input - String that contains the inputted expression
     * @param expectedOutput - String that contains the expected output
     */
    @ParameterizedTest
    @CsvSource({
            "-sin(5),-1*sin(5)",
            "3*-tan(2),3*-1*tan(2)"
    })
    void makeNegationExplicitTest(String input, String expectedOutput) {
        assertEquals(expectedOutput, Expression.makeNegationExplicit(input));
    }

    /**
     * Unit test for explicit multiplication. Compares two expressions, if they are equal, then explicit multiplication
     * was successful.
     *
     * @param input - String that contains the input expression
     * @param expectedOutput - String that contains the expected output
     */
    @ParameterizedTest
    @CsvSource({
            "2sin(5),2*sin(5)",
            "3tan(2),3*tan(2)",
            "8+4.2E-4(2+3),8+4.2E-4*(2+3)",
            "-2.tan0,-2.*tan0"
    })
    void makeMultiplicationExplicit(String input, String expectedOutput) {
        assertEquals(expectedOutput, Expression.makeMultiplicationExplicit(input));
    }

    /**
     * Unit test for changing a subtraction to an addition with negative numbers. Compares the output of two
     * expressions, one with normal subtraction, and the other using addition of negative numbers. If the two
     * outputs are equal, then the test is successful.
     *
     * @param input - String that contains the input expression
     * @param expectedOutput - String that contains the expected output
     */
    @ParameterizedTest
    @CsvSource({
            "5-3,5+-3",
            "-8-2,-8+-2",
            "10*-2sin3,10*-2sin3",
    })
    void makeSubtractionUseAddition(String input, String expectedOutput) {
        assertEquals(expectedOutput, Expression.makeSubtractionUseAddition(input));
    }

    @ParameterizedTest
    @CsvSource({
            "10-2sin3,10+-2*sin3",
            "8*2-4(1+3),8*2+-4*(1+3)",
            "-.5(3+6)cos0,-.5*(3+6)*cos0",
            "76-5log3,76+-5*log3"
    })
    void preprocessExpression(String input, String expectedOutput) {
        assertEquals(expectedOutput, Expression.preprocessExpression(input));
    }



    @Nested
    class GetTokenFromStringTests {

        @Test
        void trigTest() {
            Iterator<Token> it =  Expression.getTokensFromString("cos").iterator();
            assertEquals(it.next().getOperator(), UnaryOperator.COS);
            assertEquals(it.next().getOperator(), NullaryOperator.TERMINATOR);
            assertFalse(it.hasNext());
        }

        @Test
        void basicOperandTest() {
            Iterator<Token> it =  Expression.getTokensFromString("5").iterator();
            assertEquals(it.next().getOperand(), 5);
            assertEquals(it.next().getOperator(), NullaryOperator.TERMINATOR);
            assertFalse(it.hasNext());
        }

        @ParameterizedTest//parameter tests for hexadecimal and octal formulas
        @CsvSource({
                "0b101,5",
                "0x11,17",
                "0xA,10",
                "0o17,15"
        })
        void basicNondecimalOperandTest(String input, Double output) {
            Iterator<Token> it =  Expression.getTokensFromString(input).iterator();
            assertEquals(it.next().getOperand(), output);
            assertEquals(it.next().getOperator(), NullaryOperator.TERMINATOR);
            assertFalse(it.hasNext());
        }


        @Test
        void complicatedOperandTest() {
            Iterator<Token> it =  Expression.getTokensFromString("-5.2E-3").iterator();
            assertEquals(it.next().getOperand(), -5.2E-3);
            assertEquals(it.next().getOperator(), NullaryOperator.TERMINATOR);
            assertFalse(it.hasNext());
        }

        @Test//scientiic operand iterator
        void scientificOperandTest(){
            Iterator<Token> it = Expression.getTokensFromString("10E-10").iterator();
            assertEquals(it.next().getOperand(), 10E-10);
            assertEquals(it.next().getOperator(), NullaryOperator.TERMINATOR);
            assertFalse(it.hasNext());
        }

        @Test //subtraction test case for terminator iterator
        void subtractionTest() {
            Iterator<Token> it = Expression.getTokensFromString("5+-2").iterator();
            assertEquals(it.next().getOperand(), 5);
            assertEquals(it.next().getOperator(), BinaryOperator.ADD);
            assertEquals(it.next().getOperand(), -2);
            assertEquals(it.next().getOperator(), NullaryOperator.TERMINATOR);
            assertFalse(it.hasNext());
        }


        //Unit test for operators. A long iterator containing tokens parses for operators and asserts that they are
        //valid operators.
        @Test
        void operatorsTest() {//log/ln iterator
            Iterator<Token> it = Expression.getTokensFromString("+*/^()!sincostansin⁻¹cos⁻¹tan⁻¹loglnlg").iterator();
            assertEquals(it.next().getOperator(), BinaryOperator.ADD);
            assertEquals(it.next().getOperator(), BinaryOperator.MULTIPLY);
            assertEquals(it.next().getOperator(), BinaryOperator.DIVIDE);
            assertEquals(it.next().getOperator(), BinaryOperator.EXPONENTIATE);
            assertEquals(it.next().getOperator(), NullaryOperator.L_PAREN);
            assertEquals(it.next().getOperator(), NullaryOperator.R_PAREN);
            assertEquals(it.next().getOperator(), UnaryOperator.FACTORIAL);
            assertEquals(it.next().getOperator(), UnaryOperator.SIN);
            assertEquals(it.next().getOperator(), UnaryOperator.COS);
            assertEquals(it.next().getOperator(), UnaryOperator.TAN);
            assertEquals(it.next().getOperator(), UnaryOperator.ARCSIN);
            assertEquals(it.next().getOperator(), UnaryOperator.ARCCOS);
            assertEquals(it.next().getOperator(), UnaryOperator.ARCTAN);
            assertEquals(it.next().getOperator(), UnaryOperator.LOG_10);
            assertEquals(it.next().getOperator(), UnaryOperator.LOG_E);
            assertEquals(it.next().getOperator(), NullaryOperator.TERMINATOR);

            assertFalse(it.hasNext());
        }

        @Test
        void bitwiseOperatorsTest() {//XOR, OR, AND and shiftr/r set operator
            Iterator<Token> it = Expression.getTokensFromString("<<>>&|⊕").iterator();
            assertEquals(it.next().getOperator(), BinaryOperator.SHIFTL);
            assertEquals(it.next().getOperator(), BinaryOperator.SHIFTR);
            assertEquals(it.next().getOperator(), BinaryOperator.AND);
            assertEquals(it.next().getOperator(), BinaryOperator.OR);
            assertEquals(it.next().getOperator(), BinaryOperator.XOR);
            assertEquals(it.next().getOperator(), NullaryOperator.TERMINATOR);
            assertFalse(it.hasNext());
        }


        @Test
        void basicExpressionTest() { //basic expression tests to get tokens from string
            Iterator<Token> it = Expression.getTokensFromString("5^sin(3*4)").iterator();
            assertEquals(it.next().getOperand(), 5);
            assertEquals(it.next().getOperator(), BinaryOperator.EXPONENTIATE);
            assertEquals(it.next().getOperator(), UnaryOperator.SIN);
            assertEquals(it.next().getOperator(), NullaryOperator.L_PAREN);
            assertEquals(it.next().getOperand(), 3);
            assertEquals(it.next().getOperator(), BinaryOperator.MULTIPLY);
            assertEquals(it.next().getOperand(), 4);
            assertEquals(it.next().getOperator(), NullaryOperator.R_PAREN);
            assertEquals(it.next().getOperator(), NullaryOperator.TERMINATOR);
            assertFalse(it.hasNext());
        }

        @Test
        void basicExpressionTest2() { //More basic expression tests to get tokens from strings
            Iterator<Token> it = Expression.getTokensFromString("9^2+cos(3/2)").iterator();
            assertEquals(it.next().getOperand(), 9);
            assertEquals(it.next().getOperator(), BinaryOperator.EXPONENTIATE);
            assertEquals(it.next().getOperand(), 2);
            assertEquals(it.next().getOperator(), BinaryOperator.ADD);
            assertEquals(it.next().getOperator(), UnaryOperator.COS);
            assertEquals(it.next().getOperator(), NullaryOperator.L_PAREN);
            assertEquals(it.next().getOperand(), 3);
            assertEquals(it.next().getOperator(), BinaryOperator.DIVIDE);
            assertEquals(it.next().getOperand(), 2);
            assertEquals(it.next().getOperator(), NullaryOperator.R_PAREN);
            assertEquals(it.next().getOperator(), NullaryOperator.TERMINATOR);
            assertFalse(it.hasNext());
        }


        @Test //test case for advanced expressions with exponents, binary operators and scientific formulas
        void advancedExpressionTest() {
            Iterator<Token> it = Expression.getTokensFromString("1+23*4^5(6+-7)/8*tan(9)").iterator();
            assertEquals(it.next().getOperand(), 1);
            assertEquals(it.next().getOperator(), BinaryOperator.ADD);
            assertEquals(it.next().getOperand(), 23);
            assertEquals(it.next().getOperator(), BinaryOperator.MULTIPLY);
            assertEquals(it.next().getOperand(), 4);
            assertEquals(it.next().getOperator(), BinaryOperator.EXPONENTIATE);
            assertEquals(it.next().getOperand(), 5);
            assertEquals(it.next().getOperator(), NullaryOperator.L_PAREN);
            assertEquals(it.next().getOperand(), 6);
            assertEquals(it.next().getOperator(), BinaryOperator.ADD);
            assertEquals(it.next().getOperand(), -7);
            assertEquals(it.next().getOperator(), NullaryOperator.R_PAREN);
            assertEquals(it.next().getOperator(), BinaryOperator.DIVIDE);
            assertEquals(it.next().getOperand(), 8);
            assertEquals(it.next().getOperator(), BinaryOperator.MULTIPLY);
            assertEquals(it.next().getOperator(), UnaryOperator.TAN);
            assertEquals(it.next().getOperator(), NullaryOperator.L_PAREN);
            assertEquals(it.next().getOperand(), 9);
            assertEquals(it.next().getOperator(), NullaryOperator.R_PAREN);
            assertEquals(it.next().getOperator(), NullaryOperator.TERMINATOR);
            assertFalse(it.hasNext());
        }

    }

    @Nested //test case for factorials
    class FactorialTests {

        @ParameterizedTest
        @CsvSource({
                "0,1",
                "1,1",
                "3,6",
                "5,120"
        })
        void factorialOperationTests(Double input, Double output)
        {
            assertEquals(output, UnaryOperator.FACTORIAL.operate(input));
        }

        @ParameterizedTest//parameter test for calculator
        @CsvSource({
                "-1",
                "-5"
        })
        void factorialOperationThrowTests(Double input)
        {
            assertThrows(IllegalArgumentException.class, () -> UnaryOperator.FACTORIAL.operate(input));
        }

        /**
         * Unit test for factorials in an expression. Takes the expression and asserts that the factorial is evaluated
         * at it's proper priority (i.e. PEMDAS). If the output of the expression is equal to result, the test is
         * successful.
         *
         * @param expression - String that contains the expression to be tested
         * @param result - Double that contains the result to which the expression will be compared
         */
        @ParameterizedTest
        @CsvSource({
                "0!,1",
                "3!,6",
                "4!,24",
                "2*4!,48",
                "4!*2,48",
                "3+4!+1,28"
        })
        void factorialExpressionTests(String expression, Double result) {
            assertEquals(result, Expression.evaluate(expression));
        }
    }

    /**
     * Unit test for general expressions to assert that basic and trig functions work properly. String expression
     * is evaluated and if it equals the double result, then the test is successful.
     *
     * @param expression - String that contains the expression to be evaluated
     * @param result - Double that contains the result that the expression must equal
     */
    @ParameterizedTest
    @CsvSource({
            "2+3,5",
            "3*4,12",
            "5-2,3",
            "1/2,0.5",
            "cos0,1",
            "5/2cos0,2.5",
            "-.5(3+6),-4.5",
            "-.5(3+6)cos0,-4.5",
            "sin(-cos0/2),-0.479425538604203",
            "log4,0.6020599913279624",
            "ln10,2.302585092994046"
    })
    void evaluationTests(String expression, Double result) {
        assertEquals(result, Expression.evaluate(expression));
    }

    @ParameterizedTest //more example test cases, this time for bitwise operators
    @CsvSource({
            "1<<4,16",
            "19>>2,4",
            "0xF0|0b1110,254",
            "0b110⊕0b011,5",
            "0xFFFF&0b1010,10"
    })
    void bitwiseTests(String expression, Double result) {
        assertEquals(result, Expression.evaluate(expression));
    }
}