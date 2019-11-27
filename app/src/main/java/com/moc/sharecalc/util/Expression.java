package com.moc.sharecalc.util;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;

public class Expression {
    // The two parts of ⁻¹, used in cos⁻¹, sin⁻¹, etc. to denote inverse trig functions
    static final char INVERSE_INDICATOR_NEGATIVE = '⁻';
    static final char INVERSE_INDICATOR_ONE = '¹';

    // Regular expression for a left parenthesis
    private static final String RE_LPAREN = "\\(";

    // Regular expression to match any trig function
    // Uses non-capturing groups
    static final private String RE_TRIG = "(?:sin)|(?:cos)|(?:tan)";


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
        // (                Beginning of first capture grouo
        //     [0-9.)]      Capture a digit or dot (i.e. end of number) or right parenthesis (e.g. end of nested expression)
        // )                End of first capture group
        // (                Beginning of second capture group
        //      RE_LPAREN   Left parentheses
        //      |           Or
        //      RE_TRIG     Any trig function
        // )                End of capture group
        //
        final Pattern implicitMult = Pattern.compile("([0-9.)])" + "(" + RE_LPAREN + "|" + RE_TRIG + ")");
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
     * Given a iterator of characters, asserts that the next characters match those given in
     * a string.
     * POSTCONDITION: If the characters did match the given string, the iterator's position
     * will have mutated, i.e. it will now be positioned on the last character that matches so that
     * the next call to next() will give the first character not in the match string. If the characters
     * did not match the given string, the state of the iterator is indeterminate.
     * @param it The character iterator to assert has the given characters
     * @param chars The string of characters the iterator's next characters should match
     * @throws AssertionError the next characters do not match the given string
     */
    private static void expectChars(CharacterIterator it, String chars) throws AssertionError {
        for (char ch : chars.toCharArray())
        {
            if (it.next() != ch)
            {
                throw new AssertionError();
            }
        }
    }

