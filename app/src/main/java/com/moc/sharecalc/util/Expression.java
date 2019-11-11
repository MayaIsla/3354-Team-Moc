package com.moc.sharecalc.util;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
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
    static String makeNegationExplicit(String expression) {

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
    static String makeMultiplicationExplicit(String expression) {

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
    static String makeSubtractionUseAddition(String expression) {

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
    static String preprocessExpression(String expression) {
        return makeSubtractionUseAddition(
                makeMultiplicationExplicit(
                        makeNegationExplicit(expression)
                )
        );
    }

    /**
     * Given an expression string, return a list that contains Operators and Doubles (operands)
     * in the order they appear in the string.
     * PRECONDITION: Input string MUST be a valid expression (no whitespace, operands and operators
     * only) and should be passed through preprocessExpression first.
     * Values in the queue should be casted to Doubles and Operators.
     *
     * @param str A valid, preprocessed expression string
     * @return A list of Operators and Double operands
     */
    static List<Token> getTokensFromString(String str) {
        CharacterIterator it = new StringCharacterIterator(str);
        Scanner doubleScanner = new Scanner(str);
        List<Token> list = new LinkedList<>();

        for (char ch = it.first(); ch != StringCharacterIterator.DONE; ch = it.next()) {
            switch (ch) {
                //Binary operators
                case '+':
                    list.add(new Token(BinaryOperator.ADD));
                    break;
                case '*':
                    list.add(new Token(BinaryOperator.MULTIPLY));
                    break;
                case '/':
                    list.add(new Token(BinaryOperator.DIVIDE));
                    break;
                case '^':
                    list.add(new Token(BinaryOperator.EXPONENTIATE));
                    break;

                // Nullary operators
                case '(':
                    list.add(new Token(NullaryOperator.L_PAREN));
                    break;
                case (')'):
                    list.add(new Token(NullaryOperator.R_PAREN));
                    break;

                //Unary (trig) operators
                case ('c'): // cos
                    if (it.next() != 'o' || it.next() != 's')
                        throw new IllegalArgumentException("Expected 'cos'");
                    else
                        list.add(new Token(UnaryOperator.COS));
                    break;
                case ('s'): // sin
                    if (it.next() != 'i' || it.next() != 'n')
                        throw new IllegalArgumentException("Expected 'sin'");
                    else
                        list.add(new Token(UnaryOperator.SIN));
                    break;
                case ('t'): //tan
                    if (it.next() != 'a' || it.next() != 'n')
                        throw new IllegalArgumentException("Expected 'tan'");
                    else
                        list.add(new Token(UnaryOperator.TAN));
                    break;
                case (' '): //whitespace (illegal)
                    throw new IllegalArgumentException("Whitespace not allowed in expression");

                default: //expect number
                    // first, extract it
                    String extractedNumber = "";
                    while (ch != StringCharacterIterator.DONE && (ch == '-' || ch == '.' || ch == 'E' || Character.isDigit(ch))) {
                        // while there is still part of the number left to add
                        extractedNumber += ch;
                        ch = it.next();
                    }
                    it.previous();
                    // went one character too far (i.e. the character after
                    // the last character in the number)

                    list.add(new Token(Double.parseDouble(extractedNumber)));
            }
        }
        return list;
    }

}
