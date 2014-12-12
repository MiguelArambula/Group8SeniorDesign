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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

public class PCM{
	
	private AudioTrack audio = null;
	private byte[] stream = null;
	private boolean _hasSet = false,
					stereo = false,
					staticMode = false;
	private int bo, l, bitrate=-1;
	
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
		bitrate = -1;
	}
	
	//Returns whether there has been audio data set yet or not
	public boolean isSet()
	{
		return _hasSet;
	}
	
	public byte[] getStream()
	{
		return stream;
	}
	
	//Returns -1 if not set
	public int getBitrate()
	{
		return bitrate;
	}
	
	//Converts a 16-bit PCM sample of bytes into a short
	//for manipulation
	public short[] getSampleAsShort()
	{
		if (stream == null)
			return null;

		short [] _return = new short[stream.length / 2];
		ByteBuffer bb = ByteBuffer.wrap(stream);
		bb.order(ByteOrder.LITTLE_ENDIAN).asShortBuffer();
		for (int i = 0; i < _return.length; ++i)
			_return[i] = bb.getShort();
		
		return _return;
	}
	
	//If an error, it doesn't change the buffer
	private void shortToBuffer(short [] sh)
	{
		try {
			if (sh == null)
				return;
			
			ByteBuffer bb = ByteBuffer.allocate(sh.length * 2);
			bb.order(ByteOrder.LITTLE_ENDIAN).asShortBuffer();
			//Put the shorts all into the byte buffer:

			for (int i = 0; i < sh.length; ++i)
				bb.putShort(sh[i]);
			
			//Grab the buffer's array
			if (! bb.hasArray())
				throw new Exception();
			
			byte[] newBuff = bb.array();
			
			//Set the new sound:
			release();
			set16bit(newBuff,bitrate,stereo,staticMode);
			
		}
		catch(Exception e)
		{
			Log.e("Phat Lab","Error in PCM.shortToBuffer() : Exception:",e);
		}
	}
	
	/**
	 * Merges the audio data from one sample into this sample.
	 * The olsd oudio data IS OVERWRITTEN
	 * @param sample	The PCM to add to this one
	 * @return	Whether there was an error
	 */
	public boolean mergeSample(PCM sample)
	{
		try
		{
			if (bitrate == -1 || sample.getBitrate() == -1 ||
				bitrate != sample.getBitrate())
			{
				Log.e("Phat Lab","Incorrect bitrate(s)!");
				throw new Exception();
			}
			
			//Grab manipulative versions of the audio stream:
			short [] sample1,sample2,finalSample;
			sample1 = getSampleAsShort();
			sample2 = sample.getSampleAsShort();
			finalSample = new short [sample1.length > sample2.length ? 
									 sample1.length : sample2.length];
			
			//Average samples together:
				//Mix first half of one sample is shorter:
			int i;
			for (i = 0; i < (sample1.length > sample2.length ? 
							 sample2.length : sample1.length);
				 ++i)
				finalSample[i] =(short) ((sample1[i] + sample2[i]) / 2);
			
				//Tack the rest of the sample on:
			short [] longerSample =(sample1.length > sample2.length ? sample1 : sample2);
			for(; i < longerSample.length; ++i)
				finalSample[i] = longerSample[i];
			
			//Set this PCM to use the new mixed sample
			//shortToBuffer(finalSample);
			shortToBuffer(getSampleAsShort());
			
		}
		catch (Exception e)
		{
			return true;
		}
				
		return false;
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
			this.bitrate = bitrate;
			this.stereo = stereo;
			this.staticMode = staticMode;
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
						return; // Unlikely needed, but just in case
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