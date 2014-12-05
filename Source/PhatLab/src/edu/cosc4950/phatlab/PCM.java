/**
 * Author: Reuben Shea
 * Date: Nov 20, 2014
 * 
 * Description:
 * This class plays loaded PCM byte streams
 * One thread is dedicated to each sound clip
 * 
 */

package edu.cosc4950.phatlab;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

public class PCM{
	
	private AudioTrack audio = null;
	private byte[] stream = null;
	private boolean _hasSet = false;
	private int bo, l;
	
	public PCM(){}
	public PCM(byte[] stream, int bitrate, boolean stereo, boolean staticMode)
	{
		set16bit(stream,bitrate,stereo,staticMode);
	}
	
	//Frees the resources
	public void release()
	{
		if (audio != null)
			audio.release();
		_hasSet = false;
		audio = null;
	}
	
	public byte[] getStream()
	{
		return stream;
	}
	
	public void set16bit(byte[] stream,boolean stereo,boolean staticMode)
	{
		set16bit(stream, 44100,stereo, staticMode);
		
	}
	
	/**
	 * Sets an audio file to play, but does not play the file
	 * @param stream	Set of audio bytes to play
	 * @param bitrate	Bitrate of the audio file
	 * @param staticMode	Whether to play in static or stream mode
	 */
	public void set16bit(byte[] stream, int bitrate,boolean stereo, boolean staticMode)
	{
		try
		{
			if (stream == null)
			{
				Log.e("Phat Lab","Cannot set null audio file!");
				throw null;
			}
			
			int bufferSize = AudioTrack.getMinBufferSize(bitrate, (stereo ? AudioFormat.CHANNEL_CONFIGURATION_STEREO:AudioFormat.CHANNEL_CONFIGURATION_MONO), AudioFormat.ENCODING_PCM_16BIT);
			audio = new AudioTrack(AudioManager.STREAM_MUSIC, bitrate, (stereo ? AudioFormat.CHANNEL_CONFIGURATION_STEREO:AudioFormat.CHANNEL_CONFIGURATION_MONO), AudioFormat.ENCODING_PCM_16BIT,
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
			bo = byteOffset;
			l = length;
			new Thread()
			{
				public void run()
				{
					try {
						
						audio.stop();
						audio.play();
						audio.write(stream, bo, l);
						audio.stop();
					}
					catch (Exception E)
					{
						Log.e("Phat Lab","Exception:",E);
					}
					
				}
			}.start();
			
		}
		catch (Exception E)
		{
			Log.e("Phat Lab","Exception:",E);
		}

	}
}