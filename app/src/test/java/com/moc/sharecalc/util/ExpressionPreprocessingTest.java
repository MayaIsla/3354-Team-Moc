package com.moc.sharecalc.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
}