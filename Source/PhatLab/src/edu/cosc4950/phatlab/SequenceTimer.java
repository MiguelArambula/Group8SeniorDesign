
package edu.cosc4950.phatlab;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Environment;
import android.util.Log;


/**
 * @author Reuben Shea
 *
 *	The SequenceTimer class is responsible for creating, sorting, and playing
 *	A series of PCM samples in a sequence. This class handles the backend, not
 *  the GUI aspects
 *
 */

public class SequenceTimer
{
	int bpm, spb; // Beats per minute, steps per beat
	PCM sampleList[] = new PCM[12]; // Samples played at triggers
	sNode triggerList[] = new sNode[12]; // List of sNodes to trigger at time intervals
	long startPos=0, endPos=0, curPos = 0, totalSteps=0;
	boolean isPlaying = false,
			playAll = false, // If true, it plays the whole sequence.
			loopSequence = false;
	
	
	public SequenceTimer(int bpm, int stepsPerBeat)
	{
		//Must be > 0
		this.bpm = (bpm > 0 ? bpm : 1);
		
		//Must be a square of 2:
		//double tVal = Math.sqrt(stepsPerBeat);
		//if ((double) ((int) tVal) != tVal)
		//	stepsPerBeat = 1;
		
		this.spb =stepsPerBeat;
		
		//Set null samples:
		for (int i = 0; i < 12; ++i)
		{
			sampleList[i] = null;
			triggerList[i] = null;
		}
	}
	
	/*
		Clamps a value between two values.
	 */
	private int clamp(int track, int from, int to)
	{
		return (track > to ? to : (track < from ? from : track));
	}
	
	/**
	 * Assigns a sample to a specified track
	 * @param sample	The PCM object to trigger
	 * @param track		The track to assign the sample to
	 */
	public void setSample(PCM sample, int track)
	{
		//Clamps between [0 .. 11]
		track = clamp(track, 0, 11);
		sampleList[track] = sample;
	}
	
	/*
		Returns the trigger at the specified position
 	 */
	public sNode findTrigger(int track, long beat, int step)
	{
		return findTrigger(null, track, beat, step);
	}

	/**
	 * Finds a node in a track if it exists. Returns null if it does not.
	 * @param startNode	The node to start searching at. null starts at the beginning
	 * @param track
	 * @param beat
	 * @param step
	 * @return
	 */
	public sNode findTrigger(sNode startNode, int track, long beat, int step)
	{
		track = clamp(track, 0,11);
		long globalStep = (beat * spb) + step;
		
		if (triggerList[track] == null)
			return null;
		/*if (sampleList[track] == null)
			return null;*/
		
		if (startNode == null)
			return triggerList[track].find(globalStep);
		else
			return startNode.find(globalStep);

	}
	
	/*
		Sets the number of beats to start at and play through.
 	 */
	public void setPlayTime(long startBeat, int startStep, long endBeat, int endStep )
	{
		
		//Clamping:
		startPos = (startBeat * spb) + startStep;
		endPos = (endBeat * spb) + endStep;
		
		curPos = startPos;
		isPlaying = false;
		
		if (endBeat < 0)
			playAll = true;
		else
			playAll = false;
	}
	
	/**
	 * Signals to play a sample at a certain beat with a step offset
	 * @param track
	 * @param beat
	 * @param step
	 */
	public void addTrigger(int track, long beat, int step)
	{
		track = clamp(track, 0, 11);
		//Which step in the song to play at:
		long globalStep = (beat * spb) + step;

		sNode trigger = new sNode(globalStep);
		if (triggerList[track] == null)
			triggerList[track] = trigger;
		else
			triggerList[track].add(trigger);
		
		if (totalSteps < globalStep)
			totalSteps = globalStep;
		
		
		totalSteps += (totalSteps % spb); //Make sure to round to whole beat
		//Log.i("Phat Lab", "Set to: "+totalSteps);
		
		
	}
	
	/**
	 * Clears the trigger at the specified position if it exists
	 * @param track	Track to clear
	 * @param beat	Which beat to clear
	 * @param step	Which substep in that beat (1/4 timing would have substeps [0..3]
	 * @return	Whether or not there was a trigger at the position
	 */
	public boolean clearTrigger(int track, long beat, int step)
	{
		track = clamp(track, 0, 11);
		long globalStep = (beat * spb) + step;
		
		return clearTrigger(track, globalStep);
	}
	
	public boolean clearTrigger(int track, long globalStep)
	{
		track = clamp(track, 0, 11);
		if (triggerList[track] == null)
			return false;
		/*if (sampleList[track] == null)
			return false;*/
		
		sNode node = triggerList[track].find(globalStep);
		if (node != null)
			triggerList[track] = node.clear();
		
		if (triggerList[track] == null)
			totalSteps = 0;
		else
			totalSteps = triggerList[track].findLast().priority;
		
		totalSteps += (totalSteps % spb); // Round to whole beat
		
		return (node == null ? false : true);
	}
	
