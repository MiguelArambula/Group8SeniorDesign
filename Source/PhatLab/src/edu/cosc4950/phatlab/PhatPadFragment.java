package edu.cosc4950.phatlab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class PhatPadFragment extends Fragment {
	
	int[][] myPad;
	PhatTracks padTracks;
	Bitmap bmpEmpty, bmpLoaded, bmpPressed;
	
	boolean editEnable = ConsoleFragment.editOn;
	
	public PhatPadFragment() {}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View myView = inflater.inflate(R.layout.fragment_phatpad, container, false);

		/* pad bitmaps */
		bmpEmpty = BitmapFactory.decodeResource(getResources(), R.drawable.pad_empty);
		bmpLoaded = BitmapFactory.decodeResource(getResources(), R.drawable.pad_loaded);
		bmpPressed = BitmapFactory.decodeResource(getResources(), R.drawable.pad_pressed);
		
		/* myPad flags if a pad has a sample in it */
		myPad = new int[4][3];
		initPad();
		
		/* our instance of PhatTracks */ //TODO might move this up to MainActivity
		padTracks = new PhatTracks();
		loadTracks();
		
		/* trigger pads */
		final ImageButton pad01 = (ImageButton) myView.findViewById(R.id.pad01);
		if(myPad[0][0] == 1) pad01.setImageBitmap(bmpLoaded);
		pad01.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					pad01.setImageBitmap(bmpPressed);
					if(myPad[0][0] != 0)
	            		padTracks.play(0, 0);
					return true;
				case MotionEvent.ACTION_UP:
					if(myPad[0][0] == 0)
						pad01.setImageBitmap(bmpEmpty);
					else if(myPad[0][0] == 1)
						pad01.setImageBitmap(bmpLoaded);
					return true;
				}
				return false;
			}
		});
		
		final ImageButton pad02 = (ImageButton) myView.findViewById(R.id.pad02);
		if(myPad[1][0] == 1) pad02.setImageBitmap(bmpLoaded);
		pad02.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					pad02.setImageBitmap(bmpPressed);
					if(myPad[1][0] != 0)
	            		padTracks.play(1, 0);
					return true;
				case MotionEvent.ACTION_UP:
					if(myPad[1][0] == 0)
						pad02.setImageBitmap(bmpEmpty);
					else if(myPad[1][0] == 1)
						pad02.setImageBitmap(bmpLoaded);
					return true;
				}
				return false;
			}
		});
		
		final ImageButton pad03 = (ImageButton) myView.findViewById(R.id.pad03);
		if(myPad[2][0] == 1) pad03.setImageBitmap(bmpLoaded);
		pad03.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					pad03.setImageBitmap(bmpPressed);
					if(myPad[2][0] != 0)
	            		padTracks.play(2, 0);
					return true;
				case MotionEvent.ACTION_UP:
					if(myPad[2][0] == 0)
						pad03.setImageBitmap(bmpEmpty);
					else if(myPad[2][0] == 1)
						pad03.setImageBitmap(bmpLoaded);
					return true;
				}
				return false;
			}
		});
		
		final ImageButton pad04 = (ImageButton) myView.findViewById(R.id.pad04);
		if(myPad[3][0] == 1) pad04.setImageBitmap(bmpLoaded);
		pad04.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					pad04.setImageBitmap(bmpPressed);
					if(myPad[3][0] != 0)
	            		padTracks.play(3, 0);
					return true;
				case MotionEvent.ACTION_UP:
					if(myPad[3][0] == 0)
						pad04.setImageBitmap(bmpEmpty);
					else if(myPad[3][0] == 1)
						pad04.setImageBitmap(bmpLoaded);
					return true;
				}
				return false;
			}
		});
		
		final ImageButton pad05 = (ImageButton) myView.findViewById(R.id.pad05);
		if(myPad[0][1] == 1) pad05.setImageBitmap(bmpLoaded);
		pad05.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					pad05.setImageBitmap(bmpPressed);
					if(myPad[0][1] != 0)
	            		padTracks.play(0, 1);
					return true;
				case MotionEvent.ACTION_UP:
					if(myPad[0][1] == 0)
						pad05.setImageBitmap(bmpEmpty);
					else if(myPad[0][1] == 1)
						pad05.setImageBitmap(bmpLoaded);
					return true;
				}
				return false;
			}
		});
		
		final ImageButton pad06 = (ImageButton) myView.findViewById(R.id.pad06);
		if(myPad[1][1] == 1) pad06.setImageBitmap(bmpLoaded);
		pad06.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					pad06.setImageBitmap(bmpPressed);
					if(myPad[1][1] != 0)
	            		padTracks.play(1, 1);
					return true;
				case MotionEvent.ACTION_UP:
					if(myPad[1][1] == 0)
						pad06.setImageBitmap(bmpEmpty);
					else if(myPad[1][1] == 1)
						pad06.setImageBitmap(bmpLoaded);
					return true;
				}
				return false;
			}
		});
		
		final ImageButton pad07 = (ImageButton) myView.findViewById(R.id.pad07);
		if(myPad[2][1] == 1) pad07.setImageBitmap(bmpLoaded);
		pad07.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					pad07.setImageBitmap(bmpPressed);
					if(myPad[2][1] != 0)
	            		padTracks.play(2, 1);
					return true;
				case MotionEvent.ACTION_UP:
					if(myPad[2][1] == 0)
						pad07.setImageBitmap(bmpEmpty);
					else if(myPad[2][1] == 1)
						pad07.setImageBitmap(bmpLoaded);
					return true;
				}
				return false;
			}
		});
		
		final ImageButton pad08 = (ImageButton) myView.findViewById(R.id.pad08);
		if(myPad[3][1] == 1) pad08.setImageBitmap(bmpLoaded);
		pad08.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					pad08.setImageBitmap(bmpPressed);
					if(myPad[3][1] != 0)
	            		padTracks.play(3, 1);
					return true;
				case MotionEvent.ACTION_UP:
					if(myPad[3][1] == 0)
						pad08.setImageBitmap(bmpEmpty);
					else if(myPad[3][1] == 1)
						pad08.setImageBitmap(bmpLoaded);
					return true;
				}
				return false;
			}
		});
		
		final ImageButton pad09 = (ImageButton) myView.findViewById(R.id.pad09);
		if(myPad[0][2] == 1) pad09.setImageBitmap(bmpLoaded);
		pad09.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					pad09.setImageBitmap(bmpPressed);
					if(myPad[0][2] != 0)
	            		padTracks.play(0, 2);
					return true;
				case MotionEvent.ACTION_UP:
					if(myPad[0][2] == 0)
						pad09.setImageBitmap(bmpEmpty);
					else if(myPad[0][2] == 1)
						pad09.setImageBitmap(bmpLoaded);
					return true;
				}
				return false;
			}
		});
		
		final ImageButton pad10 = (ImageButton) myView.findViewById(R.id.pad10);
		if(myPad[1][2] == 1) pad10.setImageBitmap(bmpLoaded);
		pad10.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					pad10.setImageBitmap(bmpPressed);
					if(myPad[1][2] != 0)
	            		padTracks.play(1, 2);
					return true;
				case MotionEvent.ACTION_UP:
					if(myPad[1][2] == 0)
						pad10.setImageBitmap(bmpEmpty);
					else if(myPad[1][2] == 1)
						pad10.setImageBitmap(bmpLoaded);
					return true;
				}
				return false;
			}
		});
		
		final ImageButton pad11 = (ImageButton) myView.findViewById(R.id.pad11);
		if(myPad[2][2] == 1) pad11.setImageBitmap(bmpLoaded);
		pad11.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					pad11.setImageBitmap(bmpPressed);
					if(myPad[2][2] != 0)
	            		padTracks.play(2, 2);
					return true;
				case MotionEvent.ACTION_UP:
					if(myPad[2][2] == 0)
						pad11.setImageBitmap(bmpEmpty);
					else if(myPad[2][2] == 1)
						pad11.setImageBitmap(bmpLoaded);
					return true;
				}
				return false;
			}
		});
		
		final ImageButton pad12 = (ImageButton) myView.findViewById(R.id.pad12);
		if(myPad[3][2] == 1) pad12.setImageBitmap(bmpLoaded);
		pad12.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					pad12.setImageBitmap(bmpPressed);
					if(myPad[3][2] != 0)
	            		padTracks.play(3, 2);
					return true;
				case MotionEvent.ACTION_UP:
					if(myPad[3][2] == 0)
						pad12.setImageBitmap(bmpEmpty);
					else if(myPad[3][2] == 1)
						pad12.setImageBitmap(bmpLoaded);
					return true;
				}
				return false;
			}
		});
		
		return myView;
	}
	
	public void initPad() {
		for(int i=0; i<4; i++) {
			for(int j=0; j<3; j++) {
				myPad[i][j] = 0;
			}
		}
	}
	
	/* temporary function until we have our file manager */
	public void loadTracks() {
		//TODO
		/*padTracks.setTrack(0, 0, "airhorn");
		padTracks.setTrack(1, 0, "chant_hey1");
		padTracks.setTrack(2, 0, "bass_dred");
		
		padTracks.setTrack(1, 1, "amen_hat2");
		padTracks.setTrack(2, 1, "amen_snare1");
		padTracks.setTrack(3, 1, "amen_hat3");
		
		padTracks.setTrack(0, 2, "amen_kick1");
		padTracks.setTrack(1, 2, "amen_kick2");
		padTracks.setTrack(2, 2, "amen_hat1");*/
		
		padTracks.setTrack(1, 0, "airhorn");			//pad02
		padTracks.setTrack(2, 0, "blame_the_coders");		//pad03
		padTracks.setTrack(3, 0, "amen_crash1");	//pad04
		
		padTracks.setTrack(0, 1, "hackd_kick1");	//pad05
		padTracks.setTrack(2, 1, "amen_snare1");	//pad06
		padTracks.setTrack(3, 1, "amen_hat1");		//pad07
		
		padTracks.setTrack(0, 2, "amen_kick1");		//pad09
		padTracks.setTrack(1, 2, "amen_kick2");		//pad10
		padTracks.setTrack(3, 2, "909_hat1");		//pad12
		
		/*
		// Attaches a merged PCM to the first pad
		padTracks.setTrack(0, 0, padTracks.getTrack(1, 0).mergePCM(padTracks.getTrack(2,0));
		*/
		
		/*
		// Currently, audio is not snappy enough. Must find a fix
		SequenceTimer demo = new SequenceTimer(140, 16); //Create sequence
		demo.setSample(padTracks.getTrack(2,1), 0); // Sets track 1's sound
		demo.setSample(padTracks.getTrack(3,2), 1); //Sets track 2's sound
		
		//Add times to play the sounds:
		for (int i = 0; i < 3; ++i)
		{
			//Sound 1 at whole beat intervals at 1/16 time
			demo.addTrigger(0, i, 0);
			demo.addTrigger(0, i, 4);
			demo.addTrigger(0, i, 8);
			demo.addTrigger(0, i, 12);
			
			//Sound 2:
			demo.addTrigger(1, i, 10);
		}
		
		//What segment to play. -1 means begin and end respectively
		demo.setPlayTime(-1, 0, -1, 0);
		demo.start(); // Starts the sequence.
		//Can be stopped early with demo.stop() or demo.pause()
		*/
		
		
		/* update myPad */
		for(int i=0; i<4; i++) {
			for(int j=0; j<3; j++) {
				if(padTracks.getTrack(i, j) != null)
					myPad[i][j] = 1;
			}
		}
	}
}
