package com.example.phatlabs;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	
		ExternalData file=new ExternalData();
		file.openw("test");
		file.openr("test");
		
		file.writeKey("entry", "this is a value");
		String str = file.readKey("entry");

		if (str == null)
			Log.i("Phat Lab","Failed to read");
		else
			Log.i("Phat Lab",str);
		
		file.closer();
		file.closew();
	}
}
