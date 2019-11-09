package com.moc.sharecalc.util;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression {

    // Regular expression for a left parenthesis
    private static final String RE_LPAREN = "\\(";

    // Regular expression to match any trig function
    // Uses non-capturing groups
    static final private String RE_TRIG = "(?:sin)|(?:cos)|(?:tan)";

    // Capturing group regular expression to match the end of any number
    // (not necessarily the full number)
    static final private String RE_NUM_CAPTURE = "([0-9.])";

    /**
     * Refactor an expression string to make negation explicit
     *
     * @param expression An expression consisting of only operators and operands, no whitespace.
     * @return The expression with negation converted to explicit multiplication
     */
    String makeNegationExplicit(String expression) {

        // Replace -sin, -cos, -(...), etc. with -1*sin, -1*cos, -1*(...), etc.
        // Regex breakdown: Selects a trig function or left parentheses preceded by a minus sign.
        // -               Minus sign
        // (               Beginning of capture group
        //      RE_LPAREN  Left parentheses
        //      |          Or
        //      RE_TRIG    Any trig function
        // )               End of capture group
        final Pattern implicitMinus = Pattern.compile("-(" + RE_LPAREN + "|" + RE_TRIG + ")");
        return implicitMinus.matcher(expression)
                .replaceAll("-1*$1"); // -(capture group) -> -1*(capture group)
    }

    /**
     * Refactor an expression string to make implicit multiplication explicit
     *
     * @param expression An expression consisting of only operators and operands, no whitespace.
     * @return The expression with implicit multiplication converted to explicit multiplication
     */
    String makeMultiplicationExplicit(String expression) {

        // Replace 4sin, -3cos, 2(...), etc. with 4*sin, -3*cos, 2*(...), etc.
        //
        // Regex breakdown: Selects a trig function or left parentheses preceded by a number.
        //
        // RE_NUM_CAPTURE   Capture group to capture the (end of the) number
        // (                Beginning of second capture group
        //      RE_LPAREN   Left parentheses
        //      |           Or
        //      RE_TRIG     Any trig function
        // )                End of capture group
        //
        final Pattern implicitMult = Pattern.compile(RE_NUM_CAPTURE + "(" + RE_LPAREN + "|" + RE_TRIG + ")");
        return implicitMult.matcher(expression)
                .replaceAll("$1*$2");
    }

    /**
     * Refactor an expression string to turn subtraction into addition of a negative operand
     * so that is easier to parse.
     *
     * @param expression An expression consisting of only operators and operands, no whitespace.
     * @return The expression with subtraction converted to addition of a negative
     */
    String makeSubtractionUseAddition(String expression) {

        // For each right parentheses or number followed by a minus sign, insert a plus before
        // the minus sign. The minus sign will negate the operand that follows it.
        // Regex breakdown: Select a number or ')' followed by a minus sign.
        // ([0-9.)])         End of number or right parentheses, put in the capture group
        // -                 Minus sign
        final Pattern subtraction = Pattern.compile("([0-9.)])-");
        return subtraction.matcher(expression)
                .replaceAll("$1+-"); // (capture group)-... -> (capture group)+-...
    }

    /**
     * Preprocess an expression (e.g. by making implicit multiplication explicit) to make it
     * easier to parse.
     *
     * @param expression An expression consisting of only operators and operands, no whitespace.
     * @return The processed expression
     */
    String preprocessExpression(String expression) {
        return makeSubtractionUseAddition(
                makeMultiplicationExplicit(
                        makeNegationExplicit(expression)
                )
        );
    }
}
