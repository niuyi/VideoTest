package com.niuyi.test;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class VideoTestActivity extends Activity {
	private ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setupListView();
	}

	private void setupListView() {
		listView = (ListView) findViewById(R.id.listView1);
		setTitle(".");
		listView.setAdapter(new FileListAdapter(this, new File(".")));

		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				File file = (File) view.getTag();
				if (file.isFile()) {
					startVideoView(file);
				} else {
					listView.setAdapter(new FileListAdapter(
												VideoTestActivity.this, file));
					setTitle(file.getPath());
					listView.invalidate();
				}
			}
		});
	}

	private void startVideoView(File file) {
		Intent intent = new Intent(VideoTestActivity.this,
										VideoPlayerActivity.class);
		intent.putExtra("VIDEO_URL", file.getAbsolutePath());
		VideoTestActivity.this.startActivity(intent);
	}
}