package com.moc.sharecalc.ui.unitconverter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moc.sharecalc.unitutil.Unit;
import com.moc.sharecalc.unitutil.UnitType;

public class UnitConverterViewModel extends ViewModel {

    /**
     * The live input type category (e.g. length, temperature, etc)
     */
    private MutableLiveData<UnitType> _inputUnitType;

    /**
     * The input unit (e.g. feet, inches, etc.)
     */
    private MutableLiveData<Unit> _inputUnit;

    /**
     * The input amount (e.g. the 5.0 in 5.0 meters)
     */
    private MutableLiveData<Double> _inputAmount;

    /**
     * Gets the live, mutable type category (e.g. length, temperature, etc.)
     * @return the mutable, live input unit type
     */
    public MutableLiveData<UnitType> getLiveInputUnitType() {
        if (_inputUnitType == null) {
            _inputUnitType = new MutableLiveData<>();
        }
        return _inputUnitType;
    }

    /**
     * Gets the live, mutable input unit (Feet, Inches, etc.)
     * @return the mutable, live input unit
     */
    public MutableLiveData<Unit> getLiveInputUnit() {
        if (_inputUnit == null) {
            _inputUnit = new MutableLiveData<>();
        }
        return _inputUnit;
    }

    /**
     * Gets the live, mutable input amount (e.g. the 5.0 in 5.0 inches)
     * @return the mutable, live input amount
     */
    public MutableLiveData<Double> getLiveInputAmount() {
        if (_inputAmount == null) {
            _inputAmount = new MutableLiveData<>();
        }
        return _inputAmount;
    }

}
