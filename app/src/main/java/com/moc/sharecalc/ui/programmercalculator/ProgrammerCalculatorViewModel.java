package com.moc.sharecalc.ui.programmercalculator;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moc.sharecalc.ui.scientificcalculator.ScientificCalculatorViewModel;
import com.moc.sharecalc.util.Expression;

public class ProgrammerCalculatorViewModel extends ScientificCalculatorViewModel {

    /**
     * Method that evaluates the argument and displays the output in proper format
     *
     * @param input - String that contains the expression to be evaluated
     * @return output - Displays the output of the input String
     */
    @Override
    protected String getOutputFromInput(String input) {
        String output = "";

        //Automatically display the output in decimal, binary, hex, and octal when input is valid, respectively.
        //Decimal and binary will always print on the first 2 lines
        //Hex and octal will always print on the second 2 lines
        try {
            int result = (int) Expression.evaluate(input).doubleValue();
            output += "= " + result;
            output += "  =  0b" + Integer.toBinaryString(result);
            output += "\n  =  0x" + Integer.toHexString(result);
            output += "  =  0o" + Integer.toOctalString(result);
        } catch (Exception ex) {
            // Invalid input? Ignore, leave output as empty.
        }
        return output;
    }

}
