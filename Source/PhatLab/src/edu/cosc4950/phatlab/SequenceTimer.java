package edu.cosc4950.phatlab;

import android.util.Log;


/**
 * @author Reuben Shea
 *
 *	The SequenceTimer class is responsible for creating, sorting, and playing
 *	A series of PCM samples in a sequence.
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
		else if (triggerList[track].next == null)
			totalSteps = triggerList[track].priority;
		
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
	 * Compiles the sequence into a PCM audio object, ready to
	 * save onto the harddrive
	 * @return
	 */
	public PCM compileToPCM()
	{
		try
		{
			/*
			 *  The *16 at the end is:
			 *  2 (stereo) * 2 (bytes per sample) * 4 (quarter notes per beat)
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
			totalBytes += longestSample; // Add the length of the longest sample to the end
			//Create and clear the new byte data
			byte[] sampleBytes = new byte[totalBytes];
			for (int i = 0; i < sampleBytes.length; ++i)
				sampleBytes[i] = 0;
			
			for (int i = 0; i < 12; ++i)
			{
				//If no sample on this track, just continue:
				if (triggerList[i] == null)
					continue;
				if (sampleList[i] == null)
					continue;
				
				sNode 	first = triggerList[i].findFirst();
				byte[] samples = sampleList[i].getStream();
				
				//Loop through all samples
				for (sNode j = first; j != null; j = j.next)
				{
					//Byte position to start at:
					int bp = (int)((1.f / (double)(bpm * spb) * (double)j.priority ) * 60.f * 44100.f * 16.f);
					
					//If stereo, we just copy it directly:
					if (sampleList[i].getStereo())
					{
						//Log.i("Phat Lab", "Stereo");
						for (int k = 0; k < samples.length; ++k)
							sampleBytes[bp + k] += samples[k];
					}
					//If not stereo, we copy the mono into both channels:
					else
					{
						//Log.i("Phat Lab", "Mono");
						for (int k = 0; k < samples.length; ++k)
						{
							sampleBytes[bp + (2*k)] = samples[k];
							sampleBytes[bp + (2*k + 1)] = samples[k];
						}
					}
				}
			}
			
			PCM pcm = new PCM(sampleBytes, 44100 ,true);
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
 * @author reuben
 *	Class acts as a sample trigger object. It is a linked list of timers that
 *	specify when a sample should or shouldn't play.
 *	The "priority" is the beat / step to play at.
 */
class sNode
{
	sNode next = null,
		  prev = null;
	long  priority;
	
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
