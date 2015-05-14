package edu.cosc4950.phatlab;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

/*
 * @author Jake Harper
 * @author Patrick Weingardt
 * @author Rueben Shea
 * @author Miguel Arambula
 *
 * Stores some composition data. Creates the fragments that house the PhatPad, Sequencer, and Console.
 */

public class MainActivity extends FragmentActivity {
	
	//ViewGroup, allows the app to switch the views of the work station. 
	private ViewGroup mPadLayout, mConsoleLayout;
	private SeekBar sampleVBar;
	
	private ExternalData data = new ExternalData();
	private Point p;
	//List of all the samples in the Samples folder. 
	private List<String> item = null;
	private List<String> path = null;
	private List<String> path2 = null;
	//List of profiles in Profiles folder
	private List<String> profiles = null;
	//File paths for for the root folder and 3 sub folders. 
	private String root=Environment.getExternalStorageDirectory()+"/PhatLab/";
	private String profF=Environment.getExternalStorageDirectory()+"/PhatLab/Profiles/";
	private String sampF=Environment.getExternalStorageDirectory()+"/PhatLab/Samples/";
	private String seqF = Environment.getExternalStorageDirectory()+"/PhatLab/Sequencer/";
	private PhatPadFragment f;
	
	//Sample info data. 
	private String currFrag="pad";
	private String currSamp;
	private String currPad = "";
	private TextView pNum = null;
	private int currSampRate = 0;
	private TextView currRate = null;
	private Spinner fSpin = null;
	private PhatTracks pads = null;
	private int[][] pGrid = null;
	
	//Sequencer data. 
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
		
		//File System
		File main = new File(root);
		File subF1 = new File(profF);
		File subF2 = new File(sampF);
		File subF3 = new File(seqF);
		if(!main.exists()){
			main.mkdirs();
			subF1.mkdirs();
			subF2.mkdirs();
			subF3.mkdirs();
		} else {
			if(!subF1.exists()){
				subF1.mkdirs();
			}
			if(!subF2.exists()){
				subF2.mkdirs();
			}
			if(!subF3.exists()){
				subF3.mkdirs();
			}
		}
		
		FragmentManager fm = getFragmentManager();
		
		sequence = new SequenceTimer(130, 8); // default to 130 bpm and 8 steps per beat
		sequence.setPlayTime(0, 0, -1, -1);   // initialize play time to beginning of composition
		
		maxBeat = 1;
		currentBeat = 0;
		loopLength = 0;
		loopOn = false;
		
		initTracks(); // initialize sequencer tracks to null
		
