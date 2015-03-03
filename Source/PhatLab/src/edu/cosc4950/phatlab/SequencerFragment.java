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

public class SequencerFragment extends Fragment{
	
	/* TODO
	 * TELL Miguel to update max beat text
	 * HELP Miguel implement start and stop commands including looped play
	 * 
	 * ADD looped play
	 * ADD unity between sample on pad and in sequencer
	 */
	
	MainActivity data;
	Bitmap bmpPressed, bmpEmpty;
	
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
		
		// Loop Control
		final ToggleButton toggleLoop = (ToggleButton) myView.findViewById(R.id.toggle_loop);
		toggleLoop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked)
					data.loopOn = true;
				else
					data.loopOn = false;
			}
		});
		
		// initialize loop text
		final TextView tvLoop = (TextView) myView.findViewById(R.id.tv_loop);
		if(data.currentBeat+data.loopLength <= data.maxBeat)
			tvLoop.setText((data.currentBeat + 1) + ":" + (data.currentBeat + 1 + data.loopLength));
		
		final TextView tvBeat = (TextView) myView.findViewById(R.id.tv_current_beat);
		
		final Button btnDecLoop = (Button) myView.findViewById(R.id.btn_dec_loop);
		btnDecLoop.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.loopLength != 0) {
						data.loopLength--;
						tvLoop.setText((data.currentBeat + 1) + ":" + (data.currentBeat + 1 + data.loopLength));
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		final Button btnIncLoop = (Button) myView.findViewById(R.id.btn_inc_loop);
		btnIncLoop.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.currentBeat+data.loopLength != data.maxBeat) {
						data.loopLength++;
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
		final Button btnDecBeat = (Button) myView.findViewById(R.id.btn_dec_beat);
		btnDecBeat.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.currentBeat > 0) {
						data.currentBeat--;
						tvBeat.setText(data.currentBeat + 1 +"");
						tvLoop.setText((data.currentBeat + 1) + ":" + (data.currentBeat + 1 + data.loopLength));
						updateTracks();
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		final Button btnIncBeat = (Button) myView.findViewById(R.id.btn_inc_beat);
		btnIncBeat.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(data.currentBeat < data.maxBeat) {
						data.currentBeat++;
						tvBeat.setText(data.currentBeat + 1 +"");
						if(data.currentBeat + data.loopLength > data.maxBeat)
							data.loopLength--;
						tvLoop.setText((data.currentBeat + 1) + ":" + (data.currentBeat + 1 + data.loopLength));
						updateTracks();
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				}
				return false;
			}
		});
		
		// Trigger Buttons
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
		
		return myView;
	}
	
	/**
	 * 
	 */
	public void updateTracks() {
		// for loop : iterate through all 12 tracks
		for(int i=0; i<2; i++) {
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
					case 1:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack0Step1.setImageBitmap(bmpPressed);
						else
							btnTrack0Step1.setImageBitmap(bmpEmpty);
					case 2:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack0Step2.setImageBitmap(bmpPressed);
						else
							btnTrack0Step2.setImageBitmap(bmpEmpty);
					case 3:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack0Step3.setImageBitmap(bmpPressed);
						else
							btnTrack0Step3.setImageBitmap(bmpEmpty);
					case 4:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack0Step4.setImageBitmap(bmpPressed);
						else
							btnTrack0Step4.setImageBitmap(bmpEmpty);
					case 5:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack0Step5.setImageBitmap(bmpPressed);
						else
							btnTrack0Step5.setImageBitmap(bmpEmpty);
					case 6:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack0Step6.setImageBitmap(bmpPressed);
						else
							btnTrack0Step6.setImageBitmap(bmpEmpty);
					case 7:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack0Step7.setImageBitmap(bmpPressed);
						else
							btnTrack0Step7.setImageBitmap(bmpEmpty);
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
					case 1:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack1Step1.setImageBitmap(bmpPressed);
						else
							btnTrack1Step1.setImageBitmap(bmpEmpty);
					case 2:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack1Step2.setImageBitmap(bmpPressed);
						else
							btnTrack1Step2.setImageBitmap(bmpEmpty);
					case 3:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack1Step3.setImageBitmap(bmpPressed);
						else
							btnTrack1Step3.setImageBitmap(bmpEmpty);
					case 4:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack1Step4.setImageBitmap(bmpPressed);
						else
							btnTrack1Step4.setImageBitmap(bmpEmpty);
					case 5:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack1Step5.setImageBitmap(bmpPressed);
						else
							btnTrack1Step5.setImageBitmap(bmpEmpty);
					case 6:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack1Step6.setImageBitmap(bmpPressed);
						else
							btnTrack1Step6.setImageBitmap(bmpEmpty);
					case 7:
						if(data.sequence.findTrigger(i,data.currentBeat,j) != null)
							btnTrack1Step7.setImageBitmap(bmpPressed);
						else
							btnTrack1Step7.setImageBitmap(bmpEmpty);
					}
				}
			}
		}
	}
}
