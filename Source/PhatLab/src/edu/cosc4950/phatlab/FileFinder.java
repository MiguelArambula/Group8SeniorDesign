package edu.cosc4950.phatlab;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FileFinder extends ListActivity {
	
	private List<String> item = null;
	private List<String> path = null;
	private String root;
	private TextView myPath;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
	}

}
