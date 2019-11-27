package com.moc.sharecalc.ui.programmercalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.moc.sharecalc.R;

public class ProgrammerCalculatorFragment extends Fragment {

    private ProgrammerCalculatorViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =
                ViewModelProviders.of(this).get(ProgrammerCalculatorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_programmer_calculator, container, false);
        return root;
    }
}