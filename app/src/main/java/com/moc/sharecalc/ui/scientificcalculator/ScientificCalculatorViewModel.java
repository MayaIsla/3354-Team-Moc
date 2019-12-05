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
     * This method checks if _liveExpressionInput is null, then returns the variable
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

    //Returns the _liveResult, if it is null then it returns a whitespace, which is not null
    public LiveData<String> getLiveResult() {
        if (_liveResult == null) {
            _liveResult = new MutableLiveData<>();
            _liveResult.setValue("");
        }
        return _liveResult;
    }

    //Clears the LiveData<String>
    public void clearExpression() {setExpression("");}

    //Takes the string expression, sets getLiveExpressionInput to that value, and is evaluated
    public void setExpression(String expression) {
        ((MutableLiveData<String>)getLiveExpressionInput()).setValue(expression);
        String output = getOutputFromInput(expression);
        ((MutableLiveData<String>)getLiveResult()).setValue(output);
    }

    //Automatically print the output if the input is valid
    //If input is invalid, no output is displayed
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
