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
                } else if (operatorStack.size() == 0 || op.getPriority() > operatorStack.peek().getPriority()) {
                    // Always add Operators of higher priority so that they are evaluated (pushed off) first
                    // and also add if there are no Operators on the stack
                    operatorStack.push(op);
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
