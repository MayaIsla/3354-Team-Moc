package com.moc.sharecalc.util;

import java.util.regex.Pattern;

/**
 * A utility class that helps with inputting expressions
 */
public class ExpressionInputUtils {


    // Regex non-capturing group to match any **complete** operator or part of number at the start of the string
    // Used at the substring from the cursor point to the end of the string
    // The cursor can be in the middle of a number but not in the middle of an operator
    static private final String RE_VALID_AT_CURSOR = "^(?:" + Expression.RE_OPERATOR + "|[0-9A-F.\\-])";
    static final Pattern PATTERN_VALID_AT_CURSOR = Pattern.compile(RE_VALID_AT_CURSOR);

    /**
     * Given an expression string (that would be in a EditText) and a cursor position,
     * return the new cursor position if it were moved to the right,
     * making sure not to place it in the middle of an operator. For example,
     * if | was the cursor in 5|cos3, then the new cusor position would look like 5cos|3
     * @param initialCursorPos The current position, i.e. which character index the cursor is in front of
     * @param expression The expression string the cursor is in
     * @return The next valid cursor position to the right
     */
    public static int moveCursorRight(int initialCursorPos, String expression) {
        return moveCursor(initialCursorPos, expression, 1); //+1 means right
    }


    /**
     * Given an expression string (that would be in a EditText) and a cursor position,
     * return the new cursor position if it were moved to the left,
     * making sure not to place it in the middle of an operator. For example,
     * if | was the cursor in 5cos|3, then the new cusor position would look like 5|cos3
     * @param initialCursorPos The current position, i.e. which character index the cursor is in front of
     * @param expression The expression string the cursor is in
     * @return The next valid cursor position to the left
     */
    public static int moveCursorLeft(int initialCursorPos, String expression) {
        return moveCursor(initialCursorPos, expression, -1); //-1 means left
    }


    /**
     * Given an expression string (that would be in a EditText) and a cursor position,
     * return the new cursor position in a given direction (left (-1) or right (+1),
     * adjusting to make sure the cursor does not land in the middle of an operator
     * @param initialCursorPos The current position, i.e. which character index the cursor is in front of
     * @param expression The expression string the cursor is in
     * @param direction -1 if moving left, +1 if moving right
     * @return The next valid cursor position in the given direction
     */
    public static int moveCursor(int initialCursorPos, String expression, int direction) {
        int index = initialCursorPos + direction;

        // While it's invalid (but still in bounds), keep moving it to the until it is valid or hits the end
        while (index > 0 && index < expression.length() && !PATTERN_VALID_AT_CURSOR.matcher(expression.substring(index)).lookingAt())
        {
            index += direction;
        }

        // Constrain the index to be within 0 (i.e. left of the first character) to the length (i.e. after the last character)
        return Math.min(expression.length(), Math.max(0, index));
    }


    /**
     * Given an expression string (that would be in a EditText) and a cursor position,
     * adjust the return a new cursor position nearby that is not in the middle of an operator. For example,
     * if | was the cursor in 5c|os3, the new cursor position would be 5|cos3
     * @param initialCursorPos The current position, i.e. which character index the cursor is in front of
     * @param expression The expression string the cursor is in
     * @return A nearby cursor position that is valid
     */
    public static int adjustCursor(int initialCursorPos, String expression) {
        if (initialCursorPos == expression.length())
            return initialCursorPos; // no need to move if it's at the very end (edge case)

        // Move the cursor right, then left
        // e.g. |5 -> 5| -> |5 (no effect on things that aren't long operators
        // e.g. 5c|os3 -> 5cos|3 -> 5|cos3 (ensures the cursor is not in the middle of a long operator)
        int intermediatePosition = moveCursorRight(initialCursorPos, expression);
        return moveCursorLeft(intermediatePosition, expression);
    }





}
