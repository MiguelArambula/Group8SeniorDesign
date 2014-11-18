package edu.cosc4950.phatlab;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ConsoleFragment extends Fragment {
	
	ConsoleView cv;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View myView = inflater.inflate(R.layout.fragment_console, container, false);
		cv = (ConsoleView) myView.findViewById(R.id.consoleView1);

		return myView;
	}
}