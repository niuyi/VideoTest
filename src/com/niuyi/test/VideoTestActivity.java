package com.niuyi.test;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class VideoTestActivity extends Activity {
	private ListView listView;
	private File parentFile;
	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			File file = (File) view.getTag();
			if (file.isFile()) {
				startVideoView(file);
			} else {
				showFolder(file);
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setupListView(".");
	}

	private void setupListView(String currentDir) {
		listView = (ListView) findViewById(R.id.listView1);
		setTitle(currentDir);
		listView.setAdapter(new FileListAdapter(this, new File(currentDir)));
		listView.setOnItemClickListener(onItemClickListener);
	}

	private void startVideoView(File file) {
		Intent intent = new Intent(VideoTestActivity.this,
										VideoPlayerActivity.class);
		intent.putExtra("VIDEO_URL", file.getAbsolutePath());
		VideoTestActivity.this.startActivity(intent);
	}
	
	private void showFolder(File file) {
		parentFile = file.getParentFile();
		listView.setAdapter(new FileListAdapter(
									VideoTestActivity.this, file));
		setTitle(file.getPath());
		listView.invalidate();
	}  
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent keyEvent){
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(this.parentFile == null){
				showExitConfirmDialog();
			}else{
				showFolder(parentFile);
			}
			return true;
		}
		
		return super.onKeyDown(keyCode, keyEvent);
	}

	private void showExitConfirmDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.exit);
		builder.setMessage(R.string.sure_to_exit);
		
		builder.setNegativeButton(R.string.cancel, new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {}
		});

		builder.setPositiveButton(R.string.ok, new OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		
		builder.show();
	}
}