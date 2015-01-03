package edu.cosc4950.phatlab;

public class PhatTracks {
	PCM[][] myTracks;
	
	public PhatTracks() {
		myTracks = new PCM[4][3];
		initTracks();
	}
	
	private void initTracks() {
		for(int i=0; i<4; i++) {
			for(int j=0; j<3; j++) {
				myTracks[i][j] = null;
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
		myTracks[padRow][padCol] = new ExternalData().loadPCM(sample);//new PCM(tmp, 44100, true, false);
	}
	
	public void setTrack(int padRow, int padCol, PCM pcm)
	{
		myTracks[padRow][padCol] = pcm;
	}

	public PCM getTrack(int padRow, int padCol) {
		return myTracks[padRow][padCol];
	}
	
	public void play(int padRow, int padCol) {
		myTracks[padRow][padCol].stream();
	}
}
