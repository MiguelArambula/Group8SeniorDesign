package edu.cosc4950.phatlab;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class PadView extends View {

	/*
	 * screen size: 383 x 295
     * pad size: 81 x 82
     * padding size: 12px
	 */
	
	public Context myContext;
	float scale;
	
	int[][] myPad;
	PhatTracks padTracks;
	
	int col1_left, col1_right, col2_left, col2_right, col3_left, col3_right, col4_left, col4_right;
	int row1_top, row1_bot, row2_top, row2_bot, row3_top, row3_bot;
	
	ExternalData ed = new ExternalData();
	byte[] tmp = ed.loadPCM16bit("airhorn");
	PCM sample1 = new PCM(tmp, 44100,true, false);
	byte[] tmp2 = ed.loadPCM16bit("what");
	PCM sample2 = new PCM(tmp2, 44100,true, false);
	
	Bitmap bg, loadedPadBmp, pressedPadBmp;
	
	public PadView(Context context) {
		super(context);
		myContext = context;
		setup();
	}
	
	public PadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		myContext = context;
		setup();
	}
	
	public PadView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		myContext = context;
		setup();
	}
	
	public void setup() {
		
		bg = BitmapFactory.decodeResource(getResources(), R.drawable.pad_bg);
		loadedPadBmp = BitmapFactory.decodeResource(getResources(), R.drawable.loaded_pad);
		pressedPadBmp = BitmapFactory.decodeResource(getResources(), R.drawable.pressed_pad);
		
		padTracks = new PhatTracks();
		
		scale = getResources().getDisplayMetrics().density;
		
		col1_left = 12;   col1_right = 93;
		col2_left = 105; col2_right = 186;
		col3_left = 198; col3_right = 279;
		col4_left = 291; col4_right = 372;
		row1_top = 12;    row1_bot = 94;
		row2_top = 106;  row2_bot = 188;
		row3_top = 200;  row3_bot = 282;
		
		col1_left *= scale; col1_right *= scale;
		col2_left *= scale; col2_right *= scale;
		col3_left *= scale; col3_right *= scale;
		col4_left *= scale; col4_right *= scale;
		row1_top *= scale;  row1_bot *= scale;
		row2_top *= scale;  row2_bot *= scale;
		row3_top *= scale;  row3_bot *= scale;
		
		myPad = new int[4][3];
		initPad();
		
		loadSamples();
		
		invalidate();
	}
	
	private void initPad() {
		for(int i=0; i<4; i++) {
			for(int j=0; j<3; j++) {
				myPad[i][j] = 0;
			}
		}
	}
	
	private void loadSamples() {
		String pad01 = "airhorn";
		padTracks.setTrack(0, 0, pad01);

		for(int i=0; i<4; i++) {
			for(int j=0; j<3; j++) {
				if(padTracks.getTrack(i, j) != null)
					myPad[i][j] = 1;
			}
		}
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View myView = inflater.inflate(R.layout.fragment_phatpad, container, false);
		return myView;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		super.onDraw(canvas);
		
		canvas.drawColor(Color.DKGRAY);
		canvas.drawBitmap(bg, 0, 0, null);
		
		/* first row */
		if(myPad[0][0] == 1) canvas.drawBitmap(loadedPadBmp, col1_left, row1_top, null);
		if(myPad[1][0] == 1) canvas.drawBitmap(loadedPadBmp, col2_left, row1_top, null);
		if(myPad[2][0] == 1) canvas.drawBitmap(loadedPadBmp, col3_left, row1_top, null);
		if(myPad[3][0] == 1) canvas.drawBitmap(loadedPadBmp, col4_left, row1_top, null);
		
		/* second row */
		if(myPad[0][1] == 1) canvas.drawBitmap(loadedPadBmp, col1_left, row2_top, null);
		if(myPad[1][1] == 1) canvas.drawBitmap(loadedPadBmp, col2_left, row2_top, null);
		if(myPad[2][1] == 1) canvas.drawBitmap(loadedPadBmp, col3_left, row2_top, null);
		if(myPad[3][1] == 1) canvas.drawBitmap(loadedPadBmp, col4_left, row2_top, null);
		
		/* third row */
		if(myPad[0][2] == 1) canvas.drawBitmap(loadedPadBmp, col1_left, row3_top, null);
		if(myPad[1][2] == 1) canvas.drawBitmap(loadedPadBmp, col2_left, row3_top, null);
		if(myPad[2][2] == 1) canvas.drawBitmap(loadedPadBmp, col3_left, row3_top, null);
		if(myPad[3][2] == 1) canvas.drawBitmap(loadedPadBmp, col4_left, row3_top, null);
		
		if(myPad[0][0] == 2) canvas.drawBitmap(pressedPadBmp, col1_left, row1_top, null);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		
		int x = (int) event.getX();
		int y = (int) event.getY();
		
		switch(action) {
		case MotionEvent.ACTION_DOWN:
			if(y > row1_top && y < row1_bot) { 
				if (x > col1_left && x < col1_right && myPad[0][0] != 0) {
					// ROW 1, COL 1
					
					//padTracks.play(0,0);
					
					
					sample1.stream();
					//sample.release();
					
					//myPad[0][0] = 2; // draw red
					invalidate(); // redraws screen
					Toast.makeText(getContext(), "PAD 01", Toast.LENGTH_SHORT).show();
				}
				else if(x > col2_left && x < col2_right/* && myPad[1][0] != 0*/) {
					// ROW 1 COL 2
					sample2.stream();
					
					Toast.makeText(getContext(), "PAD 02", Toast.LENGTH_SHORT).show();
				}
				else if(x > col3_left && x < col3_right/* && myPad[2][0] != 0*/) {
					// ROW 1 COL 3
					Toast.makeText(getContext(), "PAD 03", Toast.LENGTH_SHORT).show();
				}
				else if(x > col4_left && x < col4_right/* && myPad[3][0] != 0*/) {
					// ROW 1 COL 4
					Toast.makeText(getContext(), "PAD 04", Toast.LENGTH_SHORT).show();
				}
			}
			else if(y > row2_top && y < row2_bot) { 
				if (x > col1_left && x < col1_right/* && myPad[0][1] != 0*/) {
					// ROW 2 COL 1
					Toast.makeText(getContext(), "PAD 05", Toast.LENGTH_SHORT).show();
				}
				else if(x > col2_left && x < col2_right/* && myPad[1][1] != 0*/) {
					// ROW 2 COL 2
					Toast.makeText(getContext(), "PAD 06", Toast.LENGTH_SHORT).show();
				}
				else if(x > col3_left && x < col3_right/* && myPad[2][1] != 0*/) {
					// ROW 2 COL 3
					Toast.makeText(getContext(), "PAD 07", Toast.LENGTH_SHORT).show();
				}
				else if(x > col4_left && x < col4_right/* && myPad[3][1] != 0*/) {
					// ROW 2 COL 4
					Toast.makeText(getContext(), "PAD 08", Toast.LENGTH_SHORT).show();
				}
			}
			else if(y > row3_top && y < row3_bot) {
				if (x > col1_left && x < col1_right/* && myPad[0][2] != 0*/) {
					// ROW 3 COL 1
					Toast.makeText(getContext(), "PAD 09", Toast.LENGTH_SHORT).show();
				}
				else if(x > col2_left && x < col2_right/* && myPad[1][2] != 0*/) {
					// ROW 3 COL 2
					Toast.makeText(getContext(), "PAD 10", Toast.LENGTH_SHORT).show();
				}
				else if(x > col3_left && x < col3_right/* && myPad[2][2] != 0*/) {
					// ROW 3 COL 3
					Toast.makeText(getContext(), "PAD 11", Toast.LENGTH_SHORT).show();
				}
				else if(x > col4_left && x < col4_right/* && myPad[3][2] != 0*/) {
					// ROW 3 COL 4
					Toast.makeText(getContext(), "PAD 12", Toast.LENGTH_SHORT).show();
				}
			}
		/*
		case MotionEvent.ACTION_UP:
			for(int i=0; i<4; i++) {
				for(int j=0; j<3; j++) {
					if(myPad[i][j] == 2)
						myPad[i][j]--;
				}
			}
			invalidate();
		*/
		}
		
		return false;
	}
}
