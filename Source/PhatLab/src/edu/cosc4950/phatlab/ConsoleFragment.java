package edu.cosc4950.phatlab;

import java.io.IOException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 *  @author Miguel Arambula
 *  @author Jake Harper
 * 
 * Allows users to adjust parameters of the composition and to modify which samples are stored in each trigger pad of
 * the PhatPad.
 */

public class ConsoleFragment extends Fragment{
	
	static boolean editOn;
	private String root;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//Primary setup of the needed items for the Console Fragment. 
		final MainActivity data = (MainActivity) getActivity();
		final SequenceTimer sT = data.getTimer();
		final Recorder reCord = new Recorder();
		View myView = inflater.inflate(R.layout.fragment_console, container, false);
		root = Environment.getExternalStorageDirectory()+"/PhatLab/";
		String samples = Environment.getExternalStorageDirectory()+"/PhatLab/Samples/";
		data.getDir(samples);
		data.getProfs(Environment.getExternalStorageDirectory()+"/PhatLab/Profiles/");
		
		//Export, Save and Load Sequence Buttons
		//Export Sequence will compile the pcm of all the samples in the sequence to a .wav file
		final Button expSeq = (Button) myView.findViewById(R.id.export_seq);
		expSeq.setEnabled(false);
		final Button changeRate = (Button) myView.findViewById(R.id.change_rate);
		changeRate.setEnabled(false);
		
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
				//Grab the name of the desired sample and loads it into the selected pad. 
				spin.setFocusable(true);
				spin.setSelection(position);
				String x = (String) spin.getSelectedItem();
				if(spin.isEnabled()){
					data.loadTrack(data.getCurrPad(), x);
				}
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
		
		//Master Volume-uses manager to change the volume of the app. 
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
		
