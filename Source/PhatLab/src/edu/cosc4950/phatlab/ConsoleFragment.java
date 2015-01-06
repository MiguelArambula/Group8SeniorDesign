package edu.cosc4950.phatlab;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ConsoleFragment extends Fragment {
	
	ConsoleView cv;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View myView = inflater.inflate(R.layout.fragment_console, container, false);
		//cv = (ConsoleView) myView.findViewById(R.id.consoleView1);
		
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
}