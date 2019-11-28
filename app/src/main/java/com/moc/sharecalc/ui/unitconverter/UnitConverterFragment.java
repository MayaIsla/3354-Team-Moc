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

        Spinner unitTypeSpinner = (Spinner)root.findViewById(R.id.unit_type_spinner);
        Spinner convertOptionsSpinner = (Spinner)root.findViewById(R.id.convert_options);

        // dynamically set contents of second spinner
        unitTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()  {
            @Override
            public void onItemSelected(AdapterView<?> arg, View view, int position, long id)  {
                System.out.println(position);
                switch (position)  {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                                getActivity().getBaseContext(),
                                R.array.temperature_options,
                                android.R.layout.simple_spinner_item);
                        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        convertOptionsSpinner.setAdapter(spinnerAdapter);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg)  {

            }
        });

        return root;
    }
}