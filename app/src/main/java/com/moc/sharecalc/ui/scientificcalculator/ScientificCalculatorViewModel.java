package com.moc.sharecalc.ui.scientificcalculator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moc.sharecalc.ui.programmercalculator.ProgrammerCalculatorViewModel;
import com.moc.sharecalc.util.Expression;

public class ScientificCalculatorViewModel extends ViewModel {

    private MutableLiveData<String> _liveExpressionInput;
    private MutableLiveData<String> _liveResult;

    /**
     * This method gives an immutable LiveData of the user's input
     *
     * @return _liveExpressionInput - LiveData<String> That contains the expression the user is inputting
     */
    public LiveData<String> getLiveExpressionInput() {
        if (_liveExpressionInput == null) {
            _liveExpressionInput = new MutableLiveData<String>();
            _liveExpressionInput.setValue("");
        }
        return _liveExpressionInput;
    }

    /**
     * Returns an immutable LiveData of the output
     * @return the live result of the expression
     */
    public LiveData<String> getLiveResult() {
        if (_liveResult == null) {
            _liveResult = new MutableLiveData<>();
            _liveResult.setValue("");
        }
        return _liveResult;
    }

    /**
     * Clears the expression input
     */
    public void clearExpression() {setExpression("");}

    /**
     * Changes the expression input
     * @param expression the new expression input
     */
    public void setExpression(String expression) {
        ((MutableLiveData<String>)getLiveExpressionInput()).setValue(expression);
        String output = getOutputFromInput(expression);
        ((MutableLiveData<String>)getLiveResult()).setValue(output);
    }

    /**
     * Evaluates and formats an expression input
     * @param input the expression input from the user
     * @return the calculated output or empty string
     */
    protected String getOutputFromInput(String input) {
        String output = "";
        try {
            output = "= "+Expression.evaluate(input);
        } catch (Exception ex) {
            // Invalid input? Ignore, leave output as empty.
        }
        return output;
    }
}
