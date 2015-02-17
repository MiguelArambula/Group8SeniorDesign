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
	
	/* TODO
	 * update button names and functionality
	 * update display of sequencer everytime beat is changed
	 * unity between sample on pad and in sequencer
	 * be able to swap between sequencer and phatpad
	 */
	
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
		final ImageButton btnTrack00Step00 = (ImageButton) myView.findViewById(R.id.btn_track00_step00);
		btnTrack00Step00.setOnTouchListener(new OnTouchListener() {
			// establish the track and step related to the button being pressed
			int track = 0; int step = 0;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// if boolean false add trigger set boolean true update image
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						data.sequence.addTrigger(track, data.currentBeat, step);
						btnTrack00Step00.setImageBitmap(bmpPressed);
					}
					// if boolean true remove trigger set boolean false update image
					else {
						data.sequence.clearTrigger(track, data.currentBeat, step);
						btnTrack00Step00.setImageBitmap(bmpEmpty);
					}
					return true;
				case MotionEvent.ACTION_UP:
					// nothing
					return true;
				}
				return false;
			}
		});
		
		final ImageButton btnTrack00Step01 = (ImageButton) myView.findViewById(R.id.btn_track00_step01);
		btnTrack00Step01.setOnTouchListener(new OnTouchListener() {
			int track = 0; int step = 1;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// if boolean false add trigger set boolean true update image
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						data.sequence.addTrigger(track, data.currentBeat, step);
						btnTrack00Step01.setImageBitmap(bmpPressed);
					}
					else {
						data.sequence.clearTrigger(track, data.currentBeat, step);
						btnTrack00Step01.setImageBitmap(bmpEmpty);
					}
					return true;
				case MotionEvent.ACTION_UP:
					// nothing
					return true;
				}
				return false;
			}
		});
		
		final ImageButton btnTrack00Step02 = (ImageButton) myView.findViewById(R.id.btn_track00_step02);
		btnTrack00Step02.setOnTouchListener(new OnTouchListener() {
			int track = 0; int step = 2;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// SWITCH CASE
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// if boolean false add trigger set boolean true update image
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						data.sequence.addTrigger(track, data.currentBeat, step);
						btnTrack00Step02.setImageBitmap(bmpPressed);
					}
					else {
						data.sequence.clearTrigger(track, data.currentBeat, step);
						btnTrack00Step02.setImageBitmap(bmpEmpty);
					}
					return true;
				case MotionEvent.ACTION_UP:
					// nothing
					return true;
				}
				return false;
			}
		});
		
		final ImageButton btnTrack00Step03 = (ImageButton) myView.findViewById(R.id.btn_track00_step03);
		btnTrack00Step03.setOnTouchListener(new OnTouchListener() {
			int track = 0; int step = 3;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// SWITCH CASE
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// if boolean false add trigger set boolean true update image
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						data.sequence.addTrigger(track, data.currentBeat, step);
						btnTrack00Step03.setImageBitmap(bmpPressed);
					}
					else {
						data.sequence.clearTrigger(track, data.currentBeat, step);
						btnTrack00Step03.setImageBitmap(bmpEmpty);
					}
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
					btnStart.setImageBitmap(bmpPressed);
					data.sequence.setPlayTime(0, 0, -1, -1);
					data.sequence.start();
					//Log.i("Phat Lab", "start");
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
					//Log.i("Phat Lab", "stop");
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
