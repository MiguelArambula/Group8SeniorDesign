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
	
	MainActivity data;
	int[][] myPad;
	
	Bitmap bmpEmpty, bmpLoaded, bmpPressed;
	
	boolean editEnable = ConsoleFragment.editOn;
	
	final PhatTracks tracks = new PhatTracks();
	final String currPad = "";
	
	public PhatPadFragment() {}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		data = (MainActivity) getActivity(); // create instance
		data.setTracks(tracks);
		View myView = inflater.inflate(R.layout.fragment_phatpad, container, false);
		/* pad bitmaps */
		bmpEmpty = BitmapFactory.decodeResource(getResources(), R.drawable.pad_empty);
		bmpLoaded = BitmapFactory.decodeResource(getResources(), R.drawable.pad_loaded);
		bmpPressed = BitmapFactory.decodeResource(getResources(), R.drawable.pad_pressed);
		
		/* myPad flags if a pad has a sample in it */
		//final PhatTracks tracks = new PhatTracks();
		
		myPad = new int[4][3];
		initPad();
		data.setGrid(myPad);
		//loadTracks();
		update(tracks);
		
		/* trigger pads */
		final ImageButton pad01 = (ImageButton) myView.findViewById(R.id.pad01);
		if(myPad[0][0] == 1) pad01.setImageBitmap(bmpLoaded);
		pad01.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					data.setText("Pad 1");
					pad01.setImageBitmap(bmpPressed);
					if(myPad[0][0] != 0){
	            		tracks.play(0, 0);
						data.setSel(tracks.getSampName(0, 0));
					} else {
						data.setSel("No Sample");
					}
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
					data.setText("Pad 2");
					pad02.setImageBitmap(bmpPressed);
					if(myPad[1][0] != 0){
	            		tracks.play(1, 0);
	            		data.setSel(tracks.getSampName(1, 0));
					} else {
						data.setSel("No Sample");
					}
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
					data.setText("Pad 3");
					pad03.setImageBitmap(bmpPressed);
					if(myPad[2][0] != 0){
	            		tracks.play(2, 0);
	            		data.setSel(tracks.getSampName(2, 0));
					} else {
						data.setSel("No Sample");
					}
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
					data.setText("Pad 4");
					pad04.setImageBitmap(bmpPressed);
					if(myPad[3][0] != 0){
	            		tracks.play(3, 0);
	            		data.setSel(tracks.getSampName(3, 0));
					} else {
						data.setSel("No Sample");
					}
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
					data.setText("Pad 5");
					pad05.setImageBitmap(bmpPressed);
					if(myPad[0][1] != 0){
	            		tracks.play(0, 1);
	            		data.setSel(tracks.getSampName(0, 1));
					} else {
						data.setSel("No Sample");
					}
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
					data.setText("Pad 6");
					pad06.setImageBitmap(bmpPressed);
					if(myPad[1][1] != 0){
	            		tracks.play(1, 1);
	            		data.setSel(tracks.getSampName(1, 1));
					} else {
						data.setSel("No Sample");
					}
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
					data.setText("Pad 7");
					pad07.setImageBitmap(bmpPressed);
					if(myPad[2][1] != 0){
	            		tracks.play(2, 1);
	            		data.setSel(tracks.getSampName(2, 1));
					} else {
						data.setSel("No Sample");
					}
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
					data.setText("Pad 8");
					pad08.setImageBitmap(bmpPressed);
					if(myPad[3][1] != 0){
	            		tracks.play(3, 1);
	            		data.setSel(tracks.getSampName(3, 1));
					} else {
						data.setSel("No Sample");
					}
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
					data.setText("Pad 9");
					pad09.setImageBitmap(bmpPressed);
					if(myPad[0][2] != 0){
	            		tracks.play(0, 2);
	            		data.setSel(tracks.getSampName(0, 2));
					} else {
						data.setSel("No Sample");
					}
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
					data.setText("Pad 10");
					pad10.setImageBitmap(bmpPressed);
					if(myPad[1][2] != 0){
	            		tracks.play(1, 2);
	            		data.setSel(tracks.getSampName(1, 2));
					} else {
						data.setSel("No Sample");
					}
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
					data.setText("Pad 11");
					pad11.setImageBitmap(bmpPressed);
					if(myPad[2][2] != 0){
	            		tracks.play(2, 2);
	            		data.setSel(tracks.getSampName(2, 2));
					} else {
						data.setSel("No Sample");
					}
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
					data.setText("Pad 12");
					pad12.setImageBitmap(bmpPressed);
					if(myPad[3][2] != 0){
	            		tracks.play(3, 2);
	            		data.setSel(tracks.getSampName(3, 2));
					} else {
						data.setSel("No Sample");
					}
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
	
	public void update(PhatTracks p){
		for(int i=0; i<4; i++) {
			for(int j=0; j<3; j++) {
				if(p.getTrack(i, j) != null)
					myPad[i][j] = 1;
			}
		}
	}
}
