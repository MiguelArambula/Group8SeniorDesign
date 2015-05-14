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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * @author Jake Harper
 * @author Patrick Weingardt
 * @author Miguel Arambula
 * 
 * The Sequencer allows users to program in when audio clips will trigger in respect to the steps of a beat. Each
 * pad/track has an associated step sequencer (row of toggle buttons) on a scroll view. Loop control allows users
 * to toggle whether the sequence will loop. The beat index allows users to navigate the composition.
 */

public class SequencerFragment extends Fragment{
	
	MainActivity data;				// instance of MainActivity to reference composition data
	Bitmap bmpPressed, bmpEmpty;			// button images
	
	// instances of buttons within the twelve step sequencers
	// each button represents a step within a beat associated with a track/pad
	private ImageButton btnTrack0Step0;
	private ImageButton btnTrack0Step1;
	private ImageButton btnTrack0Step2;
	private ImageButton btnTrack0Step3;
	private ImageButton btnTrack0Step4;
	private ImageButton btnTrack0Step5;
	private ImageButton btnTrack0Step6;
	private ImageButton btnTrack0Step7;
	
	private ImageButton btnTrack1Step0;
	private ImageButton btnTrack1Step1;
	private ImageButton btnTrack1Step2;
	private ImageButton btnTrack1Step3;
	private ImageButton btnTrack1Step4;
	private ImageButton btnTrack1Step5;
	private ImageButton btnTrack1Step6;
	private ImageButton btnTrack1Step7;
	
	private ImageButton btnTrack2Step0;
	private ImageButton btnTrack2Step1;
	private ImageButton btnTrack2Step2;
	private ImageButton btnTrack2Step3;
	private ImageButton btnTrack2Step4;
	private ImageButton btnTrack2Step5;
	private ImageButton btnTrack2Step6;
	private ImageButton btnTrack2Step7;
	
	private ImageButton btnTrack3Step0;
	private ImageButton btnTrack3Step1;
	private ImageButton btnTrack3Step2;
	private ImageButton btnTrack3Step3;
	private ImageButton btnTrack3Step4;
	private ImageButton btnTrack3Step5;
	private ImageButton btnTrack3Step6;
	private ImageButton btnTrack3Step7;
	
	private ImageButton btnTrack4Step0;
	private ImageButton btnTrack4Step1;
	private ImageButton btnTrack4Step2;
	private ImageButton btnTrack4Step3;
	private ImageButton btnTrack4Step4;
	private ImageButton btnTrack4Step5;
	private ImageButton btnTrack4Step6;
	private ImageButton btnTrack4Step7;
	
	private ImageButton btnTrack5Step0;
	private ImageButton btnTrack5Step1;
	private ImageButton btnTrack5Step2;
	private ImageButton btnTrack5Step3;
	private ImageButton btnTrack5Step4;
	private ImageButton btnTrack5Step5;
	private ImageButton btnTrack5Step6;
	private ImageButton btnTrack5Step7;
	
	private ImageButton btnTrack6Step0;
	private ImageButton btnTrack6Step1;
	private ImageButton btnTrack6Step2;
	private ImageButton btnTrack6Step3;
	private ImageButton btnTrack6Step4;
	private ImageButton btnTrack6Step5;
	private ImageButton btnTrack6Step6;
	private ImageButton btnTrack6Step7;
	
	private ImageButton btnTrack7Step0;
	private ImageButton btnTrack7Step1;
	private ImageButton btnTrack7Step2;
	private ImageButton btnTrack7Step3;
	private ImageButton btnTrack7Step4;
	private ImageButton btnTrack7Step5;
	private ImageButton btnTrack7Step6;
	private ImageButton btnTrack7Step7;
	
	private ImageButton btnTrack8Step0;
	private ImageButton btnTrack8Step1;
	private ImageButton btnTrack8Step2;
	private ImageButton btnTrack8Step3;
	private ImageButton btnTrack8Step4;
	private ImageButton btnTrack8Step5;
	private ImageButton btnTrack8Step6;
	private ImageButton btnTrack8Step7;
	
	private ImageButton btnTrack9Step0;
	private ImageButton btnTrack9Step1;
	private ImageButton btnTrack9Step2;
	private ImageButton btnTrack9Step3;
	private ImageButton btnTrack9Step4;
	private ImageButton btnTrack9Step5;
	private ImageButton btnTrack9Step6;
	private ImageButton btnTrack9Step7;
	
	private ImageButton btnTrack10Step0;
	private ImageButton btnTrack10Step1;
	private ImageButton btnTrack10Step2;
	private ImageButton btnTrack10Step3;
	private ImageButton btnTrack10Step4;
	private ImageButton btnTrack10Step5;
	private ImageButton btnTrack10Step6;
	private ImageButton btnTrack10Step7;
	
	private ImageButton btnTrack11Step0;
	private ImageButton btnTrack11Step1;
	private ImageButton btnTrack11Step2;
	private ImageButton btnTrack11Step3;
	private ImageButton btnTrack11Step4;
	private ImageButton btnTrack11Step5;
	private ImageButton btnTrack11Step6;
	private ImageButton btnTrack11Step7;
	
	/*
	* empty default constructor
	*/
	public SequencerFragment() {}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		// inflate view to screen
		View myView = inflater.inflate(R.layout.fragment_sequencer, container, false);
		data = (MainActivity) getActivity(); // initialize reference to composition data
		
		// define button images
		bmpPressed = BitmapFactory.decodeResource(getResources(), R.drawable.pad_pressed_sm);
		bmpEmpty = BitmapFactory.decodeResource(getResources(), R.drawable.pad_empty_sm);
		
