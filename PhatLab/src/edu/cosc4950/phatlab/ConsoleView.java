package edu.cosc4950.phatlab;

import android.graphics.Color;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class ConsoleView extends View {

	public Context myContext;
	
	public ConsoleView(Context context) {
		
		super(context);
		myContext = context;
		setup();
	}
	
	public ConsoleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		myContext = context;
		setup();
	}
	
	public ConsoleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		myContext = context;
		setup();
	}
	
	public void setup() {
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		super.onDraw(canvas);
		
		canvas.drawColor(Color.BLACK);
	}

}
