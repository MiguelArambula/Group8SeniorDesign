package edu.cosc4950.phatlab;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ConsoleFragment extends Fragment {
	
	ConsoleView cv;
	static boolean editOn;
	private Point p;
	private List<String> item = null;
	private List<String> path = null;
	private String root;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View myView = inflater.inflate(R.layout.fragment_console, container, false);
		//cv = (ConsoleView) myView.findViewById(R.id.consoleView1);
		final ToggleButton edit = (ToggleButton) myView.findViewById(R.id.edit);
		//final Button changeSamp = (Button) myView.findViewById(R.id.changeSamp);
		edit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				//changeSamp.setEnabled(isChecked);
			}
			
		});
		/*changeSamp.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
			
		});*/
		final Spinner spin = (Spinner) myView.findViewById(R.id.spin);
		final AudioManager manager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
		final SeekBar volume = (SeekBar) myView.findViewById(R.id.volume);
		volume.setMax(manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
		volume.setProgress(manager.getStreamVolume(AudioManager.STREAM_MUSIC));
		volume.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				manager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
		});
		return myView;
	}
	
	public void onToggleClicked(View view, Button button){
		//Checks to see if EDIT has been pressed
		boolean on = ((ToggleButton) view).isChecked();
		
		if(on){
			editOn=true;
		} else {
			editOn=false;
		}
	}
	
}