		// Loop Control
		final ToggleButton toggleLoop = (ToggleButton) myView.findViewById(R.id.toggle_loop);
		toggleLoop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked)
					// if on, sequence will loop
					data.loopOn = true;
				else
					data.loopOn = false;
			}
		});
		
		// initialize loop text
		final TextView tvLoop = (TextView) myView.findViewById(R.id.tv_loop);
		if(data.currentBeat+data.loopLength <= data.maxBeat)
			tvLoop.setText((data.currentBeat + 1) + ":" + (data.currentBeat + 1 + data.loopLength));
		
		// display current beat
		final TextView tvBeat = (TextView) myView.findViewById(R.id.tv_current_beat);
		
		// decrement loop length by one measure
		final Button btnDecLoop = (Button) myView.findViewById(R.id.btn_dec_loop);
		btnDecLoop.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.loopLength != 0) {
						data.loopLength--; // if loop length is larger than zero, decrement
						tvLoop.setText((data.currentBeat + 1) + ":" + (data.currentBeat + 1 + data.loopLength));
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		// increment loop length by one measure
		final Button btnIncLoop = (Button) myView.findViewById(R.id.btn_inc_loop);
		btnIncLoop.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.currentBeat+data.loopLength != data.maxBeat) {
						data.loopLength++; // if loop length won't exceed total composition length, increment
						tvLoop.setText((data.currentBeat + 1) + ":" + (data.currentBeat + 1 + data.loopLength));
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		// Current Beat Control
		// index to previous beat in composition
		final Button btnDecBeat = (Button) myView.findViewById(R.id.btn_dec_beat);
		btnDecBeat.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.currentBeat > 0) {
						data.currentBeat--; 			  // if not on first beat, index backwards
						tvBeat.setText(data.currentBeat + 1 +""); // update text views
						tvLoop.setText((data.currentBeat + 1) + ":" + (data.currentBeat + 1 + data.loopLength));
						updateTracks();				  // update button images
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		// index to next beat in composition
		final Button btnIncBeat = (Button) myView.findViewById(R.id.btn_inc_beat);
		btnIncBeat.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.currentBeat < data.maxBeat) {
						data.currentBeat++;			  // if not on last beat, index forwards
						tvBeat.setText(data.currentBeat + 1 +""); // update text views
						if(data.currentBeat + data.loopLength > data.maxBeat)
							data.loopLength--;		  // if loop length exceeds compostion, decrement
						tvLoop.setText((data.currentBeat + 1) + ":" + (data.currentBeat + 1 + data.loopLength));
						updateTracks();				  // update button images
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		/* 
		 * Trigger Buttons
		 * When pressed, these toggles will store a trigger in a linked list of when each audio clip will play.
		 * Each of the 12 AudioTracks are affected by rows of 8 buttons.
		 * Each column of buttons represent 1/8 step of the beat.
		 * Each button has the track and step that it is associated with defined in its listener.
		 * If a toggle representing a step within the sequence doesn't contain a trigger,
		     a button will add a start time for its sample to the linked list relative to its location when pressed.
		 * If a toggle representing a step within the sequence does cotain a trigger,
		 *   a button will remove that trigger from the linked list.
		 */
		btnTrack0Step0 = (ImageButton) myView.findViewById(R.id.btn_track00_step00);
		btnTrack0Step0.setOnTouchListener(new OnTouchListener() {
			int track = 0; int step = 0;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack0Step0.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack0Step0.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack0Step1 = (ImageButton) myView.findViewById(R.id.btn_track00_step01);
		btnTrack0Step1.setOnTouchListener(new OnTouchListener() {
			int track = 0; int step = 1;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack0Step1.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack0Step1.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack0Step2 = (ImageButton) myView.findViewById(R.id.btn_track00_step02);
		btnTrack0Step2.setOnTouchListener(new OnTouchListener() {
			int track = 0; int step = 2;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack0Step2.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack0Step2.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack0Step3 = (ImageButton) myView.findViewById(R.id.btn_track00_step03);
		btnTrack0Step3.setOnTouchListener(new OnTouchListener() {
			int track = 0; int step = 3;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack0Step3.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack0Step3.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		
		btnTrack0Step4 = (ImageButton) myView.findViewById(R.id.btn_track00_step04);
		btnTrack0Step4.setOnTouchListener(new OnTouchListener() {
			int track = 0; int step = 4;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack0Step4.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack0Step4.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack0Step5 = (ImageButton) myView.findViewById(R.id.btn_track00_step05);
		btnTrack0Step5.setOnTouchListener(new OnTouchListener() {
			int track = 0; int step = 5;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack0Step5.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack0Step5.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack0Step6 = (ImageButton) myView.findViewById(R.id.btn_track00_step06);
		btnTrack0Step6.setOnTouchListener(new OnTouchListener() {
			int track = 0; int step = 6;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack0Step6.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack0Step6.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack0Step7 = (ImageButton) myView.findViewById(R.id.btn_track00_step07);
		btnTrack0Step7.setOnTouchListener(new OnTouchListener() {
			int track = 0; int step = 7;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack0Step7.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack0Step7.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack1Step0 = (ImageButton) myView.findViewById(R.id.btn_track01_step00);
		btnTrack1Step0.setOnTouchListener(new OnTouchListener() {
			int track = 1; int step = 0;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack1Step0.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack1Step0.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack1Step1 = (ImageButton) myView.findViewById(R.id.btn_track01_step01);
		btnTrack1Step1.setOnTouchListener(new OnTouchListener() {
			int track = 1; int step = 1;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack1Step1.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack1Step1.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack1Step2 = (ImageButton) myView.findViewById(R.id.btn_track01_step02);
		btnTrack1Step2.setOnTouchListener(new OnTouchListener() {
			int track = 1; int step = 2;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack1Step2.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack1Step2.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack1Step3 = (ImageButton) myView.findViewById(R.id.btn_track01_step03);
		btnTrack1Step3.setOnTouchListener(new OnTouchListener() {
			int track = 1; int step = 3;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack1Step3.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack1Step3.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack1Step4 = (ImageButton) myView.findViewById(R.id.btn_track01_step04);
		btnTrack1Step4.setOnTouchListener(new OnTouchListener() {
			int track = 1; int step = 4;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack1Step4.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack1Step4.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack1Step5 = (ImageButton) myView.findViewById(R.id.btn_track01_step05);
		btnTrack1Step5.setOnTouchListener(new OnTouchListener() {
			int track = 1; int step = 5;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack1Step5.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack1Step5.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack1Step6 = (ImageButton) myView.findViewById(R.id.btn_track01_step06);
		btnTrack1Step6.setOnTouchListener(new OnTouchListener() {
			int track = 1; int step = 6;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack1Step6.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack1Step6.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack1Step7 = (ImageButton) myView.findViewById(R.id.btn_track01_step07);
		btnTrack1Step7.setOnTouchListener(new OnTouchListener() {
			int track = 1; int step = 7;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack1Step7.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack1Step7.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack2Step0 = (ImageButton) myView.findViewById(R.id.btn_track02_step00);
		btnTrack2Step0.setOnTouchListener(new OnTouchListener() {
			int track = 2; int step = 0;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack2Step0.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack2Step0.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack2Step1 = (ImageButton) myView.findViewById(R.id.btn_track02_step01);
		btnTrack2Step1.setOnTouchListener(new OnTouchListener() {
			int track = 2; int step = 1;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack2Step1.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack2Step1.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack2Step2 = (ImageButton) myView.findViewById(R.id.btn_track02_step02);
		btnTrack2Step2.setOnTouchListener(new OnTouchListener() {
			int track = 2; int step = 2;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack2Step2.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack2Step2.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack2Step3 = (ImageButton) myView.findViewById(R.id.btn_track02_step03);
		btnTrack2Step3.setOnTouchListener(new OnTouchListener() {
			int track = 2; int step = 3;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack2Step3.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack2Step3.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		
		btnTrack2Step4 = (ImageButton) myView.findViewById(R.id.btn_track02_step04);
		btnTrack2Step4.setOnTouchListener(new OnTouchListener() {
			int track = 2; int step = 4;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack2Step4.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack2Step4.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack2Step5 = (ImageButton) myView.findViewById(R.id.btn_track02_step05);
		btnTrack2Step5.setOnTouchListener(new OnTouchListener() {
			int track = 2; int step = 5;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack2Step5.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack2Step5.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack2Step6 = (ImageButton) myView.findViewById(R.id.btn_track02_step06);
		btnTrack2Step6.setOnTouchListener(new OnTouchListener() {
			int track = 2; int step = 6;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack2Step6.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack2Step6.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack2Step7 = (ImageButton) myView.findViewById(R.id.btn_track02_step07);
		btnTrack2Step7.setOnTouchListener(new OnTouchListener() {
			int track = 2; int step = 7;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack2Step7.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack2Step7.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack3Step0 = (ImageButton) myView.findViewById(R.id.btn_track03_step00);
		btnTrack3Step0.setOnTouchListener(new OnTouchListener() {
			int track = 3; int step = 0;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack3Step0.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack3Step0.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack3Step1 = (ImageButton) myView.findViewById(R.id.btn_track03_step01);
		btnTrack3Step1.setOnTouchListener(new OnTouchListener() {
			int track = 3; int step = 1;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack3Step1.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack3Step1.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack3Step2 = (ImageButton) myView.findViewById(R.id.btn_track03_step02);
		btnTrack3Step2.setOnTouchListener(new OnTouchListener() {
			int track = 3; int step = 2;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack3Step2.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack3Step2.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack3Step3 = (ImageButton) myView.findViewById(R.id.btn_track03_step03);
		btnTrack3Step3.setOnTouchListener(new OnTouchListener() {
			int track = 3; int step = 3;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack3Step3.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack3Step3.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		
		btnTrack3Step4 = (ImageButton) myView.findViewById(R.id.btn_track03_step04);
		btnTrack3Step4.setOnTouchListener(new OnTouchListener() {
			int track = 3; int step = 4;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack3Step4.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack3Step4.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack3Step5 = (ImageButton) myView.findViewById(R.id.btn_track03_step05);
		btnTrack3Step5.setOnTouchListener(new OnTouchListener() {
			int track = 3; int step = 5;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack3Step5.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack3Step5.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack3Step6 = (ImageButton) myView.findViewById(R.id.btn_track03_step06);
		btnTrack3Step6.setOnTouchListener(new OnTouchListener() {
			int track = 3; int step = 6;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack3Step6.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack3Step6.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack3Step7 = (ImageButton) myView.findViewById(R.id.btn_track03_step07);
		btnTrack3Step7.setOnTouchListener(new OnTouchListener() {
			int track = 3; int step = 7;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack3Step7.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack3Step7.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack4Step0 = (ImageButton) myView.findViewById(R.id.btn_track04_step00);
		btnTrack4Step0.setOnTouchListener(new OnTouchListener() {
			int track = 4; int step = 0;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack4Step0.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack4Step0.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack4Step1 = (ImageButton) myView.findViewById(R.id.btn_track04_step01);
		btnTrack4Step1.setOnTouchListener(new OnTouchListener() {
			int track = 4; int step = 1;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack4Step1.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack4Step1.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack4Step2 = (ImageButton) myView.findViewById(R.id.btn_track04_step02);
		btnTrack4Step2.setOnTouchListener(new OnTouchListener() {
			int track = 4; int step = 2;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack4Step2.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack4Step2.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack4Step3 = (ImageButton) myView.findViewById(R.id.btn_track04_step03);
		btnTrack4Step3.setOnTouchListener(new OnTouchListener() {
			int track = 4; int step = 3;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack4Step3.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack4Step3.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		
		btnTrack4Step4 = (ImageButton) myView.findViewById(R.id.btn_track04_step04);
		btnTrack4Step4.setOnTouchListener(new OnTouchListener() {
			int track = 4; int step = 4;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack4Step4.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack4Step4.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack4Step5 = (ImageButton) myView.findViewById(R.id.btn_track04_step05);
		btnTrack4Step5.setOnTouchListener(new OnTouchListener() {
			int track = 4; int step = 5;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack4Step5.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack4Step5.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack4Step6 = (ImageButton) myView.findViewById(R.id.btn_track04_step06);
		btnTrack4Step6.setOnTouchListener(new OnTouchListener() {
			int track = 4; int step = 6;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack4Step6.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack4Step6.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack4Step7 = (ImageButton) myView.findViewById(R.id.btn_track04_step07);
		btnTrack4Step7.setOnTouchListener(new OnTouchListener() {
			int track = 4; int step = 7;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack4Step7.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack4Step7.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack5Step0 = (ImageButton) myView.findViewById(R.id.btn_track05_step00);
		btnTrack5Step0.setOnTouchListener(new OnTouchListener() {
			int track = 5; int step = 0;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack5Step0.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack5Step0.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack5Step1 = (ImageButton) myView.findViewById(R.id.btn_track05_step01);
		btnTrack5Step1.setOnTouchListener(new OnTouchListener() {
			int track = 5; int step = 1;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack5Step1.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack5Step1.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack5Step2 = (ImageButton) myView.findViewById(R.id.btn_track05_step02);
		btnTrack5Step2.setOnTouchListener(new OnTouchListener() {
			int track = 5; int step = 2;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack5Step2.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack5Step2.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack5Step3 = (ImageButton) myView.findViewById(R.id.btn_track05_step03);
		btnTrack5Step3.setOnTouchListener(new OnTouchListener() {
			int track = 5; int step = 3;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack5Step3.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack5Step3.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		
		btnTrack5Step4 = (ImageButton) myView.findViewById(R.id.btn_track05_step04);
		btnTrack5Step4.setOnTouchListener(new OnTouchListener() {
			int track = 5; int step = 4;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack5Step4.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack5Step4.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack5Step5 = (ImageButton) myView.findViewById(R.id.btn_track05_step05);
		btnTrack5Step5.setOnTouchListener(new OnTouchListener() {
			int track = 5; int step = 5;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack5Step5.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack5Step5.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack5Step6 = (ImageButton) myView.findViewById(R.id.btn_track05_step06);
		btnTrack5Step6.setOnTouchListener(new OnTouchListener() {
			int track = 5; int step = 6;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack5Step6.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack5Step6.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack5Step7 = (ImageButton) myView.findViewById(R.id.btn_track05_step07);
		btnTrack5Step7.setOnTouchListener(new OnTouchListener() {
			int track = 5; int step = 7;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack5Step7.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack5Step7.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack6Step0 = (ImageButton) myView.findViewById(R.id.btn_track06_step00);
		btnTrack6Step0.setOnTouchListener(new OnTouchListener() {
			int track = 6; int step = 0;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack6Step0.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack6Step0.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack6Step1 = (ImageButton) myView.findViewById(R.id.btn_track06_step01);
		btnTrack6Step1.setOnTouchListener(new OnTouchListener() {
			int track = 6; int step = 1;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack6Step1.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack6Step1.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack6Step2 = (ImageButton) myView.findViewById(R.id.btn_track06_step02);
		btnTrack6Step2.setOnTouchListener(new OnTouchListener() {
			int track = 6; int step = 2;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack6Step2.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack6Step2.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack6Step3 = (ImageButton) myView.findViewById(R.id.btn_track06_step03);
		btnTrack6Step3.setOnTouchListener(new OnTouchListener() {
			int track = 6; int step = 3;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack6Step3.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack6Step3.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		
		btnTrack6Step4 = (ImageButton) myView.findViewById(R.id.btn_track06_step04);
		btnTrack6Step4.setOnTouchListener(new OnTouchListener() {
			int track = 6; int step = 4;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack6Step4.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack6Step4.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack6Step5 = (ImageButton) myView.findViewById(R.id.btn_track06_step05);
		btnTrack6Step5.setOnTouchListener(new OnTouchListener() {
			int track = 6; int step = 5;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack6Step5.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack6Step5.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack6Step6 = (ImageButton) myView.findViewById(R.id.btn_track06_step06);
		btnTrack6Step6.setOnTouchListener(new OnTouchListener() {
			int track = 6; int step = 6;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack6Step6.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack6Step6.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack6Step7 = (ImageButton) myView.findViewById(R.id.btn_track06_step07);
		btnTrack6Step7.setOnTouchListener(new OnTouchListener() {
			int track = 6; int step = 7;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack6Step7.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack6Step7.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack7Step0 = (ImageButton) myView.findViewById(R.id.btn_track07_step00);
		btnTrack7Step0.setOnTouchListener(new OnTouchListener() {
			int track = 7; int step = 0;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack7Step0.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack7Step0.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack7Step1 = (ImageButton) myView.findViewById(R.id.btn_track07_step01);
		btnTrack7Step1.setOnTouchListener(new OnTouchListener() {
			int track = 7; int step = 1;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack7Step1.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack7Step1.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack7Step2 = (ImageButton) myView.findViewById(R.id.btn_track07_step02);
		btnTrack7Step2.setOnTouchListener(new OnTouchListener() {
			int track = 7; int step = 2;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack7Step2.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack7Step2.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack7Step3 = (ImageButton) myView.findViewById(R.id.btn_track07_step03);
		btnTrack7Step3.setOnTouchListener(new OnTouchListener() {
			int track = 7; int step = 3;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack7Step3.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack7Step3.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		
		btnTrack7Step4 = (ImageButton) myView.findViewById(R.id.btn_track07_step04);
		btnTrack7Step4.setOnTouchListener(new OnTouchListener() {
			int track = 7; int step = 4;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack7Step4.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack7Step4.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack7Step5 = (ImageButton) myView.findViewById(R.id.btn_track07_step05);
		btnTrack7Step5.setOnTouchListener(new OnTouchListener() {
			int track = 7; int step = 5;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack7Step5.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack7Step5.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack7Step6 = (ImageButton) myView.findViewById(R.id.btn_track07_step06);
		btnTrack7Step6.setOnTouchListener(new OnTouchListener() {
			int track = 7; int step = 6;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack7Step6.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack7Step6.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack7Step7 = (ImageButton) myView.findViewById(R.id.btn_track07_step07);
		btnTrack7Step7.setOnTouchListener(new OnTouchListener() {
			int track = 7; int step = 7;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack7Step7.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack7Step7.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack8Step0 = (ImageButton) myView.findViewById(R.id.btn_track08_step00);
		btnTrack8Step0.setOnTouchListener(new OnTouchListener() {
			int track = 8; int step = 0;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack8Step0.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack8Step0.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack8Step1 = (ImageButton) myView.findViewById(R.id.btn_track08_step01);
		btnTrack8Step1.setOnTouchListener(new OnTouchListener() {
			int track = 8; int step = 1;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack8Step1.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack8Step1.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack8Step2 = (ImageButton) myView.findViewById(R.id.btn_track08_step02);
		btnTrack8Step2.setOnTouchListener(new OnTouchListener() {
			int track = 8; int step = 2;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack8Step2.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack8Step2.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack8Step3 = (ImageButton) myView.findViewById(R.id.btn_track08_step03);
		btnTrack8Step3.setOnTouchListener(new OnTouchListener() {
			int track = 8; int step = 3;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack8Step3.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack8Step3.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		
		btnTrack8Step4 = (ImageButton) myView.findViewById(R.id.btn_track08_step04);
		btnTrack8Step4.setOnTouchListener(new OnTouchListener() {
			int track = 8; int step = 4;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack8Step4.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack8Step4.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack8Step5 = (ImageButton) myView.findViewById(R.id.btn_track08_step05);
		btnTrack8Step5.setOnTouchListener(new OnTouchListener() {
			int track = 8; int step = 5;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack8Step5.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack8Step5.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack8Step6 = (ImageButton) myView.findViewById(R.id.btn_track08_step06);
		btnTrack8Step6.setOnTouchListener(new OnTouchListener() {
			int track = 8; int step = 6;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack8Step6.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack8Step6.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack8Step7 = (ImageButton) myView.findViewById(R.id.btn_track08_step07);
		btnTrack8Step7.setOnTouchListener(new OnTouchListener() {
			int track = 8; int step = 7;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack8Step7.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack8Step7.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack9Step0 = (ImageButton) myView.findViewById(R.id.btn_track09_step00);
		btnTrack9Step0.setOnTouchListener(new OnTouchListener() {
			int track = 9; int step = 0;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack9Step0.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack9Step0.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack9Step1 = (ImageButton) myView.findViewById(R.id.btn_track09_step01);
		btnTrack9Step1.setOnTouchListener(new OnTouchListener() {
			int track = 9; int step = 1;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack9Step1.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack9Step1.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack9Step2 = (ImageButton) myView.findViewById(R.id.btn_track09_step02);
		btnTrack9Step2.setOnTouchListener(new OnTouchListener() {
			int track = 9; int step = 2;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack9Step2.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack9Step2.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack9Step3 = (ImageButton) myView.findViewById(R.id.btn_track09_step03);
		btnTrack9Step3.setOnTouchListener(new OnTouchListener() {
			int track = 9; int step = 3;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack9Step3.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack9Step3.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		
		btnTrack9Step4 = (ImageButton) myView.findViewById(R.id.btn_track09_step04);
		btnTrack9Step4.setOnTouchListener(new OnTouchListener() {
			int track = 9; int step = 4;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack9Step4.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack9Step4.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack9Step5 = (ImageButton) myView.findViewById(R.id.btn_track09_step05);
		btnTrack9Step5.setOnTouchListener(new OnTouchListener() {
			int track = 9; int step = 5;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack9Step5.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack9Step5.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack9Step6 = (ImageButton) myView.findViewById(R.id.btn_track09_step06);
		btnTrack9Step6.setOnTouchListener(new OnTouchListener() {
			int track = 9; int step = 6;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack9Step6.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack9Step6.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack9Step7 = (ImageButton) myView.findViewById(R.id.btn_track09_step07);
		btnTrack9Step7.setOnTouchListener(new OnTouchListener() {
			int track = 9; int step = 7;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack9Step7.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack9Step7.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack10Step0 = (ImageButton) myView.findViewById(R.id.btn_track10_step00);
		btnTrack10Step0.setOnTouchListener(new OnTouchListener() {
			int track = 10; int step = 0;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack10Step0.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack10Step0.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack10Step1 = (ImageButton) myView.findViewById(R.id.btn_track10_step01);
		btnTrack10Step1.setOnTouchListener(new OnTouchListener() {
			int track = 10; int step = 1;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack10Step1.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack10Step1.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack10Step2 = (ImageButton) myView.findViewById(R.id.btn_track10_step02);
		btnTrack10Step2.setOnTouchListener(new OnTouchListener() {
			int track = 10; int step = 2;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack10Step2.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack10Step2.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack10Step3 = (ImageButton) myView.findViewById(R.id.btn_track10_step03);
		btnTrack10Step3.setOnTouchListener(new OnTouchListener() {
			int track = 10; int step = 3;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack10Step3.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack10Step3.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		
		btnTrack10Step4 = (ImageButton) myView.findViewById(R.id.btn_track10_step04);
		btnTrack10Step4.setOnTouchListener(new OnTouchListener() {
			int track = 10; int step = 4;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack10Step4.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack10Step4.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack10Step5 = (ImageButton) myView.findViewById(R.id.btn_track10_step05);
		btnTrack10Step5.setOnTouchListener(new OnTouchListener() {
			int track = 10; int step = 5;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack10Step5.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack10Step5.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack10Step6 = (ImageButton) myView.findViewById(R.id.btn_track10_step06);
		btnTrack10Step6.setOnTouchListener(new OnTouchListener() {
			int track = 10; int step = 6;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack10Step6.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack10Step6.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack10Step7 = (ImageButton) myView.findViewById(R.id.btn_track10_step07);
		btnTrack10Step7.setOnTouchListener(new OnTouchListener() {
			int track = 10; int step = 7;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack10Step7.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack10Step7.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack11Step0 = (ImageButton) myView.findViewById(R.id.btn_track11_step00);
		btnTrack11Step0.setOnTouchListener(new OnTouchListener() {
			int track = 11; int step = 0;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack11Step0.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack11Step0.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack11Step1 = (ImageButton) myView.findViewById(R.id.btn_track11_step01);
		btnTrack11Step1.setOnTouchListener(new OnTouchListener() {
			int track = 11; int step = 1;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack11Step1.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack11Step1.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack11Step2 = (ImageButton) myView.findViewById(R.id.btn_track11_step02);
		btnTrack11Step2.setOnTouchListener(new OnTouchListener() {
			int track = 11; int step = 2;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack11Step2.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack11Step2.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack11Step3 = (ImageButton) myView.findViewById(R.id.btn_track11_step03);
		btnTrack11Step3.setOnTouchListener(new OnTouchListener() {
			int track = 11; int step = 3;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack11Step3.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack11Step3.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack11Step4 = (ImageButton) myView.findViewById(R.id.btn_track11_step04);
		btnTrack11Step4.setOnTouchListener(new OnTouchListener() {
			int track = 11; int step = 4;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack11Step4.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack11Step4.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack11Step5 = (ImageButton) myView.findViewById(R.id.btn_track11_step05);
		btnTrack11Step5.setOnTouchListener(new OnTouchListener() {
			int track = 11; int step = 5;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack11Step5.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack11Step5.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack11Step6 = (ImageButton) myView.findViewById(R.id.btn_track11_step06);
		btnTrack11Step6.setOnTouchListener(new OnTouchListener() {
			int track = 11; int step = 6;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack11Step6.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack11Step6.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		btnTrack11Step7 = (ImageButton) myView.findViewById(R.id.btn_track11_step07);
		btnTrack11Step7.setOnTouchListener(new OnTouchListener() {
			int track = 11; int step = 7;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.sequence.findTrigger(track, data.currentBeat, step) == null) {
						btnTrack11Step7.setImageBitmap(bmpPressed);
						data.sequence.addTrigger(track, data.currentBeat, step);
					}
					else {
						btnTrack11Step7.setImageBitmap(bmpEmpty);
						data.sequence.clearTrigger(track, data.currentBeat, step);
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		return myView;
	}
	
	/**
	 * Called when the beat index is changed to update button images.
	 * Checks each of the 8 steps of each of the 12 tracks.
	 * If that step represents a location that contains a trigger, set image to "pressed"
	 * Else, set image to "empty"
	 */
	public void updateTracks() {
		// for loop : iterate through all 12 tracks
		for(int i=0; i<12; i++) {
			// for loop : iterate through all 8 steps
			// switch case for each step
			switch(i){
			case 0:
				for(int j=0; j<8; j++) {
					switch(j){
					case 0:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack0Step0.setImageBitmap(bmpPressed);
						else
							btnTrack0Step0.setImageBitmap(bmpEmpty);
						break;
					case 1:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack0Step1.setImageBitmap(bmpPressed);
						else
							btnTrack0Step1.setImageBitmap(bmpEmpty);
						break;
					case 2:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack0Step2.setImageBitmap(bmpPressed);
						else
							btnTrack0Step2.setImageBitmap(bmpEmpty);
						break;
					case 3:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack0Step3.setImageBitmap(bmpPressed);
						else
							btnTrack0Step3.setImageBitmap(bmpEmpty);
						break;
					case 4:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack0Step4.setImageBitmap(bmpPressed);
						else
							btnTrack0Step4.setImageBitmap(bmpEmpty);
						break;
					case 5:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack0Step5.setImageBitmap(bmpPressed);
						else
							btnTrack0Step5.setImageBitmap(bmpEmpty);
						break;
					case 6:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack0Step6.setImageBitmap(bmpPressed);
						else
							btnTrack0Step6.setImageBitmap(bmpEmpty);
						break;
					case 7:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack0Step7.setImageBitmap(bmpPressed);
						else
							btnTrack0Step7.setImageBitmap(bmpEmpty);
						break;
					}
				}
			case 1:
				for(int j=0; j<8; j++) {
					switch(j){
					case 0:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack1Step0.setImageBitmap(bmpPressed);
						else
							btnTrack1Step0.setImageBitmap(bmpEmpty);
						break;
					case 1:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack1Step1.setImageBitmap(bmpPressed);
						else
							btnTrack1Step1.setImageBitmap(bmpEmpty);
						break;
					case 2:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack1Step2.setImageBitmap(bmpPressed);
						else
							btnTrack1Step2.setImageBitmap(bmpEmpty);
						break;
					case 3:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack1Step3.setImageBitmap(bmpPressed);
						else
							btnTrack1Step3.setImageBitmap(bmpEmpty);
						break;
					case 4:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack1Step4.setImageBitmap(bmpPressed);
						else
							btnTrack1Step4.setImageBitmap(bmpEmpty);
						break;
					case 5:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack1Step5.setImageBitmap(bmpPressed);
						else
							btnTrack1Step5.setImageBitmap(bmpEmpty);
						break;
					case 6:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack1Step6.setImageBitmap(bmpPressed);
						else
							btnTrack1Step6.setImageBitmap(bmpEmpty);
						break;
					case 7:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack1Step7.setImageBitmap(bmpPressed);
						else
							btnTrack1Step7.setImageBitmap(bmpEmpty);
						break;
					}
				}
			case 2:
				for(int j=0; j<8; j++) {
					switch(j){
					case 0:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack2Step0.setImageBitmap(bmpPressed);
						else
							btnTrack2Step0.setImageBitmap(bmpEmpty);
						break;
					case 1:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack2Step1.setImageBitmap(bmpPressed);
						else
							btnTrack2Step1.setImageBitmap(bmpEmpty);
						break;
					case 2:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack2Step2.setImageBitmap(bmpPressed);
						else
							btnTrack2Step2.setImageBitmap(bmpEmpty);
						break;
					case 3:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack2Step3.setImageBitmap(bmpPressed);
						else
							btnTrack2Step3.setImageBitmap(bmpEmpty);
						break;
					case 4:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack2Step4.setImageBitmap(bmpPressed);
						else
							btnTrack2Step4.setImageBitmap(bmpEmpty);
						break;
					case 5:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack2Step5.setImageBitmap(bmpPressed);
						else
							btnTrack2Step5.setImageBitmap(bmpEmpty);
						break;
					case 6:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack2Step6.setImageBitmap(bmpPressed);
						else
							btnTrack2Step6.setImageBitmap(bmpEmpty);
						break;
					case 7:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack2Step7.setImageBitmap(bmpPressed);
						else
							btnTrack2Step7.setImageBitmap(bmpEmpty);
						break;
					}
				}
			case 3:
				for(int j=0; j<8; j++) {
					switch(j){
					case 0:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack3Step0.setImageBitmap(bmpPressed);
						else
							btnTrack3Step0.setImageBitmap(bmpEmpty);
						break;
					case 1:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack3Step1.setImageBitmap(bmpPressed);
						else
							btnTrack3Step1.setImageBitmap(bmpEmpty);
						break;
					case 2:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack3Step2.setImageBitmap(bmpPressed);
						else
							btnTrack3Step2.setImageBitmap(bmpEmpty);
						break;
					case 3:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack3Step3.setImageBitmap(bmpPressed);
						else
							btnTrack3Step3.setImageBitmap(bmpEmpty);
						break;
					case 4:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack3Step4.setImageBitmap(bmpPressed);
						else
							btnTrack3Step4.setImageBitmap(bmpEmpty);
						break;
					case 5:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack3Step5.setImageBitmap(bmpPressed);
						else
							btnTrack3Step5.setImageBitmap(bmpEmpty);
						break;
					case 6:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack3Step6.setImageBitmap(bmpPressed);
						else
							btnTrack3Step6.setImageBitmap(bmpEmpty);
						break;
					case 7:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack3Step7.setImageBitmap(bmpPressed);
						else
							btnTrack3Step7.setImageBitmap(bmpEmpty);
						break;
					}
				}
			case 4:
				for(int j=0; j<8; j++) {
					switch(j){
					case 0:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack4Step0.setImageBitmap(bmpPressed);
						else
							btnTrack4Step0.setImageBitmap(bmpEmpty);
						break;
					case 1:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack4Step1.setImageBitmap(bmpPressed);
						else
							btnTrack4Step1.setImageBitmap(bmpEmpty);
						break;
					case 2:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack4Step2.setImageBitmap(bmpPressed);
						else
							btnTrack4Step2.setImageBitmap(bmpEmpty);
						break;
					case 3:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack4Step3.setImageBitmap(bmpPressed);
						else
							btnTrack4Step3.setImageBitmap(bmpEmpty);
						break;
					case 4:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack4Step4.setImageBitmap(bmpPressed);
						else
							btnTrack4Step4.setImageBitmap(bmpEmpty);
						break;
					case 5:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack4Step5.setImageBitmap(bmpPressed);
						else
							btnTrack4Step5.setImageBitmap(bmpEmpty);
						break;
					case 6:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack4Step6.setImageBitmap(bmpPressed);
						else
							btnTrack4Step6.setImageBitmap(bmpEmpty);
						break;
					case 7:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack4Step7.setImageBitmap(bmpPressed);
						else
							btnTrack4Step7.setImageBitmap(bmpEmpty);
						break;
					}
				}
			case 5:
				for(int j=0; j<8; j++) {
					switch(j){
					case 0:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack5Step0.setImageBitmap(bmpPressed);
						else
							btnTrack5Step0.setImageBitmap(bmpEmpty);
						break;
					case 1:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack5Step1.setImageBitmap(bmpPressed);
						else
							btnTrack5Step1.setImageBitmap(bmpEmpty);
						break;
					case 2:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack5Step2.setImageBitmap(bmpPressed);
						else
							btnTrack5Step2.setImageBitmap(bmpEmpty);
						break;
					case 3:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack5Step3.setImageBitmap(bmpPressed);
						else
							btnTrack5Step3.setImageBitmap(bmpEmpty);
						break;
					case 4:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack5Step4.setImageBitmap(bmpPressed);
						else
							btnTrack5Step4.setImageBitmap(bmpEmpty);
						break;
					case 5:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack5Step5.setImageBitmap(bmpPressed);
						else
							btnTrack5Step5.setImageBitmap(bmpEmpty);
						break;
					case 6:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack5Step6.setImageBitmap(bmpPressed);
						else
							btnTrack5Step6.setImageBitmap(bmpEmpty);
						break;
					case 7:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack5Step7.setImageBitmap(bmpPressed);
						else
							btnTrack5Step7.setImageBitmap(bmpEmpty);
						break;
					}
				}
			case 6:
				for(int j=0; j<8; j++) {
					switch(j){
					case 0:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack6Step0.setImageBitmap(bmpPressed);
						else
							btnTrack6Step0.setImageBitmap(bmpEmpty);
						break;
					case 1:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack6Step1.setImageBitmap(bmpPressed);
						else
							btnTrack6Step1.setImageBitmap(bmpEmpty);
						break;
					case 2:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack6Step2.setImageBitmap(bmpPressed);
						else
							btnTrack6Step2.setImageBitmap(bmpEmpty);
						break;
					case 3:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack6Step3.setImageBitmap(bmpPressed);
						else
							btnTrack6Step3.setImageBitmap(bmpEmpty);
						break;
					case 4:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack6Step4.setImageBitmap(bmpPressed);
						else
							btnTrack6Step4.setImageBitmap(bmpEmpty);
						break;
					case 5:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack6Step5.setImageBitmap(bmpPressed);
						else
							btnTrack6Step5.setImageBitmap(bmpEmpty);
						break;
					case 6:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack6Step6.setImageBitmap(bmpPressed);
						else
							btnTrack6Step6.setImageBitmap(bmpEmpty);
						break;
					case 7:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack6Step7.setImageBitmap(bmpPressed);
						else
							btnTrack6Step7.setImageBitmap(bmpEmpty);
						break;
					}
				}
			case 7:
				for(int j=0; j<8; j++) {
					switch(j){
					case 0:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack7Step0.setImageBitmap(bmpPressed);
						else
							btnTrack7Step0.setImageBitmap(bmpEmpty);
						break;
					case 1:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack7Step1.setImageBitmap(bmpPressed);
						else
							btnTrack7Step1.setImageBitmap(bmpEmpty);
						break;
					case 2:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack7Step2.setImageBitmap(bmpPressed);
						else
							btnTrack7Step2.setImageBitmap(bmpEmpty);
						break;
					case 3:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack7Step3.setImageBitmap(bmpPressed);
						else
							btnTrack7Step3.setImageBitmap(bmpEmpty);
						break;
					case 4:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack7Step4.setImageBitmap(bmpPressed);
						else
							btnTrack7Step4.setImageBitmap(bmpEmpty);
						break;
					case 5:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack7Step5.setImageBitmap(bmpPressed);
						else
							btnTrack7Step5.setImageBitmap(bmpEmpty);
						break;
					case 6:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack7Step6.setImageBitmap(bmpPressed);
						else
							btnTrack7Step6.setImageBitmap(bmpEmpty);
						break;
					case 7:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack7Step7.setImageBitmap(bmpPressed);
						else
							btnTrack7Step7.setImageBitmap(bmpEmpty);
						break;
					}
				}
			case 8:
				for(int j=0; j<8; j++) {
					switch(j){
					case 0:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack8Step0.setImageBitmap(bmpPressed);
						else
							btnTrack8Step0.setImageBitmap(bmpEmpty);
						break;
					case 1:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack8Step1.setImageBitmap(bmpPressed);
						else
							btnTrack8Step1.setImageBitmap(bmpEmpty);
						break;
					case 2:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack8Step2.setImageBitmap(bmpPressed);
						else
							btnTrack8Step2.setImageBitmap(bmpEmpty);
						break;
					case 3:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack8Step3.setImageBitmap(bmpPressed);
						else
							btnTrack8Step3.setImageBitmap(bmpEmpty);
						break;
					case 4:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack8Step4.setImageBitmap(bmpPressed);
						else
							btnTrack8Step4.setImageBitmap(bmpEmpty);
						break;
					case 5:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack8Step5.setImageBitmap(bmpPressed);
						else
							btnTrack8Step5.setImageBitmap(bmpEmpty);
						break;
					case 6:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack8Step6.setImageBitmap(bmpPressed);
						else
							btnTrack8Step6.setImageBitmap(bmpEmpty);
						break;
					case 7:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack8Step7.setImageBitmap(bmpPressed);
						else
							btnTrack8Step7.setImageBitmap(bmpEmpty);
						break;
					}
				}
			case 9:
				for(int j=0; j<8; j++) {
					switch(j){
					case 0:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack9Step0.setImageBitmap(bmpPressed);
						else
							btnTrack9Step0.setImageBitmap(bmpEmpty);
						break;
					case 1:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack9Step1.setImageBitmap(bmpPressed);
						else
							btnTrack9Step1.setImageBitmap(bmpEmpty);
						break;
					case 2:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack9Step2.setImageBitmap(bmpPressed);
						else
							btnTrack9Step2.setImageBitmap(bmpEmpty);
						break;
					case 3:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack9Step3.setImageBitmap(bmpPressed);
						else
							btnTrack9Step3.setImageBitmap(bmpEmpty);
						break;
					case 4:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack9Step4.setImageBitmap(bmpPressed);
						else
							btnTrack9Step4.setImageBitmap(bmpEmpty);
						break;
					case 5:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack9Step5.setImageBitmap(bmpPressed);
						else
							btnTrack9Step5.setImageBitmap(bmpEmpty);
						break;
					case 6:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack9Step6.setImageBitmap(bmpPressed);
						else
							btnTrack9Step6.setImageBitmap(bmpEmpty);
						break;
					case 7:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack9Step7.setImageBitmap(bmpPressed);
						else
							btnTrack9Step7.setImageBitmap(bmpEmpty);
						break;
					}
				}
			case 10:
				for(int j=0; j<8; j++) {
					switch(j){
					case 0:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack10Step0.setImageBitmap(bmpPressed);
						else
							btnTrack10Step0.setImageBitmap(bmpEmpty);
						break;
					case 1:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack10Step1.setImageBitmap(bmpPressed);
						else
							btnTrack10Step1.setImageBitmap(bmpEmpty);
						break;
					case 2:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack10Step2.setImageBitmap(bmpPressed);
						else
							btnTrack10Step2.setImageBitmap(bmpEmpty);
						break;
					case 3:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack10Step3.setImageBitmap(bmpPressed);
						else
							btnTrack10Step3.setImageBitmap(bmpEmpty);
						break;
					case 4:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack10Step4.setImageBitmap(bmpPressed);
						else
							btnTrack10Step4.setImageBitmap(bmpEmpty);
						break;
					case 5:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack10Step5.setImageBitmap(bmpPressed);
						else
							btnTrack10Step5.setImageBitmap(bmpEmpty);
						break;
					case 6:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack10Step6.setImageBitmap(bmpPressed);
						else
							btnTrack10Step6.setImageBitmap(bmpEmpty);
						break;
					case 7:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack10Step7.setImageBitmap(bmpPressed);
						else
							btnTrack10Step7.setImageBitmap(bmpEmpty);
						break;
					}
				}
			case 11:
				for(int j=0; j<8; j++) {
					switch(j){
					case 0:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack11Step0.setImageBitmap(bmpPressed);
						else
							btnTrack11Step0.setImageBitmap(bmpEmpty);
						break;
					case 1:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack11Step1.setImageBitmap(bmpPressed);
						else
							btnTrack11Step1.setImageBitmap(bmpEmpty);
						break;
					case 2:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack11Step2.setImageBitmap(bmpPressed);
						else
							btnTrack11Step2.setImageBitmap(bmpEmpty);
						break;
					case 3:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack11Step3.setImageBitmap(bmpPressed);
						else
							btnTrack11Step3.setImageBitmap(bmpEmpty);
						break;
					case 4:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack11Step4.setImageBitmap(bmpPressed);
						else
							btnTrack11Step4.setImageBitmap(bmpEmpty);
						break;
					case 5:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack11Step5.setImageBitmap(bmpPressed);
						else
							btnTrack11Step5.setImageBitmap(bmpEmpty);
						break;
					case 6:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack11Step6.setImageBitmap(bmpPressed);
						else
							btnTrack11Step6.setImageBitmap(bmpEmpty);
						break;
					case 7:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack11Step7.setImageBitmap(bmpPressed);
						else
							btnTrack11Step7.setImageBitmap(bmpEmpty);
						break;
					}
				}
			}
		}
	}
}
