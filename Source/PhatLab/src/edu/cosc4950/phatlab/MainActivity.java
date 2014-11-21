package edu.cosc4950.phatlab;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

public class MainActivity extends FragmentActivity {
	
	private ViewGroup mPadLayout, mConsoleLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ExternalData demo = new ExternalData();
		byte[] bob = demo.loadPCM16bit("test");
		
		PCM pcm = new PCM(bob, 44100, false);
		pcm.stream();
		
		
		
		//mAudioTrack.write(bob, 0, bob.length);
		
		if (savedInstanceState == null) {

			mPadLayout = (ViewGroup) findViewById(R.id.activity_main_phat_pad_container);
			if(mPadLayout != null) {
				
				PhatPadFragment phatPadFragment = new PhatPadFragment();
				FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
				fragmentTransaction.replace(mPadLayout.getId(), phatPadFragment, PhatPadFragment.class.getName());
				
				fragmentTransaction.commit();
			}
			
			mConsoleLayout = (ViewGroup) findViewById(R.id.activity_main_console_container);
			if(mConsoleLayout != null) {
				
				ConsoleFragment consoleFragment = new ConsoleFragment();
				FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
				fragmentTransaction.replace(mConsoleLayout.getId(), consoleFragment, ConsoleFragment.class.getName());
				
				fragmentTransaction.commit();
			}
		}
	}

	@Override
	public void onStart() {
		super.onStart();
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public void onStop() {
		super.onStop();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}	
}