	/**
	 * Clears all the triggers in a given track between a certain range of beats
	 * @param track
	 * @param startBeat
	 * @param startStep
	 * @param endBeat
	 * @param endStep
	 */
	public void clearTriggerRange(int track, long startBeat, int startStep, long endBeat, int endStep)
	{
		//Clamping
		long startPos, endPos;
		startPos = (startBeat * spb) + startStep;
		endPos = (endBeat * spb) + endStep;
		if (endPos > totalSteps || endPos < 0)
			endPos = totalSteps;
		
		if (startPos > endPos)
			startPos = endPos;
		if (startPos < 0)
			startPos = 0;
		
		for (long i = startPos; i <= endPos; ++i)
			clearTrigger(track, i);
		
	}
	
	public int getBPM(){
		return bpm;
	}
	
	/*
		Changes the tempo of the sequence
 	 */
	public void setBPM(int newBpm)
	{
		double scale = (double)newBpm / (double) bpm ;
		
		//Loop through each sample playlist:
		totalSteps = 0;
		for (int i = 0; i < 12; ++i)
		{
			if (sampleList[i] == null)
				continue;
			if (triggerList[i] == null)
				continue;
			
			//Start at the front:
			sNode n = triggerList[i].findFirst();
			
			//Recursively set all new BPM
			//n.resetBPM(scale);
			long totalStepSub = n.findLast().priority;
			totalSteps = (totalSteps < totalStepSub? totalStepSub : totalSteps);
		}
		
		bpm = newBpm;
		
	}
	
	public void start()
	{
		start(false);
	}
	public void loop()
	{
		start(true);
	}
	
	public void start(boolean loop)
	{
		if (isPlaying == true)
			return;
		
		isPlaying = true;
		//Log.i("Phat Lab", "Total Steps: "+totalSteps);
		// Wrap / clamp timer if needed before playing:
		if (endPos > totalSteps || playAll)
			endPos = totalSteps;
		
		if (startPos > endPos)
			startPos = endPos;
		if (startPos < 0)
			startPos = 0;
		
		loopSequence = loop;
		
		new Thread( new Runnable()
			{
				public void run()
				{
					try 
					{
						
						if (!isPlaying)
							return;
						
						//Play sound until we say stop:
						while (isPlaying)
						{
							
							//Scan through each track:
							for (int i = 0; i < 12; ++i)
							{
								//If not set, we just skip:
								if (sampleList[i] == null || triggerList[i] == null)
									continue;
								
								sNode curNode = triggerList[i].find(curPos);
								if (curNode != null) // If there is a trigger at this time
									sampleList[i].stream(); //Play the sound on this track
								
							}
							
							if (curPos >= endPos)
							{
								if (loopSequence == false)
								{
									stop();
									isPlaying = false;
									break;
								}
								else
										curPos = startPos;
							}
							else
								++ curPos;
							//Log.i("Phat Lab", "Step: "+curPos + ": "+endPos);
							
							Thread.sleep(60000 / ((bpm * spb) / 4),0);
						}
						isPlaying = false;
					}
					catch (Exception e)
					{
						Log.e("Phat Lab","Error while playing sequence: ", e);
						isPlaying = false;
						stop();
					}
				}
			}
		).start();
		
	}
	
	public void stop()
	{
		loopSequence = false;
		isPlaying = false;
		curPos = startPos;
	}
	
	public void pause()
	{
		loopSequence = false;
		isPlaying = false;
	}
	
	/**
	 * Exports the sequence into a text file
	 * 
	 * Returns if sucesfull
	 */
	public boolean save(String filename)
	{
		//Open the file for reading:
		try
		{
			File file = new File(Environment.getExternalStorageDirectory()+"/PhatLab/Sequencer/",filename+".seq");
			BufferedWriter bOut = new BufferedWriter(new FileWriter(file));
			
			String newline = "\n";
			bOut.append(""+bpm + newline); // Save bpm
			bOut.append(""+spb + newline); // Save bpm
			for (int i = 0; i < 12; ++i)
			{
				bOut.append("Track "+i + newline);//Specifies which track the timing is for.
				if (triggerList[i] == null)//Skip if no triggers:
					continue;
				
				sNode n= triggerList[i].findFirst();
				
				//Loops until the end:
				while (n != null)
				{
					bOut.append(""+n.priority + newline);
					n = n.next;
				}
			}
			
			//Write from the buffer and close:
			bOut.flush();
			bOut.close();
		}
		catch (Exception e)
		{
			Log.e("Phat Lab", "Error saving sequence!", e);
			return false;
		}
		return true;
	}
	