    /**
     * Returns whether the next characters in the character iterator match the inverse indicator (^-1)
     * PRECONDITION: There is at least one character left in the iterator
     * POSTCONDITION A: If the next characters match, the iterator will be placed at the end of the
     * match such that another call to next() will give the first character after the match
     * POSTCONDITION B: If the next characters match partially (i.e. the superscript - but not the superscript 1),
     * an exception will be thrown.
     * POSTCONDITION C: If the next characters do not match, the iterator will be placed back where it
     * was before the function call.
     * @param it character iterator to look for the inverse indicator
     * @return Whether the next characters in the iterator match the inverse indicator
     * @throws IllegalArgumentException the characters are malformed (i.e. partially match the target)
     */
    private static boolean nextIsInverseIndicator(CharacterIterator it) throws IllegalArgumentException {
        char firstChar = it.next();
        if (firstChar == INVERSE_INDICATOR_NEGATIVE)
        {
            if (it.next() != INVERSE_INDICATOR_ONE) // if malformed/partial match
            {
                throw new IllegalArgumentException();
            } else {
                return true; // full match
            }
        }
        else {
            // match failed; move iterator back to starting position
            it.previous();
            return false;
        }
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

                // Bitwise operators
                case '⊕':
                    list.add(new Token(BinaryOperator.XOR));
                    break;
                case '&':
                    list.add(new Token(BinaryOperator.AND));
                    break;
                case '|':
                    list.add(new Token(BinaryOperator.OR));
                    break;
                case '<':  // <<
                    if (it.next() != '<')
                        throw new IllegalArgumentException("Expected '<<'");
                    else
                        list.add(new Token(BinaryOperator.SHIFTL));
                    break;
                case '>':  // >>
                    if (it.next() != '>')
                        throw new IllegalArgumentException("Expected '>>'");
                    else
                        list.add(new Token(BinaryOperator.SHIFTR));
                    break;


                // Nullary operators
                case '(':
                    list.add(new Token(NullaryOperator.L_PAREN));
                    break;
                case (')'):
                    list.add(new Token(NullaryOperator.R_PAREN));
                    break;

                //Unary operators
                case ('!'):
                    list.add(new Token(UnaryOperator.FACTORIAL));
                    break;
                case ('c'): // cos
                    expectChars(it, "os"); //remaining characters in 'cos'
                    // see whether there is an inverse indicator (^-1) afterwards
                    if (nextIsInverseIndicator(it))
                        list.add(new Token(UnaryOperator.ARCCOS));
                    else
                        list.add(new Token(UnaryOperator.COS));
                    break;
                case ('s'): // sin
                    expectChars(it, "in"); //remaining characters in 'sin'
                    if (nextIsInverseIndicator(it))
                        list.add(new Token(UnaryOperator.ARCSIN));
                    else
                        list.add(new Token(UnaryOperator.SIN));
                    break;
                case ('t'): //tan
                    expectChars(it, "an"); //remaining characters in 'tan'
                    if (nextIsInverseIndicator(it))
                        list.add(new Token(UnaryOperator.ARCTAN));
                    else
                        list.add(new Token(UnaryOperator.TAN));                    break;
                case (' '): //whitespace (illegal)
                    throw new IllegalArgumentException("Whitespace not allowed in expression");

                default: //expect number
                    // first, extract it
                    String extractedNumber = "";
                    while (ch != StringCharacterIterator.DONE && (ch == 'b' || ch == 'o' || ch == 'x' // Base indicator (e.g. in 0x...)
                            || (ch >= 'A' && ch <= 'F') // hex A-F
                            || ch == '-' || ch == '.' || ch == 'E' || Character.isDigit(ch))) {
                        // while there is still part of the number left to add
                        extractedNumber += ch;
                        ch = it.next();
                    }
                    it.previous();
                    // went one character too far (i.e. the character after
                    // the last character in the number)

                    if (extractedNumber.length() > 2) // Possibility of it being 0x..., 0b..., etc.
                    {
                        String possibleBaseIndicator = extractedNumber.substring(0,2);
                        String restOfString = extractedNumber.substring(2);

                        if (possibleBaseIndicator.equals("0b")) //binary
                        {
                            // Parse everything after the 0b as a binary integer, then cast to double
                            list.add(new Token((double) Integer.parseInt(restOfString, 2)));
                        }
                        else if (possibleBaseIndicator.equals("0o")) //octal
                        {
                            list.add(new Token((double) Integer.parseInt(restOfString, 8)));
                        }
                        else if (possibleBaseIndicator.equals("0x")) //hexadecimal
                        {
                            list.add(new Token((double) Integer.parseInt(restOfString, 16)));
                        }
                        else // decimal
                        {
                            // Interpret the entire string as a double
                            list.add(new Token(Double.parseDouble(extractedNumber)));
                        }
                    }
                    else
                    {
                        // if the number string is 2 characters or fewer, it cannot be a valid non-decimal number
                        // so parse it as a double
                        list.add(new Token(Double.parseDouble(extractedNumber)));
                    }


            }
        }
        list.add(new Token(NullaryOperator.TERMINATOR));
        // Add terminator ($) as last token to indicate
        // end of expression
        return list;
    }

    /**
     * Internal function to evaluate an expression in the form of a token list.
     * Expression must be valid and preprocessed (i.e. no implicit multiplication or negation)
     *
     * @param tokenList a list of tokens in the same order (i.e. left to right) as the expression
     * @return the result of the computation
     * @throws Exception an internal error from trying to evaluate an invalid input
     */
    static Double evaluate(List<Token> tokenList) throws Exception {

        Iterator<Token> it = tokenList.iterator();
        Stack<Operator> operatorStack = new Stack<>();
        Stack<Double> operandStack = new Stack<>();

        do {
            Token tok = it.next();

            if (tok.isOperand())
                operandStack.add(tok.getOperand());
            else // is Operator
            {
                Operator op = tok.getOperator();

                if (op == NullaryOperator.L_PAREN) {
                    // Always push on left parentheses
                    operatorStack.push(op);
<<<<<<< Updated upstream
                } else if (op == UnaryOperator.FACTORIAL) {
                    // Special case: Factorial should always be evaluated immediately (since it
                    // follows the operand instead of precedes it) instead of being pushed on to
                    // the operator stack.
                    Double operand = operandStack.pop();
                    Double result = UnaryOperator.FACTORIAL.operate(operand);
                    operandStack.push(result);
                } else if (operatorStack.size() == 0 || op.getPriority() > operatorStack.peek().getPriority()) {
                    // Always add Operators of higher priority so that they are evaluated (pushed off) first
                    // and also add if there are no Operators on the stack
=======
<<<<<<< HEAD
                } else if (operatorStack.size() == 0 || op.getPriority() > operatorStack.peek().getPriority()) {
                    // Always add Operators of higher priority so that they are evaluated (pushed off) first
                    // and also add if there are no Operators on the stack
                    operatorStack.push(op);
=======
                } else if (op == UnaryOperator.FACTORIAL) {
                    // Special case: Factorial should always be evaluated immediately (since it
                    // follows the operand instead of precedes it) instead of being pushed on to
                    // the operator stack.
                    Double operand = operandStack.pop();
                    Double result = UnaryOperator.FACTORIAL.operate(operand);
                    operandStack.push(result);
                } else if (operatorStack.size() == 0 || op.getPriority() > operatorStack.peek().getPriority()) {
                    // Always add Operators of higher priority so that they are evaluated (pushed off) first
                    // and also add if there are no Operators on the stack
>>>>>>> Stashed changes

                    // Note the special case: there are no operators in the expression, only the terminator
                    // This case can only occur when there are only factorial operators because
                    // they skip the operator stack and operate immediately when they are encountered
                    // In this case when we reach the terminator we should just have one operand on the
                    // operand stack which should be the result

                    if (op == NullaryOperator.TERMINATOR)
                    {
                        if (operandStack.size() != 1)
                        {
                            throw new IllegalArgumentException();
                        }
                        else
                        {
                            return operandStack.pop();
                        }
                    } else {
                        operatorStack.push(op);
                    }
<<<<<<< Updated upstream
=======
>>>>>>> 405381ecc43cb0e14c16d30d8a5e1aa6d39b0518
>>>>>>> Stashed changes
                } else // Operator has lower or equal priority than the head of the stack
                {
                    // Pop and evaluate while the operator has lower prcedence than the
                    // head of the stack

                    while (!operatorStack.empty() && op.getPriority() <= operatorStack.peek().getPriority()) {
                        // Pop off the operator on the head of the stack (which has greater priority) and execute it.
                        Operator priorityOp = operatorStack.pop();

                        if (priorityOp.getArity() == 2) {
                            // Get the rightmost argument (which was added second)
                            Double rArg = operandStack.pop();
                            // Get the leftmost argument (which was added first)
                            Double lArg = operandStack.pop();

                            // Push result on to stack
                            Double result = ((BinaryOperator) priorityOp).operate(lArg, rArg);
                            operandStack.push(result);
                        } else if (priorityOp.getArity() == 1) {
                            // Get the argument to operate on
                            Double arg = operandStack.pop();

                            // Push result on to stack
                            Double result = ((UnaryOperator) priorityOp).operate(arg);
                            operandStack.push(result);
                        }
                        // Nullary operators do not operate on any operands.
                        // However, if the popped operator is a left parentheses,
                        // which is only possible if op is a right parentheses b/c
                        // ) is the only operator with lower precedence than (,
                        // then we should manually stop the loop.
                        // Note that this break only breaks the inner loop
                        if (priorityOp == NullaryOperator.L_PAREN)
                            break;
                    }
                    // After the operators with higher and equal priority have been popped off,
                    // we can finally push on op

                    operatorStack.push(op);

                    // We are done if the only thing on the stack now is the terminator.
                    // Note that if there is anything else on the stack, the input was invalid.
                    // There also should only be only 1 operand on the operand stack, i.e. the result
                    if (op == NullaryOperator.TERMINATOR) {
                        if (operatorStack.size() == 1 && operandStack.size() == 1)
                            return operandStack.pop();
                        else
                            throw new IllegalArgumentException();
                    }
                }
            }
        } while (it.hasNext());

        // Proper input should never reach this point
        throw new IllegalArgumentException();
    }

    /**
     * Evaluates an expression in string form.
     * Expression may have implicit negation and multiplication; the function will
     * preprocess it.
     * Expression may not have any whitespace and must be an otherwise valid mathematical
     * expression using only the supported operators.
     *
     * @param expression The string expression to evaluate
     * @return the result of the evaluation of the expression
     * @throws IllegalArgumentException Thrown if the expression is invalid
     */
    public static Double evaluate(String expression) throws IllegalArgumentException {
        String preprocessedString = preprocessExpression(expression);
        List<Token> tokenList = getTokensFromString(preprocessedString);
        try {
            return evaluate(tokenList);
        } catch (Exception e) { //could be a number of exceptions, including IllegalArgumentException, EmptyStackException, etc.
            throw new IllegalArgumentException(); // the caller only needs to know that the input was invalid.
        }
    }
}
