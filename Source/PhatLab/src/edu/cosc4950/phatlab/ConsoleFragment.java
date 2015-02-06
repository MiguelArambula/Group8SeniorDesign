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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
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

		root = Environment.getExternalStorageDirectory()+"/PhatLab/";
		getDir(root);
		final Spinner spin = (Spinner) myView.findViewById(R.id.spin);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>
		(getActivity(), android.R.layout.simple_spinner_item, item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(adapter);
		spin.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				spin.setSelection(position);
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
		final ToggleButton edit = (ToggleButton) myView.findViewById(R.id.edit);
		edit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked){
					spin.setEnabled(true);
					spin.setSelection(0);
				} else {
					spin.setEnabled(false);
				}
			}
			
		});
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
	
	private void getDir(String dirPath){
		//myPath.setText("Location"  + dirPath);
		item = new ArrayList<String>();
		path = new ArrayList<String>();
		File f = new File(dirPath);
		File[] files = f.listFiles();
		
		if(!dirPath.equals(root)){
			item.add(root);
			path.add(root);
			item.add("../");
			path.add(f.getParent());
		}
		
		for(int i=0;i<files.length;i++){
			File temp = files[i];
			
			if(!temp.isHidden() && temp.canRead()){
				path.add(temp.getPath());
				if(temp.isDirectory()){
					item.add(temp.getName()+"/");
				} else {
					item.add(temp.getName());
				}
			}
		}
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