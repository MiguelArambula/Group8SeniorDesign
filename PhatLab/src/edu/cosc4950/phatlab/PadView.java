package edu.cosc4950.phatlab;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class PadView extends View {

	/*
	 * 288px , 360 px		button size
	 * 1152px, 1080 px		PhatPad size
	 */
	
	public Context myContext;
	Bitmap triggerPadBmp;
	float scale;
	
	int col1_left, col1_right, col2_left, col2_right, col3_left, col3_right, col4_left, col4_right;
	int row1_top, row1_bot, row2_top, row2_bot, row3_top, row3_bot;
	
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
		
		//triggerPadBmp = BitmapFactory.decodeResource(getResources(), R.drawable.phatPadTriggerPad);
	
		scale = getResources().getDisplayMetrics().density;
		
		col1_left = 0;   col1_right = 288;
		col2_left = 288; col2_right = 576;
		col3_left = 576; col3_right = 864;
		col4_left = 864; col4_right = 1152;
		row1_top = 0;    row1_bot = 360;
		row2_top = 360;  row2_bot = 720;
		row3_top = 720;  row3_bot = 1080;
		
		col1_left *= scale; col1_right *= scale;
		col2_left *= scale; col2_right *= scale;
		col3_left *= scale; col3_right *= scale;
		col4_left *= scale; col4_right *= scale;
		row1_top *= scale;  row1_bot *= scale;
		row2_top *= scale;  row2_bot *= scale;
		row3_top *= scale;  row3_bot *= scale;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		super.onDraw(canvas);
		
		canvas.drawColor(Color.DKGRAY);
	}

}
