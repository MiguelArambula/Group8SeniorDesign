package edu.cosc4950.phatlab;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FileFinder extends ListActivity {
	
	private List<String> item = null;
	private List<String> path = null;
	private String root;
	private TextView myPath;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_console);
		myPath=(TextView)findViewById(R.id.path);
		root=Environment.getExternalStorageDirectory().getPath();
		getDir(root);
	}
	
	private void getDir(String dpath){
		myPath.setText("Location: "+dpath);
		item = new ArrayList<String>();
		path = new ArrayList<String>();
		File f = new File(dpath);
		File[] files = f.listFiles();
		
		if(!dpath.equals(root)){
			item.add(root);
			path.add(root);
			item.add("../");
			path.add(f.getParent());
		}
		
		for(int i=0; i<files.length; i++){
			File file = files[i];
			
			if(!file.isHidden() && file.canRead()){
				if(file.isDirectory()){
					item.add(file.getName()+"/");
				}else{
					item.add(file.getName());
				}
			}
		}
		ArrayAdapter<String> fileList = 
				new ArrayAdapter<String>(this,R.layout.text_row,item);
		setListAdapter(fileList);
	}
	
	protected void onListItemClick(ListView l, View v, int p, long id){
		File file = new File(path.get(p));
		
		if(file.isDirectory()){
			if(file.canRead()){
				getDir(path.get(p));
			}else{
				new AlertDialog.Builder(this)
				.setIcon(R.drawable.ic_launcher)
				.setTitle("["+file.getName()+"] folder can't be read")
				.setPositiveButton("Ok",null).show();
			}
		}
	}
}
