package edu.cosc4950.phatlab;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ConsoleFragment extends Fragment{
	
	ConsoleView cv;
	static boolean editOn;
	private String root;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final MainActivity data = (MainActivity) getActivity();
		View myView = inflater.inflate(R.layout.fragment_console, container, false);
		root = Environment.getExternalStorageDirectory()+"/PhatLab/";
		data.getDir(root);
		
		//spin- Spinner for changing samples in pad. 
		final Spinner spin = (Spinner) myView.findViewById(R.id.spin);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>
		(getActivity(), android.R.layout.simple_spinner_item, data.getItems());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(adapter);
		data.setSpin(spin);
		spin.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				spin.setFocusable(true);
				spin.setSelection(position);
				String x = (String) spin.getSelectedItem();
				//Toast.makeText(getActivity(), "made it here", Toast.LENGTH_SHORT).show();
				if(spin.isEnabled()){
					data.loadTrack(data.getPad(), x);
				}
				//Toast.makeText(getActivity(), String.valueOf(spin.getSelectedItem()), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
		spin.setEnabled(false);
		
		//edit-enables when a sample can be change. 
		final ToggleButton edit = (ToggleButton) myView.findViewById(R.id.edit);
		edit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked){
					editOn = true;
					spin.setEnabled(true);
				} else {
					editOn = false;
					spin.setEnabled(false);
				}
			}
			
		});
		
		//volume-uses manager to change the volume of the app. 
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
		
		//Changes the max amount beats. 
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
		//Changing the current pad that is being looked out
		final TextView currText = (TextView) myView.findViewById(R.id.cur_text);
		currText.setText(Integer.toString(data.getCurBeat()));
		final Button decCurr = (Button) myView.findViewById(R.id.dec_cur);
		decCurr.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int x = data.changeCur("sub");
				currText.setText(Integer.toString(x));
			}
			
		});
		final Button addCurr = (Button) myView.findViewById(R.id.in_cur);
		addCurr.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int x = data.changeCur("add");
				currText.setText(Integer.toString(x));
			}
			
		});
		decMax.setEnabled(false);
		addMax.setEnabled(false);
		decCurr.setEnabled(false);
		addCurr.setEnabled(false);
		
		final RadioButton padView = (RadioButton) myView.findViewById(R.id.pad_view);
		padView.setChecked(true);
		//final RadioButton seqView = (RadioButton) myView.findViewById(R.id.seq_view);
		
		//padNum- TextView for update the status of the last pad pressed. 
		final TextView padNum = (TextView) myView.findViewById(R.id.pad_num);
		data.setTextView(padNum);
		
		return myView;
	}
}