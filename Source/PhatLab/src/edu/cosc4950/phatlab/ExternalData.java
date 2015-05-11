/**
 * Author: Reuben Shea
 * Date: Oct 31, 2014
 * 
 * Description:
 * The ExternalData class is designed to handle any data that is not 
 * immediately part of the app. This will include any file IO.
 * 
 * Requires:
 * 	Manifest entry: <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
	 								 android:maxSdkVersion="18"/>
 */

package edu.cosc4950.phatlab;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Properties;

import android.os.Environment;
import android.util.Log;

public class ExternalData 
{
	
	private Exception ex;
	private boolean wasError=false;
	private FileOutputStream openOutFile = null;
	private FileInputStream openInFile = null;
	
	
	
	/**
	 * Returns if the last function call threw an error
	 * @return
	 */
	public boolean isError()
	{
		return wasError;
	}
	
	/**
	 * Returns the exception that was thrown if there was an error
	 * @return
	 */
	public Exception getError()
	{
		return ex;
	}
	
	/**
	 * Returns whether or not the file system is writable or not.
	 * @return
	 */
	private boolean isWritable()
	{
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state))
			return true;
		
		return false;
	}
	
	/**
	 * Returns whether or not the file system is readable.
	 * @return
	 */
	private boolean isReadable()
	{
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state) || 
			Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
			return true;
		
		return false;
	}
	
	/**
	 * Returns whether or not the specified file is a WAV file that this
	 * program can process.
	 * @param filename
	 * @return
	 */
	public boolean isValidWav(String filename)
	{
		try{
			//Error loading, also checks if file exists:
			if(filename.equals("No Sample") || filename == null){
				return false;
			}
			byte[] data = loadPCM16bit(filename);
			if (data == null)
				return false;
		
			long bytes = data.length;
			//No sound data:
			if (bytes <= 44)
				return false;
		
			
			ByteBuffer bb;
			//Riff header:
			bb = ByteBuffer.wrap(data, 0, 4);
			int str = 0;
			str += bb.getInt();
			if (str != 1380533830) // RIFF
				return false;
			//Had trouble with chars, since they were 2-bytes instead of 1
			
			//WAVE header:
			bb = ByteBuffer.wrap(data, 8, 4);
			str = 0;
			//for (int i = 0; i < 4; ++i)
			str += bb.getInt();
			if (str != 1463899717) // WAVE
				return false;
			
			//Audio format:
			bb = ByteBuffer.wrap(data, 20, 2);
			bb.order(ByteOrder.LITTLE_ENDIAN);
			short shrt;
			shrt = bb.getShort();
			if (shrt != 1)
				return false;
			
			
			//Channels:
			bb = ByteBuffer.wrap(data, 22, 2);
			bb.order(ByteOrder.LITTLE_ENDIAN);
			shrt = bb.getShort();
			if (shrt != 1 && shrt != 2)
				return false;
			
			
			//Sample Rate:
			bb = ByteBuffer.wrap(data, 24, 4);
			bb.order(ByteOrder.LITTLE_ENDIAN);
			int it = bb.getInt();
			if (it < 1000 || it > 96000)
				return false;
			
			//Bit rate:
			bb = ByteBuffer.wrap(data, 34, 2);
			bb.order(ByteOrder.LITTLE_ENDIAN);
			it = bb.getShort();
			if (it != 16)
				return false;
		
			
			//Data length = filesize:
			bb = ByteBuffer.wrap(data, 40, 4);
			bb.order(ByteOrder.LITTLE_ENDIAN);
			it = bb.getInt();
			if (it != bytes - 44)
				return false;

			return true;
		}
		catch(Exception e)
		{
			Log.e("Phat Lab","Error checking WAV file!",e);
			return false;
		}
	}
	
	/**
	 * Returns whether or not the specified file exists:
	 * @param filename	Full path of the file
	 * @return			Whether or not the file exists
	 */
	public boolean fileExists(String filename)
	{
		File f = null;
		try 
		{
			f = new File(filename);
			if (!f.exists())
				return false;
			return true;
		}
		catch (Exception e)
		{
			System.out.println("No file found: "+e);
			return false;
		}
	}
	
	/**
	 * Returns the number of bytes in the file.
	 * @param filename
	 * @return
	 */
	public long getFileSize(String filename)
	{
		if (!fileExists(filename))
			return 0;
		
		File f = null;
		try 
		{
			f = new File(filename);
			return f.length();
		}
		catch (Exception e)
		{
			System.out.println("Error reading file: "+e);
			return 0;
		}
	}
	
	/**
	 * Loads raw data from a file into an array and returns the array.
	 * Error handling for WAV is not done here.
	 * @param filename	The name of the audio file to load
	 * @return	byte array of data, or null if error
	 */
	private byte[] loadPCM16bit(String filename)
	{
		wasError = false;
		byte audio[];// = new byte[8192]; // Create audio array
		try
		{
			if (!fileExists(Environment.getExternalStorageDirectory()+File.separator+"PhatLab/Samples/"+filename+".wav"))
				throw new Exception();
			
			InputStream	impS = new FileInputStream(Environment.getExternalStorageDirectory()+File.separator+"PhatLab/Samples/"+filename+".wav");
			BufferedInputStream	buffImp = new BufferedInputStream(impS);
			DataInputStream	dataImp = new DataInputStream(buffImp);
			
			//Loads file into byte array
			audio = new byte[(int) getFileSize(Environment.getExternalStorageDirectory()+File.separator+"PhatLab/Samples/"+filename+".wav")];
			dataImp.readFully(audio);
			
			//Close the stream
			dataImp.close();
			buffImp.close();
			impS.close();
			return audio;
		}
		catch (Exception e)
		{
			wasError = true;
			ex = e;
			Log.e("Phat Lab","Exception:",e);
			return null;
		}
		
	}
	
	
	/**
	 * Loads and returns a PCM object with the sample rate and channels
	 * precalculated. If the file is not a useable WAV or there is an error,
	 * null is returned.
	 * @param filename
	 * @return	The PCM object or null if an error
	 */
	public PCM loadPCM(String filename)
	{
		PCM pcm;
		byte[] audio;
		try
		{
			if (!isValidWav(filename))
				return null;
			audio = loadPCM16bit(filename);
			if (audio == null)
				return null;
			
			ByteBuffer bb;
			bb = ByteBuffer.wrap(audio, 22, 2);
			bb.order(ByteOrder.LITTLE_ENDIAN);
			short channels = bb.getShort();
			
			bb = ByteBuffer.wrap(audio, 24, 4);
			bb.order(ByteOrder.LITTLE_ENDIAN);
			int sampleRate= bb.getInt();
			
			//Cuts off header data:
			byte[] finalAudio = new byte[audio.length];
			for (int i = 44; i < audio.length; ++i)
				finalAudio[i - 44] = audio[i];
			
			pcm = new PCM(finalAudio, sampleRate, (channels == 2 ? true : false));
			
			if (sampleRate != 44100)
				pcm.linearResample(44100);
			
		}
		catch (Exception e)
		{
			return null;
		}
		return pcm;
	}
	
	/**
	 * Saves a PCM sound as a .wav using 16-bit PCM
	 * @param pcm		PCM Object to save
	 * @param filename	Name of the file that will be saved
	 * @return	whether or not there was an error
	 */
	
	public boolean savePCM(PCM pcm, String filename)
	{
		try
		{
			if (pcm == null)
				throw null;
			if (!pcm.isSet())
				throw null;
			if (!isWritable())
				throw null;
			
			OutputStream outS = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"PhatLab/Samples/"+filename+".wav");
			BufferedOutputStream	buffOut = new BufferedOutputStream(outS);
			DataOutputStream	dataOut = new DataOutputStream(buffOut);
			
			dataOut.write(pcm.generateHeader()); //Write header to buffer
			dataOut.write(pcm.getStream()); // Write audio data
			dataOut.flush(); // Flush the buffer to the SD
			
			dataOut.close();
			buffOut.close();
			outS.close();
			
		}
		catch(Exception e)
		{
			Log.e("Phat Lab", "Failed to export PCM", e);
			return false;
		}
		return true;
		//return savePCM16Bit(pcm.getStream(),filename,0,pcm.getStream().length);
	}
	
	/**
	 * Opens a file for writing. If one is already open, it is closed first;
	 * @param filename	Name of the DOCUMENT file to open
	 * @return	whether or not there was an error
	 */
	public boolean opendocw(String filename)
	{
		wasError = false;
		try
		{
			closedocw();
			File dir = new File(Environment.getExternalStorageDirectory()+File.separator+"PhatLab");
			if (!dir.exists())
				dir.mkdir();
			openOutFile = new FileOutputStream(new File(Environment.getExternalStorageDirectory()+File.separator+"PhatLab/"+filename+".ini"));
		}
		catch (Exception e)
		{
			ex=e;
			wasError=true;
			Log.e("Phat Lab","Exception:",e);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Closes the current document, if there is one, that is being written to.
	 */
	public void closedocw()
	{
		wasError = false;
		try
		{
			if (openOutFile != null)
				openOutFile.close();
			openOutFile = null;
		}
		catch (Exception e)
		{
			ex=e;
			wasError=true;
			Log.e("Phat Lab","Exception:",e);
		}
	}
	
	/**
	 * Opens a file for reading. If one is already open, it is closed first;
	 * @param filename	Name of the DOCUMENT file to open
	 * @return	whether or not there was an error
	 */
	public boolean opendocr(String filename)
	{
		wasError = false;
		try
		{
			closedocr();
			openInFile = new FileInputStream(new File(Environment.getExternalStorageDirectory()+File.separator+"PhatLab/"+filename+".ini"));
		}
		catch (Exception e)
		{
			ex=e;
			wasError=true;
			Log.e("Phat Lab","Exception:",e);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Closes the current document, if there is one, that is being read from.
	 */
	public void closedocr()
	{
		wasError = false;
		try
		{
			if (openInFile != null)
				openInFile.close();
			openInFile = null;
		}
		catch (Exception e)
		{
			ex=e;
			wasError=true;
			Log.e("Phat Lab","Exception:",e);
		}
	}
	
	/**
	 * @param filename	Name of the config file to create / save into
	 * @param key	The name of the key to use
	 * @param value	The string to store
	 * @return	Whether there was an error
	 * 
	 */
	public boolean writeKey(String key, String value)
	{
		try
		{
			wasError=false;
			Properties file = new Properties();
			
			//Will need to actually write into the key:
			file.setProperty(key,value);
			file.store(openOutFile, null);
			
		}
		
		catch (Exception e)
		{
			ex=e;
			wasError=true;
			Log.e("Phat Lab","Exception:",e);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Retrieves the value of the specified key in a given file 
	 * @param filename	Name of the file to open
	 * @param key	Key to grab the value of
	 * @return	The string, or null if an error
	 */
	public String readKey(String key)
	{
		String returnMe="";
		try
		{
			wasError=false;
			Properties file = new Properties();
			file.load(openInFile);
			
			returnMe = file.getProperty(key);
		}
		catch (Exception e)
		{
			ex=e;
			wasError=true;
			Log.e("Phat Lab","Exception:",e);
			return null;
		}
		return returnMe;
	}
	
}