	/**
	 * Imports a sequence, overwriting all the current data.
	 * 
	 * Returns if succesful
	 */
	public boolean load(String filename)
	{
		try
		{
			File file = new File(Environment.getExternalStorageDirectory()+"/PhatLab/Sequencer/",filename+".seq");
			BufferedReader bIn = new BufferedReader(new FileReader(file));
			
			//Grab BPM:
			String line = bIn.readLine();
			if (line == null)
			{
				bIn.close();
				return false;
			}
			bpm = Integer.parseInt(line);
			
			//Grab SPB:
			line = bIn.readLine();
			if (line == null)
			{
				bIn.close();
				return false;
			}
			spb = Integer.parseInt(line);
			
			//Read all the sequencer data:
			int currentTrack = 0;
			totalSteps = 0;
			while ((line = bIn.readLine()) != null)
			{
				//Scan for change in track:
				Pattern p = Pattern.compile("Track (\\d{1,2})");
				Matcher m = p.matcher(line);
				if (m.matches())
				{
					currentTrack = Integer.parseInt(m.group(1));
					if (currentTrack < 0 || currentTrack > 11)
					{
						bIn.close();
						return false;
					}
					continue;
				}
				
				//Check for actual timing:
				p = Pattern.compile("(\\d+)");
				m = p.matcher(line);
				if (m.matches())
				{
					if (totalSteps < Integer.parseInt(line))
						totalSteps = Integer.parseInt(line);
					
					if (triggerList[currentTrack] == null)
						triggerList[currentTrack] = new sNode(Integer.parseInt(line));
					else
						triggerList[currentTrack].add(new sNode(Integer.parseInt(line)));
					continue;
				}
					
				//No recognizable data:
				bIn.close();
				return false;	
			}
			
			bIn.close();
		}
		catch (Exception e)
		{
			Log.e("Phat Lab", "Failed to load sequence!", e);
			return false;
		}
		return true;
	}
	
	/**
	 * Compiles the sequence into a PCM audio object, ready to
	 * save onto the harddrive
	 * @return
	 */
	public PCM compileToPCM()
	{
		try
		{
			/*
			 *  The * 8 at the end is:
			 *  2 (stereo) * 4 (quarter notes per beat)
			 */
			int totalBytes = (int) Math.ceil((1.0 / (double)(bpm * spb) * (double)totalSteps) * 60) * 44100 * 16;
			//Get number of seconds, multiply by sample rate, then by 2 for stereo
			int longestSample = 0;
			for (int i = 0; i < 12; ++i)
			{
				if (triggerList[i] == null)
					continue;
				if (sampleList[i] == null)
					continue;
				
				if (sampleList[i].getStream().length * (sampleList[i].getStereo()?1:2) > longestSample)
					longestSample = sampleList[i].getStream().length * (sampleList[i].getStereo()?2:1);
			}
			totalBytes += longestSample*2; // Add the length of the longest sample to the end
			//Create and clear the new byte data
			/*byte[] sampleBytes = new byte[totalBytes];
			for (int i = 0; i < sampleBytes.length; ++i)
				sampleBytes[i] = 0;*/
			
			short[] sampleShorts = new short[totalBytes / 2];
			float[] sampleFloats = new float[totalBytes / 2]; // Use floats to avoid clipping
			
			for (int i = 0; i < sampleShorts.length; ++i)
			{
				sampleShorts[i] = 0;
				sampleFloats[i] = 0.f;
			}
			
			for (int i = 0; i < 12; ++i)
			{
				//If no sample on this track, just continue:
				if (triggerList[i] == null)
					continue;
				if (sampleList[i] == null)
					continue;
				
				sNode 	first = triggerList[i].findFirst();
				//byte[] samples = sampleList[i].getStream();
				
				//Copy all the byte data into short array:
				short [] pcmShorts = new short[sampleList[i].getStream().length / 2];
				
				
				ByteBuffer bb = ByteBuffer.wrap(sampleList[i].getStream(), 0, sampleList[i].getStream().length);
				bb.order(ByteOrder.LITTLE_ENDIAN);
					//Scale to the gain:
				for (int j = 0 ; j < sampleList[i].getStream().length / 2; ++j)
					pcmShorts[j] = (short) ((float) (bb.getShort()) * sampleList[i].getGain() * 0.01); // 0.01 scales the volume. WE ALSO NEED TO HANDLE CLIPPING!
				//byte[] pcmBytes = new byte[sampleList[i].getStream().length];
				
				//Loop through all samples
				for (sNode j = first; j != null; j = j.next)
				{
					//Byte position to start at:
					int bp = (int)((1.f / (double)(bpm * spb) * (double)j.priority ) * 60.f * 44100.f * 8.f);
					
					//If stereo, we just copy it directly:
					if (sampleList[i].getStereo())
					{
						//Log.i("Phat Lab", "Stereo");
						for (int k = 0; k < pcmShorts.length; ++k)
							sampleFloats[bp + k] += ((float)(pcmShorts[k]) / 32767.f); // Scale between [-1..1];
					}
					//If not stereo, we copy the mono into both channels:
					else
					{
						//Log.i("Phat Lab", "Mono");
						for (int k = 0; k < pcmShorts.length; ++k)
						{
							sampleFloats[bp + (2*k)] += (float)(pcmShorts[k]) / 32767.f;
							sampleFloats[bp + (2*k + 1)] += (float) (pcmShorts[k]) / 32767.f;
						}
					}
				}
			}
			/*float maxOverflowSize = 0.f; // How much the loudest samples goes over by
			
			// -- STUB -- // For some reason I am getting clipping, but the audio levels
						  // don't even get CLOSE to the range of the data type?
			
			//Calculate max overflow:
			for (int i = 0; i < totalBytes / 2; ++i)
			{
				if (Math.abs(sampleFloats[i]) - 1.f > maxOverflowSize)
					maxOverflowSize = Math.abs(sampleFloats[i] - 1.f);
			}*/
			
			//Compensate for overflow:
			for (int i = 0; i < totalBytes / 2; ++i)
				sampleShorts[i] = (short)((32767.f * sampleFloats[i]) / 12); // -- STUB -- // Temporary clipping fix
				//sampleShorts[i] = (short)((32767.f * sampleFloats[i]) * Math.abs(maxOverflowSize-1.f));
			
			//Log.i("Phat Lab", "" + maxOverflowSize + ":" + maxOverflowSize * 32767.f);
			
			PCM pcm = new PCM(sampleShorts, 44100 ,true);
			return pcm;
		}
		catch (Exception e)
		{
			Log.e("Phat Lab", "Error generating audio sequence!",e);
			return null;
		}
	}
}


