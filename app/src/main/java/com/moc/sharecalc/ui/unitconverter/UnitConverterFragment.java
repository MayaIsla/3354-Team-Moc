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
import androidx.lifecycle.ViewModelProviders;

import com.moc.sharecalc.R;

import java.util.Arrays;
import java.util.List;

public class UnitConverterFragment extends Fragment {

    private UnitConverterViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =
                ViewModelProviders.of(this).get(UnitConverterViewModel.class);
        View root = inflater.inflate(R.layout.fragment_unit_converter, container, false);

        setUnitOptions(root);

        return root;
    }

    public void setUnitOptions(View root)  {
        Spinner unitTypeSpinner = root.findViewById(R.id.unit_type_spinner);
        Spinner convertOptionsSpinner = root.findViewById(R.id.convert_options);

        // dynamically set contents of second spinner

        unitTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()  {
            @Override
            public void onItemSelected(AdapterView<?> arg, View view, int position, long id)  {
                ArrayAdapter<CharSequence> spinnerAdapter = getUnitOptions(position);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                convertOptionsSpinner.setAdapter(spinnerAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg)  {

            }
        });
    }

    public ArrayAdapter getUnitOptions(int position)  {
        switch (position)  {
            case 1:
                return ArrayAdapter.createFromResource(
                        getActivity().getBaseContext(),
                        R.array.volume_options,
                        android.R.layout.simple_spinner_item);
            case 2:
                return ArrayAdapter.createFromResource(
                        getActivity().getBaseContext(),
                        R.array.length_options,
                        android.R.layout.simple_spinner_item);
            case 3:
                return ArrayAdapter.createFromResource(
                        getActivity().getBaseContext(),
                        R.array.weight_mass_options,
                        android.R.layout.simple_spinner_item);
            case 4:
                return ArrayAdapter.createFromResource(
                        getActivity().getBaseContext(),
                        R.array.temperature_options,
                        android.R.layout.simple_spinner_item);
        }
        return ArrayAdapter.createFromResource(
                getActivity().getBaseContext(),
                R.array.default_units,
                android.R.layout.simple_spinner_item);
    }
}
