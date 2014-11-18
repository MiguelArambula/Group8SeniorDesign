/**
 * Author: Reuben Shea
 * Date: Oct 31, 2014
 * 
 * Description:
 * This class is used to load and save data that is "outside the app."
 * This would involve things like profiles and samples
 * 
 * Requires:
 * 	Manifest entry: <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
	 								 android:maxSdkVersion="18"/>
 */

package com.example.phatlabs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
	 * Checks whether or not the file system is writable or not.
	 * @return
	 */
	private boolean isWritable()
	{
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state))
			return true;
		
		return false;
	}
	
	private boolean isReadable()
	{
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state) || 
			Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
			return true;
		
		return false;
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
			File dir = new File(Environment.getExternalStorageDirectory()+File.separator+"PhatLabs");
			if (!dir.exists())
				dir.mkdir();
			openOutFile = new FileOutputStream(new File(Environment.getExternalStorageDirectory()+File.separator+"PhatLabs/"+filename+".ini"));
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
			openInFile = new FileInputStream(new File(Environment.getExternalStorageDirectory()+File.separator+"PhatLabs/"+filename+".ini"));
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
