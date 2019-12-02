package com.moc.sharecalc.ui;

public class ShareDataSingleton {

    private String _currentInput = "";
    private String _currentResult = "";
    private static ShareDataSingleton _instance;

    public static ShareDataSingleton getInstance() {
        if (_instance == null)
            _instance = new ShareDataSingleton();
        return _instance;
    }

    public String getCurrentInput() {
        return _currentInput;
    }

    public void setCurrentInput(String _currentInput) {
        this._currentInput = _currentInput;
    }

    public String getCurrentResult() {
        return _currentResult;
    }

    public void setCurrentResult(String _currentResult) {
        this._currentResult = _currentResult;
    }
}
