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
			int track = 0; int step = 0;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// if boolean false add trigger set boolean true update image
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack00Step00.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					// if boolean true remove trigger set boolean false update image
					else {
						btnTrack00Step00.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
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
						btnTrack00Step01.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					// if boolean true remove trigger set boolean false update image
					else {
						btnTrack00Step01.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
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
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// if boolean false add trigger set boolean true update image
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack00Step02.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					// if boolean true remove trigger set boolean false update image
					else {
						btnTrack00Step02.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
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
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// if boolean false add trigger set boolean true update image
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack00Step03.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					// if boolean true remove trigger set boolean false update image
					else {
						btnTrack00Step03.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					// nothing
					return true;
				}
				return false;
			}
		});
		
		
		final ImageButton btnTrack00Step04 = (ImageButton) myView.findViewById(R.id.btn_track00_step04);
		btnTrack00Step04.setOnTouchListener(new OnTouchListener() {
			int track = 0; int step = 4;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// if boolean false add trigger set boolean true update image
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack00Step04.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					// if boolean true remove trigger set boolean false update image
					else {
						btnTrack00Step04.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					// nothing
					return true;
				}
				return false;
			}
		});
		
		final ImageButton btnTrack00Step05 = (ImageButton) myView.findViewById(R.id.btn_track00_step05);
		btnTrack00Step05.setOnTouchListener(new OnTouchListener() {
			int track = 0; int step = 5;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// if boolean false add trigger set boolean true update image
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack00Step05.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					// if boolean true remove trigger set boolean false update image
					else {
						btnTrack00Step05.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					// nothing
					return true;
				}
				return false;
			}
		});
		
		final ImageButton btnTrack00Step06 = (ImageButton) myView.findViewById(R.id.btn_track00_step06);
		btnTrack00Step06.setOnTouchListener(new OnTouchListener() {
			int track = 0; int step = 6;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// if boolean false add trigger set boolean true update image
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack00Step06.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					// if boolean true remove trigger set boolean false update image
					else {
						btnTrack00Step06.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					// nothing
					return true;
				}
				return false;
			}
		});
		
		final ImageButton btnTrack00Step07 = (ImageButton) myView.findViewById(R.id.btn_track00_step07);
		btnTrack00Step07.setOnTouchListener(new OnTouchListener() {
			int track = 0; int step = 7;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// if boolean false add trigger set boolean true update image
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack00Step07.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					// if boolean true remove trigger set boolean false update image
					else {
						btnTrack00Step07.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					// nothing
					return true;
				}
				return false;
			}
		});
		
		final ImageButton btnTrack01Step00 = (ImageButton) myView.findViewById(R.id.btn_track01_step00);
		btnTrack01Step00.setOnTouchListener(new OnTouchListener() {
			int track = 1; int step = 0;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// if boolean false add trigger set boolean true update image
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack01Step00.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					// if boolean true remove trigger set boolean false update image
					else {
						btnTrack01Step00.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					// nothing
					return true;
				}
				return false;
			}
		});
		
		final ImageButton btnTrack01Step01 = (ImageButton) myView.findViewById(R.id.btn_track01_step01);
		btnTrack01Step01.setOnTouchListener(new OnTouchListener() {
			int track = 1; int step = 1;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// if boolean false add trigger set boolean true update image
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack01Step01.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					// if boolean true remove trigger set boolean false update image
					else {
						btnTrack01Step01.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					// nothing
					return true;
				}
				return false;
			}
		});
		
		final ImageButton btnTrack01Step02 = (ImageButton) myView.findViewById(R.id.btn_track01_step02);
		btnTrack01Step02.setOnTouchListener(new OnTouchListener() {
			int track = 1; int step = 2;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// if boolean false add trigger set boolean true update image
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack01Step02.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					// if boolean true remove trigger set boolean false update image
					else {
						btnTrack01Step02.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					// nothing
					return true;
				}
				return false;
			}
		});
		
		final ImageButton btnTrack01Step03 = (ImageButton) myView.findViewById(R.id.btn_track01_step03);
		btnTrack01Step03.setOnTouchListener(new OnTouchListener() {
			int track = 1; int step = 3;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// if boolean false add trigger set boolean true update image
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack01Step03.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					// if boolean true remove trigger set boolean false update image
					else {
						btnTrack01Step03.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					// nothing
					return true;
				}
				return false;
			}
		});
		
		final ImageButton btnTrack01Step04 = (ImageButton) myView.findViewById(R.id.btn_track01_step04);
		btnTrack01Step04.setOnTouchListener(new OnTouchListener() {
			int track = 1; int step = 4;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// if boolean false add trigger set boolean true update image
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack01Step04.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					// if boolean true remove trigger set boolean false update image
					else {
						btnTrack01Step04.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					// nothing
					return true;
				}
				return false;
			}
		});
		
		final ImageButton btnTrack01Step05 = (ImageButton) myView.findViewById(R.id.btn_track01_step05);
		btnTrack01Step05.setOnTouchListener(new OnTouchListener() {
			int track = 1; int step = 5;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// if boolean false add trigger set boolean true update image
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack01Step05.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					// if boolean true remove trigger set boolean false update image
					else {
						btnTrack01Step05.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					// nothing
					return true;
				}
				return false;
			}
		});
		
		final ImageButton btnTrack01Step06 = (ImageButton) myView.findViewById(R.id.btn_track01_step06);
		btnTrack01Step06.setOnTouchListener(new OnTouchListener() {
			int track = 1; int step = 6;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// if boolean false add trigger set boolean true update image
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack01Step06.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					// if boolean true remove trigger set boolean false update image
					else {
						btnTrack01Step06.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					// nothing
					return true;
				}
				return false;
			}
		});
		
		final ImageButton btnTrack01Step07 = (ImageButton) myView.findViewById(R.id.btn_track01_step07);
		btnTrack01Step07.setOnTouchListener(new OnTouchListener() {
			int track = 1; int step = 7;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// if boolean false add trigger set boolean true update image
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack01Step07.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					// if boolean true remove trigger set boolean false update image
					else {
						btnTrack01Step07.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
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
					data.sequence.start();
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
