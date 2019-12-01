package com.moc.sharecalc.ui.unitconverter;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;
import com.moc.sharecalc.R;
import com.moc.sharecalc.unitutil.Unit;
import com.moc.sharecalc.unitutil.UnitType;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UnitConverterFragment extends Fragment {

    private UnitConverterViewModel viewModel;
    private View root;
    private Spinner unitTypeSpinner;
    private Spinner unitSpinner;
    private TextView resultTextView;
    private MutableLiveData<UnitType> inputUnitType;
    private MutableLiveData<Unit> inputUnit;
    private MutableLiveData<Double> inputAmount;
    private EditText amountEditText;
    private boolean enableEventHandlers = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =
                ViewModelProviders.of(getActivity()).get(UnitConverterViewModel.class);
        root = inflater.inflate(R.layout.fragment_unit_converter, container, false);
        unitTypeSpinner = root.findViewById(R.id.unit_type_spinner);
        unitSpinner = root.findViewById(R.id.unit_spinner);
        resultTextView = root.findViewById(R.id.textView_result);
        amountEditText = root.findViewById(R.id.editText_amount);

        inputUnitType = viewModel.getLiveInputUnitType();
        inputUnit = viewModel.getLiveInputUnit();
        inputAmount = viewModel.getLiveInputAmount();

        setupEventHandlers();
        setupObservers();
        populateUnitTypesSpinner();
        populateUnitsSpinner();

        if (inputAmount.getValue() != null)
            amountEditText.setText(inputAmount.getValue().toString());

        enableEventHandlers = true;


        return root;
    }

    private void setupEventHandlers() {
        // Event handler: On unit type spinner selection change
        unitTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private int calls; // the number of times the handler has been called
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (calls++ > 0) { // ignore the first call (which is triggered when the event handler is created)
                    String selectedUnitType = (String) parent.getItemAtPosition(position);
                    inputUnitType.setValue(UnitType.fromString(selectedUnitType));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Event handler: On unit spinner selection change
        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedUnit = (String) parent.getItemAtPosition(position);
                inputUnit.setValue(Unit.fromString(selectedUnit));
                populateResult();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Event handler: On text input (amount) change
        amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    Double newAmount = Double.parseDouble(s.toString());
                    inputAmount.setValue(newAmount);
                } catch (NumberFormatException ex) {
                    // no change
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupObservers() {
        // Unit type observer
        inputUnitType.observe(this, new Observer<UnitType>() {
            @Override
            public void onChanged(UnitType unitType) {
                populateUnitsSpinner();
            }
        });

        // Unit observer
        inputUnit.observe(this, new Observer<Unit>() {
            @Override
            public void onChanged(Unit unit) {
                populateResult();
            }
        });

        // Amount observer
        inputAmount.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double amount) {
                populateResult();
            }
        });
    }

    private void populateUnitTypesSpinner() {
        UnitType[] types = UnitType.values();
        ArrayList<String> typeStrings = new ArrayList<String>();
        for (UnitType type : types)
        {
            typeStrings.add(type.toString());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, typeStrings);
        unitTypeSpinner.setAdapter(adapter);

        // If the view model already has a unit type selected, reflect that in the view
        try {
            final int position = typeStrings.indexOf(inputUnitType.getValue().toString());
            unitTypeSpinner.setSelection(position);
        } catch (Exception ex) { /* No value yet, ignore */ }
    }

    private void populateUnitsSpinner() {
        Unit[] units = Unit.values();
        ArrayList<String> unitStrings = new ArrayList<String>();
        for (Unit unit : units)
        {
            if (inputUnitType.getValue() == unit.getType())
                unitStrings.add(unit.toString());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, unitStrings);
        unitSpinner.setAdapter(adapter);

        // If the view model already has a unit selected, reflect that in the view
        try {
            final int position = unitStrings.indexOf(inputUnit.getValue().toString());
            unitSpinner.setSelection(position);
        } catch (Exception ex) { /* No value yet, ignore */ }
    }

    private void populateResult() {
        if (inputUnit.getValue() != null && inputAmount.getValue() != null)
            resultTextView.setText(Unit.getConversions(inputUnit.getValue(), inputAmount.getValue()));
    }

}
