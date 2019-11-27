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

    @ParameterizedTest
    @CsvSource({
            "-sin(5),-1*sin(5)",
            "3*-tan(2),3*-1*tan(2)"
    })
    void makeNegationExplicitTest(String input, String expectedOutput) {
        assertEquals(expectedOutput, Expression.makeNegationExplicit(input));
    }


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
            "-.5(3+6)cos0,-.5*(3+6)*cos0"
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

        @ParameterizedTest
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

        @Test
        void subtractionTest() {
            Iterator<Token> it = Expression.getTokensFromString("5+-2").iterator();
            assertEquals(it.next().getOperand(), 5);
            assertEquals(it.next().getOperator(), BinaryOperator.ADD);
            assertEquals(it.next().getOperand(), -2);
            assertEquals(it.next().getOperator(), NullaryOperator.TERMINATOR);
            assertFalse(it.hasNext());
        }



        @Test
        void operatorsTest() {
            Iterator<Token> it = Expression.getTokensFromString("+*/^()!sincostansin⁻¹cos⁻¹tan⁻¹").iterator();
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
            assertEquals(it.next().getOperator(), NullaryOperator.TERMINATOR);
            assertFalse(it.hasNext());
        }

        @Test
        void bitwiseOperatorsTest() {
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
        void basicExpressionTest() {
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

    @Nested
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

        @ParameterizedTest
        @CsvSource({
                "-1",
                "-5"
        })
        void factorialOperationThrowTests(Double input)
        {
            assertThrows(IllegalArgumentException.class, () -> UnaryOperator.FACTORIAL.operate(input));
        }

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
            "sin(-cos0/2),-0.479425538604203"
    })
    void evaluationTests(String expression, Double result) {
        assertEquals(result, Expression.evaluate(expression));
    }

    @ParameterizedTest
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