/**
 * 
 * @author Reuben Shea
 *	Class acts as a sample trigger object. It is a linked list of timers that
 *	specify when a sample should or shouldn't play.
 *	The "priority" is the beat / step to play at.
 */
class sNode
{
	sNode next = null,
		  prev = null;
	long  priority;
	
	public void resetBPM(double scalar)
	{
		//Log.i("Phat Lab", "Old: " + priority + " : "+priority * scalar);
		this.priority*= scalar;
		if (next != null)
			next.resetBPM(scalar);
	}
	public sNode(long priority)
	{
		this.priority = priority;
	}
	
	public sNode setNext(sNode next)
	{
		this.next = next;
		return next;
	}
	
	public sNode setPrev(sNode prev)
	{
		this.prev = prev;
		return prev;
	}
	
	public sNode add(sNode item)
	{
		//Same value / comes after
		if (item.priority == priority || item.priority > priority)
		{
			if (next == null)
				return setNext(item).setPrev(this);
			
			//If it belongs as the next item:
			if (next.priority > item.priority)
			{
				next.setPrev(item);
				item.setNext(next);
				item.setPrev(this);
				this.setNext(item);
				return item;
			}
			
			//If not, call recursively:
			return next.add(item);
		}
		//New item comes before

		if (prev == null)
			return setPrev(item).setNext(this);
		
		if (prev.priority < item.priority)
		{
			prev.setNext(item);
			item.setPrev(prev);
			item.setNext(this);
			this.setPrev(item);
			return item;
		}
		
		return prev.add(item);
		
	}
	
	public sNode find(long priority)
	{
		sNode curNode = this;
		while (true)
		{
			if (curNode.priority == priority)
				return curNode;
			
			if (curNode.priority > priority)
			{
				if (curNode.prev == null)
					return null;
				if (curNode.prev.priority < priority)
					return null;
				
				curNode = curNode.prev;
			}
			
			if (curNode.priority < priority)
			{
				if (curNode.next == null)
					return null;
				if (curNode.next.priority > priority)
					return null;
				
				curNode = curNode.next;
			}
		}
	}
	
	public sNode findFirst()
	{
		if (prev == null)
			return this;
		else
			return prev.findFirst();
	}
	
	public sNode findLast()
	{
		if (next == null)
			return this;
		else
			return next.findLast();
	}
	
	public sNode clear()
	{

		//Updates the existing prior/next nodes to link to eachother.
		if (next != null)
			next.setPrev(prev);
		if (prev != null)
			prev.setNext(next);
		
		//Returns either the previous or next / null
		return (prev != null ? prev : next);
	}
}
