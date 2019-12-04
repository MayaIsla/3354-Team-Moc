package com.moc.sharecalc.ui;

//Singleton design pattern
public class ShareDataSingleton {

    private String _currentInput = "";
    private String _currentResult = "";
    private static ShareDataSingleton _instance;

    /**
     * The method checks that the instantiated ShareDataSingleton
     * exists, if it doesn't, one is created. Doesn't allow for more than one instance
     * of ShareDataSingleton
     *
     * @return The single instance of ShareDataSingleton
     */
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
