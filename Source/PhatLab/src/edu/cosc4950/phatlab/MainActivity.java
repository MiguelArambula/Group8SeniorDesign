package edu.cosc4950.phatlab;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.os.Build;

public class MainActivity extends FragmentActivity {
	
	private ViewGroup mPadLayout, mConsoleLayout;
	private ExternalData data = new ExternalData();
	private Point p;
	private List<String> item = null;
	private List<String> path = null;
	private String root=Environment.getExternalStorageDirectory()+"/PhatLab/";
	
	SequenceTimer sequence; 
	int maxBeat, currentBeat;
	
	String currPad, currSamp;
	boolean c=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		// TEST CODE
		sequence = new SequenceTimer(130, 8); // default to 130 bpm and 8 steps per beat TODO adjust in SequenceTimer
		sequence.setPlayTime(0, 0, -1, -1);
		PCM sample1 = new ExternalData().loadPCM("amen_kick1");
		PCM sample2 = new ExternalData().loadPCM("amen_snare1");
		
		maxBeat = 1;
		currentBeat = 0;
		
		sequence.setSample(sample1, 0); // set to amen kick
		sequence.setSample(sample2, 1); // set to amen snare
		
		if (savedInstanceState == null) {

			mPadLayout = (ViewGroup) findViewById(R.id.activity_main_phat_pad_container);
			/*if(mPadLayout != null) {
				
				/*
				PhatPadFragment phatPadFragment = new PhatPadFragment();
				FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
				fragmentTransaction.replace(mPadLayout.getId(), phatPadFragment, PhatPadFragment.class.getName());
				
				fragmentTransaction.commit();* /
				
				
				// THIS IS TEST CODE. USE THE STUFF ABOVE ^^^
				SequencerFragment sequencerFragment = new SequencerFragment();
				FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
				fragmentTransaction.replace(mPadLayout.getId(), sequencerFragment, SequencerFragment.class.getName());
				
				fragmentTransaction.commit();
			}*/
			
			mConsoleLayout = (ViewGroup) findViewById(R.id.activity_main_console_container);
			/*if(mConsoleLayout != null) {
				
				ConsoleFragment consoleFragment = new ConsoleFragment();
				FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
				fragmentTransaction.replace(mConsoleLayout.getId(), consoleFragment, ConsoleFragment.class.getName());
				
				fragmentTransaction.commit();
			}*/
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
	
	public void getDir(String dirPath){
		//myPath.setText("Location"  + dirPath);
		item = new ArrayList<String>();
		item.add("");
		path = new ArrayList<String>();
		File f = new File(dirPath);
		File[] files = f.listFiles();
		
		if(!dirPath.equals(root)){
			item.add(root);
			path.add(root);
			item.add("../");
			path.add(f.getParent());
		}
		
		for(int i=0;i<files.length;i++){
			File temp = files[i];
			
			if(!temp.isHidden() && temp.canRead()){
				path.add(temp.getPath());
				if(temp.isDirectory()){
					item.add(temp.getName()+"/");
				} else {
					String wav = temp.getName();
					wav = wav.substring(0, wav.length()-4);
					if(data.isValidWav(wav)){
					item.add(wav);
					}
				}
			}
		}
	}
	
	public List<String> getItems(){
		return item;
	}
	
	public int getMaxBeat(){
		return maxBeat;
	}
	
	public int changeMax(String c){
		if(c=="add"){
			maxBeat += 1;
		} else if(c=="sub"){
			if(maxBeat>1){
				maxBeat -= 1;
			} else {
				maxBeat += 0;
			}
		}
		return maxBeat;
	}
	
	public void setCurrPad(String s){
		currPad = s;
	}
	
	public void setCurrSamp(String s){
		currSamp = s;
	}
	
	public String getCurrPad(){
		return currPad;
	}
	
	public String getCurrSamp(){
		return currSamp;
	}
}
