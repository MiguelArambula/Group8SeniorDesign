package edu.cosc4950.phatlab;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class FileList extends ListActivity{
	private String path;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		View myView = inflater.inflate(R.layout.file_list, container, false);
		
		path = "/";
		if(getIntent().hasExtra("path")){
			path = getIntent().getStringExtra("path");
		}
		setTitle(path);
		
		List values = new ArrayList();
		File dir = new File(path);
		if(!dir.canRead()){
			setTitle(getTitle()+" (inaccesible) ");
		}
		
		String[] list = dir.list();
		if(list != null){
			for(String file : list){
				if(!file.startsWith(".")){
					values.add(file);
				}
			}
		}
		Collections.sort(values);
		
		ArrayAdapter adapter = new ArrayAdapter(this,
				android.R.layout.simple_list_item_2, android.R.id.text1, values);
		setListAdapter(adapter);
		return myView;
	}
	
	
}
