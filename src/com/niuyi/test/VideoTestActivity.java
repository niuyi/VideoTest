package com.niuyi.test;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class VideoTestActivity extends Activity {
    private ListView listView;


	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);
        listView = (ListView)findViewById(R.id.listView1);
        File root = Environment.getRootDirectory();
        setTitle(root.getAbsolutePath());
        File[] files = new File("/").listFiles();
        listView.setAdapter(new FileListAdapter(this, files));
        
        listView.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				File file = (File)view.getTag();
				if(file.isDirectory()){
					listView.setAdapter(new FileListAdapter(VideoTestActivity.this, file.listFiles()));
					listView.invalidate();
				}
			}
        	
        });
    }
    
    
    class FileListAdapter extends BaseAdapter{
    	
    	private final Context context;
		private File[] files = new File[]{};

		public FileListAdapter(Context context, File[] files){
			this.context = context;
			if(files != null){
				this.files = files;
			}
    	}
    	
		public int getCount() {
			// TODO Auto-generated method stub
			return files.length;
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return files[position];
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textView = new TextView(context);
			File file = files[position];
			if(file.isDirectory()){
				textView.setText("/" + file.getName());
			}else{
				textView.setText(file.getName());
			}
			textView.setTag(file);
			return textView;
		}
    }
}