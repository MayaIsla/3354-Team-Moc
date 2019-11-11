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

class ExpressionPreprocessingTest {

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
            "8*2-4(1+3),8*2+-4*(1+3)"
    })
    void preprocessExpression(String input, String expectedOutput) {
        assertEquals(expectedOutput, Expression.preprocessExpression(input));
    }


    @Nested
    class GetTokenFromStringTests {

        @Test
        void trigTest() {
            List<Token> tokens = Expression.getTokensFromString("cos");
            assertEquals(tokens.size(), 1);
            assertEquals(tokens.get(0).getOperator(), UnaryOperator.COS);
        }

        @Test
        void basicOperandTest() {
            List<Token> tokens = Expression.getTokensFromString("5");
            assertEquals(tokens.size(), 1);
            assertEquals(tokens.get(0).getOperand(), 5);
        }

        @Test
        void fractionalOperandTest() {
            List<Token> tokens = Expression.getTokensFromString(".2");
            assertEquals(tokens.size(), 1);
            assertEquals(tokens.get(0).getOperand(), .2);
        }

        @Test
        void complicatedOperandTest() {
            List<Token> tokens = Expression.getTokensFromString("-5.2E-3");
            assertEquals(tokens.size(), 1);
            assertEquals(tokens.get(0).getOperand(), -5.2E-3);
        }

        @Test
        void subtractionTest() {
            Iterator<Token> it = Expression.getTokensFromString("5+-2").iterator();
            assertEquals(it.next().getOperand(), 5);
            assertEquals(it.next().getOperator(), BinaryOperator.ADD);
            assertEquals(it.next().getOperand(), -2);
            assertFalse(it.hasNext());
        }



        @Test
        void operatorsTest() {
            Iterator<Token> it = Expression.getTokensFromString("+*/^()sincostan").iterator();
            assertEquals(it.next().getOperator(), BinaryOperator.ADD);
            assertEquals(it.next().getOperator(), BinaryOperator.MULTIPLY);
            assertEquals(it.next().getOperator(), BinaryOperator.DIVIDE);
            assertEquals(it.next().getOperator(), BinaryOperator.EXPONENTIATE);
            assertEquals(it.next().getOperator(), NullaryOperator.L_PAREN);
            assertEquals(it.next().getOperator(), NullaryOperator.R_PAREN);
            assertEquals(it.next().getOperator(), UnaryOperator.SIN);
            assertEquals(it.next().getOperator(), UnaryOperator.COS);
            assertEquals(it.next().getOperator(), UnaryOperator.TAN);
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
            assertFalse(it.hasNext());
        }

    }
}