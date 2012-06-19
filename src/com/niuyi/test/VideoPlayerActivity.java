package com.niuyi.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoPlayerActivity extends Activity{
	
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.video_player);
		VideoView videoView = (VideoView)findViewById(R.id.videoView1);
		String path = getIntent().getExtras().getString("VIDEO_URL");
		videoView.setVideoPath(path);
		videoView.setMediaController(new MediaController(this));
		videoView.requestFocus();
		videoView.start();
	}
	
}
