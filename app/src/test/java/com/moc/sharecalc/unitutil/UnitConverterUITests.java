package com.moc.sharecalc.unitutil;

import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import com.moc.sharecalc.R;
import android.widget.ArrayAdapter;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.moc.sharecalc.ui.unitconverter.*; 

public class UnitConverterUITests {

	private Spinner unitTypeSpinner; 
	private Spinner unitSpinner; 
	private ArrayAdapter<String> unitTypeAdapter; 
	private ArrayAdapter<String> unitAdapter; 
	private View currentView; 

	@Before 
	public void initialize()  {
		currentView = inflater.inflate(R.layout.fragment_unit_converter, container, false);
		unitTypeSpinner = currentView.findViewById(R.id.unit_type_spinner); 
		unitSpinner = currentView.findViewById(R.id.unit_spinner); 
		unitTypeAdapter = unitTypeSpinner.getAdapter(); 
		unitAdapter = unitSpinner.getAdapter(); 
	}


}
