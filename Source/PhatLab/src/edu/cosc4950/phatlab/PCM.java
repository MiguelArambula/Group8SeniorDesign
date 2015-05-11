/**
 * Author: Reuben Shea
 * Date: Nov 20, 2014
 * 
 * Description:
 * The PCM class is designed to handle audio playback and manipulation for
 * individual audio tracks. This class will contain all the audio for a single
 * sample at the byte level, as well as any associated properites
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
					stereo = false;
	private float gain = 1.f; 	// Percent
	private int bitrate=-1;
	
	
	public PCM(short[] stream, int samplerate, boolean stereo)
	{
		set16bit(stream, samplerate, stereo);
	}
	
	public PCM(byte[] stream, int samplerate, boolean stereo)
	{
		set16bit(stream,samplerate,stereo);
	}
	
	/**
	 * Returns whether or not the audio is ready for playback.
	 * @return
	 */
	public boolean isSet()
	{
		return _hasSet;
	}
	
	/**
	 * Returns the audio bytestream
	 * @return
	 */
	public byte[] getStream()
	{
		return stream;
	}
	
	/**
	 * Returns the number of samples in a second
	 * @return
	 */
	public int getSamplerate()
	{
		return bitrate;
	}
	
	/**
	 * Returns whether or not this PCM object is stereo (true) or mono (false)
	 * @return
	 */
	public boolean getStereo()
	{
		return stereo;
	}
	
	/**
	 * Sets the gain or "volume" of the sound. 1.0 = full, 0.5 = half, etc
	 *
	 */
	public void setGain(float gain)
	{
		if (!isSet())
		{
			Log.e("Phat Lab", "Cannot adjust gain of uninitialized PCM!");
			return;
		}
		try
		{
			audio.setStereoVolume(gain, gain);
			this.gain = gain;
		}
		catch (Exception e)
		{
			Log.e("Phat Lab", "Failed to set gain.", e);
		}
	}
	
	public float getGain()
	{
		return gain;
	}
	
	/**
	 * Sets the start / end samples to play from the PCM object.
	 * @param start		Sample to start at
	 * @param end		Sample to end at
	 */
	
	public void setPlaybackRange(int start, int end)
	{
		if (!isSet())
			return;
		
		//Wipe all data in the buffer:
		audio.flush();
		
		start *= 2 * (stereo ? 2 : 1);
		end *= 2 * (stereo ? 2 : 1);
		start = clamp(start, 0, stream.length);
		end = clamp(end, start, stream.length - start);
		//startPlay = start;
		//endPlay = end;
		
		//Write new data to the buffer
		audio.write(stream, start, end-start);
	}
	
	/**
	 * Takes an int and limits it between two values
	 * @param value
	 * @param from
	 * @param to
	 * @return
	 */
	
	private int clamp(int value,  int from, int to)
	{
		return (value > to ? to : (value < from ? from : value));
	}
	
	
	/**
	 * Merges the current and provided PCM objects into a single PCM audio
	 * object and returns the object. Null is returned if there is a problem.
	 * Both PCM objects must have equal sample rates and channels to merge.
	 * 
	 * @param pcm
	 * @return
	 */
	public PCM mergePCM(PCM pcm)
	{
		if (!isSet())
			return null;
		if (!pcm.isSet())
			return null;
		
		PCM newpcm;
		byte [] audio;
		try
		{
			
			if (bitrate != pcm.getSamplerate())
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
			
			newpcm = new PCM(audio, bitrate, stereo);
			
		}
		catch (Exception e)
		{
			return null;
		}
		
		return newpcm;
	}
	
	/*
		Generates the header data for the wave file and returns the
		data in a byte array
	 */
	public byte[] generateHeader()
	{
		byte[] header;
		
		if (isSet() == false)
		{
			Log.e("Phat Lab","Audio has not been set, so cannot generate header!");
			return null;
		}
		
		try
		{
			ByteBuffer bb = ByteBuffer.allocate(44);
			bb.putInt(1380533830); // RIFF
			bb.order(ByteOrder.LITTLE_ENDIAN);
			bb.putInt(stream.length + 36); // FILE SIZE
			bb.order(ByteOrder.BIG_ENDIAN);
			bb.putInt(1463899717); // WAVE
			bb.putInt(1718449184); // fmt 
			bb.order(ByteOrder.LITTLE_ENDIAN);
			bb.putInt(16); // Sumbchunk1 Size
			bb.putShort((short)1); // PCM
			bb.putShort((short)(getStereo()? 2:1)); //Channels
			bb.putInt(getSamplerate()); // Sample rate
			bb.putInt(getSamplerate()*(getStereo() ? 2 : 1) * 2); //Bit rate
			bb.putShort((short)((getStereo() ? 2 : 1) * 2)); // Block align
			bb.putShort((short)16); // Bits per sample
			bb.order(ByteOrder.BIG_ENDIAN);
			bb.putInt(1684108385); // data
			bb.order(ByteOrder.LITTLE_ENDIAN);
			bb.putInt(stream.length); // Length of stream
			
			header = bb.array();
		}
		catch(Exception e)
		{
			Log.e("Phat Lab", "Failed to generate header.",e);
			return null;
		}
		
		return header;
	}
	
	/**
	 * Various functions to set the byte data for the audio.
	 */
	public void set16bit(short[] stream, boolean stereo)
	{
		ByteBuffer bb = ByteBuffer.allocate(stream.length*2);
		for (int i = 0; i < stream.length; ++i)
			bb.putShort(stream[i]);
		set16bit(bb.array(), stereo);
	}
	
	public void set16bit(short[] stream, int samplerate, boolean stereo)
	{
		ByteBuffer bb = ByteBuffer.allocate(stream.length*2);
		for (int i = 0; i < stream.length; ++i)
			bb.putShort(stream[i]);
		set16bit(bb.array(), samplerate, stereo);
	}
	
	public void set16bit(byte[] stream,boolean stereo)
	{
		set16bit(stream, 44100,stereo);
	}
	
	/**
	 * Sets an audio file to play, but does not play the file
	 * @param stream	Set of audio bytes to play
	 * @param bitrate	Samplerate of the audio file
	 * @param staticMode	Whether to play in static or stream mode
	 */
	public void set16bit(byte[] stream, int samplerate,boolean stereo)
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

			audio = new AudioTrack(AudioManager.STREAM_MUSIC, 
								   samplerate, 
								   (stereo ? AudioFormat.CHANNEL_OUT_STEREO:AudioFormat.CHANNEL_OUT_MONO), 
								   AudioFormat.ENCODING_PCM_16BIT,
					 			   stream.length,  // Perhaps not a good idea if long samples are added
					 			   AudioTrack.MODE_STATIC);
			this.stream = stream;
			this.bitrate = samplerate; // poor labeling for global variable
			this.stereo = stereo;
			_hasSet = true;
			if (audio.getState() == AudioTrack.STATE_UNINITIALIZED)
				Log.e("Phat Lab", "Failed to initialize audio for PCM!");
			
			setPlaybackRange(0, stream.length);
			
		}
		catch (Exception E)
		{
			Log.e("Phat Lab","Excepton:",E);
		}
	}
	
	//Credit to Jacquet Wong
	//Currently causes decent artifacts with bad ratios
	private short[] linearInterpolate(int min, int max, short[] values)
	{
		//if (min == max)
		//	return values;
		
		int length = (int)Math.round(((float)values.length / min) * max);
		float scale = (length / ((float) values.length));
		
		short[] result = new short[length];

		//Interpolate using y = mx + c
		for (int i = 0; i < length; ++i)
		{
			int currentPos = (int)(i / scale);
			int nearestLeft = currentPos;
			int nearestRight = nearestLeft + 1;
			
			if (nearestRight >= values.length)
				nearestRight = values.length - 1;
			
			
			
			float slope = values[nearestRight] - values[nearestLeft];
			float posFromLeft = currentPos - nearestLeft;
			
			result[i] = (short)(slope * posFromLeft + values[nearestLeft]);

		}
		
		return result;
	}
	
	/**
	 * Resamples the audio to a different sample rate and returns whether successful
	 * @param targetRate
	 * @return
	 */
	public boolean linearResample(int targetRate)
	{
		if (isSet() == false)
		{
			Log.e("Phat Lab","Audio has not been set, so resample!");
			return false;
		}
		
		try
		{
			int totalSamples = stream.length / 2;
			
			//Grab actual samples:
			short[] amps = new short[totalSamples];
			ByteBuffer bb = ByteBuffer.wrap(stream);
			//bb.order(ByteOrder.LITTLE_ENDIAN);
			for (int i = 0; i < totalSamples; ++i)
				amps[i] = bb.getShort();
			
			//Interpolate the samples:
			short[] resample;
			if (!getStereo())
				resample = linearInterpolate(getSamplerate(), targetRate, amps);
			else
			{
				short [] left = new short[amps.length / 2],
						 right = new short[amps.length / 2],
						 nLeft, nRight;
				for (int i = 0; i < amps.length / 2; ++i)
				{
					left[i] = amps[i * 2];
					right[i] = amps[(i * 2) + 1];
				}
				nLeft = linearInterpolate(getSamplerate(), targetRate, left);
				nRight = linearInterpolate(getSamplerate(), targetRate, right);
				
				resample = new short[nLeft.length * 2];
				for (int i = 0; i < nLeft.length; ++i)
				{
					resample[(i * 2)] = 	nLeft[i];
					resample[(i * 2) + 1] = nRight[i];
				}
			}

			//May need a digital filter to remove high frequencies
			set16bit(resample,targetRate, getStereo());
			//set16bit(amps,targetRate, getStereo());
		}
		catch (Exception e)
		{
			Log.e("Phat Lab", "Error resampling audio file.", e);
			return false;
		}
		
		return true;
		
	}
	
	
	/**
	 * Releases all data from the PCM audio object. The PCM will need to be
	 * reinitialized with set16bit to be useful again.
	 */
	public void clear()
	{
		if (!isSet())
			return;
		if (audio.getState() == AudioTrack.STATE_UNINITIALIZED)
			return;
		
		if(audio.getPlayState() == AudioTrack.PLAYSTATE_PLAYING);
			audio.stop();
		
		audio.flush();
		audio.release();
		_hasSet = false;
	}
	
	public boolean isPlaying()
	{
		return (audio.getPlayState() == AudioTrack.PLAYSTATE_PLAYING);
	}
	
	/**
	 * Plays the audio file through the speakers
	 * @param byteOffset	Where to start playing the file, in bytes
	 * @param length	How many bytes to play
	 */
	public void stream()
	{
		try
		{
			if (isSet() == false)
			{
				Log.e("Phat Lab","Audio has not been set, so cannot play!");
				throw null;
			}

			new Thread()
			{
				public void run()
				{
					if (audio.getPlayState() != AudioTrack.PLAYSTATE_STOPPED)
					{
						audio.stop();
						audio.reloadStaticData();
					}
					
					try 
					{
						audio.play();
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
