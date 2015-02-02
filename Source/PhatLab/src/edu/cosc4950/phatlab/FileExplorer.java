package edu.cosc4950.phatlab;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class FileExplorer extends ListActivity{
	private Point p;
	private List<String> item = null;
	private List<String> path = null;
	private String root;
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_list);
		//myPath = (TextView) findViewById(R.id.path);
		
		root = Environment.getExternalStorageDirectory().getPath();
		
		getDir(root);
		final Button close = (Button) findViewById(R.id.close);
		close.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
			
		});
	}
	
	private void getDir(String dirPath){
		//myPath.setText("Location"  + dirPath);
		item = new ArrayList<String>();
		path = new ArrayList<String>();
		File f = new File(dirPath);
		File[] files = f.listFiles();
		
		if(!dirPath.equals(root)){
			item.add(root);
			path.add(root);
			item.add("../");
			path.add(f.getParent());
		}
		
		for(int i=0;i<files.length;i++){
			File temp = files[i];
			
			if(!temp.isHidden() && temp.canRead()){
				path.add(temp.getPath());
				if(temp.isDirectory()){
					item.add(temp.getName()+"/");
				} else {
					item.add(temp.getName());
				}
			}
		}
		
		ArrayAdapter<String> fileList = 
				new ArrayAdapter<String>(this, R.layout.row, item);
		setListAdapter(fileList);
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id){
		File file = new File(path.get(position));
		
		if(file.isDirectory()){
			if(file.canRead()){
			getDir(path.get(position));
			} else {
				new AlertDialog.Builder(this)
				.setTitle("folder can't be read")
				.setPositiveButton("ok",null).show();
			}
		} else {
			new AlertDialog.Builder(this)
			.setTitle(file.getName())
			.setPositiveButton("ok",null).show();
		}
	}
	
	public List<String> getItems(){
		return item;
	}
}
