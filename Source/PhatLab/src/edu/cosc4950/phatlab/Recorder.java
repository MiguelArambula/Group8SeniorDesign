package edu.cosc4950.phatlab;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.media.audiofx.AutomaticGainControl;
import android.media.audiofx.NoiseSuppressor;
import android.util.Log;

/**
 * 
 * @author reuben
 * This class handles recording from the device's mic and creating a PCM
 * file for it.
 * 
 * Requires:
 * <uses-permission android:name="android.permission.RECORD_AUDIO"/>
 */
public class Recorder {
	private PCM recordedSample = null;
	private AudioRecord recorder = null;
	private boolean isRecording = false;
	private Thread rthread = null; // For grabbing data from the recording.
	private int buffSize = 0;
	private float volumeScale = 0.01f;
	private List<short[]> streamData = new LinkedList<short[]>();
	private NoiseSuppressor ns = null;
	
	
	public Recorder()
	{
		
		buffSize = AudioRecord.getMinBufferSize(44100, 
				    						    AudioFormat.CHANNEL_IN_MONO,
				    						    AudioFormat.ENCODING_PCM_16BIT);
		if (buffSize == AudioRecord.ERROR_BAD_VALUE)
			Log.e("Phat Lab", "Bad buffer size: "+buffSize);
	}
	
	public PCM getSample()
	{
		return recordedSample;
	}
	
	/**
	 * Sets the volume for recorded audio.
	 * NOTE: For some reason on my phone I had to lower to 1% to
	 * avoid destroying my speakers.
	 * @param scale
	 */
	public void setVolume(float scale)
	{
		volumeScale = scale / 100;
	}
	
	private void readStream()
	{
		
		short rData[] = new short[buffSize / 2];
		while (isRecording)
		{
			recorder.read(rData, 0, buffSize / 2);
			streamData.add(rData);
			rData = new short[buffSize / 2];
			
		}
	}
	
	/**
	 * Attempts to start recording audio.
	 * @return whether or not it was successful
	 */
	public boolean start()
	{
		if (isRecording)
		{
			Log.e("Phat Lab", "Cannot start recording. Already recording!");
			return false;
		}
		//ONLY 44100 is guaranteed to work, so we will be resampling
		//everything else at 44100 at load, and at export!
		
		//Only mono is guaranteed to work.
		try{
			
			recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, 44100,
									   AudioFormat.CHANNEL_IN_MONO,
								       AudioFormat.ENCODING_PCM_16BIT, 
								       buffSize);
			
			if (recorder.getState() == AudioRecord.STATE_UNINITIALIZED)
				Log.e("Phat Lab", "Failed to initialize recorder!");
			
			if (NoiseSuppressor.isAvailable())
			{
				ns = NoiseSuppressor.create(recorder.getAudioSessionId());
				ns.setEnabled(true);
				if (!ns.getEnabled())
					Log.e("Phat Lab", "Failed to enable noise suppression.");
			}
			else
				Log.i("Phat Lab", "Noise suppression not available.");
			
			recorder.startRecording();
			isRecording = true;
			
			rthread = new Thread(new Runnable()
				{
					public void run(){
						readStream(); // Reads the data into a buffer.
				}
			});
			rthread.start();
			
		}
		catch (Exception e)
		{
			Log.e("Phat Lab", "Error creating recording instance!",e);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Attempts to stop recording audio
	 * @return whether or not it was successful.
	 */
	public boolean stop()
	{
		if (isRecording)
		{
			isRecording = false;
			recorder.stop();
			recorder.release();
			if (ns != null)
				ns.release();
			recorder = null;
			rthread = null;
			
			try
			{
				//Copy all audio data into a final array:
				ByteBuffer bb = ByteBuffer.allocate(buffSize * streamData.size() * 2);
				for (short[] a : streamData)
				{
					for (int i= 0; i < a.length; ++i)
						bb.putShort((short)(a[i] * volumeScale));
				}
				if (bb.hasArray())
					recordedSample = new PCM(bb.array(), 44100, false); // Create PCM
				else
					Log.e("Phat Lab", "Error creating byte buffer!");
				
				streamData.clear(); // Wipe all audio data.
				
				recordedSample.stream();
				
			}
			catch (Exception e)
			{
				Log.e("Phat Lab","Error when stopping recording!",e);
			}
			
			return true;
		}

		return false;
	}
}
