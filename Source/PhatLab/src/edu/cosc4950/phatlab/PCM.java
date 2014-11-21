/**
 * Author: Reuben Shea
 * Date: Nov 20, 2014
 * 
 * Description:
 * This class plays loaded PCM byte streams
 * 
 */

package edu.cosc4950.phatlab;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

public class PCM {
	
	private AudioTrack audio;
	private byte[] stream;
	private boolean _hasSet = false;
	
	public PCM(){}
	public PCM(byte[] stream, int bitrate, boolean staticMode)
	{
		set16bit(stream,bitrate,staticMode);
	}
	
	
	public void set16bit(byte[] stream,boolean staticMode)
	{
		set16bit(stream, 44100,staticMode);
	}
	
	/**
	 * Sets an audio file to play, but does not play the file
	 * @param stream	Set of audio bytes to play
	 * @param bitrate	Bitrate of the audio file
	 * @param staticMode	Whether to play in static or stream mode
	 */
	public void set16bit(byte[] stream, int bitrate, boolean staticMode)
	{
		try
		{
			int bufferSize = AudioTrack.getMinBufferSize(bitrate, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT);
			audio = new AudioTrack(AudioManager.STREAM_MUSIC, bitrate, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT,
					 			   bufferSize, (staticMode? AudioTrack.MODE_STATIC: AudioTrack.MODE_STREAM));
			this.stream = stream;
			_hasSet = true;
		}
		catch (Exception E)
		{
			Log.e("Phat Lab","Excepton:",E);
		}
	}
	
	public void stream()
	{
		stream(0,stream.length);
	}
	
	public void stream(int stop)
	{
		stream(0,stop);
	}
	
	/**
	 * Plays the audio file
	 * @param byteOffset	Where to start playing the file, in bytes
	 * @param length	How many bytes to play
	 */
	public void stream(int byteOffset, int length)
	{
		try
		{
			if (_hasSet == false)
			{
				Log.e("Phat Lab","Audio has not been set, so cannot play!");
				throw null;
			}
			audio.play();
			audio.write(stream, byteOffset, length);
		}
		catch (Exception E)
		{
			Log.e("Phat Lab","Exception:",E);
		}
	}

}