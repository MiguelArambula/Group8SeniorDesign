package edu.cosc4950.phatlab;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends FragmentActivity {
	
	private ViewGroup mPadLayout, mConsoleLayout;
	private ExternalData data = new ExternalData();
	private Point p; //TODO kill?
	private List<String> item = null;
	private List<String> path = null;
	private String root=Environment.getExternalStorageDirectory()+"/PhatLab/";
	
	private String currSamp;
	private String currPad = "";
	private TextView pNum = null;
	private Spinner fSpin = null;
	private PhatTracks pads = null;
	private int[][] pGrid = null;
	
	SequenceTimer sequence; 
	int maxBeat, currentBeat;
	int loopLength;
	boolean loopOn;
	
	PhatPadFragment phatPadFragment = new PhatPadFragment();
	SequencerFragment sequencerFragment = new SequencerFragment();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		FragmentManager fm = getFragmentManager();
		
		// TEST CODE
		sequence = new SequenceTimer(160, 8); // default to 130 bpm and 8 steps per beat TODO adjust in SequenceTimer
		sequence.setPlayTime(0, 0, -1, -1);
		PCM sample1 = new ExternalData().loadPCM("amen_kick1");
		PCM sample2 = new ExternalData().loadPCM("amen_snare1");
		
		maxBeat = 1;
		currentBeat = 0;
		loopLength = 0;
		loopOn = false;
		
		sequence.setSample(sample1, 0); // set to amen kick
		sequence.setSample(sample2, 1); // set to amen snare
		
		if (savedInstanceState == null) {

			mPadLayout = (ViewGroup) findViewById(R.id.activity_main_phat_pad_container);
			
				if(mPadLayout != null) {
				
				 /*
				FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
				fragmentTransaction.replace(mPadLayout.getId(), phatPadFragment, PhatPadFragment.class.getName());
				
				fragmentTransaction.commit();//*/
				
				
				// THIS IS TEST CODE. USE THE STUFF ABOVE ^^^
				
				getSupportFragmentManager().beginTransaction()
					.add(R.id.activity_main_phat_pad_container, phatPadFragment).commit();
					
				// Add SequencerFragment
				getSupportFragmentManager().beginTransaction()
					.add(R.id.activity_main_phat_pad_container, sequencerFragment).commit();
				
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				
				//ft.replace(mPadLayout.getId(), sequencerFragment, SequencerFragment.class.getName()); //SequencerFragment.class.getName()
				
				ft.hide(sequencerFragment);
				ft.commit();
				
				//ft.replace(mPadLayout.getId(), phatPadFragment, null); //PhatPadFragment.class.getName()
				//ft.hide(phatPadFragment);
				//ft.show(sequencerFragment);
			}// * /
			
			mConsoleLayout = (ViewGroup) findViewById(R.id.activity_main_console_container);
			
			if(mConsoleLayout != null) {
				
				ConsoleFragment consoleFragment = new ConsoleFragment();
				
				FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
				fragmentTransaction.replace(mConsoleLayout.getId(), consoleFragment, ConsoleFragment.class.getName());
				fragmentTransaction.commit();
			}
		}
	}
	
	// the switcheroo bizzness
	public void swapFrag(String frag) {
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

		// if pad hide seq and show pad
		if(frag == "pad") {
			ft.hide(sequencerFragment);
			ft.show(phatPadFragment);
		}
		else if(frag == "sequencer") {
			ft.hide(phatPadFragment);
			ft.show(sequencerFragment);
		}
		
		ft.commit();
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
		item.add("No Sample");
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
	
	public int getCurBeat(){
		return currentBeat;
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
	
	public int changeCur(String c){
		if(c=="add"){
			currentBeat += 1;
		} else if(c=="sub"){
			if(currentBeat>0){
				currentBeat -= 1;
			} else {
				currentBeat += 0;
			}
		}
		return currentBeat;
	}
	
	public void setTextView(TextView v){
		pNum = v;
	}
	
	public void setText(String s){
		pNum.setText(s);
		currPad = s;
	}
	
	public String getPad(){
		return currPad;
	}
	
	public void setSpin(Spinner s){
		fSpin = s;
	}
	
	public void setSel(String s){
		if(item.contains(s)){
			fSpin.setSelection(item.indexOf(s));
		} else {
			Toast.makeText(this, "Value not in list of Samples", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void setCurrSamp(String s){
		currSamp = s;
	}
	
	public void setTracks(PhatTracks pT){
		pads = pT;
	}
	
	public void setGrid(int[][] g){
		pGrid = g;
	}
	
	public void loadTrack(String pad, String samp){
		switch(pad){
		case "Pad 1": loadTrack(0,0,samp); break;
		case "Pad 2": loadTrack(1,0,samp); break;
		case "Pad 3": loadTrack(2,0,samp); break;
		case "Pad 4": loadTrack(3,0,samp); break;
		case "Pad 5": loadTrack(0,1,samp); break;
		case "Pad 6": loadTrack(1,1,samp); break;
		case "Pad 7": loadTrack(2,1,samp); break;
		case "Pad 8": loadTrack(3,1,samp); break;
		case "Pad 9": loadTrack(0,2,samp); break;
		case "Pad 10": loadTrack(1,2,samp); break;
		case "Pad 11": loadTrack(2,2,samp); break;
		case "Pad 12": loadTrack(3,2,samp); break;
		default: Toast.makeText(getApplicationContext(), 
				"Invalid Pad or Samp", Toast.LENGTH_LONG).show(); break;
		}
	}
	
	public void loadTrack(int r, int c, String samp){
		pads.setTrack(r, c, samp);
		for(int i=0; i<4; i++) {
			for(int j=0; j<3; j++) {
				if(pads.getTrack(i, j) != null){
					pGrid[i][j] = 1;
				} else {
					pGrid[i][j] = 0;
				}
			}
		}
	}
	
	public void loadTrack(PhatTracks t, int r, int c, String samp
			, int[][] m){
		t.setTrack(r, c, samp);
		for(int i=0; i<4; i++) {
			for(int j=0; j<3; j++) {
				if(t.getTrack(i, j) != null)
					m[i][j] = 1;
			}
		}
	}
}
