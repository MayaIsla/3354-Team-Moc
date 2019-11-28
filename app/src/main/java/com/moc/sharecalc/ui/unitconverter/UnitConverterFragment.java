package com.moc.sharecalc.ui.unitconverter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.moc.sharecalc.R;
import com.moc.sharecalc.unitutil.Unit;
import com.moc.sharecalc.unitutil.UnitType;

import java.util.ArrayList;

public class UnitConverterFragment extends Fragment {

    private UnitConverterViewModel viewModel;
    private View root;
    private Spinner unitTypeSpinner;
    private Spinner unitSpinner;
    private MutableLiveData<UnitType> inputUnitType;
    private MutableLiveData<Unit> inputUnit;
    private MutableLiveData<Double> inputAmount;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =
                ViewModelProviders.of(this).get(UnitConverterViewModel.class);
        root = inflater.inflate(R.layout.fragment_unit_converter, container, false);
        unitTypeSpinner = root.findViewById(R.id.unit_type_spinner);
        unitSpinner = root.findViewById(R.id.unit_spinner);

        inputUnitType = viewModel.getLiveInputUnitType();
        inputUnit = viewModel.getLiveInputUnit();
        inputAmount = viewModel.getLiveInputAmount();

        unitTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedUnitType = (String) parent.getItemAtPosition(position);
                inputUnitType.setValue(UnitType.fromString(selectedUnitType));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Observer<UnitType> unitTypeObserver = new Observer<UnitType>() {
            @Override
            public void onChanged(UnitType unitType) {
                populateUnitsSpinner();
            }
        };
        inputUnitType.observe(this, unitTypeObserver);


        populateUnitTypesSpinner();
        return root;
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
    }

}
