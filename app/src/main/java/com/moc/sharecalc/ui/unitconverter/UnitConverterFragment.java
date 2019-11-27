package com.moc.sharecalc.ui.unitconverter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.moc.sharecalc.R;

public class UnitConverterFragment extends Fragment {

    private UnitConverterViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =
                ViewModelProviders.of(this).get(UnitConverterViewModel.class);
        View root = inflater.inflate(R.layout.fragment_unit_converter, container, false);
        return root;
    }
}