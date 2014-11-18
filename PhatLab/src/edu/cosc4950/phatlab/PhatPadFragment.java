package edu.cosc4950.phatlab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PhatPadFragment extends Fragment {
	
	PadView pv;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View myView = inflater.inflate(R.layout.fragment_phatpad, container, false);
		pv = (PadView) myView.findViewById(R.id.padView1);
		return myView;
	}
}
