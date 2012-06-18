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
		requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		  WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
		this.setContentView(R.layout.video_player);
		VideoView videoView = (VideoView)findViewById(R.id.videoView1);
		String path = getIntent().getExtras().getString("VIDEO_URL");
		videoView.setVideoPath(path);
		Toast.makeText(this, "begin to play: " + path, 10000);
		videoView.setMediaController(new MediaController(this));
		videoView.requestFocus();
		videoView.start();
	}
	
}
