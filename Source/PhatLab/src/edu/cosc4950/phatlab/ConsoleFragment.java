package edu.cosc4950.phatlab;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ConsoleFragment extends Fragment{
	
	ConsoleView cv;
	static boolean editOn;
	private String root;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final MainActivity data = (MainActivity) getActivity();
		View myView = inflater.inflate(R.layout.fragment_console, container, false);
		//cv = (ConsoleView) myView.findViewById(R.id.consoleView1);
		root = Environment.getExternalStorageDirectory()+"/PhatLab/";
		data.getDir(root);
		
		final Spinner spin = (Spinner) myView.findViewById(R.id.spin);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>
		(getActivity(), android.R.layout.simple_spinner_item, data.getItems());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(adapter);
		spin.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				spin.setFocusable(true);
				spin.setSelection(position);
				//Toast.makeText(getActivity(), String.valueOf(spin.getSelectedItem()), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
		spin.setEnabled(false);
		final ToggleButton edit = (ToggleButton) myView.findViewById(R.id.edit);
		edit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked){
					spin.setEnabled(true);
				} else {
					spin.setEnabled(false);
				}
			}
			
		});
		final AudioManager manager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
		final SeekBar volume = (SeekBar) myView.findViewById(R.id.volumebar);
		volume.setMax(manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
		volume.setProgress(manager.getStreamVolume(AudioManager.STREAM_MUSIC));
		volume.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				seekBar.setProgress(progress);
				manager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				int x = seekBar.getProgress();
				seekBar.setProgress(x);
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				int x = seekBar.getProgress();
				seekBar.setProgress(x);
			}
		});
		final TextView maxText = (TextView) myView.findViewById(R.id.max_text);
		maxText.setText(Integer.toString(data.getMaxBeat()));
		final Button decMax = (Button) myView.findViewById(R.id.dec_max);
		decMax.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int x = data.changeMax("sub");
				maxText.setText(Integer.toString(x));
			}
			
		});
		final Button addMax = (Button) myView.findViewById(R.id.in_max);
		addMax.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int x = data.changeMax("add");
				maxText.setText(Integer.toString(x));
			}
			
		});
		final TextView currPad = (TextView) myView.findViewById(R.id.pad_num);
		currPad.setText(data.getCurrPad());
		return myView;
	}
}