package com.moc.sharecalc.ui.programmercalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.moc.sharecalc.R;
import com.moc.sharecalc.ui.scientificcalculator.ScientificCalculatorFragment;
import com.moc.sharecalc.ui.scientificcalculator.ScientificCalculatorViewModel;


public class ProgrammerCalculatorFragment extends ScientificCalculatorFragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewModel =
                ViewModelProviders.of(this).get(ProgrammerCalculatorViewModel.class);
        root = inflater.inflate(R.layout.fragment_programmer_calculator, container, false);
        setup();
        return root;
    }
}