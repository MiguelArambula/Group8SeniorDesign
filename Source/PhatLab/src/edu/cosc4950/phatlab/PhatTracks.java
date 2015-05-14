package edu.cosc4950.phatlab;

/**
 * @author Jake Harper
 * @author Miguel Arambula
 * 
 * Keeps track of which pads contain audio clips.
 */

public class PhatTracks {
	PCM[][] myTracks;
	String[][] sampleNames;
	
	public PhatTracks() {
		myTracks = new PCM[4][3];
		sampleNames = new String[4][3];
		initTracks();
	}
	
	private void initTracks() {
		for(int i=0; i<4; i++) {
			for(int j=0; j<3; j++) {
				myTracks[i][j] = null;
				sampleNames[i][j] = null;
			}
		}
	}
	
	/**
	 * Adds a PCM sample to a pad.
	 * 
	 * @param padRow	row of target pad
	 * @param padCol	column of target pad
	 * @param sample	title of sample's filename
	 */
	public void setTrack(int padRow, int padCol, String sample){
		
		//ExternalData ed = new ExternalData();
		//byte[] tmp = ed.loadPCM16bit(sample);
		if(!sample.equals("No Sample")){
		myTracks[padRow][padCol] = new ExternalData().loadPCM(sample);//new PCM(tmp, 44100, true, false);
		sampleNames[padRow][padCol]=sample;
		} else {
			myTracks[padRow][padCol] = null;//new PCM(tmp, 44100, true, false);
			sampleNames[padRow][padCol]=null;
		}
	}
	
	//Given the location, set the pcm to be associated to that location. 
	public void setTrack(int padRow, int padCol, PCM pcm)
	{
		myTracks[padRow][padCol] = pcm;
	}

	//Return the PCM associated with the given location. 
	public PCM getTrack(int padRow, int padCol) {
		return myTracks[padRow][padCol];
	}
	
	//Returns the boolean value that states if the location has audio clip association with it. 
	public boolean isPadEmpty(int padRow, int padCol){	
		if(myTracks[padRow][padCol]==null){
			return true;
		} else {
			return false;
		}
	}
	
	//Return the name of the audio clip associated with the given location. 
	public String getSampName(int padRow, int padCol){
		if(isPadEmpty(padRow,padCol)){
			return "No Sample";
		} else {
			return sampleNames[padRow][padCol].toString();
		}
	}
	
	//Play back the audio clip at the given location. 
	public void play(int padRow, int padCol) {
		myTracks[padRow][padCol].stream();
	}
	
	//Return the current volume of the audio clip at the given location. 
	public int getSampVol(int padRow, int padCol){
		if(myTracks[padRow][padCol] != null){
		return Math.round(myTracks[padRow][padCol].getGain());}
		else { return 1; }
	}
	
	//Set the volume of the audio clip at the given location. 
	public void setSampVol(int padRow, int padCol, float gain){
		myTracks[padRow][padCol].setGain(gain);
	}
	

}