		if (savedInstanceState == null) {

			mPadLayout = (ViewGroup) findViewById(R.id.activity_main_phat_pad_container);
			
			if(mPadLayout != null) {
				getSupportFragmentManager().beginTransaction()
					.add(R.id.activity_main_phat_pad_container, phatPadFragment).commit();
					
				// Add SequencerFragment
				getSupportFragmentManager().beginTransaction()
					.add(R.id.activity_main_phat_pad_container, sequencerFragment).commit();
				
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				ft.hide(sequencerFragment);
				ft.commit();
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
	
	// switches between PhatPad and Sequencer fragments
	public void swapFrag(String frag) {
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		currFrag = frag;
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
	
	//Retrives the name of the audio clips inside the Samples Folder on the device. 
	public void getDir(String dirPath){
		item = new ArrayList<String>();
		item.add("No Sample");
		path = new ArrayList<String>();
		File f = new File(dirPath);
		File[] files = f.listFiles();
		for(int i=0;i<files.length;i++){
			File temp = files[i];
			
			String wav = temp.getName();
			wav = wav.substring(0, wav.length()-4);
			if(data.isValidWav(wav)){
				item.add(wav);					
			}
		}

	}
	
	//Returns a List of the audio clip names
	public List<String> getItems(){
		return item;
	}
	
	//Returns the current beat in the sequence being worked on. 
	public int getCurBeat(){
		return currentBeat;
	}
	
	//Returns the max number of beats of the sequence
	public int getMaxBeat(){
		return maxBeat;
	}
	
	//Change the max number of beats in the sequence
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
	
	//Change which current beat is being looked at.
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
	
	//Sets the TextView that tells the user the current pad of the PhatPad for further use. 
	public void setTextView(TextView v){
		pNum = v;
	}
	
	//Sets the TextView that tells the user of the current BPM of the sequence for further use. 
	public void setCRTextView(TextView v){
		currRate = v;
	}
	
	//Updates the last pad that was pressed. 
	public void setText(String s){
		pNum.setText(s);
		currPad = s;
	}
	
	//Return the name of the pad last pressed. 
	public String getCurrPad(){
		return currPad;
	}
	
	//Sets the spinner of audio clips avaible to the user for further use. 
	public void setSpin(Spinner s){
		fSpin = s;
	}
	
	//Sets the spinner to the lasted selected audio clip
	public void setSel(String s){
		if(item.contains(s)){
			fSpin.setSelection(item.indexOf(s));
		} else {
			Toast.makeText(this, "Value not in list of Samples", Toast.LENGTH_SHORT).show();
		}
	}
	
	//Sets the name of the current selected audio clip.
	public void setCurrSamp(String s){
		currSamp = s;
	}
	
	//Sets the PhatTracks for use of changing values. 
	public void setTracks(PhatTracks pT){
		pads = pT;
	}
	
	//Sets the grid so changes to the grid can be updated from other classes.
	public void setGrid(int[][] g){
		pGrid = g;
	}
	
	//Give a pad number and audio clip name, connects the current audio clip to the desired pad. 
	public void loadTrack(String pad, String samp){
		switch(pad){
		case "Pad 1": loadTrack(0,0,samp); loadSeqTrack(samp, 0); break;
		case "Pad 2": loadTrack(1,0,samp); loadSeqTrack(samp, 1); break;
		case "Pad 3": loadTrack(2,0,samp); loadSeqTrack(samp, 2); break;
		case "Pad 4": loadTrack(3,0,samp); loadSeqTrack(samp, 3); break;
		case "Pad 5": loadTrack(0,1,samp); loadSeqTrack(samp, 4); break;
		case "Pad 6": loadTrack(1,1,samp); loadSeqTrack(samp, 5); break;
		case "Pad 7": loadTrack(2,1,samp); loadSeqTrack(samp, 6); break;
		case "Pad 8": loadTrack(3,1,samp); loadSeqTrack(samp, 7); break;
		case "Pad 9": loadTrack(0,2,samp); loadSeqTrack(samp, 8); break;
		case "Pad 10": loadTrack(1,2,samp); loadSeqTrack(samp, 9); break;
		case "Pad 11": loadTrack(2,2,samp); loadSeqTrack(samp, 10); break;
		case "Pad 12": loadTrack(3,2,samp); loadSeqTrack(samp, 11); break;
		default: Toast.makeText(getApplicationContext(), 
				"Invalid Pad or Samp", Toast.LENGTH_LONG).show(); break;
		}
	}
	
	//Give a PhatTrack location and audio clip name, update the location with the given name. 
	public void loadTrack(int r, int c, String samp){
		pads.setTrack(r, c, samp);
		phatPadFragment.update(pads);
	}
	
	//Given a PhatTrack object, the location and a audio clip name, update the location in the PhatTrack with the given name. 
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
	
	//Connects the audio clip to the given track of the sequence. 
	public void loadSeqTrack(String sampleName, int track) {
		PCM sample = new ExternalData().loadPCM(sampleName);
		sequence.setSample(sample, track);
	}
	
	//Initialize the tracks of the sequence.
	public void initTracks() {
		for(int i=0; i<12; i++) {
			sequence.setSample(null, i);
		}
	}
	
	//Return the timer or set of play triggers of the sequence.
	public SequenceTimer getTimer(){
		return sequence;
	}
	
	//Given a pcm and name, save the wanted PCM into the Sample Folder of the application. 
	public void savePCM(PCM pcm, String filename){
		data.savePCM(pcm, filename);
	}
	
	//Return a list of all saved profiles on the application.
	public List<String> getAllProfiles(){
		return profiles;
	}
	
	//Get the name of the profiles from the Profile folder of the applcation. 
	public void getProfs(String dirPath){
		profiles = new ArrayList<String>();
		profiles.add("No Profile");
		path2 = new ArrayList<String>();
		String proRoot = Environment.getExternalStorageDirectory()+"/PhatLab/Profiles/";
		
		File f = new File(dirPath);
		File[] files = f.listFiles();

		for(int i=0; i<files.length;i++){
			File temp = files[i];
				
			String p = temp.getName();
			p = p.substring(0,p.length()-4);
			profiles.add(p);
		}				
			
	}
	
	//Create a profile that will save both the current sample load and pad location
	//as well as save the current sequence in progress as well. 
	public void createProfile(String profName){
		String troot = Environment.getExternalStorageDirectory()+"/PhatLab/Profiles/";
		File prof = new File(troot,profName+".txt");
		sequence.save(profName);
		if(prof.exists()){
			Toast.makeText(getApplicationContext(), "File exists", Toast.LENGTH_SHORT).show();
		} else {
			BufferedWriter profWrite;
			try {
				profWrite = new BufferedWriter(new FileWriter(prof));
				for(int x=0; x<3; x++){
					for(int y=0; y<4; y++){
						profWrite.append(pads.getSampName(y, x));
						profWrite.append("\r\n");
					}
				}
				profiles.add(profName);
				profWrite.flush();
				profWrite.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	//loadProfile
	//Takes a name of a profile and loads the samples to the association tracks.
	//As well as load the sequence that was in the works in the Sequencer wrokstation. 
	public void loadProfile(String profName) throws IOException{
		String troot = Environment.getExternalStorageDirectory()+"/PhatLab/Profiles/";
		File file = new File(troot, profName+".txt");
		ArrayList<String> tempList = new ArrayList<String>();
		sequence.load(profName);
		if(!profName.equals("No Profile")){
			try {
				BufferedReader buffRead = new BufferedReader(new FileReader(file));
				String line;
			
				while((line=buffRead.readLine()) != null){
					tempList.add(line);
				}
				buffRead.close();
				
				//Load the samples. 
				if(tempList.size() != 0){
					loadTrack("Pad 1", tempList.get(0));
					loadTrack("Pad 2", tempList.get(1));
					loadTrack("Pad 3", tempList.get(2));
					loadTrack("Pad 4", tempList.get(3));
					loadTrack("Pad 5", tempList.get(4));
					loadTrack("Pad 6", tempList.get(5));
					loadTrack("Pad 7", tempList.get(6));
					loadTrack("Pad 8", tempList.get(7));
					loadTrack("Pad 9", tempList.get(8));
					loadTrack("Pad 10", tempList.get(9));
					loadTrack("Pad 11", tempList.get(10));
					loadTrack("Pad 12", tempList.get(11));
				} else {
					Toast.makeText(getApplicationContext(), "Profile is empty", Toast.LENGTH_LONG).show();
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} /*else {
			loadTrack("Pad 1", "No Sample");
			loadTrack("Pad 2", "No Sample");
			loadTrack("Pad 3", "No Sample");
			loadTrack("Pad 4", "No Sample");
			loadTrack("Pad 5", "No Sample");
			loadTrack("Pad 6", "No Sample");
			loadTrack("Pad 7", "No Sample");
			loadTrack("Pad 8", "No Sample");
			loadTrack("Pad 9", "No Sample");
			loadTrack("Pad 10", "No Sample");
			loadTrack("Pad 11", "No Sample");
			loadTrack("Pad 12", "No Sample");
		}*/
		
		sequencerFragment.updateTracks();
	}
	
	//Sample Volume get and set code
	public void setSampVolBar(SeekBar p){
		sampleVBar = p;
	}
	
	//Set the Sample Volume Bar to the selected audio clips volume.
	//(*still in progress*)
	public void setSampVolBar(){
		switch(currPad){
		case "Pad 1": sampleVBar.setProgress(pads.getSampVol(0, 0)); break; 
		case "Pad 2": sampleVBar.setProgress(pads.getSampVol(1, 0)); break; 
		case "Pad 3": sampleVBar.setProgress(pads.getSampVol(2, 0)); break; 
		case "Pad 4": sampleVBar.setProgress(pads.getSampVol(3, 0)); break; 
		case "Pad 5": sampleVBar.setProgress(pads.getSampVol(0, 1)); break; 
		case "Pad 6": sampleVBar.setProgress(pads.getSampVol(1, 1)); break; 
		case "Pad 7": sampleVBar.setProgress(pads.getSampVol(2, 1)); break; 
		case "Pad 8": sampleVBar.setProgress(pads.getSampVol(3, 1)); break; 
		case "Pad 9": sampleVBar.setProgress(pads.getSampVol(0, 2)); break; 
		case "Pad 10":sampleVBar.setProgress(pads.getSampVol(1, 2)); break; 
		case "Pad 11": sampleVBar.setProgress(pads.getSampVol(2, 2)); break; 
		case "Pad 12": sampleVBar.setProgress(pads.getSampVol(3, 2)); break; 
		default: sampleVBar.setProgress(1); break; 
		}
	}
	
	//Given a valume from the Sample Volume bar, set the current sample to the given volume. 
	public void setSampVol(float gain){
		switch(currPad){
		case "Pad 1": pads.setSampVol(0, 0, gain); break;
		case "Pad 2": pads.setSampVol(1, 0, gain); break;
		case "Pad 3": pads.setSampVol(2, 0, gain); break;
		case "Pad 4": pads.setSampVol(3, 0, gain); break;
		case "Pad 5": pads.setSampVol(0, 1, gain); break;
		case "Pad 6": pads.setSampVol(1, 1, gain); break;
		case "Pad 7": pads.setSampVol(2, 1, gain); break;
		case "Pad 8": pads.setSampVol(3, 1, gain); break;
		case "Pad 9": pads.setSampVol(0, 2, gain); break;
		case "Pad 10": pads.setSampVol(1, 2, gain); break;
		case "Pad 11": pads.setSampVol(2, 2, gain); break;
		case "Pad 12": pads.setSampVol(3, 2, gain); break;
		default: break;
		}
	}
	
	//Return the current BPM of the sequence.
	public int getBPM(){
		return sequence.getBPM();
	}
	
	//Update the sequence's BPM with the given value. 
	public void setBPM(int x){
		sequence.setBPM(x);
	}
}
