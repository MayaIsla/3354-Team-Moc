package com.moc.sharecalc.ui.programmercalculator;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moc.sharecalc.ui.scientificcalculator.ScientificCalculatorViewModel;
import com.moc.sharecalc.util.Expression;

public class ProgrammerCalculatorViewModel extends ScientificCalculatorViewModel {

    @Override
    protected String getOutputFromInput(String input) {
        String output = "";
        try {
            int result = (int) Expression.evaluate(input).doubleValue();
            output += "= " + result;
            output += "\n= 0b" + Integer.toBinaryString(result);
            output += "\n= 0x" + Integer.toHexString(result);
            output += "\n= 0o" + Integer.toOctalString(result);
        } catch (Exception ex) {
            // Invalid input? Ignore, leave output as empty.
        }
        return output;
    }

}
