package com.moc.sharecalc.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;



import static org.junit.jupiter.api.Assertions.*;

class ExpressionInputUtilsTests {


    // Tests the regex that determines whether a expression is valid at its start (i.e. the string starts where the cursor is)
    @ParameterizedTest
    @CsvSource({
            "234,true",
            "cos7,true",
            ".2,true",
            "-4,true",
            "sin,true",
            "in,false",
            "<,false",
            "<<,true"
    })
    void regexValidatorTests(String input, boolean expectMatches) {
        assertEquals(expectMatches, ExpressionInputUtils.PATTERN_VALID_AT_CURSOR.matcher(input).lookingAt());
    }


    @ParameterizedTest
    @CsvSource({
            "'',0,0", // empty string
            "+,0,1", // |+ -> +|
            "+,1,1", // +| -> +|
            "cos,0,3", // |cos -> cos|
            "cos,1,3",  // c|os -> cos|
            "cos5,1,3",  // c|os5 -> cos|5
            "+23,1,2", // +|23 -> +2|3
            "+cos3,1,4", // +|cos3 -> +cos|3
            "+cos3,2,4",  // +c|os3 -> +cos|3
            "sin⁻¹,0,5"  // |sin⁻¹ -> sin⁻¹|
    })
    void moveCursorRight(String expression, int cursorInputPos, int cursorOutputPos) {
        assertEquals(cursorOutputPos, ExpressionInputUtils.moveCursorRight(cursorInputPos, expression));
    }

    @ParameterizedTest
    @CsvSource({
            "'',0,0", // empty string
            "+,0,0", // |+ -> |+
            "+,1,0", // +| -> |+
            "cos,3,0", // cos| -> |cos
            "cos,2,0",  // co|s -> |cos
            "cos5,4,3",  // cos5| -> cos|5
            "+23,2,1", // +2|3 -> +|23
            "+cos3,4,1", // +cos|3 -> +|cos3
            "+cos3,3,1",  // +co|s3 -> +|cos3
            "sin⁻¹,5,0"  // sin⁻¹| -> |sin⁻¹
    })
    void moveCursorLeft(String expression, int cursorInputPos, int cursorOutputPos) {
        assertEquals(cursorOutputPos, ExpressionInputUtils.moveCursorLeft(cursorInputPos, expression));
    }

}