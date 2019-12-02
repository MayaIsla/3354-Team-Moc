package com.moc.sharecalc.ui.scientificcalculator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moc.sharecalc.util.Expression;

public class ScientificCalculatorViewModel extends ViewModel {

    private MutableLiveData<String> _liveExpressionInput;
    private MutableLiveData<String> _liveResult;

    public LiveData<String> getLiveExpressionInput() {
        if (_liveExpressionInput == null) {
            _liveExpressionInput = new MutableLiveData<String>();
            _liveExpressionInput.setValue("");
        }
        return _liveExpressionInput;
    }

    public LiveData<String> getLiveResult() {
        if (_liveResult == null) {
            _liveResult = new MutableLiveData<>();
            _liveResult.setValue("");
        }
        return _liveResult;
    }

    public void clearExpression() {setExpression("");}

    public void setExpression(String expression) {
        ((MutableLiveData<String>)getLiveExpressionInput()).setValue(expression);

        String output = "";
        try {
            output = ""+Expression.evaluate(expression);
        } catch (Exception ex) {
            // Invalid input? Ignore, leave output as "".
        }

        ((MutableLiveData<String>)getLiveResult()).setValue(output);
    }
}