		//Sample Volume-allows the user to user to change the volume of the current sample if one is selected. 
		final SeekBar sampVol = (SeekBar) myView.findViewById(R.id.sample_volumebar);
		sampVol.setMax(manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
		data.setSampVolBar(sampVol);
		data.setSampVolBar();
		sampVol.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
			float y=0;
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				seekBar.setProgress(progress);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				int x = seekBar.getProgress();
				y = ((float) x)/100;
				seekBar.setProgress(x);
				data.setSampVol(y);
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				int x = seekBar.getProgress();
				seekBar.setProgress(x);
				y = ((float) x)/100;
				data.setSampVol(y);
			}
		});
		
		//Changes the max amount beats in the sequence.  
		final TextView maxText = (TextView) myView.findViewById(R.id.max_text);
		maxText.setText(Integer.toString(data.getMaxBeat()+1));
		final Button decMax = (Button) myView.findViewById(R.id.dec_max);
		decMax.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int x = data.changeMax("sub");
				maxText.setText(Integer.toString(x+1));
			}
			
		});
		final Button addMax = (Button) myView.findViewById(R.id.in_max);
		addMax.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int x = data.changeMax("add");
				maxText.setText(Integer.toString(x+1));
			}
			
		});
		
		//These buttons allow the user to switch the view of the work station between 
		final RadioButton padView = (RadioButton) myView.findViewById(R.id.pad_view);
		padView.setChecked(true);
		padView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				data.swapFrag("sequencer");
				expSeq.setEnabled(true);
				changeRate.setEnabled(true);
			}
		});
		final RadioButton seqView = (RadioButton) myView.findViewById(R.id.seq_view);
		seqView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				data.swapFrag("pad");
				expSeq.setEnabled(false);
				changeRate.setEnabled(false);
			}
		});
		
		//padNum- TextView for update the status of the last pad pressed. 
		final TextView padNum = (TextView) myView.findViewById(R.id.pad_num);
		data.setTextView(padNum);
		
		//Start Button for beginning the play back of the sequence in the work station. 
		final Button btnStart = (Button) myView.findViewById(R.id.start);
		btnStart.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					//btnStart.setImageBitmap(bmpPressed);
					if(data.loopOn) {
						data.sequence.loop();
					}
					else {
						data.sequence.setPlayTime(0, 0, -1, -1);
						data.sequence.start();
					}
					return true;
				case MotionEvent.ACTION_UP:
					//btnStart.setImageBitmap(bmpEmpty);
					return true;
				}
				return false;
			}
		});
		
		//Stop Button for stopping the playback of the sequence. 
		final Button btnStop = (Button) myView.findViewById(R.id.stop);
		btnStop.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					data.sequence.stop();
					//btnStop.setImageBitmap(bmpPressed);
					return true;
				case MotionEvent.ACTION_UP:
					//btnStop.setImageBitmap(bmpEmpty);
					return true;
				}
				return false;
			}
		});
		
		//Save sequence code
		//Pop-up and intakes the name. 
		expSeq.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final LayoutInflater lf = LayoutInflater.from(getActivity());
				View savePrompt = lf.inflate(R.layout.saveseq_prompt, null);
				
				final EditText saveName = (EditText) savePrompt.findViewById(R.id.save_name);
				
				AlertDialog.Builder saveDialogBuild = new AlertDialog.Builder(getActivity());
				saveDialogBuild.setView(savePrompt);
				saveDialogBuild.setCancelable(false)
				.setPositiveButton("SAVE", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String newName = saveName.getText().toString();
						PCM seq = sT.compileToPCM();
						data.savePCM(seq, newName);
						ArrayAdapter<String> adapter = new ArrayAdapter<String>
						(getActivity(), android.R.layout.simple_spinner_item, data.getItems());
						adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						spin.setAdapter(adapter);
					}
					
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
					
				});
				AlertDialog seqSave = saveDialogBuild.create();
				seqSave.show();
			}
			
		});
		
		//Record, prompts the user with a box with a text box for enter the name of the recording
		//and a button to start and stop the recording. Once the recording is done the users can just hit
		//the Save button or cancel it if it was not to their liking. 
		Button record = (Button) myView.findViewById(R.id.record);
		record.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final LayoutInflater lf = LayoutInflater.from(getActivity());
				View recordPrompt = lf.inflate(R.layout.record_prompt, null);
				
				final EditText saveName = (EditText) recordPrompt.findViewById(R.id.name_record);
				final Button recordBTN = (Button) recordPrompt.findViewById(R.id.btn_record);
				recordBTN.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(!reCord.isRecording()){
							reCord.start();
							recordBTN.setText("Stop");
						} else {
							reCord.stop();
							recordBTN.setText("Start");
						}
						
					}
					
				});
				
				AlertDialog.Builder recordDialogBuild = new AlertDialog.Builder(getActivity());
				recordDialogBuild.setView(recordPrompt);
				recordDialogBuild.setCancelable(false)
				.setPositiveButton("SAVE", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String newName = saveName.getText().toString();
						PCM recFile = reCord.getSample();
						data.savePCM(recFile, newName);
						data.getDir(Environment.getExternalStorageDirectory()+"/PhatLab/Samples/");
						ArrayAdapter<String> adapter = new ArrayAdapter<String>
						(getActivity(), android.R.layout.simple_spinner_item, data.getItems());
						adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						spin.setAdapter(adapter);
					}
					
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
					
				});
				AlertDialog recSave = recordDialogBuild.create();
				recSave.show();
			}
			
		});
		
		//A spinner that will contain a list of saved profiles for the user to load. 
		final Spinner selProf = (Spinner) myView.findViewById(R.id.sel_profile);
		ArrayAdapter<String> profAdapter = new ArrayAdapter<String>
		(getActivity(), android.R.layout.simple_spinner_item, data.getAllProfiles());
		selProf.setAdapter(profAdapter);
		selProf.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				try {
					data.loadProfile(selProf.getSelectedItem().toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		//Prompts the user with a text box to give a profile a name.
		//Once the user hits the save button it will save the information of the location of loaded samples
		//and any info of a inprogress sequence if one is the Sequencer Workstation. 
		final Button createProf = (Button) myView.findViewById(R.id.create_pro);
		createProf.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final LayoutInflater lf = LayoutInflater.from(getActivity());
				View createPrompt = lf.inflate(R.layout.profile_prompt, null);
				
				final EditText saveName = (EditText) createPrompt.findViewById(R.id.prof_name);
				
				AlertDialog.Builder createDialogBuild = new AlertDialog.Builder(getActivity());
				createDialogBuild.setView(createPrompt);
				createDialogBuild.setCancelable(false)
				.setPositiveButton("SAVE", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String newName = saveName.getText().toString();
						data.createProfile(newName);
						data.getProfs(Environment.getExternalStorageDirectory()+"/PhatLab/Profiles/");
						ArrayAdapter<String> profAdapter = new ArrayAdapter<String>
						(getActivity(), android.R.layout.simple_spinner_item, data.getAllProfiles());
						selProf.setAdapter(profAdapter);
					}
					
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
					
				});
				AlertDialog createProfile = createDialogBuild.create();
				createProfile.show();
			}		
			
		});
		
		//Changing the sample rate
		final TextView currRate = (TextView) myView.findViewById(R.id.curr_rate);
		currRate.setText(Integer.toString(data.getBPM()));
		changeRate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final LayoutInflater lf = LayoutInflater.from(getActivity());
				View createPrompt = lf.inflate(R.layout.changerate_prompt, null);
				
				final EditText newRate = (EditText) createPrompt.findViewById(R.id.new_rate);
				
				AlertDialog.Builder createDialogBuild = new AlertDialog.Builder(getActivity());
				createDialogBuild.setView(createPrompt);
				createDialogBuild.setCancelable(false)
				.setPositiveButton("SAVE", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						int x = Integer.parseInt(newRate.getText().toString());
						data.setBPM(x);
						currRate.setText(Integer.toString(data.getBPM()));
					}
					
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
					
				});
				AlertDialog createProfile = createDialogBuild.create();
				createProfile.show();
			}		
			
		});
		
		
		return myView;
	}
}
