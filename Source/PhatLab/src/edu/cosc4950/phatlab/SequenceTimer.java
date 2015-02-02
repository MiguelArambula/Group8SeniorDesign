package edu.cosc4950.phatlab;

import android.util.Log;


/**
 * Sequencer timer is responsible for sorting and playing sequences of samples.
 * This will be used for the overal sequencer or any other sub sequences we might need
 * @author Reuben Shea
 *
 *
 */

public class SequenceTimer implements Runnable
{
	int bpm, spb; // Beats per minute, steps per beat
	PCM sampleList[] = new PCM[12]; // Samples played at triggers
	sNode triggerList[] = new sNode[12]; // List of sNodes to trigger at time intervals
	long startPos=0, endPos=0, curPos = 0, totalSteps=0;
	boolean isPlaying = false;
	
	
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
	
	public void setPlayTime(long startBeat, int startStep, long endBeat, int endStep )
	{
		
		//Clamping:
		startPos = (startBeat * spb) + startStep;
		endPos = (endBeat * spb) + endStep;
		if (endPos > totalSteps || endPos < 0)
			endPos = totalSteps;
		
		if (startPos > endPos)
			startPos = endPos;
		if (startPos < 0)
			startPos = 0;
		
		curPos = startPos;
		isPlaying = false;
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
			totalSteps =globalStep;
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
		if (sampleList[track] == null)
			return false;
		
		sNode node = triggerList[track].find(globalStep);
		if (node != null)
			node.clear();
		
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
		if (isPlaying == true)
			return;
		
		isPlaying = true;
		run();
		
	}
	
	public void stop()
	{
		isPlaying = false;
		curPos = startPos;
	}
	
	public void pause()
	{
		isPlaying = false;
	}
	
	/**
	 * Compiles the sequence into a PCM audio object, ready to
	 * save onto the harddrive
	 * @return
	 */
	public PCM compileToPCM()
	{
		// -- STUB -- //
		//MUST CREATE A RESAMPLE-FUNCTION FIRST
		return null;
	}
	
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
					if (curNode != null)
						sampleList[i].stream();
					
				}
				
				if (curPos == endPos)
				{
					stop();
					break;
				}
				
				++ curPos;
				Thread.sleep(60000 / ((bpm * spb) / 4),0);
			}
		}
		catch (Exception e)
		{
			Log.e("Phat Lab","Error while playing sequence: ", e);
			stop();
		}
	}
}


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
			
			if (next.priority > item.priority)
			{
				next.setPrev(item);
				item.setNext(next);
				item.setPrev(this);
				this.setNext(item);
				return this;
			}
			
			next.add(item);
			return this;
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
	
	public void clear()
	{
		sNode node = this;
		
		if (node.next != null)
			node.next.setPrev(node.prev);
		if (node.prev != null)
			node.prev.setNext(node.next);
	}
}