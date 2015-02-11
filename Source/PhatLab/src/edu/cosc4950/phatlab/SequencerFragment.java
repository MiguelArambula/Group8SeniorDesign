package edu.cosc4950.phatlab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;

public class SequencerFragment extends Fragment{
	
	MainActivity data;
	Bitmap bmpPressed, bmpEmpty;
	
	public SequencerFragment() {}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View myView = inflater.inflate(R.layout.fragment_sequencer, container, false);
		data = (MainActivity) getActivity(); // create instance
		
		bmpPressed = BitmapFactory.decodeResource(getResources(), R.drawable.pad_pressed);
		bmpEmpty = BitmapFactory.decodeResource(getResources(), R.drawable.pad_empty);
		
		// ADD BUTTONS & CODE
		// eight buttons in a row for each sample
		// store values in array
		// stored in an ArrayList of frames
		final ImageButton btnTrack01Step01 = (ImageButton) myView.findViewById(R.id.btn_track01_step01);
		btnTrack01Step01.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// SWITCH CASE
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// if boolean false add trigger set boolean true update image
					// TODO if find() false
					data.sequence.addTrigger(0, 0, 4);
					data.sequence.addTrigger(0, 1, 4);
					Log.i("Phat Lab", "sample 0 : " + data.sequence.sampleList[0]);
					Log.i("Phat Lab", "sample 1 : " + data.sequence.sampleList[1]);
					Log.i("Phat Lab", data.butts);
					btnTrack01Step01.setImageBitmap(bmpPressed);
					// if boolean true remove trigger set boolean false update image

					return true;
				case MotionEvent.ACTION_UP:
					// nothing
					return true;
				}
				
				return false;
			}
		});
		
		/* TEST BUTTONS, REMOVE LATER (GOES IN CONSOLE) */
		// start
		final ImageButton btnStart = (ImageButton) myView.findViewById(R.id.btn_start);
		btnStart.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					data.sequence.start();
					Log.i("Phat Lab", "start");
					if(data.sequence.isPlaying)
						Log.i("Phat Lab", "now playing");
					Log.i("Phat Lab", ""+data.sequence.isPlaying);
					btnStart.setImageBitmap(bmpPressed);
					return true;
				case MotionEvent.ACTION_UP:
					// nothing functionally
					btnStart.setImageBitmap(bmpEmpty);
					return true;
				}
				
				return false;
			}
		});
		
		final ImageButton btnStop = (ImageButton) myView.findViewById(R.id.btn_stop);
		btnStop.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					data.sequence.stop();
					if(data.sequence.isPlaying)
						Log.i("Phat Lab", "now playing");
					Log.i("Phat Lab", "stop");
					btnStop.setImageBitmap(bmpPressed);
					return true;
				case MotionEvent.ACTION_UP:
					// nothing functionally
					btnStop.setImageBitmap(bmpEmpty);
					return true;
				}
				
				return false;
			}
		});
		
		return myView;
	}
	
	// ADD AUX CLASSES
	
	public void checkTracks() {
		// if trigger display red else grey
	}

}
