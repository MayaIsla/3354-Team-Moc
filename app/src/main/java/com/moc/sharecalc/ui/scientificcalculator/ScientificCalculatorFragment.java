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

        View btn = root.findViewById(R.id.btn_root);
        btn.setOnClickListener(new View.OnClickListener()  {
            public void onClick(View view)  {
                onRootClick(view);
                onPowerClick(view);
                onSquareClick(view);
                onClickFactorial(view);
                onClickInverse(view);
                onClickPIorE(view);
                onClickFunction(view);
                onClickClear(view);
                onPorMClick(view);
                onModuloClick(view);
                onClickOprator(view);
                onClickNumber(view);
                onDotClick(view);
                onClickDelete(view);
                onClickEqual(view);
            }

        });
        return root;
    }

    public void onClickEqual(View view)  {

    }

    public void onClickDelete(View view)  {

    }

    public void onDotClick(View view)  {

    }

    public void onClickNumber(View view)  {

    }

    public void onClickOprator(View view)  {

    }

    public void onModuloClick(View view)  {

    }
    public void onPorMClick(View view)  {

    }

    public void onClickClear(View view)  {

    }

    public void onClickFunction(View view)  {

    }

    public void onClickPIorE(View view)  {

    }

    public void onRootClick(View view)  {

    }

    public void onPowerClick(View view)  {

    }

    public void onSquareClick(View view)  {

    }

    public void onClickFactorial(View view)  {

    }

    public void onClickInverse(View view)  {

    }

}