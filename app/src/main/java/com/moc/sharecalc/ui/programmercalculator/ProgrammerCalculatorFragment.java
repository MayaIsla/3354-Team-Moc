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

    protected void addFragmentSpecificButtonListeners() {
        root.findViewById(R.id.btn_left_parenthesis).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_right_parenthesis).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_left_shift).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_right_shift).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_OR).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_AND).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_XOR).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_0a).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_0x).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_0b).setOnClickListener(this::onInsertableButtonClick);
    }

    protected void addListenersToNumberButtons() {
        super.addListenersToNumberButtons();
        root.findViewById(R.id.btn_A).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_B).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_C).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_D).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_E).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_F).setOnClickListener(this::onInsertableButtonClick);
    }

}