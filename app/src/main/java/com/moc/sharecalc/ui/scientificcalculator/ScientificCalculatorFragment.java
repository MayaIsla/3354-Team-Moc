package com.moc.sharecalc.ui.scientificcalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.moc.sharecalc.R;
import com.moc.sharecalc.ui.ShareDataSingleton;
import com.moc.sharecalc.ui.programmercalculator.ProgrammerCalculatorViewModel;
import com.moc.sharecalc.util.ExpressionInputUtils;


public class ScientificCalculatorFragment extends Fragment {

    protected ScientificCalculatorViewModel viewModel;
    protected EditText expressionEditText;
    protected TextView resultTextView;
    protected LiveData<String> liveResult;
    protected View root;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewModel =
                ViewModelProviders.of(this).get(ScientificCalculatorViewModel.class);
        root = inflater.inflate(R.layout.fragment_scientific_calculator, container, false);
        setup();
        return root;
    }

    public void onInsertableButtonClick(View v) {
        expressionEditText.requestFocus();
        Button button = (Button) v;
        int cursorPosition = ExpressionInputUtils.adjustCursor(expressionEditText.getSelectionStart(), expressionEditText.getText().toString());
        expressionEditText.getText().insert(cursorPosition, button.getText());
        viewModel.setExpression(expressionEditText.getText().toString());
    }

    //Assign listeners for all the operation buttons in this instance
    protected void addFragmentSpecificButtonListeners() {
        root.findViewById(R.id.btn_left_parenthesis).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_right_parenthesis).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_log).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_ln).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_plus).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_minus).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_multiplication).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_division).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_power).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_factorial).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_sin).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_sinInverse).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_cos).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_cosInverse).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_tan).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_tanInverse).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_dot).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_left_parenthesis).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_right_parenthesis).setOnClickListener(this::onInsertableButtonClick);

    }

    //Assign listeners for all the number buttons in this instance
    protected void addListenersToNumberButtons() {
        root.findViewById(R.id.btn_one).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_two).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_three).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_four).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_five).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_six).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_seven).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_eight).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_nine).setOnClickListener(this::onInsertableButtonClick);
        root.findViewById(R.id.btn_zero).setOnClickListener(this::onInsertableButtonClick);
    }

    /**
     * In here the buttons pertaining to move left, move right, delete, and clear are instantiated.
     * liveResult updates the viewModel of the result accordingly to the changes.
     */
    protected void setup() {
        expressionEditText = root.findViewById(R.id.editText_scientific_expression);
        resultTextView = root.findViewById(R.id.textView_scientific_answer);
        expressionEditText.setShowSoftInputOnFocus(false);
        expressionEditText.requestFocus();
        expressionEditText.setSelection(0);


        liveResult = viewModel.getLiveResult();

        addFragmentSpecificButtonListeners();
        addListenersToNumberButtons();


        root.findViewById(R.id.btn_move_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newCursorPosition = ExpressionInputUtils.moveCursorLeft(expressionEditText.getSelectionStart(), expressionEditText.getText().toString());
                expressionEditText.setSelection(newCursorPosition);
            }
        });

        root.findViewById(R.id.btn_move_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newCursorPosition = ExpressionInputUtils.moveCursorRight(expressionEditText.getSelectionStart(), expressionEditText.getText().toString());
                expressionEditText.setSelection(newCursorPosition);
            }
        });

        root.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldExpression = expressionEditText.getText().toString();
                int deleteEnd = expressionEditText.getSelectionStart();
                int deleteStart = ExpressionInputUtils.moveCursorLeft(deleteEnd, oldExpression);
                viewModel.setExpression(oldExpression.substring(0,deleteStart)+oldExpression.substring(deleteEnd));
                expressionEditText.setText(viewModel.getLiveExpressionInput().getValue());
                expressionEditText.setSelection(deleteStart);
            }
        });

        root.findViewById(R.id.btn_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.clearExpression();
                expressionEditText.setText("");
            }
        });





        liveResult.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                resultTextView.setText(s);
                ShareDataSingleton.getInstance().setCurrentInput(expressionEditText.getText().toString());
                ShareDataSingleton.getInstance().setCurrentResult(s);
            }
        });
    }

}