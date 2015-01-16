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
					staticMode = false,
					isPlaying = false;
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
	
	public boolean getStereo()
	{
		return stereo;
	}
	
	private int clamp(int value,  int from, int to)
	{
		return (value > to ? to : (value < from ? from : value));
	}
	
	//Converts a 16-bit PCM sample of bytes into a short
	//for manipulation
	
	public PCM mergePCM(PCM pcm)
	{
		if (!isSet())
			return null;
		PCM newpcm;
		byte [] audio;
		try
		{
			
			if (bitrate != pcm.getBitrate())
			{
				Log.e("Phat Lab","Cannot merge PCMs with different bit rates!");
				throw new Exception();
			}
			if (stereo != pcm.getStereo())
			{
				Log.e("Phat Lab","Cannot merge PCMs with different number of channels!");
				throw new Exception();
			}
			
			byte[] larger, smaller;
			larger = (getStream().length >= pcm.getStream().length ? stream : pcm.getStream());
			smaller = (getStream().length >= pcm.getStream().length ?  pcm.getStream() : stream);
			
			audio = new byte[larger.length];
			
			//Copy header data from larger file:
			for (int i = 0; i < 44; ++i)
				audio[i] = larger[i];
			
			//Create shorts for audio merging
			short[] a1, a2;
			a1 = new short[(larger.length - 44) / 2];
			a2 = new short[(smaller.length - 44) / 2];
			
			ByteBuffer bb;
			bb = ByteBuffer.wrap(larger,44,larger.length - 44);
			bb.order(ByteOrder.LITTLE_ENDIAN);
			
			for (int i = 0; i < a1.length; ++i)
				a1[i] = bb.getShort();
			
			bb = ByteBuffer.wrap(smaller,44,smaller.length - 44);
			bb.order(ByteOrder.LITTLE_ENDIAN);
			
			for (int i = 0; i < a2.length; ++i)
				a2[i] = bb.getShort();
			
			//Merge samples:
			for (int i = 0; i < a2.length; ++i)
				a1[i] = (short) (a1[i] + a2[i]);
			
			//Convert back into the byte array:
			bb = ByteBuffer.wrap(audio, 44, audio.length - 44);
			bb.order(ByteOrder.LITTLE_ENDIAN);
			bb.asShortBuffer().put(a1);
			
			newpcm = new PCM(audio, bitrate, stereo, false);
			
		}
		catch (Exception e)
		{
			return null;
		}
		
		return newpcm;
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
			
			if (isSet())
				clear();
			
			// Divided by 10 because the write writes a 10th of the buffer size.
			// Doesn't really have much of an effect
			int bufferSize = AudioTrack.getMinBufferSize(bitrate, 
							 (stereo ? AudioFormat.CHANNEL_OUT_STEREO : AudioFormat.CHANNEL_OUT_MONO), 
							 AudioFormat.ENCODING_PCM_16BIT) / 10;
			
			audio = new AudioTrack(AudioManager.STREAM_MUSIC, 
								   bitrate, 
								   (stereo ? AudioFormat.CHANNEL_OUT_STEREO:AudioFormat.CHANNEL_OUT_MONO), 
								   AudioFormat.ENCODING_PCM_16BIT,
					 			   bufferSize * 2, 
					 			   (staticMode? AudioTrack.MODE_STATIC: AudioTrack.MODE_STREAM));
			this.stream = stream;
			this.bitrate = bitrate;
			this.stereo = stereo;
			this.staticMode = staticMode;
			_hasSet = true;
			
			/*if (audio.getPlayState() != AudioTrack.PLAYSTATE_STOPPED)
			{
				audio.stop();
				
				while (audio.getPlayState() != AudioTrack.PLAYSTATE_STOPPED);
				
				audio.flush();
				
			}*/
			
			try 
			{
				audio.play();
			}
			catch (Exception e)
			{
				Log.e("Phat Lab","Failed to play audio!", e);
				return;
			}
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
	
	public void clear()
	{
		if (!isSet())
			return;
		
		audio.flush();
		if(audio.getPlayState() != AudioTrack.PLAYSTATE_STOPPED);
			audio.stop();
		audio.release();
		_hasSet = false;
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
			
			audio.flush();
			
			if (isPlaying)
			{
				isPlaying = false;
				audio.stop();
				while (audio.getPlayState() != AudioTrack.PLAYSTATE_STOPPED);
				
				audio.play();
			}
			
			isPlaying = true;
			
			new Thread()
			{
				public void run()
				{
					try 
					{
						//audio.write(stream, bo, l);
						for (int i = bo, il = bo; i < stream.length; i = clamp((il = i) + (bitrate / 10), bo, l))
						{
							if (isPlaying == false)
								return;
							audio.write(stream, il, i - il);
						}
					}
					catch (Exception E)
					{
						Log.e("Phat Lab","Exception:",E);
					}
					isPlaying = false;
					
				}
				
			}.start();
			
			
			//audio.stop();
			
		}
		catch (Exception E)
		{
			Log.e("Phat Lab","Exception:",E);
		}

	}
}