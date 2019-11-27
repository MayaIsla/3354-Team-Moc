package com.moc.sharecalc.ui.scientificcalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.moc.sharecalc.R;

public class ScientificCalculatorFragment extends Fragment {

    private ScientificCalculatorViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =
                ViewModelProviders.of(this).get(ScientificCalculatorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_scientific_calculator, container, false);
        return root;
    }
}