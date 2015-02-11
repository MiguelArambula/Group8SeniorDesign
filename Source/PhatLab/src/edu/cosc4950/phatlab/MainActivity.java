package edu.cosc4950.phatlab;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class MainActivity extends FragmentActivity {
	
	SequenceTimer sequence; 
	String butts;
	private ViewGroup mPadLayout, mConsoleLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// TEST CODE
		sequence = new SequenceTimer(130, 8); // default to 130 bpm and 8 steps per beat TODO adjust in SequenceTimer
		PCM sample = new ExternalData().loadPCM("amen_kick1");
		
		sequence.setSample(sample, 1);
		sequence.addTrigger(1, 0, 4);
		sequence.addTrigger(1, 0, 5);
		sequence.addTrigger(1, 0, 7);
		sequence.setPlayTime(0, 0, -1, -1);
		butts = "butts";
		// END OF TEST CODE
		
		if (savedInstanceState == null) {

			mPadLayout = (ViewGroup) findViewById(R.id.activity_main_phat_pad_container);
			if(mPadLayout != null) {
				
				/*
				PhatPadFragment phatPadFragment = new PhatPadFragment();
				FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
				fragmentTransaction.replace(mPadLayout.getId(), phatPadFragment, PhatPadFragment.class.getName());
				
				fragmentTransaction.commit();*/
				
				
				// THIS IS TEST CODE USE THE STUFF ABOVE ^^^
				SequencerFragment sequencerFragment = new SequencerFragment();
				FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
				fragmentTransaction.replace(mPadLayout.getId(), sequencerFragment, SequencerFragment.class.getName());
				
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
